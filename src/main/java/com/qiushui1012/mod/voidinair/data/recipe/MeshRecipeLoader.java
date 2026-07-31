package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.MeshRecipe;
import net.minecraft.world.item.Items;

public class MeshRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        MeshRecipe.builder()
            .requires(ViaBlocks.DEEPSLATE_CHIPS)
            .result(ViaBlocks.DEEPSLATE_CHIPS, 0.5F)
            .result(Items.REDSTONE, 0.08F)
            .result(Items.LAPIS_LAZULI, 0.08F)
            .result(Items.GOLD_NUGGET, 0.05F)
            .result(Items.IRON_NUGGET, 0.05F)
            .result(Items.COAL, 0.02F)
            .result(Items.DIAMOND, 0.01F)
            .result(Items.EMERALD, 0.01F)
            .result(ModItems.EARTH_CORE_SHARD, 0.01F)
            .result(ModItems.VOID_MATTER, 0.01F)
            .save(provider, AncVoidInAir.of("mesh/deepslate_chips"));

        MeshRecipe.builder()
            .requires(ViaBlocks.BLACK_SAND)
            .result(ViaBlocks.BLACK_SAND, 0.5F)
            .result(Items.COAL, 0.1F)
            .result(ModItems.TUNGSTEN_NUGGET, 0.05F)
            .save(provider, AncVoidInAir.of("mesh/black_sand"));
    }
}
