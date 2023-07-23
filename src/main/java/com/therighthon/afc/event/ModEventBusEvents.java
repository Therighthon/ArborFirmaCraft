package com.therighthon.afc.event;

import com.therighthon.afc.AFC;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AFC.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents
{
    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event)
    {
        Registry.register(Registry.RECIPE_TYPE, TreeTapRecipe.Type.ID, TreeTapRecipe.Type.INSTANCE);
    }
}
