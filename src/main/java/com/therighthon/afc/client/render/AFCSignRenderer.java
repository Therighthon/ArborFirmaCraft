package com.therighthon.afc.client.render;
    import com.therighthon.afc.common.blocks.AFCBlocks;
    import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

    import net.dries007.tfc.client.render.blockentity.TFCSignBlockEntityRenderer;
    import net.dries007.tfc.common.blocks.wood.Wood;

public class AFCSignRenderer extends TFCSignBlockEntityRenderer
{
    public AFCSignRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context, AFCBlocks.WOODS.entrySet().stream().map((entry) -> new SignModelData("tfc", entry.getKey().getSerializedName(), entry.getValue().get(Wood.BlockType.SIGN).get(), entry.getValue().get(Wood.BlockType.WALL_SIGN).get())));
    }
}
