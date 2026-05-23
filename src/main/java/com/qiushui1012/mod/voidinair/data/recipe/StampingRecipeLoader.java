package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.item.ViaItemTags;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.StampingRecipe;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class StampingRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        HolderGetter<Item> items = provider.getItems();
        StampingRecipe.builder()
            .requires(items, ViaItemTags.PULP)
            .result(Items.PAPER, 1)
            .save(provider, AncVoidInAir.of("stamping/").withSuffix(provider.safeName(Items.PAPER)));
    }
}
