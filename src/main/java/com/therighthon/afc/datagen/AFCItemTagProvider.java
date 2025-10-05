package com.therighthon.afc.datagen;

import com.therighthon.afc.common.AFCTags;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.UniqueLogs;
import com.therighthon.afc.common.items.AFCItems;
import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
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
            (w, m) -> tag(Tags.Items.BARRELS_WOODEN).add(w.getBlock(Wood.BlockType.BARREL).get().asItem())
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

        tag(SWEETENERS).add(AFCItems.BIRCH_SUGAR.asItem()).add(AFCItems.MAPLE_SUGAR.asItem());

    }


}
