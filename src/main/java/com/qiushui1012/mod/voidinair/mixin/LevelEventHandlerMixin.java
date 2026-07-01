package com.qiushui1012.mod.voidinair.mixin;

import com.qiushui1012.mod.voidinair.init.ViaSoundEvents;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.LevelEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelEventHandler.class)
public class LevelEventHandlerMixin {
    @Shadow
    @Final
    private ClientLevel level;

    @Inject(method = "levelEvent", at = @At("HEAD"), cancellable = true)
    public void levelEvent(int eventType, BlockPos pos, int data, CallbackInfo ci) {
        if (this.level.getBlockState(pos).is(ViaBlocks.BLACK_CAT)) {
            switch (eventType) {
                case LevelEvent.SOUND_ANVIL_BROKEN -> {
                    this.level.playLocalSound(
                        pos,
                        ViaSoundEvents.BLACK_CAT_BREAK.get(),
                        SoundSource.BLOCKS,
                        1f,
                        1f,
                        false
                    );
                    ci.cancel();
                }
                case LevelEvent.SOUND_ANVIL_LAND -> {
                    this.level.playLocalSound(
                        pos,
                        ViaSoundEvents.BLACK_CAT_LAND.get(),
                        SoundSource.BLOCKS,
                        1f,
                        1f,
                        false
                    );
                    ci.cancel();
                }
                case LevelEvent.SOUND_ANVIL_USED -> {
                    this.level.playLocalSound(
                        pos,
                        ViaSoundEvents.BLACK_CAT_USE.get(),
                        SoundSource.BLOCKS,
                        1f,
                        1f,
                        false
                    );
                    ci.cancel();
                }
                default -> {}
            }
        }
    }
}
