package com.qiushui1012.mod.voidinair;

import com.qiushui1012.mod.voidinair.config.ViaServerConfig;
import com.qiushui1012.mod.voidinair.data.AncVoidInAirDatagen;
import com.qiushui1012.mod.voidinair.init.block.ViaBlockEntities;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.init.block.ViaCriterionTriggers;
import com.qiushui1012.mod.voidinair.init.item.ViaAmuletTypes;
import com.qiushui1012.mod.voidinair.init.item.ViaConsumeEffects;
import com.qiushui1012.mod.voidinair.init.item.ViaItemGroup;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import dev.anvilcraft.lib.v2.config.ConfigManager;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(AncVoidInAir.MOD_ID)
public class AncVoidInAir {
    public static final String MOD_ID = "voidinair";
    public static final ViaServerConfig CONFIG = ConfigManager.register(AncVoidInAir.MOD_ID, ViaServerConfig::new);
    public static final Registrum REGISTRUM = Registrum.create(AncVoidInAir.MOD_ID);

    public AncVoidInAir(IEventBus modEventBus, ModContainer ignored) {
        ViaItemGroup.init(modEventBus);
        ViaBlocks.init();
        ViaBlockEntities.init();
        ViaItems.init();
        ViaAmuletTypes.init(modEventBus);
        ViaConsumeEffects.init(modEventBus);
        ViaCriterionTriggers.init(modEventBus);

        // datagen
        AncVoidInAirDatagen.init();
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(AncVoidInAir.MOD_ID, path);
    }
}
