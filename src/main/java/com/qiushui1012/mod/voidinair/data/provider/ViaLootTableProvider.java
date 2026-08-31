package com.qiushui1012.mod.voidinair.data.provider;

import com.qiushui1012.mod.voidinair.data.provider.loot.ViaBeheadingLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ViaLootTableProvider extends LootTableProvider {
    public ViaLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(
            output,
            Set.of(),
            List.of(new SubProviderEntry(ViaBeheadingLootSubProvider::new, LootContextParamSets.ENTITY)),
            provider
        );
    }
}
