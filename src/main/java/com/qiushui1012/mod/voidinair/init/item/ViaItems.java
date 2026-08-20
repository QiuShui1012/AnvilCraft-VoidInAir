package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.util.recipe.BetterShapedRecipeBuilder;
import com.qiushui1012.mod.voidinair.util.recipe.BetterShapelessRecipeBuilder;
import dev.anvilcraft.lib.v2.registrum.util.entry.ItemEntry;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModComponents;
import dev.dubhe.anvilcraft.init.item.ModItemTags;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.JewelCraftingRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

@SuppressWarnings({"CodeBlock2Expr"})
public class ViaItems {
    public static final ItemEntry<Item> TOTEM_OF_VOID = REGISTRUM
        .item("totem_of_void", Item::new)
        .lang("Totem of Void")
        .properties((properties) -> properties
            .stacksTo(1)
            .rarity(Rarity.UNCOMMON)
        )
        .recipe((ctx, provider) -> {
            BetterShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
                .pattern("MMM")
                .pattern("BTB")
                .pattern("MMM")
                .define('T', Items.TOTEM_OF_UNDYING)
                .define('B', ModBlocks.VOID_MATTER_BLOCK)
                .define('M', ModItems.VOID_MATTER)
                .save(provider);
        })
        .register();

    public static final ItemEntry<Item> VOID_AMULET = REGISTRUM
        .item("void_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.VOID)
        )
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder()
                .requires(ModItems.SILVER_INGOT)
                .requires(ModBlocks.VOID_MATTER_BLOCK)
                .result(ctx.get().getDefaultInstance())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> SNOWFLAKE_AMULET = REGISTRUM
        .item("snowflake_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.SNOWFLAKE)
        )
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder()
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.POWDER_SNOW_BUCKET)
                .requires(Items.POWDER_SNOW_BUCKET)
                .requires(Items.POWDER_SNOW_BUCKET)
                .result(ctx.get().getDefaultInstance())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> BEEHIVE_AMULET = REGISTRUM
        .item("beehive_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.BEEHIVE)
        )
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder()
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.HONEYCOMB, 8)
                .requires(Items.HONEY_BLOCK, 2)
                .result(ctx.get().getDefaultInstance())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> GOLD_AMULET = REGISTRUM
        .item("gold_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.GOLD)
        )
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder()
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.GOLD_BLOCK, 2)
                .result(ctx.get().getDefaultInstance())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> DOLPHIN_AMULET = REGISTRUM
        .item("dolphin_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.DOLPHIN)
        )
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder()
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.PRISMARINE_SHARD, 4)
                .requires(Items.PRISMARINE_CRYSTALS, 4)
                .result(ctx.get().getDefaultInstance())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> PUMPKIN_AMULET = REGISTRUM
        .item("pumpkin_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.PUMPKIN)
        )
        .recipe((ctx, provider) -> {
            JewelCraftingRecipe.builder()
                .requires(ModItems.SILVER_INGOT)
                .requires(Items.PUMPKIN_SEEDS, 16)
                .requires(Items.CARVED_PUMPKIN, 4)
                .result(ctx.get().getDefaultInstance())
                .save(provider, ctx.getId().withPrefix("jewel_crafting/"));
        })
        .register();

    public static final ItemEntry<Item> ANVILCRAFT_AMULET = REGISTRUM
        .item("anvilcraft_amulet", Item::new)
        .lang("AnvilCraft Amulet")
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.ANVILCRAFT)
        )
        .register();

    public static final ItemEntry<Item> BLACK_CAT_AMULET = REGISTRUM
        .item("black_cat_amulet", Item::new)
        .properties(properties -> properties
            .component(ModComponents.AMULET, ViaAmulets.BLACK_CAT)
        )
        .register();

    public static final ItemEntry<Item> CRIMSON_BOUND_MATTER = REGISTRUM
        .item("crimson_bound_matter", Item::new)
        .tag(ModItemTags.VOID_RESISTANT)
        .recipe((ctx, provider) -> {
            BetterShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get(), 9)
                .requires(ViaBlocks.CRIMSON_BOUND_MATTER_BLOCK)
                .save(provider);
        })
        .register();

    public static final ItemEntry<Item> PULP = REGISTRUM
        .item("pulp", Item::new)
        .tag(ViaItemTags.PULP)
        .model((ctx, provider) -> provider.generated(
            ctx::get,
            AnvilCraft.of(ctx.getId().withPrefix("item/").getPath())
        ))
        .register();

    public static void init() {
    }
}
