package com.therighthon.afc.client.render.colors;

import java.util.Map;
import java.util.stream.Stream;
import com.google.common.collect.ImmutableMap;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import net.dries007.tfc.client.render.blockentity.TFCSignBlockEntityRenderer;

public class AFCSignBlockEntityRenderer extends SignRenderer
{
    private final Map<WoodType, SignModel> signModels;

    public AFCSignBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this(context, AFCBlocks.WOODS.keySet()
            .stream()
            .map(map -> new AFCSignBlockEntityRenderer.SignModelData(
                AFC.MOD_ID,
                map.getSerializedName(),
                map.getVanillaWoodType()
            )));
    }

    public AFCSignBlockEntityRenderer(BlockEntityRendererProvider.Context context, Stream<AFCSignBlockEntityRenderer.SignModelData> blocks)
    {
        super(context);

        ImmutableMap.Builder<WoodType, SignModel> modelBuilder = ImmutableMap.builder();
        blocks.forEach(data -> {
            modelBuilder.put(data.type, new SignModel(context.bakeLayer(new ModelLayerLocation(new ResourceLocation(data.domain, "sign/" + data.name), "main"))));
        });
        this.signModels = modelBuilder.build();
    }

    public record SignModelData(String domain, String name, WoodType type) {}
}