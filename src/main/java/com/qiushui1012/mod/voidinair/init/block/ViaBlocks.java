package com.qiushui1012.mod.voidinair.init.block;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.block.decoration.BlackCatHeadBlock;
import com.qiushui1012.mod.voidinair.block.decoration.BlackCatWallHeadBlock;
import com.qiushui1012.mod.voidinair.block.power.batch.AutoCrafterBlock;
import com.qiushui1012.mod.voidinair.block.production.VoidFountainBlock;
import com.qiushui1012.mod.voidinair.block.utility.redstone.RandomTransmitterBlock;
import com.qiushui1012.mod.voidinair.block.workstation.BlackCatBlock;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import com.qiushui1012.mod.voidinair.util.recipe.BetterShapedRecipeBuilder;
import com.qiushui1012.mod.voidinair.util.recipe.BetterShapelessRecipeBuilder;
import dev.anvilcraft.lib.v2.registrum.providers.ProviderType;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.anvilcraft.lib.v2.util.nullness.NonNullBiConsumer;
import dev.dubhe.anvilcraft.block.batch.BaseBatchCraftingBlock;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

@SuppressWarnings("CodeBlock2Expr")
public class ViaBlocks {
    public static final BlockEntry<AutoCrafterBlock> AUTO_CRAFTER = REGISTRUM
        .block("auto_crafter", AutoCrafterBlock::new)
        .initialProperties(() -> Blocks.IRON_BLOCK)
        .properties(properties -> properties.noOcclusion().isValidSpawn(Blocks::never))
        .blockstate(DataGenUtil::noExtraModelOrState)
        .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE)
        .recipe((ctx, provider) -> {
            BetterShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModBlocks.BATCH_CRAFTER.get())
                .requires(ctx.get())
                .save(provider, AncVoidInAir.of("batch_crafter_convert"));
            BetterShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ctx.get())
                .requires(ModBlocks.BATCH_CRAFTER.get())
                .save(provider, AncVoidInAir.of("auto_crafter_convert"));
        })
        .simpleItem()
        .onRegister(block -> BaseBatchCraftingBlock.registerBatchCrafting(() -> block))
        .register();

    public static final BlockEntry<BlackCatBlock> BLACK_CAT = REGISTRUM
        .block("black_cat", BlackCatBlock::new)
        .initialProperties(ModBlocks.NEOFORGE)
        .properties(properties -> properties.sound(BlackCatBlock.SOUND_TYPE))
        .blockstate(DataGenUtil::noExtraModelOrState)
        .recipe((ctx, provider) -> {
            BetterShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ctx.get())
                .pattern("MMM")
                .pattern(" B ")
                .pattern("BBB")
                .define('B', ModBlocks.VOID_MATTER_BLOCK)
                .define('M', ModItems.VOID_MATTER)
                .save(provider);
        })
        .simpleItem()
        .register();

    public static final BlockEntry<VoidFountainBlock> VOID_FOUNTAIN = REGISTRUM
        .block("void_fountain", VoidFountainBlock::new)
        .initialProperties(ModBlocks.MINERAL_FOUNTAIN)
        .blockstate((ctx, provider) -> provider.simpleBlock(
            ctx.get(),
            new ConfiguredModel(new ModelFile.UncheckedModelFile(ctx.getId().withPrefix("block/")))
        ))
        .simpleItem()
        .register();

    public static final BlockEntry<RandomTransmitterBlock> RANDOM_TRANSMITTER = REGISTRUM
        .block("random_transmitter", RandomTransmitterBlock::new)
        .properties(properties -> properties
            .pushReaction(PushReaction.DESTROY)
            .mapColor(MapColor.COLOR_RED)
            .requiresCorrectToolForDrops()
        )
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .loot((tables, block) -> tables.add(
            block,
            LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                        .when(ExplosionCondition.survivesExplosion())
                        .add(LootItem.lootTableItem(block.asItem()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(6.0F))))
                        .apply(RandomTransmitterBlock.side(RandomTransmitterBlock.NORTH, block))
                        .apply(RandomTransmitterBlock.side(RandomTransmitterBlock.SOUTH, block))
                        .apply(RandomTransmitterBlock.side(RandomTransmitterBlock.EAST, block))
                        .apply(RandomTransmitterBlock.side(RandomTransmitterBlock.WEST, block))
                        .apply(RandomTransmitterBlock.side(RandomTransmitterBlock.UP, block))
                        .apply(RandomTransmitterBlock.side(RandomTransmitterBlock.DOWN, block))
                )
        ))
        .blockstate(DataGenUtil::noExtraModelOrState)
        .recipe((ctx, provider) -> {
            BetterShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ctx.get(), 6)
                .pattern("R")
                .pattern("I")
                .pattern("P")
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_INGOT)
                .define('P', ModItems.PROCESSOR)
                .save(provider);
        })
        .item()
        .model(NonNullBiConsumer.noop())
        .build()
        .register();

    public static final BlockEntry<ColoredFallingBlock> DEEPSLATE_CHIPS = REGISTRUM
        .block(
            "deepslate_chips",
            properties -> new ColoredFallingBlock(new ColorRGBA(0x4F4F55FF), properties)
        )
        .initialProperties(() -> Blocks.GRAVEL)
        .blockstate((ctx, provider) -> provider.simpleBlock(
            ctx.get(),
            new ConfiguredModel(new ModelFile.UncheckedModelFile(ctx.getId().withPrefix("block/")))
        ))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .simpleItem()
        .register();

    public static final BlockEntry<ColoredFallingBlock> BLACK_SAND = REGISTRUM
        .block(
            "black_sand",
            properties -> new ColoredFallingBlock(new ColorRGBA(0x1D1D1DFF), properties)
        )
        .initialProperties(() -> Blocks.SAND)
        .blockstate((ctx, provider) -> provider.simpleBlock(
            ctx.get(),
            new ConfiguredModel(new ModelFile.UncheckedModelFile(ctx.getId().withPrefix("block/")))
        ))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .simpleItem()
        .register();

    public static final BlockEntry<BlackCatHeadBlock> BLACK_CAT_HEAD = REGISTRUM
        .block("black_cat_head", BlackCatHeadBlock::new)
        .properties(properties -> properties
            .strength(1.0F)
            .pushReaction(PushReaction.DESTROY)
            .noOcclusion()
            .instrument(NoteBlockInstrument.CUSTOM_HEAD)
        )
        .blockstate((ctx, provider) -> provider.simpleBlock(
            ctx.get(),
            new ModelFile.ExistingModelFile(
                ResourceLocation.withDefaultNamespace("block/skull"),
                provider.models().existingFileHelper
            )
        ))
        .item((block, properties) -> new StandingAndWallBlockItem(block, ViaBlocks.BLACK_CAT_WALL_HEAD.get(), properties, Direction.DOWN))
        .model((ctx, provider) -> provider.withExistingParent(
            ctx.getName(),
            ResourceLocation.withDefaultNamespace("item/template_skull")
        ))
        .build()
        .register();
    public static final BlockEntry<BlackCatWallHeadBlock> BLACK_CAT_WALL_HEAD = REGISTRUM
        .block("black_cat_wall_head", BlackCatWallHeadBlock::new)
        .setData(ProviderType.LANG, NonNullBiConsumer.noop())
        .properties(properties -> properties
            .lootFrom(ViaBlocks.BLACK_CAT_HEAD)
            .strength(1.0F)
            .pushReaction(PushReaction.DESTROY)
        )
        .blockstate((ctx, provider) -> provider.simpleBlock(
            ctx.get(),
            new ModelFile.ExistingModelFile(
                ResourceLocation.withDefaultNamespace("block/skull"),
                provider.models().existingFileHelper
            )
        ))
        .register();

    public static final BlockEntry<Block> CRIMSON_BOUND_MATTER_BLOCK = REGISTRUM
        .block("crimson_bound_matter_block", Block::new)
        .initialProperties(ModBlocks.VOID_MATTER_BLOCK)
        .blockstate((ctx, provider) -> provider.simpleBlock(
            ctx.get(),
            new ConfiguredModel(new ModelFile.UncheckedModelFile(ctx.getId().withPrefix("block/")))
        ))
        .recipe((ctx, provider) -> {
            BetterShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ctx.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ViaItems.CRIMSON_BOUND_MATTER)
                .save(provider);
        })
        .simpleItem()
        .register();

    public static void init() {
    }
}
