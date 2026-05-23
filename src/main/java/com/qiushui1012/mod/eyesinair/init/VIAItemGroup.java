package com.qiushui1012.mod.eyesinair.init;

import com.qiushui1012.mod.eyesinair.AncVoidInAir;
import com.qiushui1012.mod.eyesinair.util.LambdaUtil;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.item.ModItemGroups;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.eyesinair.AncVoidInAir.REGISTRUM;

public class VIAItemGroup {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        AncVoidInAir.MOD_ID
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INSTANCE = REGISTER.register(
        "tab",
        () -> CreativeModeTab.builder()
            .icon(Items.CAT_SPAWN_EGG::getDefaultInstance)
            .displayItems(LambdaUtil::noop)
            .title(REGISTRUM.addLang("itemGroup", AncVoidInAir.of("tab"), "AnvilCraft: Void in Air"))
            .withTabsAfter(
                AnvilCraft.of("ingredients"),
                AnvilCraft.of("functional_blocks"),
                AnvilCraft.of("building_blocks")
            )
            .withTabsBefore(ModItemGroups.ANVILCRAFT_BUILD_BLOCK.getId())
            .build()
    );

    public static void init(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
