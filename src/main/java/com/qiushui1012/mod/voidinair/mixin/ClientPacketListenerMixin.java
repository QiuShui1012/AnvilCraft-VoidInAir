package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {
    @Shadow
    private ClientLevel level;

    ClientPacketListenerMixin(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(
        method = "handleEntityEvent",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/protocol/game/ClientboundEntityEventPacket;getEventId()B"
        )
    )
    public void handleEntityEvent(ClientboundEntityEventPacket packet, CallbackInfo ci, @Local(name = "entity") Entity entity) {
        if (packet.getEventId() == 38) {
            this.minecraft.particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
            this.level.playLocalSound(
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.TOTEM_USE,
                entity.getSoundSource(),
                1.0F,
                1.0F,
                false
            );
            if (entity == this.minecraft.player) {
                this.minecraft.gameRenderer.displayItemActivation(ViaItems.TOTEM_OF_VOID.asStack());
            }
        }
    }
}
