package com.qiushui1012.mod.voidinair.data;

import com.qiushui1012.mod.voidinair.data.advancement.ViaAdvancementsHandler;
import com.qiushui1012.mod.voidinair.data.lang.ViaLangHandler;
import com.qiushui1012.mod.voidinair.data.provider.ViaLootTableProvider;
import com.qiushui1012.mod.voidinair.data.provider.ViaSoundProvider;
import com.qiushui1012.mod.voidinair.data.recipe.ViaRecipeHandler;
import com.qiushui1012.mod.voidinair.data.tag.ViaTagHandler;
import com.qiushui1012.mod.voidinair.init.item.ViaAmuletDefinitions;
import dev.anvilcraft.lib.v2.registrum.providers.DataProviderInitializer;
import dev.anvilcraft.lib.v2.registrum.providers.ProviderType;
import dev.dubhe.anvilcraft.init.registry.ModRegistryKeys;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

@EventBusSubscriber
public class AncVoidInAirDatagen {
    /**
     * 初始化生成器
     */
    public static void init() {
        DataProviderInitializer genInit = REGISTRUM.getDataGenInitializer();
        genInit.add(ModRegistryKeys.AMULET_DEF, ViaAmuletDefinitions::bootstrap);

        REGISTRUM.addDataGenerator(ProviderType.ADVANCEMENT, ViaAdvancementsHandler::init);
        REGISTRUM.addDataGenerator(ProviderType.RECIPE, ViaRecipeHandler::init);
        REGISTRUM.addDataGenerator(ProviderType.LANG, ViaLangHandler::init);
        REGISTRUM.addDataGenerator(ProviderType.ENTITY_TAGS, ViaTagHandler::entity);
        REGISTRUM.addDataGenerator(ProviderType.DAMAGE_TYPE_TAGS, ViaTagHandler::damage);
    }

    @SubscribeEvent
    public static void onData(GatherDataEvent event) {
        event.getGenerator().addProvider(
            event.includeClient(),
            new ViaSoundProvider(event.getGenerator().getPackOutput(), event.getExistingFileHelper())
        );
        event.getGenerator().addProvider(
            event.includeServer(),
            new ViaLootTableProvider(event.getGenerator().getPackOutput(), event.getLookupProvider())
        );
    }
}
