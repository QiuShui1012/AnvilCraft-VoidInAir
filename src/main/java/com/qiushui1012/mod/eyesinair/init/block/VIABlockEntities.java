package com.qiushui1012.mod.eyesinair.init.block;

import com.qiushui1012.mod.eyesinair.block.entity.VoidFountainBlockEntity;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntityEntry;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.eyesinair.AncVoidInAir.REGISTRUM;

public class VIABlockEntities {
    public static final BlockEntityEntry<VoidFountainBlockEntity> VOID_FOUNTAIN = REGISTRUM
        .blockEntity("void_fountain", VoidFountainBlockEntity::new)
        .validBlock(VIABlocks.VOID_FOUNTAIN)
        .register();
}
