package com.therighthon.afc.common;

import java.util.Iterator;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

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
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        StackedTreeConfig config = (StackedTreeConfig)context.config();
        ChunkPos chunkPos = new ChunkPos(pos);
        BlockPos.MutableBlockPos mutablePos = (new BlockPos.MutableBlockPos()).set(pos);
        StructureTemplateManager manager = TreeHelpers.getStructureManager(level);
        StructurePlaceSettings settings = TreeHelpers.getPlacementSettings(level, chunkPos, random);
        if (TreeHelpers.isValidGround(level, pos, settings, config.placement())) {
            boolean placeTree = (Boolean)config.rootSystem().map((roots) -> {
                return TreeHelpers.placeRoots(level, pos.below(), roots, random) || !roots.required();
            }).orElse(true);
            if (placeTree) {
                config.rootSystem().ifPresent((roots) -> {
                    TreeHelpers.placeRoots(level, pos.below(), roots, random);
                });
                Iterator var12 = config.layers().iterator();

                while(var12.hasNext()) {
                    StackedTreeConfig.Layer layer = (StackedTreeConfig.Layer)var12.next();
                    int layerCount = layer.getCount(random);

                    for(int i = 0; i < layerCount; ++i) {
                        ResourceLocation structureId = (ResourceLocation)layer.templates().get(random.nextInt(layer.templates().size()));
                        StructureTemplate structure = manager.getOrCreate(structureId);
                        TreeHelpers.placeTemplate(structure, settings, level, mutablePos.subtract(TreeHelpers.transformCenter(structure.getSize(), settings)));
                        mutablePos.move(0, structure.getSize().getY(), 0);
                    }
                }

                return true;
            }
        }

        return false;
    }
}
