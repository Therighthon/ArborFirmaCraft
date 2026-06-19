package com.therighthon.afc.datagen;

import com.therighthon.afc.common.AFCTags;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AncientLogs;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.blocks.UniqueLogs;
import com.therighthon.afc.common.items.AFCItems;
import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;

import static net.dries007.tfc.common.TFCTags.Items.*;

public class AFCItemTagProvider extends ItemTagsProvider
{
    public AFCItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, blockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        // AFC Woods
        makeStandardLogTag(AFCWood.ARAUCARIA, AncientLogs.ANCIENT_ARAUCARIA, AFCTags.Items.ARAUCARIA_LOGS);
        makeStandardLogTag(AFCWood.BAOBAB, AncientLogs.ANCIENT_BAOBAB, AFCTags.Items.BAOBAB_LOGS);
        makeStandardLogTag(AFCWood.BEECH, AncientLogs.ANCIENT_BEECH, AFCTags.Items.BEECH_LOGS);
        makeStandardLogTag(AFCWood.CYPRESS, AncientLogs.ANCIENT_CYPRESS, AFCTags.Items.CYPRESS_LOGS);
        makeStandardLogTag(AFCWood.EUCALYPTUS, AncientLogs.ANCIENT_EUCALYPTUS, AFCTags.Items.EUCALYPTUS_LOGS);
        makeStandardLogTag(AFCWood.FIG, AncientLogs.ANCIENT_FIG, AFCTags.Items.FIG_LOGS);
        makeStandardLogTag(AFCWood.GINKGO, AncientLogs.ANCIENT_GINKGO, AFCTags.Items.GINKGO_LOGS);
        makeStandardLogTag(AFCWood.HEVEA, AncientLogs.ANCIENT_HEVEA, AFCTags.Items.HEVEA_LOGS);
        makeStandardLogTag(AFCWood.IPE, AncientLogs.ANCIENT_IPE, AFCTags.Items.IPE_LOGS);
        makeStandardLogTag(AFCWood.IRONWOOD, AncientLogs.ANCIENT_IRONWOOD, AFCTags.Items.IRONWOOD_LOGS);
        makeStandardLogTag(AFCWood.MAHOE, AncientLogs.ANCIENT_MAHOE, AFCTags.Items.MAHOE_LOGS);
        makeStandardLogTag(AFCWood.MAHOGANY, AncientLogs.ANCIENT_MAHOGANY, AFCTags.Items.MAHOGANY_LOGS);
        makeStandardLogTag(AFCWood.TEAK, AncientLogs.ANCIENT_TEAK, AFCTags.Items.TEAK_LOGS);
        makeStandardLogTag(AFCWood.TUALANG, AncientLogs.ANCIENT_TUALANG, AFCTags.Items.TUALANG_LOGS);

        // Unique logs - Also add to base wood tags
        makeUniqueLogTag(UniqueLogs.BLACK_OAK, AncientLogs.ANCIENT_BLACK_OAK, AFCTags.Items.BLACK_OAK_LOGS);
        tag(TagKey.create(Registries.ITEM, Helpers.identifier("oak_logs"))).addTag(AFCTags.Items.BLACK_OAK_LOGS);
        makeUniqueLogTag(UniqueLogs.GUM_ARABIC, AncientLogs.ANCIENT_GUM_ARABIC, AFCTags.Items.GUM_ARABIC_LOGS);
        tag(TagKey.create(Registries.ITEM, Helpers.identifier("acacia_logs"))).addTag(AFCTags.Items.GUM_ARABIC_LOGS);
        makeUniqueLogTag(UniqueLogs.POPLAR, AncientLogs.ANCIENT_POPLAR, AFCTags.Items.POPLAR_LOGS);
        tag(TagKey.create(Registries.ITEM, Helpers.identifier("aspen_logs"))).addTag(AFCTags.Items.POPLAR_LOGS);

        makeUniqueLogTag(UniqueLogs.RAINBOW_EUCALYPTUS, AncientLogs.ANCIENT_RAINBOW_EUCALYPTUS, AFCTags.Items.RAINBOW_EUCALYPTUS_LOGS);
        tag(AFCTags.Items.EUCALYPTUS_LOGS).addTag(AFCTags.Items.RAINBOW_EUCALYPTUS_LOGS);
        makeUniqueLogTag(UniqueLogs.REDCEDAR, AncientLogs.ANCIENT_REDCEDAR, AFCTags.Items.REDCEDAR_LOGS);
        tag(AFCTags.Items.CYPRESS_LOGS).addTag(AFCTags.Items.REDCEDAR_LOGS);
        makeUniqueLogTag(UniqueLogs.RUBBER_FIG, AncientLogs.ANCIENT_RUBBER_FIG, AFCTags.Items.RUBBER_FIG_LOGS);
        tag(AFCTags.Items.FIG_LOGS).addTag(AFCTags.Items.RUBBER_FIG_LOGS);
        makeUniqueLogTag(UniqueLogs.KAURI, AncientLogs.ANCIENT_KAURI, AFCTags.Items.KAURI_LOGS);
        tag(AFCTags.Items.ARAUCARIA_LOGS).addTag(AFCTags.Items.KAURI_LOGS);

        tag(ItemTags.LOGS_THAT_BURN).addTags(
            AFCTags.Items.BAOBAB_LOGS,
            AFCTags.Items.CYPRESS_LOGS,
            AFCTags.Items.EUCALYPTUS_LOGS,
            AFCTags.Items.FIG_LOGS,
            AFCTags.Items.HEVEA_LOGS,
            AFCTags.Items.IPE_LOGS,
            AFCTags.Items.IRONWOOD_LOGS,
            AFCTags.Items.MAHOGANY_LOGS,
            AFCTags.Items.TEAK_LOGS,
            AFCTags.Items.TUALANG_LOGS,
            AFCTags.Items.ARAUCARIA_LOGS,
            AFCTags.Items.BEECH_LOGS,
            AFCTags.Items.GINKGO_LOGS,
            AFCTags.Items.MAHOE_LOGS
        );

        // For all true wood types, add item tags as well as the block tags
        addAllAFCWoods(Wood.BlockType.PLANKS, ItemTags.PLANKS);
        addAllAFCWoods(Wood.BlockType.DOOR, ItemTags.WOODEN_DOORS);
        addAllAFCWoods(Wood.BlockType.TRAPDOOR, ItemTags.WOODEN_TRAPDOORS);
        addAllAFCWoods(Wood.BlockType.FENCE, ItemTags.WOODEN_FENCES);
        addAllAFCWoods(Wood.BlockType.LOG_FENCE, ItemTags.WOODEN_FENCES);
        addAllAFCWoods(Wood.BlockType.FENCE_GATE, ItemTags.FENCE_GATES);
        addAllAFCWoods(Wood.BlockType.BUTTON, ItemTags.BUTTONS);
        addAllAFCWoods(Wood.BlockType.PRESSURE_PLATE, ItemTags.WOODEN_PRESSURE_PLATES);
        addAllAFCWoods(Wood.BlockType.SLAB, ItemTags.WOODEN_SLABS);
        addAllAFCWoods(Wood.BlockType.STAIRS, ItemTags.WOODEN_STAIRS);
        addAllAFCWoods(Wood.BlockType.WORKBENCH, WORKBENCHES);
        addAllAFCWoods(Wood.BlockType.CHEST, Tags.Items.CHESTS_WOODEN);
        addAllAFCWoods(Wood.BlockType.TRAPPED_CHEST, Tags.Items.CHESTS_WOODEN);
        addAllAFCWoods(Wood.BlockType.TRAPPED_CHEST, Tags.Items.CHESTS_TRAPPED);
        addAllAFCWoods(Wood.BlockType.SIGN, ItemTags.SIGNS);

        // Unique logs
        addAllUniqueAFCWoods(UniqueLogs.BlockType.LOG_FENCE, ItemTags.WOODEN_FENCES);

        AFCItems.SUPPORTS.forEach(
            (w, i) -> tag(TFCTags.Items.SUPPORT_BEAMS).add(i.asItem())
        );
        AFCItems.SIGNS.forEach(
            (w, i) -> tag(ItemTags.SIGNS).add(i.asItem())
        );
        AFCItems.LUMBER.forEach(
        (w, i) -> tag(TFCTags.Items.LUMBER).add(i.asItem())
        );
        AFCItems.CHEST_MINECARTS.forEach(
            (w, i) -> tag(TFCTags.Items.MINECARTS).add(i.asItem())
        );

        AFCBlocks.WOODS.forEach(
            (w, i) -> tag(CARRIED_BY_HORSE)
                .add(w.getBlock(Wood.BlockType.BARREL).get().asItem())
                .add(w.getBlock(Wood.BlockType.CHEST).get().asItem())
                .add(w.getBlock(Wood.BlockType.TRAPPED_CHEST).get().asItem())
        );

        AFCBlocks.WOODS.forEach(
            (w, i) ->
            {
                tag(MINECART_HOLDABLE).add(w.getBlock(Wood.BlockType.BARREL).get().asItem());
                tag(BARRELS).add(w.getBlock(Wood.BlockType.BARREL).get().asItem());
            }
        );

        tag(TANNIN_LOGS)
            .add(AFCWood.CYPRESS.getBlock(Wood.BlockType.LOG).get().asItem())
            .add(AFCWood.CYPRESS.getBlock(Wood.BlockType.WOOD).get().asItem())
            .add(AFCWood.TEAK.getBlock(Wood.BlockType.LOG).get().asItem())
            .add(AFCWood.TEAK.getBlock(Wood.BlockType.WOOD).get().asItem())
            .add(AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.LOG).get().asItem())
            .add(AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.WOOD).get().asItem())
            .add(UniqueLogs.GUM_ARABIC.getBlock(UniqueLogs.BlockType.LOG).get().asItem())
            .add(UniqueLogs.GUM_ARABIC.getBlock(UniqueLogs.BlockType.WOOD).get().asItem())
            .add(UniqueLogs.REDCEDAR.getBlock(UniqueLogs.BlockType.LOG).get().asItem())
            .add(UniqueLogs.REDCEDAR.getBlock(UniqueLogs.BlockType.WOOD).get().asItem())
            .add(UniqueLogs.RAINBOW_EUCALYPTUS.getBlock(UniqueLogs.BlockType.LOG).get().asItem())
            .add(UniqueLogs.RAINBOW_EUCALYPTUS.getBlock(UniqueLogs.BlockType.WOOD).get().asItem())
            .add(UniqueLogs.BLACK_OAK.getBlock(UniqueLogs.BlockType.LOG).get().asItem())
            .add(UniqueLogs.BLACK_OAK.getBlock(UniqueLogs.BlockType.WOOD).get().asItem());

        // Twigs
        addAllAFCWoods(Wood.BlockType.TWIG, Tags.Items.RODS_WOODEN);
        addAllUniqueAFCWoods(UniqueLogs.BlockType.TWIG, Tags.Items.RODS_WOODEN);
        addAllAFCWoods(Wood.BlockType.TWIG, TWIGS);
        addAllUniqueAFCWoods(UniqueLogs.BlockType.TWIG, TWIGS);

        // Stuff that exists per species
        addAllAFCSpecies(TreeSpecies.BlockType.LEAVES, Wood.BlockType.LEAVES, ItemTags.LEAVES); // Lots to check
        addAllAFCSpecies(TreeSpecies.BlockType.FALLEN_LEAVES, Wood.BlockType.FALLEN_LEAVES, TFCTags.Items.FALLEN_LEAVES); // Lots to check
        addAllAFCSpecies(TreeSpecies.BlockType.SAPLING, Wood.BlockType.SAPLING, ItemTags.SAPLINGS);

        tag(SWEETENERS).add(AFCItems.BIRCH_SUGAR.asItem()).add(AFCItems.MAPLE_SUGAR.asItem());

        // Hanging Signs
        for (Metal metal : Metal.values())
        {
            if (metal.allParts())
            {
                for (AFCWood wood : AFCWood.values())
                {
                    tag(ItemTags.HANGING_SIGNS).add(AFCItems.HANGING_SIGNS.get(wood).get(metal).get());
                }
            }
        }
    }

    private void makeStandardLogTag(AFCWood logType, AncientLogs ancient, TagKey<Item> itemTag)
    {
        tag(itemTag)
            .add(logType.getBlock(Wood.BlockType.LOG).get().asItem())
            .add(logType.getBlock(Wood.BlockType.WOOD).get().asItem())
            .add(logType.getBlock(Wood.BlockType.STRIPPED_LOG).get().asItem())
            .add(logType.getBlock(Wood.BlockType.STRIPPED_WOOD).get().asItem())
            .add(ancient.getBlock(AncientLogs.BlockType.LOG).get().asItem())
            .add(ancient.getBlock(AncientLogs.BlockType.WOOD).get().asItem());
    }

    private void makeUniqueLogTag(UniqueLogs logType, AncientLogs ancient, TagKey<Item> itemTag)
    {
        tag(itemTag)
            .add(logType.getBlock(UniqueLogs.BlockType.LOG).get().asItem())
            .add(logType.getBlock(UniqueLogs.BlockType.WOOD).get().asItem())
            .add(ancient.getBlock(AncientLogs.BlockType.LOG).get().asItem())
            .add(ancient.getBlock(AncientLogs.BlockType.WOOD).get().asItem());
    }

    private void addAllAFCWoods(Wood.BlockType type, TagKey<Item> tagKey)
    {
        AFCBlocks.WOODS.forEach(
            (s, m) -> tag(tagKey).add(s.getBlock(type).get().asItem())
        );
    }

    private void addAllUniqueAFCWoods(UniqueLogs.BlockType type, TagKey<Item> tagKey)
    {
        AFCBlocks.UNIQUE_LOGS.forEach(
            (s, m) -> tag(tagKey).add(s.getBlock(type).get().asItem())
        );
    }

    private void addAllAFCSpecies(TreeSpecies.BlockType type, Wood.BlockType woodType, TagKey<Item> tagKey)
    {
        AFCBlocks.TREE_SPECIES.forEach(
            (s, m) -> tag(tagKey).add(s.getBlock(type).get().asItem())
        );
        addAllAFCWoods(woodType, tagKey);
    }
}
