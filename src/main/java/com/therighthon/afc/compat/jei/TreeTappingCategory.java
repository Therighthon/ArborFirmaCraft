package com.therighthon.afc.compat.jei;

import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;

public class TreeTappingCategory extends BaseRecipeCategory<TreeTapRecipe>
{

    public TreeTappingCategory(RecipeType<TreeTapRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(118, 26), new ItemStack(AFCBlocks.TREE_TAP.get()));

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TreeTapRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 6, 5)
            .addIngredients(collapse(recipe.getBlockIngredient()))
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5)
            .addFluidStack(recipe.getOutput().getFluid(), 1000)
            .setBackground(slot, -1, -1);
    }

    @Override
    public void draw(TreeTapRecipe recipe, IRecipeSlotsView recipeSlots, GuiGraphics stack, double mouseX, double mouseY)
    {
        arrow.draw(stack, 48, 3);
    }
}
