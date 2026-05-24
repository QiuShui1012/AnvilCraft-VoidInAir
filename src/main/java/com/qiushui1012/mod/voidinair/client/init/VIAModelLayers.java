package com.qiushui1012.mod.voidinair.client.init;

import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class VIAModelLayers {
    public static final ModelLayerLocation BLACK_CAT_HEAD = new ModelLayerLocation(SimpleSkullBlockType.VOID.id(), "main");

    @SubscribeEvent
    public static void on(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BLACK_CAT_HEAD, VIAModelLayers::createBlackCatHeadLayDef);
    }

    @SubscribeEvent
    public static void on(EntityRenderersEvent.CreateSkullModels event) {
        event.registerSkullModel(
            SimpleSkullBlockType.VOID,
            VIAModelLayers.BLACK_CAT_HEAD,
            Identifier.withDefaultNamespace("textures/entity/cat/cat_all_black.png")
        );
    }

    private static LayerDefinition createBlackCatHeadLayDef() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        part.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                .addBox("main", -2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F)
                .addBox("nose", -1.5F, -0.001F, -4.0F, 3, 2, 2, 0, 24)
                .addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2, 0, 10)
                .addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2, 6, 10),
            PartPose.offset(0F, -2.125F, 2F)
        );

        return LayerDefinition.create(mesh, 64, 32);
    }
}
