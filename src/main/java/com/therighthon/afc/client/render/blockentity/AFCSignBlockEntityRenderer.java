package com.therighthon.afc.client.render.blockentity;

import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.render.blockentity.TFCSignBlockEntityRenderer;
import net.dries007.tfc.common.blocks.wood.Wood;

public class AFCSignBlockEntityRenderer extends TFCSignBlockEntityRenderer
{

    public AFCSignBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context, AFCBlocks.WOODS.entrySet()
            .stream()
            .map(entry -> new SignModelData(
                TerraFirmaCraft.MOD_ID, //Crashes on startup with AFC, started up but changes nothing with TFC
                entry.getKey().getSerializedName(),
                entry.getValue().get(Wood.BlockType.SIGN).get(),
                entry.getValue().get(Wood.BlockType.WALL_SIGN).get()
            )));
    }
}
