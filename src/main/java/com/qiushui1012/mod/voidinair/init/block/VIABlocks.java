package com.qiushui1012.mod.voidinair.init.block;

import com.mojang.math.Transformation;
import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import com.qiushui1012.mod.voidinair.block.production.VoidFountainBlock;
import com.qiushui1012.mod.voidinair.init.item.VIAItemGroup;
import dev.anvilcraft.lib.v2.registrum.providers.DataGenContext;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumBlockModelGenerator;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumItemModelGenerator;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.anvilcraft.lib.v2.util.nullness.NonNullBiConsumer;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.special.SkullSpecialRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.material.PushReaction;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Optional;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

@SuppressWarnings("Convert2Lambda")
public class VIABlocks {
    static {
        REGISTRUM.defaultCreativeTab(VIAItemGroup.INSTANCE.getKey());
    }

    public static final BlockEntry<VoidFountainBlock> VOID_FOUNTAIN = REGISTRUM
        .block("void_fountain", VoidFountainBlock::new)
        .initialProperties(ModBlocks.MINERAL_FOUNTAIN)
        .blockstate(() -> new NonNullBiConsumer<>() {
            @Override
            public void accept(
                DataGenContext<Block, VoidFountainBlock> ctx,
                RegistrumBlockModelGenerator generator
            ) {
                VoidFountainBlock block = ctx.get();
                Identifier model = generator.withParent(ModelTemplates.CUBE_BOTTOM_TOP)
                    .texture(TextureSlot.BOTTOM, AnvilCraft.of("block/sturdy_deepslate"))
                    .texture(TextureSlot.TOP, AncVoidInAir.of("block/void_fountain_top"))
                    .texture(TextureSlot.SIDE, AncVoidInAir.of("block/void_fountain"))
                    .build(block);
                generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(model)));
            }
        })
        .simpleItem()
        .register();

    public static final BlockEntry<SkullBlock> BLACK_CAT_HEAD = REGISTRUM
        .block("black_cat_head", properties -> new SkullBlock(SimpleSkullBlockType.VOID, properties))
        .properties(properties -> properties.strength(1.0F).pushReaction(PushReaction.DESTROY).noOcclusion())
        .blockstate(() -> new NonNullBiConsumer<>() {
            @Override
            public void accept(DataGenContext<Block, SkullBlock> ctx, RegistrumBlockModelGenerator generator) {
                @SuppressWarnings("deprecation")
                MultiVariant skull = BlockModelGenerators.plainVariant(ModelLocationUtils.decorateBlockModelLocation("skull"));
                generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(ctx.get(), skull));
            }
        })
        .item((block, properties) -> new StandingAndWallBlockItem(block, VIABlocks.BLACK_CAT_WALL_HEAD.get(), Direction.DOWN, properties))
        .model(() -> new NonNullBiConsumer<>() {
            @Override
            public void accept(DataGenContext<Item, StandingAndWallBlockItem> ctx, RegistrumItemModelGenerator generator) {
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
            }
        })
        .build()
        .register();

    public static final BlockEntry<WallSkullBlock> BLACK_CAT_WALL_HEAD = REGISTRUM
        .block("black_cat_wall_head", properties -> new WallSkullBlock(SimpleSkullBlockType.VOID, properties))
        .properties(properties -> properties
            .overrideLootTable(Optional.of(ResourceKey.create(Registries.LOOT_TABLE, AncVoidInAir.of("blocks/black_cat_head"))))
            .overrideDescription(Util.makeDescriptionId("block", AncVoidInAir.of("blocks/black_cat_head")))
            .strength(1.0F)
            .pushReaction(PushReaction.DESTROY)
        )
        .blockstate(() -> new NonNullBiConsumer<>() {
            @Override
            public void accept(DataGenContext<Block, WallSkullBlock> ctx, RegistrumBlockModelGenerator generator) {
                @SuppressWarnings("deprecation")
                MultiVariant skull = BlockModelGenerators.plainVariant(ModelLocationUtils.decorateBlockModelLocation("skull"));
                generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(ctx.get(), skull));
            }
        })
        .register();

    public static void init() {
    }
}
