package com.therighthon.afc.common.blocks;

import java.util.function.Supplier;

import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;

public interface RegistryTreeSpecies extends StringRepresentable
{
    TFCTreeGrower tree();

    int maxDecayDistance();

    int daysToGrow();
    Supplier<Block> getBlock(TreeSpecies.BlockType var1);
}