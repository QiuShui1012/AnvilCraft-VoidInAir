package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import com.qiushui1012.mod.voidinair.item.property.custom.BlackCatAmuletData;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.multiple.EightToOneSmithingRecipe;
import dev.dubhe.anvilcraft.recipe.multiple.FourToOneSmithingRecipe;

public class MultipleToOneRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        FourToOneSmithingRecipe.builder()
            .material(ModBlocks.TRANSCENDIUM_BLOCK)
            .input(ModItems.ABNORMAL_AMULET)
            .input(ModItems.ANVIL_AMULET)
            .input(ModItems.GEM_AMULET)
            .input(ModItems.NATURE_AMULET)
            .result(ViaItems.ANVILCRAFT_AMULET)
            .save(provider, ViaItems.ANVILCRAFT_AMULET.getId().withPrefix("four_to_one_smithing/"));
        EightToOneSmithingRecipe.builder()
            .material(ModBlocks.TRANSCENDIUM_BLOCK)
            .input(ViaItems.VOID_AMULET)
            .input(ViaItems.SNOWFLAKE_AMULET)
            .input(ViaItems.BEEHIVE_AMULET)
            .input(ViaItems.GOLD_AMULET)
            .input(ViaItems.DOLPHIN_AMULET)
            .input(ViaItems.PUMPKIN_AMULET)
            .input(ViaItems.ANVILCRAFT_AMULET)
            .input(ModItems.COMRADE_AMULET)
            .resultCopy(ViaItems.BLACK_CAT_AMULET, BlackCatAmuletData.INSTANCE)
            .save(provider, ViaItems.BLACK_CAT_AMULET.getId().withPrefix("eight_to_one_smithing/"));
    }
}
