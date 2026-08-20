package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.util.LambdaUtil;
import dev.dubhe.anvilcraft.init.item.ModItemGroups;
import dev.dubhe.anvilcraft.init.item.tabs.DisplayItemsGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

public class ViaItemGroup extends DisplayItemsGenerator {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        AncVoidInAir.MOD_ID
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INSTANCE = REGISTER.register(
        "tab",
        () -> CreativeModeTab.builder()
            .icon(ViaBlocks.BLACK_CAT::asStack)
            .displayItems(LambdaUtil::noop)
            .title(REGISTRUM.addLang("itemGroup", AncVoidInAir.of("tab"), "AnvilCraft: Void in Air"))
            .displayItems(new ViaItemGroup())
            .withTabsBefore(ModItemGroups.ANVILCRAFT_BUILD_BLOCK.getId())
            .build()
    );

    @Override
    public void accept() {
        this.plain(ViaBlocks.AUTO_CRAFTER);
        this.plain(ViaBlocks.BLACK_CAT_HEAD);
        this.plain(ViaBlocks.BLACK_CAT);
        this.plain(ViaBlocks.DEEPSLATE_CHIPS);
        this.plain(ViaBlocks.BLACK_SAND);
        this.plain(ViaItems.TOTEM_OF_VOID);
        this.plain(ViaItems.VOID_AMULET);
        this.plain(ViaItems.SNOWFLAKE_AMULET);
        this.plain(ViaItems.BEEHIVE_AMULET);
        this.plain(ViaItems.GOLD_AMULET);
        this.plain(ViaItems.DOLPHIN_AMULET);
        this.plain(ViaItems.PUMPKIN_AMULET);
        this.plain(ViaItems.ANVILCRAFT_AMULET);
        this.plain(ViaItems.BLACK_CAT_AMULET);
        this.plain(ViaBlocks.VOID_FOUNTAIN);
        this.plain(ViaBlocks.RANDOM_TRANSMITTER);
        this.plain(ViaItems.CRIMSON_BOUND_MATTER);
        this.plain(ViaBlocks.CRIMSON_BOUND_MATTER_BLOCK);
    }

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
