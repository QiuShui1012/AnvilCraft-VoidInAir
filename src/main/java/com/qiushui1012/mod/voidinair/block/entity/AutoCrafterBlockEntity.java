package com.qiushui1012.mod.voidinair.block.entity;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.ViaMenuTypes;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.inventory.AutoCrafterMenu;
import com.qiushui1012.mod.voidinair.network.AutoCrafterSelectPacket;
import dev.dubhe.anvilcraft.api.itemhandler.PollableFilteredItemStackHandler;
import dev.dubhe.anvilcraft.block.batch.BaseBatchCraftingBlock;
import dev.dubhe.anvilcraft.block.entity.batch.BaseBatchCraftingBlockEntity;
import dev.dubhe.anvilcraft.network.UpdateDisplayItemPacket;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class AutoCrafterBlockEntity extends BaseBatchCraftingBlockEntity {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private final Deque<AutoCrafterCache> cache = new ArrayDeque<>();
    @Getter
    private int selecting;

    public AutoCrafterBlockEntity(
        BlockEntityType<? extends BlockEntity> type,
        BlockPos pos,
        BlockState blockState
    ) {
        super(type, pos, blockState, COUNTER.incrementAndGet());
    }

    @Override
    protected PollableFilteredItemStackHandler constructHandler() {
        return new PollableFilteredItemStackHandler(9) {
            @Override
            protected void onContentsChanged(int slot) {
                AutoCrafterBlockEntity.this.onContentsChanged();
            }
        };
    }

    private void onContentsChanged() {
        if (this.level == null || this.level.isClientSide()) {
            this.setChanged();
            return;
        }

        CraftingInput input = this.dummyCraftingContainer.asCraftInput();
        List<RecipeHolder<CraftingRecipe>> recipes = this.getRecipes(input);
        ItemStack displayItem = ItemStack.EMPTY;
        if (recipes.isEmpty()) {
            if (this.selecting != 0) {
                this.selecting = 0;
                PacketDistributor.sendToAllPlayers(new AutoCrafterSelectPacket(this.selecting, this.getPos()));
            }
        } else {
            if (this.selecting < 0 || this.selecting >= recipes.size()) {
                this.selecting = 0;
                PacketDistributor.sendToAllPlayers(new AutoCrafterSelectPacket(this.selecting, this.getPos()));
            }
            displayItem = recipes.get(this.selecting).value().assemble(input, this.level.registryAccess());
        }
        this.updateDisplayItem(displayItem);
        PacketDistributor.sendToAllPlayers(new UpdateDisplayItemPacket(displayItem, this.getPos()));
        this.setChanged();
    }

    @Override
    public void tick(Level level, BlockPos pos) {
        this.flushState(level, pos);
        BlockState state = level.getBlockState(pos);
        level.updateNeighbourForOutputSignal(pos, state.getBlock());
        if (level.isClientSide()) return;

        boolean powered = state.getValue(BaseBatchCraftingBlock.POWERED);
        this.cooldown = Math.max(0, this.cooldown - 1);
        if (!powered && this.cooldown == 0 && this.craft(level)) {
            this.cooldown = this.getCooldownDuration();
        }
        this.poweredBefore = powered;
    }

    @Override
    protected int getCooldownDuration() {
        return AncVoidInAir.CONFIG.autoCrafterCooldown;
    }

    @Override
    public boolean craft(Level level) {
        if (this.craftingContainer.isEmpty() || !this.canCraft()) return false;

        CraftingInput input = this.craftingContainer.asCraftInput();
        Optional<AutoCrafterCache> cached = this.cache.stream()
            .filter(entry -> entry.test(this.craftingContainer))
            .findFirst();
        List<RecipeHolder<CraftingRecipe>> recipes;
        if (cached.isPresent()) {
            recipes = cached.get().recipes();
        } else {
            recipes = this.getRecipes(input);
            this.cache.addFirst(AutoCrafterCache.copyOf(this.craftingContainer, recipes));
            while (this.cache.size() > 10) {
                this.cache.removeLast();
            }
        }
        if (recipes.isEmpty()) return false;
        if (this.selecting < 0 || this.selecting >= recipes.size()) this.setSelecting(0);

        CraftingRecipe craftingRecipe = recipes.get(this.selecting).value();
        ItemStack result = craftingRecipe.assemble(input, level.registryAccess());
        if (result.isEmpty()) return false;
        ItemStack displayItem = result.copy();
        this.updateDisplayItem(displayItem);
        PacketDistributor.sendToAllPlayers(new UpdateDisplayItemPacket(displayItem, this.getPos()));
        if (!result.isItemEnabled(level.enabledFeatures())) return false;

        int times = IntStream.range(0, this.handler.getSlots())
            .mapToObj(this.handler::getStackInSlot)
            .filter(stack -> !stack.isEmpty())
            .mapToInt(ItemStack::getCount)
            .min()
            .orElse(0);
        if (times < 1) return false;

        result.setCount(result.getCount() * times);
        List<ItemStack> remainingItems = new ArrayList<>(craftingRecipe.getRemainingItems(input));
        remainingItems = remainingItems.stream()
            .map(stack -> stack.copyWithCount(stack.getCount() * times))
            .collect(Collectors.toList());
        if (this.ejectItems(result, remainingItems, this.getDirection())) return false;

        for (int i = 0; i < this.handler.getSlots(); i++) {
            ItemStack inSlot = this.handler.getStackInSlot(i);
            if (inSlot.isEmpty()) continue;
            this.handler.extractItem(i, inSlot.getCount(), false);
        }
        this.setChanged();
        level.updateNeighborsAt(this.getBlockPos(), ViaBlocks.AUTO_CRAFTER.get());
        return true;
    }

    public List<RecipeHolder<CraftingRecipe>> getRecipes() {
        return this.getRecipes(this.dummyCraftingContainer.asCraftInput());
    }

    private List<RecipeHolder<CraftingRecipe>> getRecipes(CraftingInput input) {
        if (this.level == null) return List.of();
        return this.level.getRecipeManager().getRecipesFor(RecipeType.CRAFTING, input, this.level);
    }

    public void setSelecting(int selecting) {
        List<RecipeHolder<CraftingRecipe>> recipes = this.getRecipes();
        this.selecting = recipes.isEmpty() ? 0 : Math.floorMod(selecting, recipes.size());
        CraftingInput input = this.dummyCraftingContainer.asCraftInput();
        ItemStack displayItem = recipes.isEmpty()
            ? ItemStack.EMPTY
            : recipes.get(this.selecting).value().assemble(input, Objects.requireNonNull(this.level).registryAccess());
        this.updateDisplayItem(displayItem);
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            PacketDistributor.sendToAllPlayers(new AutoCrafterSelectPacket(this.selecting, this.getPos()));
            PacketDistributor.sendToAllPlayers(new UpdateDisplayItemPacket(displayItem, this.getPos()));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("Selecting", this.selecting);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.selecting = tag.getInt("Selecting");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        if (player.isSpectator()) return null;
        return new AutoCrafterMenu(ViaMenuTypes.AUTO_CRAFTER.get(), containerId, inventory, this);
    }

    private record AutoCrafterCache(
        Container container,
        List<RecipeHolder<CraftingRecipe>> recipes
    ) implements Predicate<Container> {
        private static AutoCrafterCache copyOf(
            Container source,
            List<RecipeHolder<CraftingRecipe>> recipes
        ) {
            return new AutoCrafterCache(copyContainer(source), List.copyOf(recipes));
        }

        private static Container copyContainer(Container source) {
            SimpleContainer copy = new SimpleContainer(source.getContainerSize());
            for (int i = 0; i < source.getContainerSize(); i++) {
                ItemStack stack = source.getItem(i);
                copy.setItem(i, stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1));
            }
            return copy;
        }

        @Override
        public boolean test(Container other) {
            if (other.getContainerSize() != this.container.getContainerSize()) return false;
            for (int i = 0; i < this.container.getContainerSize(); i++) {
                if (!ItemStack.isSameItemSameComponents(this.container.getItem(i), other.getItem(i))) return false;
            }
            return true;
        }
    }

    private final CraftingContainer craftingContainer = new CraftingContainer() {
        @Override
        public int getWidth() {
            return 3;
        }

        @Override
        public int getHeight() {
            return 3;
        }

        @Override
        public List<ItemStack> getItems() {
            return AutoCrafterBlockEntity.this.handler.getStacks();
        }

        @Override
        public int getContainerSize() {
            return AutoCrafterBlockEntity.this.handler.getSlots();
        }

        @Override
        public boolean isEmpty() {
            for (int i = 0; i < this.getContainerSize(); i++) {
                if (!this.getItem(i).isEmpty()) return false;
            }
            return true;
        }

        @Override
        public ItemStack getItem(int slot) {
            return AutoCrafterBlockEntity.this.handler.getStackInSlot(slot);
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            ItemStack stack = AutoCrafterBlockEntity.this.handler.getStackInSlot(slot);
            if (stack.isEmpty()) return ItemStack.EMPTY;
            int extracted = AutoCrafterBlockEntity.this.handler.extractItem(slot, amount, true).getCount();
            if (extracted <= 0) return ItemStack.EMPTY;
            AutoCrafterBlockEntity.this.setChanged();
            return AutoCrafterBlockEntity.this.handler.extractItem(slot, extracted, false);
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            ItemStack stack = this.getItem(slot);
            AutoCrafterBlockEntity.this.handler.setStackInSlot(slot, ItemStack.EMPTY);
            return stack;
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
            AutoCrafterBlockEntity.this.handler.setStackInSlot(slot, stack);
        }

        @Override
        public void setChanged() {
            AutoCrafterBlockEntity.this.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            return true;
        }

        @Override
        public void clearContent() {
            for (int i = 0; i < this.getContainerSize(); i++) {
                this.removeItemNoUpdate(i);
            }
        }

        @Override
        public void fillStackedContents(StackedContents contents) {
            for (int i = 0; i < this.getContainerSize(); i++) {
                contents.accountSimpleStack(this.getItem(i));
            }
        }
    };

    @Getter
    private final CraftingContainer dummyCraftingContainer = new CraftingContainer() {
        @Override
        public int getWidth() {
            return 3;
        }

        @Override
        public int getHeight() {
            return 3;
        }

        @Override
        public List<ItemStack> getItems() {
            List<ItemStack> items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            for (int i = 0; i < items.size(); i++) {
                items.set(i, this.getItem(i));
            }
            return items;
        }

        @Override
        public int getContainerSize() {
            return AutoCrafterBlockEntity.this.handler.getSlots();
        }

        @Override
        public boolean isEmpty() {
            for (int i = 0; i < this.getContainerSize(); i++) {
                if (!this.getItem(i).isEmpty()) return false;
            }
            return true;
        }

        @Override
        public ItemStack getItem(int slot) {
            ItemStack stack = AutoCrafterBlockEntity.this.handler.getStackInSlot(slot);
            return stack.isEmpty() ? AutoCrafterBlockEntity.this.handler.getFilter(slot) : stack;
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            return this.getItem(slot);
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            return this.getItem(slot);
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
        }

        @Override
        public void setChanged() {
            AutoCrafterBlockEntity.this.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            return true;
        }

        @Override
        public void clearContent() {
        }

        @Override
        public void fillStackedContents(StackedContents contents) {
            for (int i = 0; i < this.getContainerSize(); i++) {
                contents.accountSimpleStack(this.getItem(i));
            }
        }
    };
}
