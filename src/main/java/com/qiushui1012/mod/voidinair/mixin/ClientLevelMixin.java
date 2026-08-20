package com.qiushui1012.mod.voidinair.mixin;

import com.qiushui1012.mod.voidinair.init.ViaSoundEvents;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
abstract class ClientLevelMixin extends Level {
    protected ClientLevelMixin(
        WritableLevelData levelData,
        ResourceKey<Level> dimension,
        RegistryAccess registryAccess,
        Holder<DimensionType> dimensionTypeRegistration,
        Supplier<ProfilerFiller> profiler,
        boolean isClientSide,
        boolean isDebug,
        long biomeZoomSeed,
        int maxChainedNeighborUpdates
    ) {
        super(
            levelData,
            dimension,
            registryAccess,
            dimensionTypeRegistration,
            profiler,
            isClientSide,
            isDebug,
            biomeZoomSeed,
            maxChainedNeighborUpdates
        );
    }

    @Inject(method = "levelEvent", at = @At("HEAD"), cancellable = true)
    public void levelEvent(Player player, int type, BlockPos pos, int data, CallbackInfo ci) {
        if (this.getBlockState(pos).is(ViaBlocks.BLACK_CAT)) {
            switch (type) {
                case LevelEvent.SOUND_ANVIL_BROKEN -> {
                    this.playLocalSound(
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
                    this.playLocalSound(
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
                    this.playLocalSound(
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
