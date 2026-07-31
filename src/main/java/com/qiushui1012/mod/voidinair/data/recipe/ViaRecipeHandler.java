package com.qiushui1012.mod.voidinair.data.recipe;

import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;

public class ViaRecipeHandler {
    public static void init(RegistrumRecipeProvider provider) {
        CookingRecipeLoader.init(provider);
        CrushingRecipeLoader.init(provider);
        ItemCompressRecipeLoader.init(provider);
        MeshRecipeLoader.init(provider);
        MultipleToOneRecipeLoader.init(provider);
        SolidLiquidRecipeLoader.init(provider);
        StampingRecipeLoader.init(provider);
        VanillaRecipeLoader.init(provider);
    }
}
