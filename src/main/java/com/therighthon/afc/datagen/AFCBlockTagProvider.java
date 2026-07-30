package com.therighthon.afc.datagen;

import com.therighthon.afc.common.AFCTags;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AncientLogs;
import com.therighthon.afc.common.blocks.FLCompatBlocks;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.blocks.UniqueLogs;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;

public class AFCBlockTagProvider extends BlockTagsProvider
{

    public AFCBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(AFCTags.Blocks.TAPPABLE_LOGS).addTags(
            AFCTags.Blocks.MAPLE_LOGS,
            AFCTags.Blocks.BIRCH_LOGS,
            AFCTags.Blocks.HEVEA_LOGS,
            AFCTags.Blocks.RUBBER_FIG_LOGS
        );

        // Register tags for Vanilla TFC Wood to help tree-tap recipe makers etc.
        makeVanillaLogTag(Wood.ACACIA, AncientLogs.ANCIENT_ACACIA, AFCTags.Blocks.ACACIA_LOGS);
        makeVanillaLogTag(Wood.ASH, AncientLogs.ANCIENT_ASH, AFCTags.Blocks.ASH_LOGS);
        makeVanillaLogTag(Wood.ASPEN, AncientLogs.ANCIENT_ASPEN, AFCTags.Blocks.ASPEN_LOGS);
        makeVanillaLogTag(Wood.BIRCH, AncientLogs.ANCIENT_BIRCH, AFCTags.Blocks.BIRCH_LOGS);
        makeVanillaLogTag(Wood.BLACKWOOD, AncientLogs.ANCIENT_BLACKWOOD, AFCTags.Blocks.BLACKWOOD_LOGS);
        makeVanillaLogTag(Wood.CHESTNUT, AncientLogs.ANCIENT_CHESTNUT, AFCTags.Blocks.CHESTNUT_LOGS);
        makeVanillaLogTag(Wood.DOUGLAS_FIR, AncientLogs.ANCIENT_DOUGLAS_FIR, AFCTags.Blocks.DOUGLAS_FIR_LOGS);
        makeVanillaLogTag(Wood.HICKORY, AncientLogs.ANCIENT_HICKORY, AFCTags.Blocks.HICKORY_LOGS);
        makeVanillaLogTag(Wood.KAPOK, AncientLogs.ANCIENT_KAPOK, AFCTags.Blocks.KAPOK_LOGS);
        makeVanillaLogTag(Wood.MANGROVE, AncientLogs.ANCIENT_MANGROVE, AFCTags.Blocks.MANGROVE_LOGS);
        makeVanillaLogTag(Wood.MAPLE, AncientLogs.ANCIENT_MAPLE, AFCTags.Blocks.MAPLE_LOGS);
        makeVanillaLogTag(Wood.OAK, AncientLogs.ANCIENT_OAK, AFCTags.Blocks.OAK_LOGS);
        makeVanillaLogTag(Wood.PALM, AncientLogs.ANCIENT_PALM, AFCTags.Blocks.PALM_LOGS);
        makeVanillaLogTag(Wood.PINE, AncientLogs.ANCIENT_PINE, AFCTags.Blocks.PINE_LOGS);
        makeVanillaLogTag(Wood.ROSEWOOD, AncientLogs.ANCIENT_ROSEWOOD, AFCTags.Blocks.ROSEWOOD_LOGS);
        makeVanillaLogTag(Wood.SEQUOIA, AncientLogs.ANCIENT_SEQUOIA, AFCTags.Blocks.SEQUOIA_LOGS);
        makeVanillaLogTag(Wood.SPRUCE, AncientLogs.ANCIENT_SPRUCE, AFCTags.Blocks.SPRUCE_LOGS);
        makeVanillaLogTag(Wood.SYCAMORE, AncientLogs.ANCIENT_SYCAMORE, AFCTags.Blocks.SYCAMORE_LOGS);
        makeVanillaLogTag(Wood.WHITE_CEDAR, AncientLogs.ANCIENT_WHITE_CEDAR, AFCTags.Blocks.WHITE_CEDAR_LOGS);
        makeVanillaLogTag(Wood.WILLOW, AncientLogs.ANCIENT_WILLOW, AFCTags.Blocks.WILLOW_LOGS);

        // AFC Woods
        makeStandardLogTag(AFCWood.ARAUCARIA, AncientLogs.ANCIENT_ARAUCARIA, AFCTags.Blocks.ARAUCARIA_LOGS);
        makeStandardLogTag(AFCWood.BAOBAB, AncientLogs.ANCIENT_BAOBAB, AFCTags.Blocks.BAOBAB_LOGS);
        makeStandardLogTag(AFCWood.BEECH, AncientLogs.ANCIENT_BEECH, AFCTags.Blocks.BEECH_LOGS);
        makeStandardLogTag(AFCWood.CYPRESS, AncientLogs.ANCIENT_CYPRESS, AFCTags.Blocks.CYPRESS_LOGS);
        makeStandardLogTag(AFCWood.EUCALYPTUS, AncientLogs.ANCIENT_EUCALYPTUS, AFCTags.Blocks.EUCALYPTUS_LOGS);
        makeStandardLogTag(AFCWood.FIG, AncientLogs.ANCIENT_FIG, AFCTags.Blocks.FIG_LOGS);
        makeStandardLogTag(AFCWood.GINKGO, AncientLogs.ANCIENT_GINKGO, AFCTags.Blocks.GINKGO_LOGS);
        makeStandardLogTag(AFCWood.HEVEA, AncientLogs.ANCIENT_HEVEA, AFCTags.Blocks.HEVEA_LOGS);
        makeStandardLogTag(AFCWood.IPE, AncientLogs.ANCIENT_IPE, AFCTags.Blocks.IPE_LOGS);
        makeStandardLogTag(AFCWood.IRONWOOD, AncientLogs.ANCIENT_IRONWOOD, AFCTags.Blocks.IRONWOOD_LOGS);
        makeStandardLogTag(AFCWood.MAHOE, AncientLogs.ANCIENT_MAHOE, AFCTags.Blocks.MAHOE_LOGS);
        makeStandardLogTag(AFCWood.MAHOGANY, AncientLogs.ANCIENT_MAHOGANY, AFCTags.Blocks.MAHOGANY_LOGS);
        makeStandardLogTag(AFCWood.TEAK, AncientLogs.ANCIENT_TEAK, AFCTags.Blocks.TEAK_LOGS);
        makeStandardLogTag(AFCWood.TUALANG, AncientLogs.ANCIENT_TUALANG, AFCTags.Blocks.TUALANG_LOGS);

        // Unique logs - Also add to base wood tags
        makeUniqueLogTag(UniqueLogs.BLACK_OAK, AncientLogs.ANCIENT_BLACK_OAK, AFCTags.Blocks.BLACK_OAK_LOGS);
        tag(AFCTags.Blocks.OAK_LOGS).addTag(AFCTags.Blocks.BLACK_OAK_LOGS);
        makeUniqueLogTag(UniqueLogs.GUM_ARABIC, AncientLogs.ANCIENT_GUM_ARABIC, AFCTags.Blocks.GUM_ARABIC_LOGS);
        tag(AFCTags.Blocks.ACACIA_LOGS).addTag(AFCTags.Blocks.GUM_ARABIC_LOGS);
        makeUniqueLogTag(UniqueLogs.POPLAR, AncientLogs.ANCIENT_POPLAR, AFCTags.Blocks.POPLAR_LOGS);
        tag(AFCTags.Blocks.ASPEN_LOGS).addTag(AFCTags.Blocks.POPLAR_LOGS);
        makeUniqueLogTag(UniqueLogs.RAINBOW_EUCALYPTUS, AncientLogs.ANCIENT_RAINBOW_EUCALYPTUS, AFCTags.Blocks.RAINBOW_EUCALYPTUS_LOGS);
        tag(AFCTags.Blocks.EUCALYPTUS_LOGS).addTag(AFCTags.Blocks.RAINBOW_EUCALYPTUS_LOGS);
        makeUniqueLogTag(UniqueLogs.REDCEDAR, AncientLogs.ANCIENT_REDCEDAR, AFCTags.Blocks.REDCEDAR_LOGS);
        tag(AFCTags.Blocks.CYPRESS_LOGS).addTag(AFCTags.Blocks.REDCEDAR_LOGS);
        makeUniqueLogTag(UniqueLogs.RUBBER_FIG, AncientLogs.ANCIENT_RUBBER_FIG, AFCTags.Blocks.RUBBER_FIG_LOGS);
        tag(AFCTags.Blocks.FIG_LOGS).addTag(AFCTags.Blocks.RUBBER_FIG_LOGS);
        makeUniqueLogTag(UniqueLogs.KAURI, AncientLogs.ANCIENT_KAURI, AFCTags.Blocks.KAURI_LOGS);
        tag(AFCTags.Blocks.ARAUCARIA_LOGS).addTag(AFCTags.Blocks.KAURI_LOGS);

        addAllAFCUniqueWoods(UniqueLogs.BlockType.LOG_FENCE, Tags.Blocks.FENCES_WOODEN);
        addAllAFCUniqueWoods(UniqueLogs.BlockType.LOG_FENCE, BlockTags.WOODEN_FENCES);

        tag(BlockTags.OVERWORLD_NATURAL_LOGS).addTags(
            AFCTags.Blocks.BAOBAB_LOGS,
            AFCTags.Blocks.CYPRESS_LOGS,
            AFCTags.Blocks.EUCALYPTUS_LOGS,
            AFCTags.Blocks.FIG_LOGS,
            AFCTags.Blocks.HEVEA_LOGS,
            AFCTags.Blocks.IPE_LOGS,
            AFCTags.Blocks.IRONWOOD_LOGS,
            AFCTags.Blocks.MAHOGANY_LOGS,
            AFCTags.Blocks.TEAK_LOGS,
            AFCTags.Blocks.TUALANG_LOGS,
            AFCTags.Blocks.ARAUCARIA_LOGS,
            AFCTags.Blocks.BEECH_LOGS,
            AFCTags.Blocks.GINKGO_LOGS,
            AFCTags.Blocks.MAHOE_LOGS
        );
        tag(BlockTags.LOGS_THAT_BURN).addTags(
            AFCTags.Blocks.BAOBAB_LOGS,
            AFCTags.Blocks.CYPRESS_LOGS,
            AFCTags.Blocks.EUCALYPTUS_LOGS,
            AFCTags.Blocks.FIG_LOGS,
            AFCTags.Blocks.HEVEA_LOGS,
            AFCTags.Blocks.IPE_LOGS,
            AFCTags.Blocks.IRONWOOD_LOGS,
            AFCTags.Blocks.MAHOGANY_LOGS,
            AFCTags.Blocks.TEAK_LOGS,
            AFCTags.Blocks.TUALANG_LOGS,
            AFCTags.Blocks.ARAUCARIA_LOGS,
            AFCTags.Blocks.BEECH_LOGS,
            AFCTags.Blocks.GINKGO_LOGS,
            AFCTags.Blocks.MAHOE_LOGS
        );

        // Stuff without individual tags that still needs to be mineable with an axe
        addAllAFCWoods(Wood.BlockType.BOOKSHELF, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.CRATE, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.TOOL_RACK, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.TWIG, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.TWIG, TFCTags.Blocks.CAN_BE_SNOW_PILED);
        addAllAFCWoods(Wood.BlockType.LOOM, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.SLUICE, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.BARREL, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.LECTERN, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.SCRIBING_TABLE, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.SEWING_TABLE, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.SHELF, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.AXLE, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.BLADED_AXLE, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.ENCASED_AXLE, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.CLUTCH, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.GEAR_BOX, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.HORIZONTAL_SUPPORT, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.VERTICAL_SUPPORT, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.WATER_WHEEL, BlockTags.MINEABLE_WITH_AXE);

        // For all true wood types
        addAllAFCWoods(Wood.BlockType.BARREL, TFCTags.Blocks.CLOCK_READABLE);
        addAllAFCWoods(Wood.BlockType.PLANKS, BlockTags.PLANKS);
        addAllAFCWoods(Wood.BlockType.DOOR, BlockTags.WOODEN_DOORS); // doors? mob interactable doors? added automatically from this?
        addAllAFCWoods(Wood.BlockType.TRAPDOOR, BlockTags.WOODEN_TRAPDOORS);
        addAllAFCWoods(Wood.BlockType.FENCE, Tags.Blocks.FENCES_WOODEN);
        addAllAFCWoods(Wood.BlockType.LOG_FENCE, Tags.Blocks.FENCES_WOODEN);
        addAllAFCWoods(Wood.BlockType.FENCE, BlockTags.WOODEN_FENCES);
        addAllAFCWoods(Wood.BlockType.LOG_FENCE, BlockTags.WOODEN_FENCES);
        addAllAFCWoods(Wood.BlockType.FENCE_GATE, Tags.Blocks.FENCE_GATES_WOODEN); // unstable bottom center?
        addAllAFCWoods(Wood.BlockType.BUTTON, BlockTags.WOODEN_BUTTONS);
        addAllAFCWoods(Wood.BlockType.PRESSURE_PLATE, BlockTags.WOODEN_PRESSURE_PLATES); // Wall post overrides?
        addAllAFCWoods(Wood.BlockType.SLAB, BlockTags.WOODEN_SLABS);
        addAllAFCWoods(Wood.BlockType.STAIRS, BlockTags.WOODEN_STAIRS);
        addAllAFCWoods(Wood.BlockType.WORKBENCH, TFCTags.Blocks.WORKBENCHES);
        addAllAFCWoods(Wood.BlockType.WORKBENCH, Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES);
        addAllAFCWoods(Wood.BlockType.WORKBENCH, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.CHEST, Tags.Blocks.CHESTS_WOODEN); // tfc Pet sits on
        addAllAFCWoods(Wood.BlockType.CHEST, BlockTags.MINEABLE_WITH_AXE); // tfc Pet sits on
        addAllAFCWoods(Wood.BlockType.TRAPPED_CHEST, Tags.Blocks.CHESTS_WOODEN);
        addAllAFCWoods(Wood.BlockType.TRAPPED_CHEST, BlockTags.MINEABLE_WITH_AXE);
        addAllAFCWoods(Wood.BlockType.HORIZONTAL_SUPPORT, TFCTags.Blocks.SUPPORT_BEAMS);
        addAllAFCWoods(Wood.BlockType.VERTICAL_SUPPORT, TFCTags.Blocks.SUPPORT_BEAMS);
        addAllAFCWoods(Wood.BlockType.SIGN, BlockTags.STANDING_SIGNS);
        addAllAFCWoods(Wood.BlockType.WALL_SIGN, BlockTags.WALL_SIGNS);

        // For every tree species
        addAllAFCSpecies(TreeSpecies.BlockType.SAPLING, Wood.BlockType.SAPLING, BlockTags.SAPLINGS);
        addAllAFCSpecies(TreeSpecies.BlockType.POTTED_SAPLING, Wood.BlockType.POTTED_SAPLING, BlockTags.FLOWER_POTS);
        addAllAFCSpecies(TreeSpecies.BlockType.FALLEN_LEAVES, Wood.BlockType.FALLEN_LEAVES, TFCTags.Blocks.FALLEN_LEAVES); // Lots to check
        addAllAFCSpecies(TreeSpecies.BlockType.LEAVES, Wood.BlockType.LEAVES, BlockTags.LEAVES); // Lots to check

        // Breakable by Sharp tools
        breakableBySharps(TreeSpecies.BlockType.LEAVES, Wood.BlockType.LEAVES);
        breakableBySharps(TreeSpecies.BlockType.FALLEN_LEAVES, Wood.BlockType.FALLEN_LEAVES);
        breakableBySharps(TreeSpecies.BlockType.SAPLING, Wood.BlockType.SAPLING);

        // Hanging Signs
        for (Metal metal : Metal.values())
        {
            if (metal.allParts())
            {
                for (AFCWood wood : AFCWood.values())
                {
                    tag(BlockTags.WALL_HANGING_SIGNS).add(AFCBlocks.WALL_HANGING_SIGNS.get(wood).get(metal).get());
                    tag(BlockTags.CEILING_HANGING_SIGNS).add(AFCBlocks.CEILING_HANGING_SIGNS.get(wood).get(metal).get());
                }
            }
        }

        // Firmalife - there has to be a better way to do this...
//        addAllAFCWoods(FLCompatBlocks.BARREL_PRESSES, BlockTags.MINEABLE_WITH_AXE);
//        addAllAFCWoods(FLCompatBlocks.HANGERS, BlockTags.MINEABLE_WITH_AXE);
//        addAllAFCWoods(FLCompatBlocks.KEGS, BlockTags.MINEABLE_WITH_AXE);
//        addAllAFCWoods(FLCompatBlocks.KEG_SUBS, BlockTags.MINEABLE_WITH_AXE);
//        addAllAFCWoods(FLCompatBlocks.FOOD_SHELVES, BlockTags.MINEABLE_WITH_AXE);
//        addAllAFCWoods(FLCompatBlocks.JARBNETS, BlockTags.MINEABLE_WITH_AXE);
//        addAllAFCWoods(FLCompatBlocks.WINE_SHELVES, BlockTags.MINEABLE_WITH_AXE);
//        addAllAFCWoods(FLCompatBlocks.STOMPING_BARRELS, BlockTags.MINEABLE_WITH_AXE);
    }

    private void breakableBySharps(TreeSpecies.BlockType species, Wood.BlockType wood)
    {
        addAllAFCSpecies(species, wood, BlockTags.MINEABLE_WITH_HOE);
        addAllAFCSpecies(species, wood, TFCTags.Blocks.MINEABLE_WITH_KNIFE);
        addAllAFCSpecies(species, wood, TFCTags.Blocks.MINEABLE_WITH_SCYTHE);
    }

    private void addAllAFCWoods(Wood.BlockType type, TagKey<Block> tagKey)
    {
        AFCBlocks.WOODS.forEach(
            (s, m) -> tag(tagKey).add(s.getBlock(type).get())
        );
    }

    private void addAllAFCWoods(Map<AFCWood, TFCBlocks.Id<Block>> type, TagKey<Block> tagKey)
    {
        AFCBlocks.WOODS.forEach(
            (s, m) -> tag(tagKey).add(type.get(s).get())
        );
    }

    private void addAllAFCSpecies(TreeSpecies.BlockType type, Wood.BlockType woodType, TagKey<Block> tagKey)
    {
        AFCBlocks.TREE_SPECIES.forEach(
            (s, m) -> tag(tagKey).add(s.getBlock(type).get())
        );
        addAllAFCWoods(woodType, tagKey);
    }

    private void addAllAFCUniqueWoods(UniqueLogs.BlockType type, TagKey<Block> tagKey)
    {
        AFCBlocks.UNIQUE_LOGS.forEach(
            (s, m) -> tag(tagKey).add(s.getBlock(type).get())
        );
    }

    private void makeVanillaLogTag(Wood logType, AncientLogs ancient, TagKey<Block> blockTag)
    {
        tag(blockTag)
            .add(logType.getBlock(Wood.BlockType.LOG).get())
            .add(logType.getBlock(Wood.BlockType.WOOD).get())
            .add(logType.getBlock(Wood.BlockType.STRIPPED_LOG).get())
            .add(logType.getBlock(Wood.BlockType.STRIPPED_WOOD).get())
            .add(ancient.getBlock(AncientLogs.BlockType.LOG).get())
            .add(ancient.getBlock(AncientLogs.BlockType.WOOD).get());
    }

    private void makeStandardLogTag(AFCWood logType, AncientLogs ancient, TagKey<Block> blockTag)
    {
        tag(blockTag)
            .add(logType.getBlock(Wood.BlockType.LOG).get())
            .add(logType.getBlock(Wood.BlockType.WOOD).get())
            .add(logType.getBlock(Wood.BlockType.STRIPPED_LOG).get())
            .add(logType.getBlock(Wood.BlockType.STRIPPED_WOOD).get())
            .add(ancient.getBlock(AncientLogs.BlockType.LOG).get())
            .add(ancient.getBlock(AncientLogs.BlockType.WOOD).get());
    }

    private void makeUniqueLogTag(UniqueLogs logType, AncientLogs ancient, TagKey<Block> blockTag)
    {
        tag(blockTag)
            .add(logType.getBlock(UniqueLogs.BlockType.LOG).get())
            .add(logType.getBlock(UniqueLogs.BlockType.WOOD).get())
            .add(ancient.getBlock(AncientLogs.BlockType.LOG).get())
            .add(ancient.getBlock(AncientLogs.BlockType.WOOD).get());
    }
}
