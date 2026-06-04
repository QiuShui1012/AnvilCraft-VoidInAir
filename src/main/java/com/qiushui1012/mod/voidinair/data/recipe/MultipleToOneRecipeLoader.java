package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.multiple.EightToOneSmithingRecipe;

public class MultipleToOneRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        EightToOneSmithingRecipe.builder()
            .material(ModBlocks.TRANSCENDIUM_BLOCK)
            .input(ViaItems.VOID_AMULET)
            .input(ViaItems.SNOWFLAKE_AMULET)
            .input(ViaItems.BEEHIVE_AMULET)
            .input(ViaItems.GOLD_AMULET)
            .input(ViaItems.DOLPHIN_AMULET)
            .input(ModItems.ANVIL_AMULET)
            .input(ModItems.GEM_AMULET)
            .input(ModItems.NATURE_AMULET)
            .result(ViaItems.TRANSCENDED_AMULET)
            .save(provider, ViaItems.TRANSCENDED_AMULET.getId().withPrefix("eight_to_one_smithing/"));
    }
}
