package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ForgeConfigSpec;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryWood;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public enum AFCWood implements RegistryWood
{
    //Wood color, then bark color
    EUCALYPTUS(true, MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10),
    MAHOGANY(true, MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10),
    BAOBAB(true, MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10),
    HEVEA(true, MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10),
    TUALANG(true, MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10),
    TEAK(false, MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10),
    CYPRESS(false, MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10);

    public static final AFCWood[] VALUES = values();

    private final String serializedName;
    private final boolean conifer;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;
    private final TFCTreeGrower tree;
    private final int maxDecayDistance;
    private final int defaultDaysToGrow;

    AFCWood(boolean conifer, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.conifer = conifer;
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.tree = new TFCTreeGrower(Helpers.identifier("tree/" + this.serializedName), Helpers.identifier("tree/" + this.serializedName + "_large"));
        this.maxDecayDistance = maxDecayDistance;
        this.defaultDaysToGrow = daysToGrow;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public boolean isConifer()
    {
        return conifer;
    }

    @Override
    public MaterialColor woodColor()
    {
        return woodColor;
    }

    @Override
    public MaterialColor barkColor()
    {
        return barkColor;
    }
    public Supplier<Block> getBlock(Wood.BlockType type) {
        return ModBlocks.WOODS.get(this).get(type);
    }

    public TFCTreeGrower tree() {
        return tree;
    }
    public int maxDecayDistance() {
        return maxDecayDistance;
    }

    public int daysToGrow() {
        return (Integer)((ForgeConfigSpec.IntValue) TFCConfig.SERVER.saplingGrowthDays.get(this)).get();
    }

    public int defaultDaysToGrow() {
        return defaultDaysToGrow;
    }
}