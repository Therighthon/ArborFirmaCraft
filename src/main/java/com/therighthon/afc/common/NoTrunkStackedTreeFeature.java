package com.therighthon.afc.common;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import net.dries007.tfc.world.feature.tree.StackedTreeConfig;
import net.dries007.tfc.world.feature.tree.StackedTreeFeature;
import net.dries007.tfc.world.feature.tree.TreeHelpers;

public class NoTrunkStackedTreeFeature extends StackedTreeFeature
{

    public NoTrunkStackedTreeFeature(Codec<StackedTreeConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StackedTreeConfig> context)
    {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final var random = context.random();
        final StackedTreeConfig config = context.config();

        final ChunkPos chunkPos = new ChunkPos(pos);
        final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);
        final StructureManager manager = TreeHelpers.getStructureManager(level);
        final StructurePlaceSettings settings = TreeHelpers.getPlacementSettings(level, chunkPos, random);

        if (TreeHelpers.isValidGround(level, pos, settings, config.placement()))
        {
            for (StackedTreeConfig.Layer layer : config.layers())
            {
                // Place each layer
                int layerCount = layer.getCount(random);
                for (int i = 0; i < layerCount; i++)
                {
                    final ResourceLocation structureId = layer.templates().get(random.nextInt(layer.templates().size()));
                    final StructureTemplate structure = manager.getOrCreate(structureId);
                    TreeHelpers.placeTemplate(structure, settings, level, mutablePos.subtract(TreeHelpers.transformCenter(structure.getSize(), settings)));
                    mutablePos.move(0, structure.getSize().getY(), 0);
                }
            }
            return true;
        }
        return false;
    }
}
