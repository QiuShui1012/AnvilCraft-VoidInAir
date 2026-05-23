package com.qiushui1012.mod.voidinair.data.lang;

import dev.anvilcraft.lib.v2.registrum.providers.RegistrumLangProvider;

public class ViaLangHandler {
    /**
     * 语言文件初始化
     *
     * @param provider 提供器
     */
    public static void init(RegistrumLangProvider provider) {
        AdvancementLang.init(provider);
        ConfigScreenLang.init(provider);
        ItemTooltipLang.init(provider);
    }
}
