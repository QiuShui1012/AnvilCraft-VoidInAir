package com.qiushui1012.mod.voidinair.data.recipe;

import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;

public class ViaRecipeHandler {
    public static void init(RegistrumRecipeProvider provider) {
        MultipleToOneRecipeLoader.init(provider);
    }
}
