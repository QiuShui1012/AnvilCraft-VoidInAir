package com.qiushui1012.mod.voidinair.data.provider.loot;

import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.init.loot.ViaLootTables;
import com.qiushui1012.mod.voidinair.loot.conditions.MatchCatVariant;
import dev.dubhe.anvilcraft.init.enchantment.ModEnchantments;
import dev.dubhe.anvilcraft.init.item.ModComponents;
import dev.dubhe.anvilcraft.item.property.component.Merciless;
import dev.dubhe.anvilcraft.loot.conditions.MatchDataComponent;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

public class ViaBeheadingLootSubProvider implements LootTableSubProvider {
    public ViaBeheadingLootSubProvider(HolderLookup.Provider ignored) {
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        this.generateBeheading(
            consumer,
            ViaLootTables.BEHEADING_CAT,
            ViaBlocks.BLACK_CAT_HEAD,
            0.1F,
            0.1F,
            () -> new MatchCatVariant(Optional.of(CatVariant.ALL_BLACK))
        );
    }

    public void generateBeheading(
        BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer,
        ResourceKey<LootTable> lootTableKey,
        ItemLike headItem,
        float baseChance,
        float bonusChancePerLevel,
        LootItemCondition.Builder variant
    ) {
        consumer.accept(lootTableKey, LootTable.lootTable()
            .withPool(this.generatePool(headItem, baseChance, bonusChancePerLevel, variant))
            .withPool(
                this.generatePool(headItem, baseChance, bonusChancePerLevel, variant)
                    .when(() -> new LootItemRandomChanceCondition(ConstantValue.exactly(0.25f)))
            )
            .withPool(
                this.generatePool(headItem, baseChance, bonusChancePerLevel, variant)
                    .when(() -> new LootItemRandomChanceCondition(ConstantValue.exactly(0.05f)))
            )
        );
    }

    private LootPool.Builder generatePool(
        ItemLike headItem,
        float baseChance,
        float bonusChancePerLevel,
        LootItemCondition.Builder variant
    ) {
        return LootPool.lootPool()
            .add(LootItem.lootTableItem(headItem))
            .when(() -> new LootItemRandomChanceWithEnchantedBonusCondition(
                0.0f,
                LevelBasedValue.perLevel(baseChance, bonusChancePerLevel),
                new DummyHolder(ModEnchantments.BEHEADING_KEY)
            ))
            .when(LootItemKilledByPlayerCondition.killedByPlayer())
            .when(InvertedLootItemCondition.invert(MatchDataComponent.component(
                DataComponentPredicate.builder().expect(ModComponents.MERCILESS, Merciless.DEFAULT)
            )))
            .when(variant);
    }

    private static class DummyHolder extends Holder.Reference<Enchantment> {
        @SuppressWarnings("DataFlowIssue")
        protected DummyHolder(@Nullable ResourceKey<Enchantment> key) {
            super(Type.STAND_ALONE, null, key, null);
        }

        @Override
        public boolean canSerializeIn(HolderOwner<Enchantment> owner) {
            return true;
        }
    }
}
