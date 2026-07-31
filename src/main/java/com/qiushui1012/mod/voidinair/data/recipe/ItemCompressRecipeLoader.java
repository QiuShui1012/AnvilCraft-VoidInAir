package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.item.ViaItemTags;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItemTags;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemCompressRecipe;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.Item;

public class ItemCompressRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        HolderGetter<Item> items = provider.getItems();
        ItemCompressRecipe.builder()
            .requires(ModItems.WOOD_FIBER, 3)
            .requires(items, ViaItemTags.PULP, 2)
            .requires(items, ModItemTags.RESIN)
            .result(ModBlocks.PLYWOOD_BLOCK, 12)
            .save(provider, AncVoidInAir.of("item_compress/").withSuffix(provider.safeName(ModBlocks.PLYWOOD_BLOCK)));
    }
}
