package com.qiushui1012.mod.voidinair.init.block;

import com.mojang.math.Transformation;
import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import com.qiushui1012.mod.voidinair.block.decoration.BlackCatHeadBlock;
import com.qiushui1012.mod.voidinair.block.decoration.BlackCatWallHeadBlock;
import com.qiushui1012.mod.voidinair.block.production.VoidFountainBlock;
import com.qiushui1012.mod.voidinair.block.utility.redstone.RandomTransmitterBlock;
import com.qiushui1012.mod.voidinair.block.workstation.BlackCatBlock;
import com.qiushui1012.mod.voidinair.init.item.ViaItemGroup;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.util.registrater.DataGenUtil;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.special.SkullSpecialRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Optional;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

public class ViaBlocks {
    static {
        REGISTRUM.defaultCreativeTab(ViaItemGroup.INSTANCE.getKey());
    }

    public static final BlockEntry<BlackCatBlock> BLACK_CAT = REGISTRUM
        .block("black_cat", BlackCatBlock::new)
        .initialProperties(ModBlocks.NEOFORGE)
        .properties(properties -> properties.sound(BlackCatBlock.SOUND_TYPE))
        .blockstate(DataGenUtil::horizontalFacingBlockInverted)
        .recipe((ctx, provider) -> {
            HolderGetter<Item> items = provider.getItems();
            ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ctx.get())
                .pattern("MMM")
                .pattern(" B ")
                .pattern("BBB")
                .define('B', ModBlocks.VOID_MATTER_BLOCK)
                .define('M', ModItems.VOID_MATTER)
                .unlockedBy(AnvilCraftDatagen.hasItem(ModItems.VOID_MATTER), AnvilCraftDatagen.has(items, ModItems.VOID_MATTER))
                .unlockedBy(
                    AnvilCraftDatagen.hasItem(ModBlocks.VOID_MATTER_BLOCK),
                    AnvilCraftDatagen.has(items, ModBlocks.VOID_MATTER_BLOCK)
                )
                .save(provider);
        })
        .simpleItem()
        .register();

    public static final BlockEntry<VoidFountainBlock> VOID_FOUNTAIN = REGISTRUM
        .block("void_fountain", VoidFountainBlock::new)
        .initialProperties(ModBlocks.MINERAL_FOUNTAIN)
        .blockstate(() -> (ctx, generator) -> {
            VoidFountainBlock block = ctx.get();
            Identifier model = generator.withParent(ModelTemplates.CUBE_BOTTOM_TOP)
                .texture(TextureSlot.BOTTOM, AnvilCraft.of("block/sturdy_deepslate"))
                .texture(TextureSlot.TOP, AncVoidInAir.of("block/void_fountain_top"))
                .texture(TextureSlot.SIDE, AncVoidInAir.of("block/void_fountain"))
                .build(block);
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(model)));
        })
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
        .blockstate(() -> (ctx, generator) -> {
            @SuppressWarnings("deprecation")
            MultiVariant skull = BlockModelGenerators.plainVariant(ModelLocationUtils.decorateBlockModelLocation("skull"));
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(ctx.get(), skull));
        })
        .item((block, properties) -> new StandingAndWallBlockItem(block, ViaBlocks.BLACK_CAT_WALL_HEAD.get(), Direction.DOWN, properties))
        .model(() -> (ctx, generator) -> {
            Identifier itemSkull = ModelLocationUtils.decorateItemModelLocation("template_skull");
            generator.itemModelOutput.accept(
                ctx.get().asItem(),
                ItemModelUtils.specialModel(
                    itemSkull,
                    new Transformation(
                        new Vector3f(0.55F, -0.05F, 0.6F),
                        new Quaternionf().rotationX((float) Math.PI),
                        new Vector3f(1.7F, 1.7F, 1.7F),
                        null
                    ),
                    new SkullSpecialRenderer.Unbaked(SimpleSkullBlockType.VOID)
                )
            );
        })
        .build()
        .register();
    public static final BlockEntry<BlackCatWallHeadBlock> BLACK_CAT_WALL_HEAD = REGISTRUM
        .block("black_cat_wall_head", BlackCatWallHeadBlock::new)
        .properties(properties -> properties
            .overrideLootTable(Optional.of(ResourceKey.create(Registries.LOOT_TABLE, AncVoidInAir.of("blocks/black_cat_head"))))
            .overrideDescription(Util.makeDescriptionId("block", AncVoidInAir.of("blocks/black_cat_head")))
            .strength(1.0F)
            .pushReaction(PushReaction.DESTROY)
        )
        .blockstate(() -> (ctx, generator) -> {
            @SuppressWarnings("deprecation")
            MultiVariant skull = BlockModelGenerators.plainVariant(ModelLocationUtils.decorateBlockModelLocation("skull"));
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(ctx.get(), skull));
        })
        .register();
    public static final BlockEntry<RandomTransmitterBlock> RANDOM_TRANSMITTER = REGISTRUM
        .block("random_transmitter", RandomTransmitterBlock::new)
        .properties(properties -> properties
            .pushReaction(PushReaction.DESTROY)
            .mapColor(MapColor.COLOR_RED)
            .requiresCorrectToolForDrops()
        )
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .blockstate(DataGenUtil::noExtraModelOrState)
        .item()
        .model(DataGenUtil::onlyInfo)
        .build()
        .register();

    public static void init() {
    }
}
