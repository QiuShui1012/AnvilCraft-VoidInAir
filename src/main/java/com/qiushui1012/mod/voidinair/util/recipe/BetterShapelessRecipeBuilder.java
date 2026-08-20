package com.qiushui1012.mod.voidinair.util.recipe;

import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;

public class BetterShapelessRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public BetterShapelessRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
        this(category, new ItemStack(result, count));
    }

    public BetterShapelessRecipeBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result = result;
    }

    /**
     * Creates a new builder for a shapeless recipe.
     */
    public static BetterShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike result) {
        return new BetterShapelessRecipeBuilder(category, result, 1);
    }

    /**
     * Creates a new builder for a shapeless recipe.
     */
    public static BetterShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike result, int count) {
        return new BetterShapelessRecipeBuilder(category, result, count);
    }

    public static BetterShapelessRecipeBuilder shapeless(RecipeCategory p_252339_, ItemStack result) {
        return new BetterShapelessRecipeBuilder(p_252339_, result);
    }

    /**
     * Adds an ingredient that can be any item in the given tag.
     */
    public BetterShapelessRecipeBuilder requires(TagKey<Item> tag) {
        this.requires(Ingredient.of(tag));
        return this.unlockedBy(tag);
    }

    /**
     * Adds an ingredient of the given item.
     */
    public BetterShapelessRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    /**
     * Adds the given ingredient multiple times.
     */
    public BetterShapelessRecipeBuilder requires(ItemLike item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.requires(Ingredient.of(item));
            this.unlockedBy(item);
        }

        return this;
    }

    /**
     * Adds an ingredient.
     */
    public BetterShapelessRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    /**
     * Adds an ingredient multiple times.
     */
    public BetterShapelessRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public BetterShapelessRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public BetterShapelessRecipeBuilder unlockedBy(ItemLike item) {
        return this.unlockedBy(AnvilCraftDatagen.hasItem(item), AnvilCraftDatagen.has(item));
    }

    public BetterShapelessRecipeBuilder unlockedBy(TagKey<Item> tag) {
        return this.unlockedBy(AnvilCraftDatagen.hasItem(tag), AnvilCraftDatagen.has(tag));
    }

    public BetterShapelessRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder advBuilder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advBuilder::addCriterion);
        ShapelessRecipe shapelessrecipe = new ShapelessRecipe(
            Objects.requireNonNullElse(this.group, ""),
            RecipeBuilder.determineBookCategory(this.category),
            this.result,
            this.ingredients
        );
        recipeOutput.accept(id, shapelessrecipe, advBuilder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
