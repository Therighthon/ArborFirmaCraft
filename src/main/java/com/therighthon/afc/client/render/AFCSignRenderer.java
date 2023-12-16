package com.therighthon.afc.client.render;
    import com.mojang.blaze3d.vertex.PoseStack;
    import com.therighthon.afc.AFC;
    import com.therighthon.afc.common.blocks.AFCBlocks;
    import net.minecraft.client.renderer.MultiBufferSource;
    import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
    import net.minecraft.world.level.block.SignBlock;
    import net.minecraft.world.level.block.StandingSignBlock;
    import net.minecraft.world.level.block.entity.SignBlockEntity;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.world.level.block.state.properties.WoodType;

    import net.dries007.tfc.TerraFirmaCraft;
    import net.dries007.tfc.client.render.blockentity.TFCSignBlockEntityRenderer;
    import net.dries007.tfc.common.blocks.TFCBlocks;
    import net.dries007.tfc.common.blocks.wood.Wood;
    import net.dries007.tfc.mixin.client.accessor.SignRendererAccessor;

public class AFCSignRenderer extends TFCSignBlockEntityRenderer
{
    public AFCSignRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context, AFCBlocks.WOODS.keySet()
            .stream()
            .map(map -> new SignModelData(
                TerraFirmaCraft.MOD_ID,
                map.getSerializedName(),
                map.getVanillaWoodType()
            )));
    }
}
