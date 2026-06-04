package com.qiushui1012.mod.voidinair.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.feline.CatSoundVariant;
import net.minecraft.world.entity.animal.feline.CatSoundVariants;

public class SoundUtil {
    public static CatSoundVariant.CatSoundSet randomCatSet(RandomSource random) {
        return SoundUtil.randomCatSet(random, 0.05F);
    }

    public static CatSoundVariant.CatSoundSet randomCatSet(RandomSource random, float chance) {
        return SoundUtil.randomCatSet(random, chance, chance);
    }

    public static CatSoundVariant.CatSoundSet randomCatSet(RandomSource random, float classicChance, float babyChance) {
        CatSoundVariant variant = SoundEvents.CAT_SOUNDS.get(
            random.nextFloat() < classicChance
            ? CatSoundVariants.SoundSet.ROYAL
            : CatSoundVariants.SoundSet.CLASSIC
        );
        return random.nextFloat() < babyChance
               ? variant.babySounds()
               : variant.adultSounds();
    }
}
