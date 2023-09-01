package com.therighthon.afc.common.blocks;

import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import net.dries007.tfc.common.blocks.wood.Wood;

public interface RegistryUniqueLogs extends StringRepresentable
{

    MapColor woodColor();

    MapColor barkColor();

    AFCWood AFCWoodType();

    Wood TFCWoodType();

    boolean isAFCWoodType();

    Supplier<Block> getBlock(UniqueLogs.BlockType var1);
}