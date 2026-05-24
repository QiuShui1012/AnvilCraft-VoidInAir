package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.item.property.consume.SaveEntityFromVoidConsumeEffect;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import dev.dubhe.anvilcraft.api.amulet.type.AmuletType;
import dev.dubhe.anvilcraft.init.ModRegistries;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VIAAmuletTypes {
    public static final DeferredRegister<AmuletType> REGISTER = DeferredRegister.create(ModRegistries.AMULET_TYPE_KEY, AncVoidInAir.MOD_ID);

    public static final DeferredHolder<AmuletType, AmuletType> VOID = REGISTER.register(
        "void",
        () -> AmuletType.builder()
            .amulet(VIAItems.VOID_AMULET)
            .obtainByDamage(DamageTypes.FELL_OUT_OF_WORLD)
            .immuneDamageFromObtain()
            .inventoryTick((player, amulet, isEnabled) -> {
                if (!isEnabled) return;
                if (player.getY() >= player.level().getMinY()) return;
                SaveEntityFromVoidConsumeEffect.INSTANCE.apply(player.level(), amulet, player);
            })
            .build()
    );

    public static void init(IEventBus modEventBus) {
        REGISTER.register(modEventBus);

        AmuletManager.INSTANCE.registerAmulets(VIAItems.VOID_AMULET::get);
    }
}
