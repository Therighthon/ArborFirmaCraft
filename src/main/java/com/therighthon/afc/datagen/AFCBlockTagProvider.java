package com.therighthon.afc.datagen;

import com.therighthon.afc.common.AFCTags;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AncientLogs;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.blocks.UniqueLogs;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.wood.Wood;

public class AFCBlockTagProvider extends BlockTagsProvider
{

    public AFCBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        AFCBlocks.TREE_SPECIES.forEach(
            (s, m) -> tag(TFCTags.Blocks.CAN_BE_SNOW_PILED).add(s.getBlock(TreeSpecies.BlockType.FALLEN_LEAVES).get())
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
        // TODO: Araucaria makeStandardLogTag(AFCWood.BAOBAB, AncientLogs.ANCIENT_BAOBAB, AFCTags.Blocks.BAOBAB_LOGS);
        makeStandardLogTag(AFCWood.BAOBAB, AncientLogs.ANCIENT_BAOBAB, AFCTags.Blocks.BAOBAB_LOGS);
        makeStandardLogTag(AFCWood.CYPRESS, AncientLogs.ANCIENT_CYPRESS, AFCTags.Blocks.CYPRESS_LOGS);
        makeStandardLogTag(AFCWood.EUCALYPTUS, AncientLogs.ANCIENT_EUCALYPTUS, AFCTags.Blocks.EUCALYPTUS_LOGS);
        makeStandardLogTag(AFCWood.FIG, AncientLogs.ANCIENT_FIG, AFCTags.Blocks.FIG_LOGS);
        makeStandardLogTag(AFCWood.HEVEA, AncientLogs.ANCIENT_HEVEA, AFCTags.Blocks.HEVEA_LOGS);
        makeStandardLogTag(AFCWood.IPE, AncientLogs.ANCIENT_IPE, AFCTags.Blocks.IPE_LOGS);
        makeStandardLogTag(AFCWood.IRONWOOD, AncientLogs.ANCIENT_IRONWOOD, AFCTags.Blocks.IRONWOOD_LOGS);
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
        String name = logType.getSerializedName();
        tag(blockTag)
            .add(logType.getBlock(UniqueLogs.BlockType.LOG).get())
            .add(logType.getBlock(UniqueLogs.BlockType.WOOD).get())
            .add(ancient.getBlock(AncientLogs.BlockType.LOG).get())
            .add(ancient.getBlock(AncientLogs.BlockType.WOOD).get());
    }
}
