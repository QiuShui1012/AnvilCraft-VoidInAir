package com.qiushui1012.mod.voidinair.client.gui.screen;

import com.qiushui1012.mod.voidinair.inventory.AutoCrafterMenu;
import com.qiushui1012.mod.voidinair.network.AutoCrafterSelectPacket;
import dev.dubhe.anvilcraft.api.itemhandler.SlotItemHandlerWithFilter;
import dev.dubhe.anvilcraft.client.gui.component.EnableFilterButton;
import dev.dubhe.anvilcraft.client.gui.component.RecipeCycleButton;
import dev.dubhe.anvilcraft.client.gui.screen.BaseMachineScreen;
import dev.dubhe.anvilcraft.client.gui.screen.IFilterScreen;
import dev.dubhe.anvilcraft.constant.SharedTextures;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.item.FilterItem;
import dev.dubhe.anvilcraft.network.SlotDisableChangePacket;
import dev.dubhe.anvilcraft.network.SlotFilterChangePacket;
import dev.dubhe.anvilcraft.network.SlotFilterMaxStackSizeChangePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class AutoCrafterScreen extends BaseMachineScreen<AutoCrafterMenu> implements IFilterScreen<AutoCrafterMenu> {
    private static final ResourceLocation BACKGROUND = SharedTextures.bg("machine", "batch_crafter");

    private final BiFunction<Integer, Integer, EnableFilterButton> enableFilterButtonSupplier =
        this.getEnableFilterButtonSupplier(116, 18);
    private final AutoCrafterMenu menu;
    private @Nullable EnableFilterButton enableFilterButton;
    private @Nullable RecipeCycleButton recipeCycleButton;

    public AutoCrafterScreen(AutoCrafterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();
        this.recipeCycleButton = new RecipeCycleButton(this.leftPos + 151, this.topPos + 17, ignored -> {
            int recipeCount = this.menu.getRecipes().size();
            if (recipeCount <= 1) return;
            int selecting = (this.menu.getBlockEntity().getSelecting() + 1) % recipeCount;
            this.menu.getBlockEntity().setSelecting(selecting);
            this.menu.refreshResult();
            PacketDistributor.sendToServer(
                new AutoCrafterSelectPacket(selecting, this.menu.getBlockEntity().getPos())
            );
        });
        this.addRenderableWidget(this.recipeCycleButton);
        this.enableFilterButton = this.enableFilterButtonSupplier.apply(this.leftPos, this.topPos);
        this.addRenderableWidget(this.enableFilterButton);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (this.recipeCycleButton != null) this.recipeCycleButton.active = this.menu.getRecipes().size() > 1;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, 176, 166, 256, 256);
    }

    @Override
    public void renderSlot(GuiGraphics graphics, Slot slot) {
        super.renderSlot(graphics, slot);
        IFilterScreen.super.renderSlot(graphics, slot);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int x, int y) {
        super.renderTooltip(graphics, x, y);
        if (!(this.hoveredSlot instanceof SlotItemHandlerWithFilter filterSlot)) return;
        if (!filterSlot.isFilter() || !this.isFilterEnabled()) return;
        if (!this.isSlotDisabled(filterSlot.getContainerSlot())) return;
        graphics.renderTooltip(
            this.font,
            Component.translatable("screen.anvilcraft.slot.disable.tooltip"),
            x,
            y
        );
    }

    @Override
    protected List<Component> getTooltipFromContainerItem(ItemStack stack) {
        List<Component> components = super.getTooltipFromContainerItem(stack);
        if (this.hoveredSlot instanceof SlotItemHandlerWithFilter filterSlot
            && filterSlot.isFilter()
            && !filterSlot.getItem().isEmpty()) {
            components.add(SCROLL_WHEEL_TO_CHANGE_STACK_LIMIT_TOOLTIP);
            components.add(SHIFT_TO_SCROLL_FASTER_TOOLTIP);
        }
        return components;
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        if (slot instanceof SlotItemHandlerWithFilter && slot.getItem().isEmpty()) {
            ItemStack carriedItem = this.menu.getCarried().copy();
            int realSlotId = slot.getContainerSlot();
            if (!carriedItem.isEmpty() && this.menu.isFilterEnabled()) {
                if (this.menu.isSlotDisabled(realSlotId)) {
                    PacketDistributor.sendToServer(new SlotDisableChangePacket(realSlotId, false));
                    this.menu.setSlotDisabled(realSlotId, false);
                }
                ItemStack filter = this.menu.getFilter(realSlotId);
                PacketDistributor.sendToServer(new SlotFilterChangePacket(realSlotId, carriedItem));
                this.menu.setFilter(realSlotId, carriedItem);
                if (carriedItem.is(ModItems.FILTER) && (filter.isEmpty() || !FilterItem.filter(filter, carriedItem))) {
                    return;
                }
            } else if (Screen.hasShiftDown()) {
                boolean disable = carriedItem.isEmpty() && !this.menu.isSlotDisabled(realSlotId);
                PacketDistributor.sendToServer(new SlotDisableChangePacket(realSlotId, disable));
            }
        }
        super.slotClicked(slot, slotId, mouseButton, type);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.hoveredSlot instanceof SlotItemHandlerWithFilter filterSlot
            && filterSlot.isFilter()
            && scrollY != 0) {
            int slotIndex = filterSlot.getContainerSlot();
            int currentLimit = this.getSlotLimit(slotIndex);
            int scrollSpeed = Screen.hasShiftDown() ? 5 : 1;
            int newLimit = Mth.clamp(currentLimit + (scrollY > 0 ? scrollSpeed : -scrollSpeed), 1, 64);
            if (newLimit != currentLimit) {
                this.setSlotLimit(slotIndex, newLimit);
                PacketDistributor.sendToServer(new SlotFilterMaxStackSizeChangePacket(slotIndex, newLimit));
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public AutoCrafterMenu getFilterMenu() {
        return this.menu;
    }

    @Override
    public void flush() {
        if (this.enableFilterButton != null) this.enableFilterButton.flush();
    }

    @Override
    public int getOffsetX() {
        return this.leftPos = (this.width - this.imageWidth) / 2;
    }

    @Override
    public int getOffsetY() {
        return this.topPos = (this.height - this.imageHeight) / 2;
    }
}
