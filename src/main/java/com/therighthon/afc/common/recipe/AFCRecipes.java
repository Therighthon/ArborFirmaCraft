package com.therighthon.afc.common.recipe;

import com.therighthon.afc.AFC;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AFCRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AFC.MOD_ID);

    public static final RegistryObject<RecipeSerializer<TreeTapRecipe>> TREE_TAPPING_SERIALIZER =
        SERIALIZERS.register("tree_tapping", () -> TreeTapRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
    }
}