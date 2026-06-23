package com.qiushui1012.mod.voidinair.mixin;

import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MapItemSavedData.class)
public class MapItemSavedDataMixin {
    @Inject(method = "hasMapInvisibilityItemEquipped", at = @At("HEAD"), cancellable = true)
    private static void hasPumpkinAmulet(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.PUMPKIN)) {
            cir.setReturnValue(true);
        }
    }
}
