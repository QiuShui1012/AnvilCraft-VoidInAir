package com.qiushui1012.mod.eyesinair;

import com.qiushui1012.mod.eyesinair.config.VIAServerConfig;
import com.qiushui1012.mod.eyesinair.init.VIAItemGroup;
import dev.anvilcraft.lib.v2.config.ConfigManager;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(AncVoidInAir.MOD_ID)
public class AncVoidInAir {
    public static final String MOD_ID = "eyesinair";
    public static final VIAServerConfig CONFIG = ConfigManager.register(AncVoidInAir.MOD_ID, VIAServerConfig::new);
    public static final Registrum REGISTRUM = Registrum.create(AncVoidInAir.MOD_ID);

    public AncVoidInAir(IEventBus modEventBus, ModContainer ignored) {
        VIAItemGroup.init(modEventBus);
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(AncVoidInAir.MOD_ID, path);
    }
}
