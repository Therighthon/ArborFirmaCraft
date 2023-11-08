package com.therighthon.afc.common.recipe;

import com.google.gson.JsonObject;
import com.therighthon.afc.AFC;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.antlr.runtime.tree.Tree;

import net.dries007.tfc.common.recipes.ISimpleRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.util.JsonHelpers;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.collections.IndirectHashCollection;

public class TreeTapRecipe implements ISimpleRecipe<TapInventory>
{
    private final Boolean requiresNaturalLog;
    private final ResourceLocation id;
    private final FluidStack output;
    private final BlockIngredient blockIngredient;
    private final Boolean springOnly;
    private final float minTemp;
    private final float maxTemp;

    public TreeTapRecipe(ResourceLocation id, FluidStack output, BlockIngredient blockIngredient, Boolean requiresNaturalLog, Boolean springOnly, float minTemp, float maxTemp) //, NonNullList<Month> validMonths
    {
        this.id = id;
        this.output = output;
        this.blockIngredient = blockIngredient;
        this.requiresNaturalLog = requiresNaturalLog;
        this.springOnly = springOnly;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;

    }

    public static final IndirectHashCollection<Block, TreeTapRecipe> CACHE = IndirectHashCollection.createForRecipe(recipe -> recipe.getBlockIngredient().blocks(), AFCRecipeTypes.TREE_TAPPING_RECIPE);

    public static TreeTapRecipe getRecipe(BlockState state)
    {
        for (TreeTapRecipe recipe : CACHE.getAll(state.getBlock()))
        {
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

    public float getMinTemp()
    {
        return this.minTemp;
    }

    public float getMaxTemp()
    {
        return this.maxTemp;
    }

    public Boolean springOnly()
    {
        return this.springOnly;
    }

    public BlockIngredient getBlockIngredient()
    {
        return this.blockIngredient;
    }

    public FluidStack getOutput()
    {
        return output;
    }

    public Boolean requiresNaturalLog()
    {
        return this.requiresNaturalLog;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess)
    {
        return null;
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
            final FluidStack output = json.has("result_fluid") ? JsonHelpers.getFluidStack(json.getAsJsonObject("result_fluid")) : FluidStack.EMPTY;
            final float minTemp = json.has("minimum_temperature") ? JsonHelpers.getAsFloat(json, "minimum_temperature") : -50f;
            final float maxTemp = json.has("maximum_temperature") ? JsonHelpers.getAsFloat(json, "maximum_temperature") : 50f;
            final Boolean requiresNaturalLog = json.has("requires_natural_log") ? JsonHelpers.getAsBoolean(json, "requires_natural_log") : Boolean.TRUE;
            final Boolean springOnly = json.has("spring_only") ? JsonHelpers.getAsBoolean(json, "spring_only") : Boolean.FALSE;

            BlockIngredient blockIngredient = BlockIngredient.fromJson(JsonHelpers.get(json, "input_block"));

            return new TreeTapRecipe(id, output, blockIngredient, requiresNaturalLog, springOnly, minTemp, maxTemp);
        }

        @Nullable
        @Override
        public TreeTapRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            final BlockIngredient recipeBlock = BlockIngredient.fromNetwork(buffer);
            final FluidStack output = buffer.readFluidStack();
            final float minTemp = buffer.readFloat();
            final float maxTemp = buffer.readFloat();
            final Boolean springOnly = buffer.readBoolean();
            final Boolean requiresNaturalLog = buffer.readBoolean();

            return new TreeTapRecipe(id, output, recipeBlock, requiresNaturalLog, springOnly, minTemp, maxTemp);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TreeTapRecipe recipe) {
           recipe.blockIngredient.toNetwork(buffer);
           buffer.writeFluidStack(recipe.output);
           buffer.writeFloat(recipe.minTemp);
           buffer.writeFloat(recipe.maxTemp);
           buffer.writeBoolean(recipe.springOnly);
           buffer.writeBoolean(recipe.requiresNaturalLog);
        }

    }

}
