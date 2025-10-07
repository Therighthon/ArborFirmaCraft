package com.therighthon.afc.common.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.therighthon.afc.AFC;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.ISimpleRecipe;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.util.collections.IndirectHashCollection;

public class TreeTapRecipe implements ISimpleRecipe<TapInventory>
{

    private final Boolean requireNaturalLog;
    private final FluidStack output;
    private final BlockIngredient ingredient;
    private final Boolean springOnly;
    private final float minTemp;
    private final float maxTemp;


    public TreeTapRecipe(FluidStack output, BlockIngredient ingredient, Boolean requireNaturalLog, Boolean springOnly, float minTemp, float maxTemp) //, NonNullList<Month> validMonths
    {
        this.output = output;
        this.ingredient = ingredient;
        this.requireNaturalLog = requireNaturalLog;
        this.springOnly = springOnly;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public static final IndirectHashCollection<Block, TreeTapRecipe> CACHE = IndirectHashCollection.createForRecipe(recipe -> recipe.getIngredient().blocks(), AFCRecipeTypes.TREE_TAPPING_RECIPE);

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

    @Override
    public ItemStack assemble(TapInventory tapInventory, HolderLookup.Provider provider)
    {
        return null;
    }

    public boolean matches(BlockState state)
    {
        return ingredient.test(state);
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

    public BlockIngredient getIngredient()
    {
        return this.ingredient;
    }

    public FluidStack getOutput()
    {
        return output;
    }

    public Boolean requiresNaturalLog()
    {
        return this.requireNaturalLog;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }


    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return AFCRecipeSerializers.TREE_TAPPING.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return AFCRecipeTypes.TREE_TAPPING_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<TreeTapRecipe>
    {
        public static final MapCodec<TreeTapRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            FluidStack.CODEC.fieldOf("result_fluid").forGetter(c -> c.output),
            BlockIngredient.CODEC.fieldOf("input_block").forGetter(c -> c.ingredient),
            Codec.BOOL.fieldOf("require_natural_log").forGetter(c -> c.requireNaturalLog),
            Codec.BOOL.fieldOf("spring_only").forGetter(c -> c.springOnly),
            Codec.FLOAT.fieldOf("minimum_temperature").forGetter(c -> c.minTemp),
            Codec.FLOAT.fieldOf("maximum_temperature").forGetter(c -> c.maxTemp)
            ).apply(i, TreeTapRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, TreeTapRecipe> STREAM_CODEC = StreamCodec.composite(
            FluidStack.STREAM_CODEC, c -> c.output,
            BlockIngredient.STREAM_CODEC, c -> c.ingredient,
            ByteBufCodecs.BOOL, c -> c.requireNaturalLog,
            ByteBufCodecs.BOOL, c -> c.springOnly,
            ByteBufCodecs.FLOAT, c -> c.minTemp,
            ByteBufCodecs.FLOAT, c -> c.maxTemp,
            TreeTapRecipe::new
        );

        @Override
        public MapCodec<TreeTapRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TreeTapRecipe> streamCodec()
        {
            return STREAM_CODEC;
        }
    }
}
