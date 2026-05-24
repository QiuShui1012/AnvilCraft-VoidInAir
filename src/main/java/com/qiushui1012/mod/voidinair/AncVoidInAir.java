package com.qiushui1012.mod.voidinair;

import com.qiushui1012.mod.voidinair.config.VIAServerConfig;
import com.qiushui1012.mod.voidinair.data.AncVoidInAirDatagen;
import com.qiushui1012.mod.voidinair.init.block.VIABlockEntities;
import com.qiushui1012.mod.voidinair.init.block.VIABlocks;
import com.qiushui1012.mod.voidinair.init.block.VIACriterionTriggers;
import com.qiushui1012.mod.voidinair.init.item.VIAAmuletTypes;
import com.qiushui1012.mod.voidinair.init.item.VIAConsumeEffects;
import com.qiushui1012.mod.voidinair.init.item.VIAItemGroup;
import com.qiushui1012.mod.voidinair.init.item.VIAItems;
import dev.anvilcraft.lib.v2.config.ConfigManager;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(AncVoidInAir.MOD_ID)
public class AncVoidInAir {
    public static final String MOD_ID = "voidinair";
    public static final VIAServerConfig CONFIG = ConfigManager.register(AncVoidInAir.MOD_ID, VIAServerConfig::new);
    public static final Registrum REGISTRUM = Registrum.create(AncVoidInAir.MOD_ID);

    public AncVoidInAir(IEventBus modEventBus, ModContainer ignored) {
        VIAItemGroup.init(modEventBus);
        VIABlocks.init();
        VIABlockEntities.init();
        VIAItems.init();
        VIAAmuletTypes.init(modEventBus);
        VIAConsumeEffects.init(modEventBus);
        VIACriterionTriggers.init(modEventBus);

        // datagen
        AncVoidInAirDatagen.init();
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(AncVoidInAir.MOD_ID, path);
    }
}
