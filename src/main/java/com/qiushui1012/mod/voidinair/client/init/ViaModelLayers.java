package com.qiushui1012.mod.voidinair.client.init;

import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ViaModelLayers {
    public static final ModelLayerLocation BLACK_CAT_HEAD = new ModelLayerLocation(SimpleSkullBlockType.VOID.id(), "head");

    @SubscribeEvent
    public static void on(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BLACK_CAT_HEAD, ViaModelLayers::createBlackCatHeadLayDef);
    }

    @SubscribeEvent
    public static void on(EntityRenderersEvent.CreateSkullModels event) {
        SkullBlockRenderer.SKIN_BY_TYPE.put(
            SimpleSkullBlockType.VOID,
            ResourceLocation.withDefaultNamespace("textures/entity/cat/all_black.png")
        );
        event.registerSkullModel(
            SimpleSkullBlockType.VOID,
            new SkullModel(event.getEntityModelSet().bakeLayer(ViaModelLayers.BLACK_CAT_HEAD))
        );
    }

    private static LayerDefinition createBlackCatHeadLayDef() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        part.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                .addBox("main", -2.5F, -5.5F, -1.0F, 5.0F, 4.0F, 5.0F, CubeDeformation.NONE)
                .addBox("nose", -1.5F, -3.5F, -2.0F, 3, 2, 2, CubeDeformation.NONE, 0, 24)
                .addBox("ear1", -2.0F, -6.5F, 2.0F, 1, 1, 2, CubeDeformation.NONE, 0, 10)
                .addBox("ear2", 1.0F, -6.5F, 2.0F, 1, 1, 2, CubeDeformation.NONE, 6, 10),
            PartPose.ZERO
        );

        return LayerDefinition.create(mesh, 64, 32);
    }
}
