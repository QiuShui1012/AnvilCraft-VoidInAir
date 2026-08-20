package com.qiushui1012.mod.voidinair.data.provider;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.ViaSoundEvents;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ViaSoundProvider extends SoundDefinitionsProvider {
    public ViaSoundProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, AncVoidInAir.MOD_ID, fileHelper);
    }

    private static final int BLACK_CAT_SOUND_MEOW_WEIGHT = 33 * 4;
    private static final int BLACK_CAT_SOUND_PURREOW_WEIGHT = 33 * 2;
    private static final int BLACK_CAT_SOUND_IDLE_WEIGHT = 33 * 4;
    private static final int BLACK_CAT_SOUND_HISS_WEIGHT = 3;
    private static final double BLACK_CAT_SOUND_MEOW_VOLUME = 0.7;
    private static final double BLACK_CAT_SOUND_PURREOW_VOLUME = 0.5;
    private static final double BLACK_CAT_SOUND_IDLE_VOLUME = 0.35;
    private static final double BLACK_CAT_SOUND_HISS_VOLUME = 0.25;

    @Override
    public void registerSounds() {
        this.add(
            ViaSoundEvents.BLACK_CAT_BASE.getId(),
            SoundDefinitionsProvider.definition()
                .with(
                    SoundDefinitionsProvider.sound("mob/cat/meow1")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/meow2")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/meow3")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/meow4")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_MEOW_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/purreow1")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_PURREOW_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_PURREOW_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/purreow2")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_PURREOW_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_PURREOW_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/stray/idle1")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/stray/idle2")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/stray/idle3")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/stray/idle4")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_IDLE_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/hiss1")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_HISS_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_HISS_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/hiss2")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_HISS_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_HISS_WEIGHT),
                    SoundDefinitionsProvider.sound("mob/cat/hiss3")
                        .volume(ViaSoundProvider.BLACK_CAT_SOUND_HISS_VOLUME)
                        .weight(ViaSoundProvider.BLACK_CAT_SOUND_HISS_WEIGHT)
                )
        );
        this.add(
            ViaSoundEvents.BLACK_CAT_BREAK.getId(),
            SoundDefinitionsProvider.definition()
                .subtitle("subtitles.block.generic.break")
                .with(SoundDefinitionsProvider.sound(ViaSoundEvents.BLACK_CAT_BASE.getId(), SoundDefinition.SoundType.EVENT))
        );
        this.add(
            ViaSoundEvents.BLACK_CAT_STEP.getId(),
            SoundDefinitionsProvider.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(SoundDefinitionsProvider.sound(ViaSoundEvents.BLACK_CAT_BASE.getId(), SoundDefinition.SoundType.EVENT))
        );
        this.add(
            ViaSoundEvents.BLACK_CAT_PLACE.getId(),
            SoundDefinitionsProvider.definition()
                .subtitle("subtitles.block.generic.place")
                .with(SoundDefinitionsProvider.sound(ViaSoundEvents.BLACK_CAT_BASE.getId(), SoundDefinition.SoundType.EVENT))
        );
        this.add(
            ViaSoundEvents.BLACK_CAT_HIT.getId(),
            SoundDefinitionsProvider.definition()
                .subtitle("subtitles.block.generic.hit")
                .with(SoundDefinitionsProvider.sound(ViaSoundEvents.BLACK_CAT_BASE.getId(), SoundDefinition.SoundType.EVENT))
        );
        this.add(
            ViaSoundEvents.BLACK_CAT_FALL.getId(),
            SoundDefinitionsProvider.definition()
                .subtitle("subtitles.block.generic.fall")
                .with(SoundDefinitionsProvider.sound(ViaSoundEvents.BLACK_CAT_BASE.getId(), SoundDefinition.SoundType.EVENT))
        );
        this.add(
            ViaSoundEvents.BLACK_CAT_LAND.getId(),
            SoundDefinitionsProvider.definition()
                .subtitle("subtitles.block.anvil.land")
                .with(SoundDefinitionsProvider.sound(ViaSoundEvents.BLACK_CAT_BASE.getId(), SoundDefinition.SoundType.EVENT))
        );
        this.add(
            ViaSoundEvents.BLACK_CAT_USE.getId(),
            SoundDefinitionsProvider.definition()
                .subtitle("subtitles.block.anvil.use")
                .with(SoundDefinitionsProvider.sound(ViaSoundEvents.BLACK_CAT_BASE.getId(), SoundDefinition.SoundType.EVENT))
        );
    }
}
