package com.qiushui1012.mod.voidinair.inventory;

import com.qiushui1012.mod.voidinair.block.workstation.BlackCatBlock;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class BlackCatMenu extends AnvilMenu {
    public BlackCatMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        super.onTake(player, stack);
        this.access.execute((level, pos) -> {
            if (level.random.nextDouble() < 0.01) {
                BlackCatBlock.damage(level, pos);
            }
        });
    }
}
