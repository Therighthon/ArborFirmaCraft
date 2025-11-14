package com.therighthon.afc.common.items;

import com.therighthon.afc.common.blocks.AFCBlocks;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.component.block.Barrel;

import static net.dries007.tfc.common.capabilities.ItemCapabilities.*;

public final class AFCItemCapabilities
{
    public static void register(RegisterCapabilitiesEvent event)
    {
        final ItemLike[] barrels = AFCBlocks.WOODS.values()
            .stream()
            .map(m -> m.get(Wood.BlockType.BARREL).asItem())
            .toArray(ItemLike[]::new);

        event.registerItem(FLUID, (stack, context) -> new Barrel(stack), barrels);

    }
}
