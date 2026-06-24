package com.qiushui1012.mod.voidinair.init.item;

import dev.anvilcraft.lib.v2.registrum.util.entry.ItemEntry;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModComponents;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.JewelCraftingRecipe;
import dev.dubhe.anvilcraft.util.registrater.DataGenUtil;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

@SuppressWarnings("CodeBlock2Expr")
public class ViaItems {
    static {
        REGISTRUM.defaultCreativeTab(ViaItemGroup.INSTANCE.getKey());
    }

    public static final ItemEntry<Item> TOTEM_OF_VOID = REGISTRUM
        .item("totem_of_void", Item::new)
        .lang("Totem of Void")
        .properties((properties) -> properties
            .stacksTo(1)
            .rarity(Rarity.UNCOMMON)
            .component(DataComponents.DEATH_PROTECTION, ViaDeathProtections.TOTEM_OF_VOID)
        )
        .recipe((ctx, provider) -> {
            HolderGetter<Item> items = provider.getItems();
            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ctx.get())
                .pattern("MMM")
                .pattern("BTB")
                .pattern("MMM")
                .define('T', Items.TOTEM_OF_UNDYING)
                .define('B', ModBlocks.VOID_MATTER_BLOCK)
                .define('M', ModItems.VOID_MATTER)
                .unlockedBy(AnvilCraftDatagen.hasItem(Items.TOTEM_OF_UNDYING), AnvilCraftDatagen.has(items, Items.TOTEM_OF_UNDYING))
                .unlockedBy(
                    AnvilCraftDatagen.hasItem(ModBlocks.VOID_MATTER_BLOCK),
                    AnvilCraftDatagen.has(items, ModBlocks.VOID_MATTER_BLOCK)
                )
                .unlockedBy(AnvilCraftDatagen.hasItem(ModItems.VOID_MATTER), AnvilCraftDatagen.has(items, ModItems.VOID_MATTER))
                .save(provider);
        })
        .register();

    public static final ItemEntry<Item> VOID_AMULET = REGISTRUM
        .item("void_amulet", Item::new)
        .properties(properties -> properties
            .component(DataComponents.DEATH_PROTECTION, ViaDeathProtections.VOID_AMULET)
            .component(ModComponents.AMULET, ViaAmulets.VOID)
        )
        .model(DataGenUtil::flatItem)
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder(provider.getItems())
                .requires(ModItems.SILVER_INGOT)
                .requires(ModBlocks.VOID_MATTER_BLOCK)
                .source(ctx.get())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> SNOWFLAKE_AMULET = REGISTRUM
        .item("snowflake_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.SNOWFLAKE)
        )
        .model(DataGenUtil::flatItem)
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder(provider.getItems())
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.POWDER_SNOW_BUCKET)
                .requires(Items.POWDER_SNOW_BUCKET)
                .requires(Items.POWDER_SNOW_BUCKET)
                .source(ctx.get())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> BEEHIVE_AMULET = REGISTRUM
        .item("beehive_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.BEEHIVE)
        )
        .model(DataGenUtil::flatItem)
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder(provider.getItems())
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.HONEYCOMB, 8)
                .requires(Items.HONEY_BLOCK, 2)
                .source(ctx.get())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> GOLD_AMULET = REGISTRUM
        .item("gold_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.GOLD)
        )
        .model(DataGenUtil::flatItem)
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder(provider.getItems())
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.GOLD_BLOCK, 2)
                .source(ctx.get())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> DOLPHIN_AMULET = REGISTRUM
        .item("dolphin_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.DOLPHIN)
        )
        .model(DataGenUtil::flatItem)
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder(provider.getItems())
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.PRISMARINE_SHARD, 4)
                .requires(Items.PRISMARINE_CRYSTALS, 4)
                .source(ctx.get())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> PUMPKIN_AMULET = REGISTRUM
        .item("pumpkin_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.PUMPKIN)
        )
        .model(DataGenUtil::flatItem)
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder(provider.getItems())
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.PUMPKIN_SEEDS, 16)
                .requires(Items.CARVED_PUMPKIN, 4)
                .source(ctx.get())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> ANVILCRAFT_AMULET = REGISTRUM
        .item("anvilcraft_amulet", Item::new)
        .lang("AnvilCraft Amulet")
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.ANVILCRAFT)
        )
        .model(DataGenUtil::flatItem)
        .register();

    public static final ItemEntry<Item> BLACK_CAT_AMULET = REGISTRUM
        .item("black_cat_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.BLACK_CAT)
            .component(DataComponents.DEATH_PROTECTION, ViaDeathProtections.VOID_AMULET)
        )
        .model(DataGenUtil::flatItem)
        .register();

    public static void init() {
    }
}
