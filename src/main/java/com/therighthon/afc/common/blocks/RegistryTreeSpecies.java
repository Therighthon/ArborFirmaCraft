package com.therighthon.afc.common.blocks;

import java.util.function.Supplier;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;

public interface RegistryTreeSpecies extends StringRepresentable
{
    TreeGrower tree();

    int autumnIndex();

    int daysToGrow();
    Supplier<Block> getBlock(TreeSpecies.BlockType var1);
}