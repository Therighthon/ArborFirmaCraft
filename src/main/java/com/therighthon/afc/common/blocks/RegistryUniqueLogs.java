package com.therighthon.afc.common.blocks;

import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public interface RegistryUniqueLogs extends StringRepresentable
{

    MaterialColor woodColor();

    MaterialColor barkColor();

    AFCWood woodType();

    Supplier<Block> getBlock(UniqueLogs.BlockType var1);
}