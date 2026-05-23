package com.qiushui1012.mod.eyesinair.init.block;

import com.qiushui1012.mod.eyesinair.block.production.VoidFountainBlock;
import com.qiushui1012.mod.eyesinair.init.VIAItemGroup;
import dev.anvilcraft.lib.v2.registrum.providers.DataGenContext;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumBlockModelGenerator;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.anvilcraft.lib.v2.util.nullness.NonNullBiConsumer;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import net.minecraft.world.level.block.Block;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.eyesinair.AncVoidInAir.REGISTRUM;

@SuppressWarnings("Convert2Lambda")
public class VIABlocks {
    static {
        REGISTRUM.defaultCreativeTab(VIAItemGroup.INSTANCE.getKey());
    }

    public static final BlockEntry<VoidFountainBlock> VOID_FOUNTAIN = REGISTRUM
        .block("void_fountain", VoidFountainBlock::new)
        .initialProperties(ModBlocks.MINERAL_FOUNTAIN)
        .blockstate(() -> new NonNullBiConsumer<>() {
            @Override
            public void accept(
                DataGenContext<Block, VoidFountainBlock> ctx,
                RegistrumBlockModelGenerator generator
            ) {

            }
        })
        .simpleItem()
        .register();
}
