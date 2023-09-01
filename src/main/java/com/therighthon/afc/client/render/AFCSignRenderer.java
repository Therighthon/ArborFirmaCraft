package com.therighthon.afc.client.render;
    import com.therighthon.afc.common.blocks.AFCBlocks;
    import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

    import net.dries007.tfc.client.render.blockentity.TFCSignBlockEntityRenderer;
    import net.dries007.tfc.common.blocks.wood.Wood;

public class AFCSignRenderer extends TFCSignBlockEntityRenderer
{
    public AFCSignRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context, AFCBlocks.WOODS.keySet().stream().map(blockTypeRegistryObjectMap -> new SignModelData("tfc", blockTypeRegistryObjectMap.getSerializedName(), blockTypeRegistryObjectMap.getVanillaWoodType())));
    }
}
