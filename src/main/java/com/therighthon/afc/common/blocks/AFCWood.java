package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.client.render.colors.ColorScheme;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public enum AFCWood implements RegistryWood
{
    //Wood color, then bark color
    EUCALYPTUS(MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10, ColorScheme.EVERGREEN),
    MAHOGANY( MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10, ColorScheme.EVERGREEN),
    BAOBAB(MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10, ColorScheme.EVERGREEN),
    HEVEA(MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10, ColorScheme.EVERGREEN),
    TUALANG(MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10, ColorScheme.EVERGREEN),
    TEAK(MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10, ColorScheme.EVERGREEN),
    CYPRESS(MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,10, ColorScheme.EVERGREEN),
    FIG(MaterialColor.TERRACOTTA_ORANGE, MaterialColor.TERRACOTTA_LIGHT_GRAY,7,12, ColorScheme.EVERGREEN);

    public static final AFCWood[] VALUES = values();

    private final String serializedName;
    private final ColorScheme colorScheme;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;
    private final TFCTreeGrower tree;
    private final int maxDecayDistance;
    private final int defaultDaysToGrow;

    AFCWood(MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow, ColorScheme colorScheme) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.woodColor = woodColor;
        this.colorScheme = colorScheme;
        this.barkColor = barkColor;
        this.tree = new TFCTreeGrower(AFC.treeIdentifier("tree/" + this.serializedName), AFC.treeIdentifier("tree/" + this.serializedName + "_large"));
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
        return colorScheme == ColorScheme.EVERGREEN;
    }

    public ColorScheme getColorScheme()
    {
        return colorScheme;
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
        return AFCBlocks.WOODS.get(this).get(type);
    }

    public TFCTreeGrower tree() {
        return tree;
    }
    public int maxDecayDistance() {
        return maxDecayDistance;
    }

    public int daysToGrow() {
        //return (Integer)((ForgeConfigSpec.IntValue) AFCConfig.SERVER.saplingGrowthDays.get(this)).get();
        return defaultDaysToGrow();
    }

    public int defaultDaysToGrow() {
        return defaultDaysToGrow;
    }

}