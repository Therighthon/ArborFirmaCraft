package com.therighthon.afc.common.blocks;

import java.util.function.Supplier;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;

import net.dries007.tfc.util.registry.RegistryWood;

public interface RegistryTreeSpecies extends RegistryWood
{
    TreeGrower tree();

    boolean isConifer();

    float getFlowerOffset();

    Supplier<Block> getBlock(TreeSpecies.BlockType var1);
}