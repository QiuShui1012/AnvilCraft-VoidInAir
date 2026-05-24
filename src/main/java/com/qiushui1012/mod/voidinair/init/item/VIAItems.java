package com.qiushui1012.mod.voidinair.init.item;

import dev.anvilcraft.lib.v2.registrum.util.entry.ItemEntry;
import dev.dubhe.anvilcraft.api.amulet.type.AmuletType;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.item.amulet.AmuletItem;
import dev.dubhe.anvilcraft.util.registrater.DataGenUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

// CHECKSTYLE.SUPPRESS: AvoidStaticImport for +1 lines
import static com.qiushui1012.mod.voidinair.AncVoidInAir.REGISTRUM;

public class VIAItems {
    static {
        REGISTRUM.defaultCreativeTab(VIAItemGroup.INSTANCE.getKey());
    }

    public static final ItemEntry<Item> TOTEM_OF_VOID = REGISTRUM
        .item("totem_of_void", Item::new)
        .lang("Totem of Void")
        .properties((properties) -> properties
            .stacksTo(1)
            .rarity(Rarity.UNCOMMON)
            .component(DataComponents.DEATH_PROTECTION, VIADeathProtections.TOTEM_OF_VOID)
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

    public static final ItemEntry<? extends AmuletItem> VOID_AMULET = REGISTRUM
        .item("void_amulet", properties -> new AmuletItem(properties) {
            @Override
            public Holder<AmuletType> getType() {
                return VIAAmuletTypes.VOID;
            }
        })
        .properties(properties -> properties
            .component(DataComponents.DEATH_PROTECTION, VIADeathProtections.VOID_AMULET)
        )
        .model(DataGenUtil::flatItem)
        .register();

    public static void init() {
    }
}
