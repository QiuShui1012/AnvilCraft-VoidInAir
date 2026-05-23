package com.qiushui1012.mod.voidinair.block.workstation;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.ViaSoundEvents;
import dev.dubhe.anvilcraft.block.workstation.NeoforgeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.CatSoundVariant;
import net.minecraft.world.entity.animal.feline.CatSoundVariants;
import net.minecraft.world.entity.animal.feline.CatVariants;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class BlackCatBlock extends NeoforgeBlock {
    public static final SoundType SOUND_TYPE = new DeferredSoundType(
        1F,
        1F,
        ViaSoundEvents.BLACK_CAT_BREAK,
        ViaSoundEvents.BLACK_CAT_STEP,
        ViaSoundEvents.BLACK_CAT_PLACE,
        ViaSoundEvents.BLACK_CAT_HIT,
        ViaSoundEvents.BLACK_CAT_FALL
    );

    public BlackCatBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<AnvilBlock> codec() {
        return NeoforgeBlock.simpleCodec(BlackCatBlock::new);
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
    }

    public static void damage(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        CatSoundVariant.CatSoundSet set;
        if (level.getRandom().nextFloat() < 0.05F) {
            set = SoundEvents.CAT_SOUNDS.get(CatSoundVariants.SoundSet.ROYAL).babySounds();
        } else {
            set = SoundEvents.CAT_SOUNDS.get(CatSoundVariants.SoundSet.ROYAL).adultSounds();
        }
        level.playSound(
            null,
            pos,
            set.eatSound().value(),
            SoundSource.BLOCKS,
            1.0F,
            level.getRandom().nextFloat() * 0.1F + 0.9F
        );
        Cat spawned = EntityType.CAT.spawn(
            (ServerLevel) level,
            null,
            null,
            pos,
            EntitySpawnReason.SPAWN_ITEM_USE,
            true,
            false
        );
        if (spawned != null) {
            spawned.setVariant(level.registryAccess().getOrThrow(CatVariants.ALL_BLACK));
            if (level.getRandom().nextFloat() < 0.05F) spawned.setBaby(true);
            level.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
        }
    }
}
