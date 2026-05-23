package com.qiushui1012.mod.voidinair.init.block;

import com.qiushui1012.mod.voidinair.block.entity.AutoCrafterBlockEntity;
import com.qiushui1012.mod.voidinair.block.entity.VoidFountainBlockEntity;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntityEntry;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

public class ViaBlockEntities {
    public static final BlockEntityEntry<AutoCrafterBlockEntity> AUTO_CRAFTER = REGISTRUM
        .blockEntity("auto_crafter", AutoCrafterBlockEntity::new)
        .validBlock(ViaBlocks.AUTO_CRAFTER)
        .register();

    public static final BlockEntityEntry<VoidFountainBlockEntity> VOID_FOUNTAIN = REGISTRUM
        .blockEntity("void_fountain", VoidFountainBlockEntity::new)
        .validBlock(ViaBlocks.VOID_FOUNTAIN)
        .register();

    public static void init() {
    }
}
