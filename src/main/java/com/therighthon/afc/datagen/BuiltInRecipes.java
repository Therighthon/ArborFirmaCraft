package com.therighthon.afc.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;

public final class BuiltInRecipes extends VanillaRecipeProvider implements
    CraftingRecipes
{
    RecipeOutput output;
    HolderLookup.Provider lookup;

    public BuiltInRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<?> before)
    {
        super(output, lookup);
    }

    @Override
    public void buildRecipes(RecipeOutput output)
    {
        this.output = output;

        craftingRecipes();

        // TODO: Tree tap (and related) recipes
        // Anvil to craft, pot to boil syrup, tap recipes themselves
    }
}
