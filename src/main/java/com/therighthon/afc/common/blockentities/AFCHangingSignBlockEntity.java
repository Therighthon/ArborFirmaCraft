package com.therighthon.afc.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AFCHangingSignBlockEntity extends SignBlockEntity
{
    public AFCHangingSignBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return AFCBlockEntities.HANGING_SIGN.get();
    }
}
