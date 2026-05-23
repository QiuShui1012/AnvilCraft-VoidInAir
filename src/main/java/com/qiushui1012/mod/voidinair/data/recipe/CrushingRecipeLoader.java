package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCrushRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemCrushRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CrushingRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        CrushingRecipeLoader.crush(
            provider,
            Blocks.DEEPSLATE,
            ViaBlocks.DEEPSLATE_CHIPS.get(),
            "deepslate_chips_from_deepslate"
        );
        CrushingRecipeLoader.crush(
            provider,
            Blocks.BLACKSTONE,
            ViaBlocks.BLACK_SAND.get(),
            "black_sand_from_blackstone"
        );
    }

    private static void crush(RegistrumRecipeProvider provider, Block input, Block result, String name) {
        BlockCrushRecipe.builder()
            .input(input)
            .result(result)
            .save(provider, AncVoidInAir.of("block_crush/" + name));
        ItemCrushRecipe.builder()
            .requires(input)
            .result(result, 0.8F)
            .save(provider, AncVoidInAir.of("item_crush/block_crush/" + name));
    }
}
