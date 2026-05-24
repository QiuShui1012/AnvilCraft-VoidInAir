package com.qiushui1012.mod.voidinair.data.lang;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.config.VIAServerConfig;
import dev.anvilcraft.lib.v2.config.ConfigData;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumLangProvider;
import lombok.SneakyThrows;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ConfigScreenLang {
    @SneakyThrows
    public static void init(RegistrumLangProvider provider) {
        provider.add("voidinair.configuration.title", "AnvilCraft: Void in Air Configuration");
        provider.add("voidinair.configuration.section.voidinair.server.toml", "AnvilCraft: Void in Air Server Configuration");
        provider.add("voidinair.configuration.section.voidinair.server.toml.title", "AnvilCraft: Void in Air Server Configuration");
        Class<?> providerProxy = ConfigData.class.getDeclaredClasses()[0];
        Method readConfigClass = ConfigData.class.getDeclaredMethod(
            "readConfigClass",
            providerProxy,
            String.class,
            ModConfig.Type.class,
            Class.class,
            String.class
        );
        readConfigClass.setAccessible(true);
        Constructor<?> providerProxyConstructor = providerProxy.getDeclaredConstructor(LanguageProvider.class);
        providerProxyConstructor.setAccessible(true);
        readConfigClass.invoke(
            null,
            providerProxyConstructor.newInstance(provider),
            AncVoidInAir.MOD_ID,
            ModConfig.Type.SERVER,
            VIAServerConfig.class,
            null
        );
    }
}
