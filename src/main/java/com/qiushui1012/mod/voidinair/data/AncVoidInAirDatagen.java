package com.qiushui1012.mod.voidinair.data;

import com.qiushui1012.mod.voidinair.data.advancement.VIAAdvancementsHandler;
import com.qiushui1012.mod.voidinair.data.lang.VIALangHandler;
import dev.anvilcraft.lib.v2.registrum.providers.ProviderType;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

public class AncVoidInAirDatagen {
    /**
     * 初始化生成器
     */
    public static void init() {
        REGISTRUM.addDataGenerator(ProviderType.ADVANCEMENT, VIAAdvancementsHandler::init);
        REGISTRUM.addDataGenerator(ProviderType.LANG, VIALangHandler::init);
    }
}
