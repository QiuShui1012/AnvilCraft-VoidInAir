package com.qiushui1012.mod.voidinair;

import com.qiushui1012.mod.voidinair.config.ViaServerConfig;
import com.qiushui1012.mod.voidinair.data.AncVoidInAirDatagen;
import com.qiushui1012.mod.voidinair.init.ViaMenuTypes;
import com.qiushui1012.mod.voidinair.init.ViaSoundEvents;
import com.qiushui1012.mod.voidinair.init.block.ViaBlockEntities;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.init.block.ViaCriterionTriggers;
import com.qiushui1012.mod.voidinair.init.item.ViaAmuletTypes;
import com.qiushui1012.mod.voidinair.init.item.ViaCustomDataComponents;
import com.qiushui1012.mod.voidinair.init.item.ViaItemGroup;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import com.qiushui1012.mod.voidinair.item.totem.TotemOfVoidHandler;
import dev.anvilcraft.lib.v2.config.ConfigManager;
import dev.anvilcraft.lib.v2.network.register.NetworkRegistrar;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import dev.dubhe.anvilcraft.api.totem.TotemManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(AncVoidInAir.MOD_ID)
public class AncVoidInAir {
    public static final String MOD_ID = "voidinair";
    public static final ViaServerConfig CONFIG = ConfigManager.register(AncVoidInAir.MOD_ID, ViaServerConfig::new);
    public static final Registrum REGISTRUM = Registrum.create(AncVoidInAir.MOD_ID).defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    public AncVoidInAir(IEventBus modEventBus, ModContainer ignored) {
        ViaItemGroup.register(modEventBus);
        ViaBlocks.init();
        ViaBlockEntities.init();
        ViaMenuTypes.init();
        ViaItems.init();

        ViaAmuletTypes.register(modEventBus);
        ViaCustomDataComponents.register(modEventBus);
        ViaCriterionTriggers.register(modEventBus);
        ViaSoundEvents.register(modEventBus);
        modEventBus.addListener(AncVoidInAir::onSetup);
        modEventBus.addListener(AncVoidInAir::registerPayload);

        // datagen
        AncVoidInAirDatagen.init();
    }

    private static void onSetup(FMLCommonSetupEvent event) {
        TotemManager.INSTANCE.registerTotem(ViaItems.TOTEM_OF_VOID.asItem(), TotemOfVoidHandler.INSTANCE);
    }

    private static void registerPayload(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        NetworkRegistrar.register(registrar, MOD_ID);
    }

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(AncVoidInAir.MOD_ID, path);
    }
}
