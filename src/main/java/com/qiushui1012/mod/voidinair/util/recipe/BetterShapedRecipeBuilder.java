package com.qiushui1012.mod.voidinair.util.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;

public class BetterShapedRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStack result; // Neo: add stack result support
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private boolean showNotification = true;

    public BetterShapedRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
        this(category, new ItemStack(result, count));
    }

    public BetterShapedRecipeBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result = result;
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static BetterShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result) {
        return shaped(category, result, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static BetterShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result, int count) {
        return new BetterShapedRecipeBuilder(category, result, count);
    }

    public static BetterShapedRecipeBuilder shaped(RecipeCategory category, ItemStack result) {
        return new BetterShapedRecipeBuilder(category, result);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public BetterShapedRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        this.define(symbol, Ingredient.of(tag));
        return this.unlockedBy(tag);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public BetterShapedRecipeBuilder define(Character symbol, ItemLike item) {
        this.define(symbol, Ingredient.of(item));
        return this.unlockedBy(item);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public BetterShapedRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    /**
     * Adds a new entry to the patterns for this recipe.
     */
    public BetterShapedRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.getFirst().length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    public BetterShapedRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public BetterShapedRecipeBuilder unlockedBy(ItemLike item) {
        return this.unlockedBy(AnvilCraftDatagen.hasItem(item), AnvilCraftDatagen.has(item));
    }

    public BetterShapedRecipeBuilder unlockedBy(TagKey<Item> tag) {
        return this.unlockedBy(AnvilCraftDatagen.hasItem(tag), AnvilCraftDatagen.has(tag));
    }

    public BetterShapedRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    public BetterShapedRecipeBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ShapedRecipePattern shapedrecipepattern = this.ensureValid(id);
        Advancement.Builder advBuilder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advBuilder::addCriterion);
        ShapedRecipe shapedrecipe = new ShapedRecipe(
            Objects.requireNonNullElse(this.group, ""),
            RecipeBuilder.determineBookCategory(this.category),
            shapedrecipepattern,
            this.result,
            this.showNotification
        );
        recipeOutput.accept(id, shapedrecipe, advBuilder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private ShapedRecipePattern ensureValid(ResourceLocation location) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + location);
        } else {
            return ShapedRecipePattern.of(this.key, this.rows);
        }
    }
}
