package com.qiushui1012.mod.voidinair.api.skull;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;

public record SimpleSkullBlockType(ResourceLocation id) implements SkullBlock.Type {
    public static final SimpleSkullBlockType VOID = new SimpleSkullBlockType(AncVoidInAir.of("void"));

    public SimpleSkullBlockType {
        SkullBlock.Type.TYPES.put(id.getPath(), this);
    }

    @Override
    public String getSerializedName() {
        return this.id.getPath();
    }
}
