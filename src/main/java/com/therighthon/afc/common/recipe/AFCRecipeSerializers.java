package com.therighthon.afc.common.recipe;

import java.util.function.Supplier;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;


import net.dries007.tfc.common.recipes.TFCRecipeSerializers;

import static com.therighthon.afc.AFC.MOD_ID;

public class AFCRecipeSerializers
{
    //TODO: Tree taps
//    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MOD_ID);
//
//    public static final TFCRecipeSerializers.Id<TreeTapRecipe> TREE_TAPPING = register("tree_tapping", TreeTapRecipe.CODEC, TreeTapRecipe.STREAM_CODEC);
//
//    private static <R extends Recipe<?>> TFCRecipeSerializers.Id<R> register(String name, MapCodec<R> codec, StreamCodec<RegistryFriendlyByteBuf, R> stream)
//    {
//        return register(name, new RecipeSerializerImpl<>(codec, stream));
//    }
//
//    private static <R extends Recipe<?>> TFCRecipeSerializers.Id<R> register(String name, RecipeSerializer<R> serializer)
//    {
//        return new Id<>(RECIPE_SERIALIZERS.register(name, () -> serializer));
//    }

}
