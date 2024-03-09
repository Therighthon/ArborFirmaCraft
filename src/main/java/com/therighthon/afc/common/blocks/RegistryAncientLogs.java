package com.therighthon.afc.common.blocks;

import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blocks.wood.Wood;

public interface RegistryAncientLogs extends StringRepresentable
{
    MapColor woodColor();

    MapColor barkColor();

    @Nullable
    AFCWood AFCWoodType();

    @Nullable
    Wood TFCWoodType();

    boolean isAFCWoodType();

    Supplier<Block> getBlock(AncientLogs.BlockType var1);
}
