package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.item.ViaItemTags;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.init.item.ModItemTags;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.FastCookingRecipe;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class CookingRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        HolderGetter<Item> items = provider.getItems();
        FastCookingRecipe.builder()
            .cauldron(Blocks.WATER_CAULDRON)
            .requires(items, ViaItemTags.PULP)
            .requires(items, ModItemTags.RESIN)
            .result(Items.SLIME_BALL, 4)
            .save(provider, AncVoidInAir.of("fast_cooking/").withSuffix(provider.safeName(Items.SLIME_BALL)));
    }
}
