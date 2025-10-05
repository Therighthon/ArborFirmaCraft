/*
 * This file was reproduced in part from net.dries007.tfc.data.Providers.BuiltinRecipes
 * on October 4, 2025, and has been modified from the original work
 *
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package com.therighthon.afc.datagen;

import com.therighthon.afc.datagen.recipes.AnvilRecipes;
import com.therighthon.afc.datagen.recipes.CraftingRecipes;
import com.therighthon.afc.datagen.recipes.PotRecipes;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.item.crafting.Recipe;

import net.dries007.tfc.util.Helpers;

public final class AFCRecipeProvider extends VanillaRecipeProvider implements
    AnvilRecipes,
    CraftingRecipes,
    PotRecipes
{
    final CompletableFuture<?> before;

    RecipeOutput output;
    HolderLookup.Provider lookup;

    public AFCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
        this.before = registries;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output, HolderLookup.Provider lookup)
    {
        this.lookup = lookup;
        return before;
    }

    @Override
    public HolderLookup.Provider lookup()
    {
        return lookup;
    }

    @Override
    public void add(String prefix, String name, Recipe<?> recipe)
    {
        output.accept(Helpers.identifier((prefix + "/" + name).toLowerCase(Locale.ROOT)), recipe, null);
    }

    @Override
    public void remove(String... names)
    {
    }

    @Override
    public void replace(String name, Recipe<?> recipe)
    {
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput)
    {
        this.output = recipeOutput;

        craftingRecipes();
        anvilRecipes();
        potRecipes();
    }
}
