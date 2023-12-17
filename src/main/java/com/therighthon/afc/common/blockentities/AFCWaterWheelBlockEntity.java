package com.therighthon.afc.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.rotation.WaterWheelBlockEntity;

public class AFCWaterWheelBlockEntity extends WaterWheelBlockEntity
{
    public AFCWaterWheelBlockEntity(BlockPos pos, BlockState state)
    {
        super(AFCBlockEntities.WATER_WHEEL.get(), pos, state);
    }
}
