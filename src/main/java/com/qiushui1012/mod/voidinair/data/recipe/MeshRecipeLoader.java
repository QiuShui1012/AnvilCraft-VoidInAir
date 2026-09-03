package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.MeshRecipe;
import net.minecraft.world.item.Items;

public class MeshRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        MeshRecipe.builder()
            .requires(ViaBlocks.DEEPSLATE_CHIPS, 64)
            .result(ViaBlocks.DEEPSLATE_CHIPS, 0.5F)
            .result(Items.REDSTONE, 0.015F)
            .result(Items.LAPIS_LAZULI, 0.08F)
            .result(Items.GOLD_NUGGET, 0.08F)
            .result(Items.IRON_INGOT, 0.010F)
            .result(Items.COAL, 0.005F)
            .result(Items.DIAMOND, 0.005F)
            .result(Items.EMERALD, 0.005F)
            .result(ModItems.EARTH_CORE_SHARD, 0.002F)
            .result(ModItems.VOID_MATTER, 0.002F)
            .save(provider, AncVoidInAir.of("mesh/deepslate_chips"));

        MeshRecipe.builder()
            .requires(ViaBlocks.BLACK_SAND)
            .result(ViaBlocks.BLACK_SAND, 0.5F)
            .result(Items.COAL, 0.1F)
            .result(ModItems.TUNGSTEN_NUGGET, 0.05F)
            .save(provider, AncVoidInAir.of("mesh/black_sand"));
    }
}
