package com.therighthon.afc.common.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.recipes.TFCRecipeTypes;

import static com.therighthon.afc.AFC.MOD_ID;

public class AFCRecipeTypes
{
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MOD_ID);

    public static final TFCRecipeTypes.Id<TreeTapRecipe> TREE_TAPPING_RECIPE = register("tree_tapping");

    private static <R extends Recipe<?>> TFCRecipeTypes.Id<R> register(String name)
    {
        return new TFCRecipeTypes.Id<>(RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString()
            {
                return name;
            }
        }));
    }
}
