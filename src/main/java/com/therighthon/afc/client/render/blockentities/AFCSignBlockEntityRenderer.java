package com.therighthon.afc.client.render.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.therighthon.afc.AFCHelpers;
import com.therighthon.afc.common.blocks.AFCWood;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.mixin.client.accessor.SignRendererAccessor;
import net.dries007.tfc.util.Helpers;

public class AFCSignBlockEntityRenderer extends SignRenderer
{
    public static final Map<WoodType, Function<BlockEntityRendererProvider.Context, SignRenderer.SignModel>> MODELS = RenderHelpers.mapOf(map -> {
        for (AFCWood wood : AFCWood.values())
            map.accept(
                wood::getVanillaWoodType,
                context -> new SignRenderer.SignModel(context.bakeLayer(AFCHelpers.layerId("sign/" + wood.getSerializedName())))
            );
        for (Wood wood : Wood.values())
            map.accept(
                wood::getVanillaWoodType,
                context -> new SignModel(context.bakeLayer(RenderHelpers.layerId("sign/" + wood.getSerializedName())))
            );
    });

    private final Map<WoodType, SignRenderer.SignModel> signModels;

    public AFCSignBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
        signModels = Helpers.mapValue(MODELS, f -> f.apply(context));
    }

    @Override
    public void render(SignBlockEntity sign, float partialTicks, PoseStack poseStack, MultiBufferSource source, int packedLight, int overlay)
    {
        BlockState blockstate = sign.getBlockState();
        SignBlock signblock = (SignBlock) blockstate.getBlock();
        WoodType woodType = SignBlock.getWoodType(signblock);
        SignRenderer.SignModel model = this.signModels.get(woodType);
        model.stick.visible = blockstate.getBlock() instanceof StandingSignBlock;
        ((SignRendererAccessor) this).invoke$renderSignWithText(sign, poseStack, source, packedLight, overlay, blockstate, signblock, woodType, model);
    }
}
