package com.therighthon.afc.compat.jei;

import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;

public class TreeTappingCategory extends BaseRecipeCategory<TreeTapRecipe>
{

    public TreeTappingCategory(RecipeType<TreeTapRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(118, 50), new ItemStack(AFCBlocks.TREE_TAP.get()));

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

        var font = Minecraft.getInstance().font;

        Component tempRange = Component.translatable("tooltip.afc.tap.temp_range")
            .withStyle(ChatFormatting.WHITE)
            .append(Component.literal(String.format("%.1f°C", recipe.getMinTemp())).withStyle(ChatFormatting.AQUA))
            .append(Component.literal(" - ").withStyle(ChatFormatting.WHITE))
            .append(Component.literal(String.format("%.1f°C", recipe.getMaxTemp())).withStyle(ChatFormatting.GOLD));

        stack.drawString(font, tempRange, 6, 28, 0xFF404040, true);

        if (recipe.springOnly())
        {
            Component springOnly = Component.translatable("tfc.tooltip.calendar_season",
                    Component.translatable(String.format("%s", "tfc.enum.season.april")).withStyle(ChatFormatting.GREEN)
            ).withStyle(ChatFormatting.WHITE);
            stack.drawString(font, springOnly, 6, 40, 0xFF404040, true);
        }
    }
}
