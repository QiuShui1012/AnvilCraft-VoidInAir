package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import dev.anvilcraft.lib.v2.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.feline.CatSoundVariants;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.NoteBlockEvent;

@EventBusSubscriber
public class NoteBlockPlayEventListener {
    @SubscribeEvent
    public static void on(NoteBlockEvent.Play event) {
        Level level = Util.cast(event.getLevel());
        BlockPos pos = event.getPos();
        if (
            !level.getBlockState(pos.above()).is(ViaBlocks.BLACK_CAT_HEAD)
            && !level.getBlockState(pos.above()).is(ViaBlocks.BLACK_CAT_WALL_HEAD)
        ) {
            return;
        }
        level.playSeededSound(
            null,
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            SoundEvents.CAT_SOUNDS.get(CatSoundVariants.SoundSet.ROYAL).adultSounds().ambientSound(),
            SoundSource.RECORDS,
            3.0F,
            1.0F,
            level.getRandom().nextLong()
        );
        event.setCanceled(true);
    }
}
