package com.therighthon.afc.client.render.colors;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;

public class AFCSignBlockEntityRenderer extends SignRenderer
{
    //TODO: Remove generic constructor
    public AFCSignBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }
    //TODO: Hanging Signs
//    private final Map<WoodType, SignModel> signModels;

//    public AFCSignBlockEntityRenderer(BlockEntityRendererProvider.Context context)
//    {
//        this(context, AFCBlocks.WOODS.keySet()
//            .stream()
//            .map(map -> new AFCSignBlockEntityRenderer.SignModelData(
//                AFC.MOD_ID,
//                map.getSerializedName(),
//                map.getVanillaWoodType()
//            )));
//    }

//    public AFCSignBlockEntityRenderer(BlockEntityRendererProvider.Context context, Stream<AFCSignBlockEntityRenderer.SignModelData> blocks)
//    {
//        super(context);
//
//        ImmutableMap.Builder<WoodType, SignModel> modelBuilder = ImmutableMap.builder();
//        blocks.forEach(data -> {
//            modelBuilder.put(data.type, new SignModel(context.bakeLayer(new ModelLayerLocation(new ResourceLocation(data.domain, "sign/" + data.name), "main"))));
//        });
//        this.signModels = modelBuilder.build();
//    }
//
//    public record SignModelData(String domain, String name, WoodType type) {}
}