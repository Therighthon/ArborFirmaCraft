package com.therighthon.afc.common.recipe;

import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.dries007.tfc.common.recipes.TFCRecipeSerializers;

public class AFCRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, AFC.MOD_ID);

    //TODO: Tree taps
//    public static final TFCRecipeSerializers.Id<TreeTapRecipe> TREE_TAPPING_SERIALIZER =
//        SERIALIZERS.register("tree_tapping", () -> TreeTapRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
    }
}