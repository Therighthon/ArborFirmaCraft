package com.therighthon.afc.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.TFCSignBlockEntity;

public class AFCSignBlockEntity extends SignBlockEntity
{
    //TODO: Delete generic constructor
    public AFCSignBlockEntity(BlockPos pos, BlockState blockState)
    {
        super(pos, blockState);
    }
    //TODO: Signs
//    public AFCSignBlockEntity(BlockPos pos, BlockState state)
//    {
//        super(AFCBlockEntities.SIGN.get(), pos, state);
//    }
//
//    @Override
//    public BlockEntityType<?> getType()
//    {
//        return AFCBlockEntities.SIGN.get();
//    }
}
