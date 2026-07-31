package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.anvilcraft.lib.v2.util.predicate.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.SolidLiquidRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public class SolidLiquidRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        SolidLiquidRecipeLoader.water(provider, ModItems.WOOD_FIBER, ViaItems.PULP, 250);
    }

    private static void water(RegistrumRecipeProvider provider, ItemLike input, ItemLike result, int consume) {
        SolidLiquidRecipe.builder()
            .cauldron(Blocks.WATER_CAULDRON)
            .requires(input)
            .result(result)
            .consume(consume)
            .save(provider, AncVoidInAir.of("solid_liquid/").withSuffix(provider.safeName(result)));
    }

    private static void water(RegistrumRecipeProvider provider, ItemLike input, ItemLike result) {
        SolidLiquidRecipeLoader.water(provider, input, result, 0);
    }

    @SuppressWarnings("SameParameterValue")
    private static void water(RegistrumRecipeProvider provider, TagKey<Item> input, ItemLike result, int consume) {
        SolidLiquidRecipe.builder()
            .cauldron(Blocks.WATER_CAULDRON)
            .requires(ItemIngredientPredicate.of(provider.getItems(), input).build())
            .result(result)
            .consume(consume)
            .save(provider, AncVoidInAir.of("solid_liquid/").withSuffix(provider.safeName(result)));
    }

    @SuppressWarnings("SameParameterValue")
    private static void water(RegistrumRecipeProvider provider, TagKey<Item> input, ItemLike result) {
        SolidLiquidRecipeLoader.water(provider, input, result, 0);
    }
}
