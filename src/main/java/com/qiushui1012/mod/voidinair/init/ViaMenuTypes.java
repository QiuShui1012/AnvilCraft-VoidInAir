package com.qiushui1012.mod.voidinair.init;

import com.qiushui1012.mod.voidinair.client.gui.screen.AutoCrafterScreen;
import com.qiushui1012.mod.voidinair.inventory.AutoCrafterMenu;
import dev.anvilcraft.lib.v2.registrum.util.entry.MenuEntry;

import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

@SuppressWarnings("DataFlowIssue")
public class ViaMenuTypes {
    public static final MenuEntry<AutoCrafterMenu> AUTO_CRAFTER = REGISTRUM
        .menu("auto_crafter", AutoCrafterMenu::new, () -> AutoCrafterScreen::new)
        .register();

    public static void init() {
    }
}
