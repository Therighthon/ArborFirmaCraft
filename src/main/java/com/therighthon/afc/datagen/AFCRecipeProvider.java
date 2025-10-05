package com.therighthon.afc.datagen;

import com.therighthon.afc.AFCHelpers;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.UniqueLogs;
import com.therighthon.afc.common.items.AFCItems;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import org.jetbrains.annotations.NotNull;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import static net.dries007.tfc.util.DataGenerationHelpers.Builder;

// Much of this class was reproduced from TerraFirmaCraft's data generation
// Reproduced and modified from the original work on Oct 5, 2025
public class AFCRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    RecipeOutput output;
    HolderLookup.Provider lookup;

    public AFCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput, HolderLookup.@NotNull Provider lookup)
    {
        this.lookup = lookup;
        return super.run(cachedOutput, lookup);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput)
    {
        this.output = recipeOutput;

        crafting();
    }

    private void crafting()
    {

        // Full woods
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
                .input(woodLogsTagOf(Registries.ITEM, wood))
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
                .input('L', woodLogsTagOf(Registries.ITEM, wood))
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

            // TODO: Hanging Signs
//            for (Metal metal : Metal.values())
//                if (metal.allParts())
//                {
//                    recipe()
//                        .input('L', AFCItems.LUMBER.get(wood))
//                        .input('C', ingredientOf(metal, Metal.BlockType.CHAIN))
//                        .pattern("C C", "LLL", "LLL")
//                        .shaped(AFCItems.HANGING_SIGNS.get(wood).get(metal), 3);
//                }
        }

        // Unique Logs
        for (UniqueLogs uLog : UniqueLogs.values())
        {
            final var uBlocks = AFCBlocks.UNIQUE_LOGS.get(uLog);

            final var sBlocks = uLog.isAFCWoodType()
                ? AFCBlocks.WOODS.get(uLog.AFCWoodType())
                : TFCBlocks.WOODS.get(uLog.TFCWoodType());
            final var lumber = uLog.isAFCWoodType()
                ? AFCItems.LUMBER.get(uLog.AFCWoodType()).asItem()
                : TFCItems.LUMBER.get(uLog.TFCWoodType()).get().asItem();
            final var planks = uLog.isAFCWoodType()
                ? AFCBlocks.WOODS.get(uLog.AFCWoodType()).get(Wood.BlockType.PLANKS).asItem()
                : TFCBlocks.WOODS.get(uLog.TFCWoodType()).get(Wood.BlockType.PLANKS).asItem();
            final var supports = uLog.isAFCWoodType()
                ? AFCItems.SUPPORTS.get(uLog.AFCWoodType()).asItem()
                : TFCItems.SUPPORTS.get(uLog.TFCWoodType()).get().asItem();

            // TODO: Log fences for unique logs?
//            recipe("from_" + uLog.getSerializedName().toLowerCase() + "_logs")
//                .input('P', blocks.get(Wood.BlockType.LOG))
//                .input('L', lumber)
//                .pattern("PLP", "PLP")
//                .shaped(blocks.get(Wood.BlockType.LOG_FENCE), 8);
            recipe("from_" + uLog.getSerializedName().toLowerCase() + "_logs")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(uniqueLogsTagOf(Registries.ITEM, uLog))
                .damageInputs()
                .shapeless(lumber, 8);
            recipe("from_" + uLog.getSerializedName().toLowerCase() + "_logs")
                .input('S', Tags.Items.TOOLS_SHEAR)
                .input('L', Tags.Items.LEATHERS)
                .input('P', planks)
                .input('G', uBlocks.get(UniqueLogs.BlockType.LOG))
                .pattern(" LS", "PPP", "G G")
                .shaped(sBlocks.get(Wood.BlockType.SEWING_TABLE));
            recipe("from_" + uLog.getSerializedName().toLowerCase() + "_logs")
                .input('L', uniqueLogsTagOf(Registries.ITEM, uLog))
                .input('S', TFCTags.Items.TOOLS_SAW)
                .pattern("LS", "L ")
                .damageInputs()
                .source(0, 1)
                .shaped(supports, 8);
            recipe().to2x2(uBlocks.get(UniqueLogs.BlockType.LOG), uBlocks.get(UniqueLogs.BlockType.WOOD), 3);
        }
    }

    private Ingredient ingredientOf(Metal metal, Metal.BlockType type)
    {
        return type == Metal.BlockType.BLOCK
            ? Ingredient.of(storageBlockTagOf(Registries.ITEM, metal))
            : Ingredient.of(TFCBlocks.METALS.get(metal).get(type).get());
    }

    private Ingredient ingredientOf(Metal metal, Metal.ItemType type)
    {
        return type.isCommonTagPart()
            ? Ingredient.of(commonTagOf(metal, type))
            : Ingredient.of(TFCItems.METAL_ITEMS.get(metal).get(type).get());
    }

    private TagKey<Item> commonTagOf(Metal metal, Metal.ItemType type)
    {
        assert type.isCommonTagPart() : "Non-typical use of tag for " + metal.getSerializedName() + " / " + type.name();
        assert type.has(metal) : "Non-typical use of " + metal.getSerializedName() + " / " + type.name();
        return commonTagOf(Registries.ITEM, type.name() + "s/" + metal.name());
    }

    private <T> TagKey<T> commonTagOf(ResourceKey<Registry<T>> key, String name)
    {
        return TagKey.create(key, ResourceLocation.fromNamespaceAndPath("c", name.toLowerCase(Locale.ROOT)));
    }

    private <T> TagKey<T> woodLogsTagOf(ResourceKey<Registry<T>> registry, AFCWood wood)
    {
        return TagKey.create(registry, AFCHelpers.identifier(wood.getSerializedName() + "_logs"));
    }

    private <T> TagKey<T> uniqueLogsTagOf(ResourceKey<Registry<T>> registry, UniqueLogs wood)
    {
        return TagKey.create(registry, AFCHelpers.identifier(wood.getSerializedName() + "_logs"));
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
            add(nameOf(r.getResultItem(lookup).getItem()) + "_" + suffix, r);
        });
    }

    private void add(Recipe<?> recipe)
    {
        add(nameOf(recipe), recipe);
    }

    private void add(String name, Recipe<?> recipe)
    {
        add(Objects.requireNonNull(BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType()), "No recipe type").getPath(), name, recipe);
    }

    private void add(String prefix, String name, Recipe<?> recipe)
    {
        output.accept(AFCHelpers.identifier((prefix + "/" + name).toLowerCase(Locale.ROOT)), recipe, null);
    }

    private String nameOf(Recipe<?> recipe)
    {
        return nameOf(recipe.getResultItem(lookup).getItem());
    }

    private String nameOf(Ingredient ingredient)
    {
        if (ingredient.getCustomIngredient() instanceof CompoundIngredient ing) return nameOf(ing.children().get(0));
        final Ingredient.Value value = ingredient.getValues()[0];
        if (value instanceof Ingredient.TagValue(TagKey<Item> tag)) return tag.location().getPath();
        if (value instanceof Ingredient.ItemValue(ItemStack item)) return nameOf(item.getItem());
        throw new AssertionError("Unknown ingredient value");
    }

    private String nameOf(Fluid fluid)
    {
        assert fluid != Fluids.EMPTY : "Should never get name of Items.AIR";
        return BuiltInRegistries.FLUID.getKey(fluid).getPath();
    }

    private String nameOf(ItemLike item)
    {
        assert item.asItem() != Items.AIR : "Should never get name of Items.AIR";
        assert item.asItem() != Items.BARRIER : "Should never get name of Items.BARRIER";
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private <T> TagKey<T> storageBlockTagOf(ResourceKey<Registry<T>> key, Metal metal)
    {
        assert metal.defaultParts() : "Non-typical use of a non-default metal " + metal.getSerializedName();
        return commonTagOf(key, "storage_blocks/" + metal.getSerializedName());
    }

}
