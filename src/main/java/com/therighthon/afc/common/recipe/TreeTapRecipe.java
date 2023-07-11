package com.therighthon.afc.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.therighthon.afc.AFC;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.intellij.lang.annotations.JdkConstants;

import net.dries007.tfc.common.recipes.ISimpleRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredients;
import net.dries007.tfc.util.JsonHelpers;
import net.dries007.tfc.util.calendar.Month;

public class TreeTapRecipe implements ISimpleRecipe<TapInventory>
{
    private final ResourceLocation id;
    private final FluidStack output;
    private final BlockIngredient recipeBlock;
//    private final NonNullList<Month> validMonths;

//TODO: make work only during certain months
    public TreeTapRecipe(ResourceLocation id, FluidStack output, BlockIngredient recipeBlock) //, NonNullList<Month> validMonths
    {
        this.id = id;
        this.output = output;
        this.recipeBlock = recipeBlock;
//        this.validMonths = validMonths;
    }

    @Override
    public boolean matches(TapInventory inv, Level level)
    {
        return matches(inv.getState());
    }

    public boolean matches(BlockState state)
    {
        return recipeBlock.test(state);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<TreeTapRecipe>
    {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "tree_tapping";
    }



    public static class Serializer implements RecipeSerializer<TreeTapRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
            new ResourceLocation(AFC.MOD_ID,"tree_tapping");

        @Override
        public TreeTapRecipe fromJson(ResourceLocation id, JsonObject json) {
            final FluidStack output = json.has("result_fluid") ? JsonHelpers.getFluidStack(json.getAsJsonObject("result_fluid")) : FluidStack.EMPTY;
            BlockIngredient recipeBlock = BlockIngredients.fromJson(JsonHelpers.get(json, "input_block"));

            return new TreeTapRecipe(id, output, recipeBlock);
        }

        @Override
        public TreeTapRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            final BlockIngredient recipeBlock = BlockIngredients.fromNetwork(buffer);
            final FluidStack output = buffer.readFluidStack();
            return new TreeTapRecipe(id, output, recipeBlock);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TreeTapRecipe recipe) {
           recipe.recipeBlock.toNetwork(buffer);
           buffer.writeFluidStack(recipe.output);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
