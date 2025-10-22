package com.therighthon.afc.common.recipe;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;

public class TapInventory implements RecipeInput
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

    @Override
    public ItemStack getItem(int i)
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }
}
