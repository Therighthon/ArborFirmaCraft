package com.therighthon.afc.common.recipe;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.inventory.EmptyInventory;

public class TapInventory implements EmptyInventory
{
    protected final BlockState state;
    protected final BlockIngredient recipeBlock;

    public TapInventory(BlockState state, BlockIngredient recipeBlock)
    {
        this.state = state;
        this.recipeBlock = recipeBlock;
    }

    public BlockIngredient getBlockIngredient() {
        return this.recipeBlock;
    }

    public BlockState getState()
    {
        return state;
    }

}
