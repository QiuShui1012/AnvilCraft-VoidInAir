package com.qiushui1012.mod.voidinair.data.recipe;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.item.ViaItemTags;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.init.item.ModItemTags;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.FastCookingRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class CookingRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        FastCookingRecipe.builder()
            .cauldron(Blocks.WATER_CAULDRON)
            .requires(ViaItemTags.PULP)
            .requires(ModItemTags.RESIN)
            .result(Items.SLIME_BALL, 4)
            .save(provider, AncVoidInAir.of("fast_cooking/").withSuffix(provider.safeName(Items.SLIME_BALL)));
    }
}
