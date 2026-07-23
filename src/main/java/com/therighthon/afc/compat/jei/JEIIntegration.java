package com.therighthon.afc.compat.jei;

import com.therighthon.afc.common.recipe.AFCRecipeTypes;
import java.util.List;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import com.therighthon.afc.common.blocks.AFCBlocks;
import net.dries007.tfc.client.ClientHelpers;

@JeiPlugin
public class JEIIntegration implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(AFC.MOD_ID, "jei");
    }

    private static <T> RecipeType<T> type(String name, Class<T> tClass)
    {
        return RecipeType.create(AFC.MOD_ID, name, tClass);
    }

    public static final RecipeType<TreeTapRecipe> TREE_TAP = type("tree_tap", TreeTapRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration r)
    {
        IGuiHelper gui = r.getJeiHelpers().getGuiHelper();
        r.addRecipeCategories(new TreeTappingCategory(TREE_TAP, gui));
    }

    @Override
    public void registerRecipes(IRecipeRegistration r)
    {
        r.addRecipes(TREE_TAP, recipes(AFCRecipeTypes.TREE_TAPPING_RECIPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(AFCBlocks.TREE_TAP.get()), TREE_TAP);
    }

    private static <C extends Container, T extends Recipe<C>> List<T> recipes(net.minecraft.world.item.crafting.RecipeType<T> type)
    {
        return ClientHelpers.getLevelOrThrow().getRecipeManager().getAllRecipesFor(type);
    }
}
