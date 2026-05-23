package com.qiushui1012.mod.voidinair.inventory;

import com.qiushui1012.mod.voidinair.block.entity.AutoCrafterBlockEntity;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import dev.dubhe.anvilcraft.api.itemhandler.FilteredItemStackHandler;
import dev.dubhe.anvilcraft.api.itemhandler.SlotItemHandlerWithFilter;
import dev.dubhe.anvilcraft.block.entity.IFilterBlockEntity;
import dev.dubhe.anvilcraft.inventory.BaseMachineMenu;
import dev.dubhe.anvilcraft.inventory.IFilterMenu;
import dev.dubhe.anvilcraft.inventory.component.ReadOnlySlot;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AutoCrafterMenu extends BaseMachineMenu implements IFilterMenu, ContainerListener {
    private static final int PLAYER_SLOT_COUNT = 36;
    private static final int MACHINE_SLOT_START = 36;
    private static final int MACHINE_SLOT_END = 45;

    @Getter
    private final AutoCrafterBlockEntity blockEntity;
    private final Slot resultSlot;
    private final Level level;
    @Getter
    private List<RecipeHolder<CraftingRecipe>> recipes = List.of();

    public AutoCrafterMenu(
        @Nullable MenuType<?> menuType,
        int containerId,
        Inventory inventory,
        FriendlyByteBuf extraData
    ) {
        this(
            menuType,
            containerId,
            inventory,
            Objects.requireNonNull(inventory.player.level().getBlockEntity(extraData.readBlockPos()))
        );
    }

    public AutoCrafterMenu(
        @Nullable MenuType<?> menuType,
        int containerId,
        Inventory inventory,
        BlockEntity blockEntity
    ) {
        super(menuType, containerId, blockEntity);
        this.blockEntity = (AutoCrafterBlockEntity) blockEntity;
        this.level = inventory.player.level();

        this.addPlayerInventory(inventory);
        this.addPlayerHotbar(inventory);
        FilteredItemStackHandler handler = this.blockEntity.getFilteredItemStackHandler();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                int slot = row * 3 + column;
                this.addSlot(new SlotItemHandlerWithFilter(handler, handler::set, slot, 26 + column * 18, 18 + row * 18));
            }
        }
        this.resultSlot = this.addSlot(new ReadOnlySlot(new SimpleContainer(1), 0, 134, 54));
        this.refreshResult();
        this.addSlotListener(this);
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(inventory, column, 8 + column * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        final ItemStack original = sourceStack.copy();
        if (index < PLAYER_SLOT_COUNT) {
            if (!this.moveItemToActiveSlot(sourceStack)) return ItemStack.EMPTY;
        } else if (index < MACHINE_SLOT_END) {
            if (!this.moveItemStackTo(sourceStack, 0, PLAYER_SLOT_COUNT, false)) return ItemStack.EMPTY;
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return original;
    }

    private boolean moveItemToActiveSlot(ItemStack stack) {
        int originalCount = stack.getCount();
        for (int index = MACHINE_SLOT_START; index < MACHINE_SLOT_END && !stack.isEmpty(); index++) {
            if (!this.canPlace(stack, index)) continue;
            this.moveItemStackTo(stack, index, index + 1, false);
        }
        return stack.getCount() < originalCount;
    }

    private boolean canPlace(ItemStack stack, int index) {
        if (!(this.getSlot(index) instanceof SlotItemHandlerWithFilter filterSlot)) return false;
        int machineSlot = index - MACHINE_SLOT_START;
        if (filterSlot.isSlotDisabled(machineSlot)) return false;
        ItemStack filter = filterSlot.getFilterItem(machineSlot);
        return filter.isEmpty() || filter.is(stack.getItem());
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(
            ContainerLevelAccess.create(this.level, this.blockEntity.getBlockPos()),
            player,
            ViaBlocks.AUTO_CRAFTER.get()
        );
    }

    @Override
    public IFilterBlockEntity getFilterBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public int getFilterSlotIndex(Slot slot) {
        return slot.index - MACHINE_SLOT_START;
    }

    @Override
    public void setItem(int slotId, int stateId, ItemStack stack) {
        super.setItem(slotId, stateId, stack);
        this.refreshResult();
    }

    public void refreshResult() {
        this.recipes = this.blockEntity.getRecipes();
        if (this.recipes.isEmpty()) {
            this.resultSlot.set(ItemStack.EMPTY);
            return;
        }
        if (this.blockEntity.getSelecting() < 0 || this.blockEntity.getSelecting() >= this.recipes.size()) {
            this.blockEntity.setSelecting(0);
        }
        this.resultSlot.set(
            this.recipes.get(this.blockEntity.getSelecting())
                .value()
                .assemble(this.blockEntity.getDummyCraftingContainer().asCraftInput())
        );
    }

    @Override
    public void slotChanged(AbstractContainerMenu container, int dataSlotIndex, ItemStack stack) {
        this.refreshResult();
    }

    @Override
    public void dataChanged(AbstractContainerMenu container, int dataSlotIndex, int value) {
    }

    @Override
    public void flush() {
        this.refreshResult();
    }
}
