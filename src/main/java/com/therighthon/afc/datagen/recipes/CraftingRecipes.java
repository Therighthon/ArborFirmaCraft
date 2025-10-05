package com.therighthon.afc.datagen.recipes;

import com.google.common.collect.ImmutableMap;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.items.AFCItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.component.food.FoodTrait;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.AdvancedShapedRecipe;
import net.dries007.tfc.common.recipes.AdvancedShapelessRecipe;
import net.dries007.tfc.common.recipes.outputs.AddBaitToRodModifier;
import net.dries007.tfc.common.recipes.outputs.AddGlassModifier;
import net.dries007.tfc.common.recipes.outputs.AddPowderModifier;
import net.dries007.tfc.common.recipes.outputs.AddTraitModifier;
import net.dries007.tfc.common.recipes.outputs.CopyFoodModifier;
import net.dries007.tfc.common.recipes.outputs.CopyForgingBonusModifier;
import net.dries007.tfc.common.recipes.outputs.CopyInputModifier;
import net.dries007.tfc.common.recipes.outputs.CopyOldestFoodModifier;
import net.dries007.tfc.common.recipes.outputs.DamageCraftingRemainderModifier;
import net.dries007.tfc.common.recipes.outputs.ExtraProductModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.Metal;

public interface CraftingRecipes extends Recipes
{
    default void craftingRecipes()
    {
        for (AFCWood wood : AFCWood.values())
        {
            final var blocks = AFCBlocks.WOODS.get(wood);
            final var lumber = AFCItems.LUMBER.get(wood);
            final var planks = blocks.get(Wood.BlockType.PLANKS);

            recipe()
                .input('W', blocks.get(Wood.BlockType.STRIPPED_LOG))
                .input('G', TFCItems.GLUE)
                .pattern("WGW")
                .shaped(blocks.get(Wood.BlockType.AXLE), 4);
            recipe()
                .input('L', lumber)
                .pattern("L L", "L L", "LLL")
                .shaped(blocks.get(Wood.BlockType.BARREL));
            recipe()
                .input(blocks.get(Wood.BlockType.AXLE))
                .input(ingredientOf(Metal.STEEL, Metal.ItemType.INGOT))
                .shapeless(blocks.get(Wood.BlockType.BLADED_AXLE));
            // TODO: Boats
//            recipe()
//                .input('P', planks)
//                .pattern("P P", "PPP")
//                .shaped(AFCItems.BOATS.get(wood));
            recipe()
                .input('L', lumber)
                .input('S', Tags.Items.RODS_WOODEN)
                .pattern("LLL", "SSS", "LLL")
                .shaped(blocks.get(Wood.BlockType.BOOKSHELF));
            recipe()
                .input(planks)
                .shapeless(blocks.get(Wood.BlockType.BUTTON));
            recipe()
                .input('L', lumber)
                .pattern("LLL", "L L", "LLL")
                .shaped(blocks.get(Wood.BlockType.CHEST));
            recipe()
                .input(blocks.get(Wood.BlockType.CHEST))
                .input(Items.MINECART)
                .shapeless(AFCItems.CHEST_MINECARTS.get(wood));
            recipe()
                .input('L', lumber)
                .input('S', blocks.get(Wood.BlockType.STRIPPED_LOG))
                .input('M', TFCItems.BRASS_MECHANISMS)
                .input('A', blocks.get(Wood.BlockType.AXLE))
                .input('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("LSL", "MAR", "LSL")
                .shaped(blocks.get(Wood.BlockType.CLUTCH), 2);
            recipe()
                .input('L', lumber)
                .pattern("LL", "LL", "LL")
                .shaped(blocks.get(Wood.BlockType.DOOR), 2);
            recipe()
                .input('L', lumber)
                .input('S', blocks.get(Wood.BlockType.STRIPPED_LOG))
                .input('A', blocks.get(Wood.BlockType.AXLE))
                .pattern(" S ", "LAL", " S ")
                .shaped(blocks.get(Wood.BlockType.ENCASED_AXLE), 4);
            recipe()
                .input('P', planks)
                .input('L', lumber)
                .pattern("PLP", "PLP")
                .shaped(blocks.get(Wood.BlockType.FENCE), 8);
            recipe()
                .input('P', planks)
                .input('L', lumber)
                .pattern("LPL", "LPL")
                .shaped(blocks.get(Wood.BlockType.FENCE_GATE), 2);
            recipe()
                .input('L', lumber)
                .input('M', TFCItems.BRASS_MECHANISMS)
                .pattern(" L ", "LML", " L ")
                .shaped(blocks.get(Wood.BlockType.GEAR_BOX), 2);
            recipe()
                .input('L', lumber)
                .input('B', blocks.get(Wood.BlockType.BOOKSHELF))
                .pattern("LLL", " B ", " L ")
                .shaped(blocks.get(Wood.BlockType.LECTERN));
            recipe()
                .input('P', blocks.get(Wood.BlockType.LOG))
                .input('L', lumber)
                .pattern("PLP", "PLP")
                .shaped(blocks.get(Wood.BlockType.LOG_FENCE), 8);
            recipe()
                .input('L', lumber)
                .input('S', Tags.Items.RODS_WOODEN)
                .pattern("LLL", "LSL", "L L")
                .shaped(blocks.get(Wood.BlockType.LOOM));
            recipe("from_logs")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(logsTagOf(Registries.ITEM, wood))
                .damageInputs()
                .shapeless(lumber, 8);
            recipe("from_planks")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(planks)
                .damageInputs()
                .shapeless(lumber, 4);
            recipe("from_stairs")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(blocks.get(Wood.BlockType.STAIRS))
                .damageInputs()
                .shapeless(lumber, 3);
            recipe("from_slabs")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(blocks.get(Wood.BlockType.SLAB))
                .damageInputs()
                .shapeless(lumber, 2);
            recipe().to2x2(lumber, planks, 1);
            recipe()
                .input('L', lumber)
                .pattern("LL")
                .shaped(blocks.get(Wood.BlockType.PRESSURE_PLATE));
            recipe()
                .input('F', Tags.Items.FEATHERS)
                .input('D', Tags.Items.DYES_BLACK)
                .input('S', blocks.get(Wood.BlockType.SLAB))
                .input('W', planks)
                .pattern("F D", "SSS", "W W")
                .shaped(blocks.get(Wood.BlockType.SCRIBING_TABLE));
            recipe()
                .input('S', Tags.Items.TOOLS_SHEAR)
                .input('L', Tags.Items.LEATHERS)
                .input('P', planks)
                .input('G', blocks.get(Wood.BlockType.LOG))
                .pattern(" LS", "PPP", "G G")
                .shaped(blocks.get(Wood.BlockType.SEWING_TABLE));
            recipe()
                .input('L', lumber)
                .input('P', planks)
                .input('S', Tags.Items.RODS_WOODEN)
                .pattern("PPP", "L L", "S S")
                .shaped(blocks.get(Wood.BlockType.SHELF), 2);
            recipe()
                .input('L', lumber)
                .input('S', Tags.Items.RODS_WOODEN)
                .pattern("LLL", "LLL", " S ")
                .shaped(blocks.get(Wood.BlockType.SIGN), 3);
            recipe()
                .input('#', planks)
                .pattern("###")
                .shaped(blocks.get(Wood.BlockType.SLAB), 6);
            recipe()
                .input('#', planks)
                .pattern("#  ", "## ", "###")
                .shaped(blocks.get(Wood.BlockType.STAIRS), 8);
            recipe()
                .input('L', lumber)
                .input('S', Tags.Items.RODS_WOODEN)
                .pattern("  S", " SL", "SLL")
                .shaped(blocks.get(Wood.BlockType.SLUICE));
            recipe()
                .input('L', logsTagOf(Registries.ITEM, wood))
                .input('S', TFCTags.Items.TOOLS_SAW)
                .pattern("LS", "L ")
                .damageInputs()
                .source(0, 1)
                .shaped(AFCItems.SUPPORTS.get(wood), 8);
            recipe()
                .input('L', lumber)
                .pattern("LLL", "   ", "LLL")
                .shaped(blocks.get(Wood.BlockType.TOOL_RACK));
            recipe()
                .input('L', lumber)
                .pattern("LLL", "LLL")
                .shaped(blocks.get(Wood.BlockType.TRAPDOOR));
            recipe()
                .input(blocks.get(Wood.BlockType.CHEST))
                .input(Items.TRIPWIRE_HOOK)
                .shapeless(blocks.get(Wood.BlockType.TRAPPED_CHEST));
            recipe()
                .input('L', lumber)
                .input('P', planks)
                .input('A', blocks.get(Wood.BlockType.AXLE))
                .pattern("LPL", "PAP", "LPL")
                .shaped(blocks.get(Wood.BlockType.WATER_WHEEL));
            recipe().to2x2(blocks.get(Wood.BlockType.LOG), blocks.get(Wood.BlockType.WOOD), 3);
            recipe().to2x2(planks, blocks.get(Wood.BlockType.WORKBENCH), 1);
        }

        // TODO: Hanging signs
//        for (Metal metal : Metal.values())
//        {
//            if (metal.allParts())
//            {
//                for (AFCWood wood : AFCWood.values())
//                    recipe()
//                        .input('L', AFCItems.LUMBER.get(wood))
//                        .input('C', ingredientOf(metal, Metal.BlockType.CHAIN))
//                        .pattern("C C", "LLL", "LLL")
//                        .shaped(AFCItems.HANGING_SIGNS.get(wood).get(metal), 3);
//            }
//        }
    }

    /**
     * @return A builder for a new recipe with a name inferred from the output.
     */
    private Builder recipe()
    {
        return new Builder((name, r) -> {
            if (name != null) add(name, r);
            else add(r);
        });
    }

    /**
     * @return A builder for a new recipe with a name inferred from the output, plus a suffix. The suffix should not start with an underscore.
     */
    private Builder recipe(String suffix)
    {
        return new Builder((name, r) -> {
            assert !suffix.startsWith("_") : "recipe(String suffix) shouldn't start with an '_', it is added for you!";
            assert name == null : "Cannot use a named recipe and recipe(String suffix) at the same time!";
            add(nameOf(r.getResultItem(lookup()).getItem()) + "_" + suffix, r);
        });
    }

    /**
     * A recipe builder capable of building shaped, shapeless recipes, optionally with both output and remainder features
     * of advanced shaped / shapeless recipes. It has some preliminary validations to ensure legal recipes are built
     */
    class Builder
    {
        final BiConsumer<String, Recipe<?>> onFinish;
        @Nullable String name = null;

        final List<ItemStackModifier> remainder = new ArrayList<>(); // For advanced recipes, remainder modifiers
        final List<ItemStackModifier> outputs = new ArrayList<>(); // For advanced recipes, output modifiers
        final NonNullList<Ingredient> ingredients = NonNullList.create(); // Shapeless recipes only
        final List<String> pattern = new ArrayList<>(); // Shaped recipes only
        final ImmutableMap.Builder<Character, Ingredient> keys = ImmutableMap.builder();
        int inputRow = 0, inputCol = 0;
        @Nullable Ingredient primaryInput = null;
        boolean needsAdvInput = false, hasAdvInputShaped = false, hasAdvInputShapeless = false;

        Builder(BiConsumer<String, Recipe<?>> onFinish)
        {
            this.onFinish = onFinish;
        }

        void useTool(TagKey<Item> tool, ItemLike input, ItemLike output)
        {
            input(input).inputIsPrimary(tool).damageInputs().shapeless(output);
        }

        void useTool(TagKey<Item> tool, Ingredient input, ItemLike output, int count)
        {
            input(input).inputIsPrimary(tool).damageInputs().shapeless(output, count);
        }

        void bricksWithMortar(ItemLike brick, ItemLike bricks, int count)
        {
            input('Y', TFCItems.MORTAR).input('X', brick).pattern("XYX", "YXY", "XYX").shaped(bricks, count);
        }

        void to3x3(Ingredient input, ItemLike storage)
        {
            input('X', input).pattern("XXX", "XXX", "XXX").shaped(storage);
        }

        void from3x3(Ingredient input, ItemLike item)
        {
            input(input).shapeless(item, 9);
        }

        void to2x2(ItemLike input, ItemLike output, int count)
        {
            input('X', input).pattern("XX", "XX").shaped(output, count);
        }

        Builder damageInputs()
        {
            remainder.add(DamageCraftingRemainderModifier.INSTANCE);
            return this;
        }

        Builder copyOldestFood()
        {
            outputs.add(CopyOldestFoodModifier.INSTANCE);
            return this;
        }

        Builder copyFood()
        {
            outputs.add(CopyFoodModifier.INSTANCE);
            return this;
        }

        Builder copyForging()
        {
            needsAdvInput = true;
            return addOutputModifier(CopyForgingBonusModifier.INSTANCE);
        }

        Builder copyInput()
        {
            needsAdvInput = true;
            return addOutputModifier(CopyInputModifier.INSTANCE);
        }

        Builder addGlass()
        {
            needsAdvInput = true;
            return addOutputModifier(AddGlassModifier.INSTANCE);
        }

        Builder addPowder() {return addOutputModifier(AddPowderModifier.INSTANCE);}

        Builder addBait() {return addOutputModifier(AddBaitToRodModifier.INSTANCE);}

        Builder extraProduct(ItemLike item) {return extraProduct(item, 1);}

        Builder extraProduct(ItemLike item, int count)
        {
            remainder.add(new ExtraProductModifier(new ItemStack(item, count)));
            return this;
        }

        Builder addTrait(Holder<FoodTrait> trait) {return addOutputModifier(AddTraitModifier.of(trait));}

        Builder addOutputModifier(ItemStackModifier modifier)
        {
            outputs.add(modifier);
            return this;
        }

        Builder input(ItemLike item) {return input(item, 1);}

        Builder input(ItemLike item, int count) {return input(Ingredient.of(item), count);}

        Builder input(TagKey<Item> item) {return input(item, 1);}

        Builder input(TagKey<Item> item, int count) {return input(Ingredient.of(item), count);}

        Builder input(Ingredient item) {return input(item, 1);}

        Builder input(Ingredient item, int count)
        {
            for (int n = 0; n < count; n++) ingredients.add(item);
            return this;
        }

        Builder inputIsPrimary(ItemLike item) {return inputIsPrimary(Ingredient.of(item));}

        Builder inputIsPrimary(TagKey<Item> item) {return inputIsPrimary(Ingredient.of(item));}

        Builder inputIsPrimary(Ingredient item)
        {
            primaryInput = item;
            hasAdvInputShapeless = true;
            return input(item);
        }

        Builder input(char key, TagKey<Item> input) {return input(key, Ingredient.of(input));}

        Builder input(char key, ItemLike input) {return input(key, Ingredient.of(input));}

        Builder input(char key, Ingredient input)
        {
            keys.put(key, input);
            return this;
        }

        Builder source(int row, int col)
        {
            inputRow = row;
            inputCol = col;
            hasAdvInputShaped = true;
            return this;
        }

        Builder pattern(String... pattern)
        {
            this.pattern.addAll(List.of(pattern));
            return this;
        }

        void shapeless(String name)
        {
            this.name = name;
            shapeless(ItemStack.EMPTY);
        }

        void shapeless(ItemLike output) {shapeless(output, 1);}

        void shapeless(ItemLike output, int count) {shapeless(new ItemStack(output, count));}

        void shapeless(ItemStack output)
        {
            assert pattern.isEmpty() && keys.build().isEmpty() : "Mixing shaped and shapeless recipes";
            assert hasAdvInputShapeless || !needsAdvInput : "Missing a .inputIsPrimary(Ingredient) for a recipe which depends on input";
            assert !outputs.isEmpty() || !output.isEmpty() : "Either non-empty output, or output modifiers must be present";

            onFinish.accept(name, isAdvanced()
                ? new AdvancedShapelessRecipe(ingredients, ItemStackProvider.of(output, outputs), remainder(), Optional.ofNullable(primaryInput))
                : new ShapelessRecipe("", CraftingBookCategory.MISC, output, ingredients));
        }

        void shaped(String name)
        {
            this.name = name;
            shaped(ItemStack.EMPTY);
        }

        void shaped(ItemLike output) {shaped(output, 1);}

        void shaped(ItemLike output, int count) {shaped(new ItemStack(output, count));}

        void shaped(ItemStack output)
        {
            assert ingredients.isEmpty() : "Mixing shaped and shapeless recipes";
            assert hasAdvInputShaped || !needsAdvInput : "Missing a .source(int, int) for a recipe which depends on input";
            assert !outputs.isEmpty() || !output.isEmpty() : "Either non-empty output, or output modifiers must be present";

            final ShapedRecipePattern pattern = ShapedRecipePattern.of(keys.build(), this.pattern);
            onFinish.accept(name, isAdvanced()
                ? new AdvancedShapedRecipe(pattern, true, ItemStackProvider.of(output, outputs), remainder(), inputRow, inputCol)
                : new ShapedRecipe("", CraftingBookCategory.MISC, pattern, output));
        }

        private Optional<ItemStackProvider> remainder()
        {
            return remainder.isEmpty() ? Optional.empty() : Optional.of(ItemStackProvider.of(ItemStack.EMPTY, remainder));
        }

        private boolean isAdvanced()
        {
            return !remainder.isEmpty() || !outputs.isEmpty();
        }
    }
}
