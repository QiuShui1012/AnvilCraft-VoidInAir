package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.item.property.consume.amulet.BeehiveAmulet;
import dev.dubhe.anvilcraft.init.registry.ModRegistryKeys;
import dev.dubhe.anvilcraft.item.property.component.amulet.IAmulet;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ViaAmuletTypes {
    private static final DeferredRegister<IAmulet.Type<?>> REGISTER = DeferredRegister.create(
        ModRegistryKeys.AMULET_TYPE,
        AncVoidInAir.MOD_ID
    );

    public static final DeferredHolder<IAmulet.Type<?>, BeehiveAmulet.Type> BEEHIVE = REGISTER.register(
        "beehive",
        BeehiveAmulet.Type::new
    );

    public static void init(IEventBus modEventBus) {
        ViaAmuletTypes.REGISTER.register(modEventBus);
    }
}
