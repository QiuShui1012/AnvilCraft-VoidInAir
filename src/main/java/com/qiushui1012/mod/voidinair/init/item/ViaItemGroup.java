package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.util.LambdaUtil;
import dev.dubhe.anvilcraft.init.item.ModItemGroups;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

public class ViaItemGroup {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        AncVoidInAir.MOD_ID
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INSTANCE = REGISTER.register(
        "tab",
        () -> CreativeModeTab.builder()
            .icon(ViaBlocks.BLACK_CAT_HEAD::asStack)
            .displayItems(LambdaUtil::noop)
            .title(REGISTRUM.addLang("itemGroup", AncVoidInAir.of("tab"), "AnvilCraft: Void in Air"))
            .withTabsBefore(ModItemGroups.ANVILCRAFT_BUILD_BLOCK.getId())
            .build()
    );

    public static void init(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
