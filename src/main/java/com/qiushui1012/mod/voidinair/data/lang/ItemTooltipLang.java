package com.qiushui1012.mod.voidinair.data.lang;

import com.qiushui1012.mod.voidinair.item.tooltip.ItemTooltipManager;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumLangProvider;

public class ItemTooltipLang {
    /**
     * 物品工具提示语言条目初始化
     *
     * @param provider 提供器
     */
    public static void init(RegistrumLangProvider provider) {
        ItemTooltipManager.NEED_TOOLTIP_ITEM.forEach((item, s) -> provider.add(ItemTooltipManager.getTranslationKey(item), s));
    }
}
