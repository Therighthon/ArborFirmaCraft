package com.therighthon.afc.common.recipe;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import net.dries007.tfc.common.recipes.inventory.EmptyInventory;

public class TapInventory implements EmptyInventory
{
    protected final BlockPos pos;
    protected final BlockState state;
    protected final FluidStack output;

    public TapInventory(BlockPos pos, BlockState state, FluidStack output)
    {
        this.pos = pos;
        this.state = state;
        this.output = output;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public BlockState getState()
    {
        return state;
    }

    public FluidStack getOutput()
    {
        return output;
    }

}
