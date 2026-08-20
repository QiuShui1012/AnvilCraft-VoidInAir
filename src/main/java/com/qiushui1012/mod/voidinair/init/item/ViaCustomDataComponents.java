package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.item.property.custom.BlackCatAmuletData;
import dev.dubhe.anvilcraft.api.recipe.data.ICustomDataComponent;
import dev.dubhe.anvilcraft.init.registry.ModRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ViaCustomDataComponents {
    private static final DeferredRegister<ICustomDataComponent.Type<?>> DF = DeferredRegister
        .create(ModRegistries.CUSTOM_DATA_TYPE, AncVoidInAir.MOD_ID);

    public static final DeferredHolder<ICustomDataComponent.Type<?>, BlackCatAmuletData.Type> BLACK_CAT = DF
        .register("black_cat_amulet", BlackCatAmuletData.Type::new);

    public static void register(IEventBus bus) {
        DF.register(bus);
    }
}
