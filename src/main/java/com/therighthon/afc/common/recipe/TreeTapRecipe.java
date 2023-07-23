package com.therighthon.afc.common.recipe;

import com.google.gson.JsonObject;
import com.therighthon.afc.AFC;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import net.dries007.tfc.common.recipes.ISimpleRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredients;
import net.dries007.tfc.util.JsonHelpers;
import net.dries007.tfc.util.collections.IndirectHashCollection;

public class TreeTapRecipe implements ISimpleRecipe<TapInventory>
{
    private static Boolean requiresNaturalLog;
    private final ResourceLocation id;
    private final FluidStack output;
    private final BlockIngredient blockIngredient;
//    private final NonNullList<Month> validMonths;

    //TODO: make work only during certain months
    public TreeTapRecipe(ResourceLocation id, FluidStack output, BlockIngredient blockIngredient) //, NonNullList<Month> validMonths
    {
        this.id = id;
        this.output = output;
        this.blockIngredient = blockIngredient;
        requiresNaturalLog = Boolean.TRUE; //TODO: make custom per recipe
//        this.validMonths = validMonths;
    }

    public BlockIngredient getBlockIngredient()
    {
        return this.blockIngredient;
    }

    public static final IndirectHashCollection<Block, TreeTapRecipe> CACHE = IndirectHashCollection.createForRecipe(recipe -> recipe.getBlockIngredient().getValidBlocks(), AFCRecipeTypes.TREE_TAPPING_RECIPE);

    public static TreeTapRecipe getRecipe(BlockState state)
    {
        AFC.LOGGER.debug("Hello there!");
        for (TreeTapRecipe recipe : CACHE.getAll(state.getBlock()))
        {
            AFC.LOGGER.debug("Printing cache:");
            AFC.LOGGER.debug(state.getBlock().toString());
            if (recipe.matches(state))
            {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public boolean matches(TapInventory inv, Level level)
    {
        return matches(inv.getState());
    }

    public boolean matches(BlockState state)
    {
        return blockIngredient.test(state);
    }

    public FluidStack getOutput()
    {
        return output;
    }

    public static Boolean requiresNaturalLog()
    {
        return requiresNaturalLog;
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
        return AFCRecipeTypes.TREE_TAPPING_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<TreeTapRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
            new ResourceLocation(AFC.MOD_ID,"tree_tapping");

        @Override
        public TreeTapRecipe fromJson(ResourceLocation id, JsonObject json) {
            AFC.LOGGER.debug("Loaded treetap recipe:");
            AFC.LOGGER.debug(String.valueOf(id)); //Get an idea if the recipes are being loaded
            final FluidStack output = json.has("result_fluid") ? JsonHelpers.getFluidStack(json.getAsJsonObject("result_fluid")) : FluidStack.EMPTY;
            BlockIngredient blockIngredient = BlockIngredients.fromJson(JsonHelpers.get(json, "input_block"));

            return new TreeTapRecipe(id, output, blockIngredient);
        }

        @Override
        public TreeTapRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            final BlockIngredient recipeBlock = BlockIngredients.fromNetwork(buffer);
            final FluidStack output = buffer.readFluidStack();
            return new TreeTapRecipe(id, output, recipeBlock);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TreeTapRecipe recipe) {
           recipe.blockIngredient.toNetwork(buffer);
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
