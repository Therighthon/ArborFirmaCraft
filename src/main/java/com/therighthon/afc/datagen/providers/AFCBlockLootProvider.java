package com.therighthon.afc.datagen.providers;

import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.TreeSpecies;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class AFCBlockLootProvider extends BlockLootSubProvider
{

    protected AFCBlockLootProvider(HolderLookup.Provider registries)
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate()
    {
        AFCBlocks.BLOCKS.getEntries().forEach(b -> dropSelf(b.get()));

        AFCBlocks.TREE_SPECIES.forEach(
            (species, map) -> add(species.getBlock(TreeSpecies.BlockType.LEAVES).get(),
                block -> createLeavesDrops(block, species.getBlock(TreeSpecies.BlockType.SAPLING).get(), 0.1f) // TODO: Need to add in the custom chances
            )
        );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks()
    {
        return AFCBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
