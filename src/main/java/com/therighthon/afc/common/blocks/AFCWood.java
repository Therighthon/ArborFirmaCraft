package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.client.render.colors.ColorScheme;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryWood;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public enum AFCWood implements RegistryWood
{
    //Wood color, then bark color
    BAOBAB(MapColor.WOOD, MapColor.WOOD,7,10, ColorScheme.EVERGREEN),
    EUCALYPTUS(MapColor.WOOD, MapColor.WOOD,7,10, ColorScheme.EVERGREEN),
    MAHOGANY( MapColor.WOOD, MapColor.WOOD,7,10, ColorScheme.EVERGREEN),
    HEVEA(MapColor.WOOD, MapColor.WOOD,7,10, ColorScheme.EVERGREEN),
    TUALANG(MapColor.WOOD, MapColor.WOOD,7,10, ColorScheme.EVERGREEN),
    TEAK(MapColor.WOOD, MapColor.WOOD,7,10, ColorScheme.EVERGREEN),
    CYPRESS(MapColor.WOOD, MapColor.WOOD,7,10, ColorScheme.EVERGREEN),
    FIG(MapColor.WOOD, MapColor.WOOD,7,12, ColorScheme.EVERGREEN);

    public static final AFCWood[] VALUES = values();

    private final String serializedName;
    private final ColorScheme colorScheme;
    private final MapColor woodColor;
    private final MapColor barkColor;
    private final TFCTreeGrower tree;
    private final int maxDecayDistance;
    private final int defaultDaysToGrow;
    private final BlockSetType blockSet;
    private final WoodType woodType;

    AFCWood(MapColor woodColor, MapColor barkColor, int maxDecayDistance, int daysToGrow, ColorScheme colorScheme) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.woodColor = woodColor;
        this.colorScheme = colorScheme;
        this.barkColor = barkColor;
        this.tree = new TFCTreeGrower(AFC.treeIdentifier("tree/" + this.serializedName), AFC.treeIdentifier("tree/" + this.serializedName + "_large"));
        this.maxDecayDistance = maxDecayDistance;
        this.defaultDaysToGrow = daysToGrow;
        this.blockSet = new BlockSetType(serializedName);
        this.woodType = new WoodType(Helpers.identifier(this.serializedName).toString(), this.blockSet);
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
    public MapColor woodColor()
    {
        return woodColor;
    }

    @Override
    public MapColor barkColor()
    {
        return barkColor;
    }
    public Supplier<Block> getBlock(Wood.BlockType type) {
        return AFCBlocks.WOODS.get(this).get(type);
    }

    @Override
    public BlockSetType getBlockSet()
    {
        return blockSet;
    }

    @Override
    public WoodType getVanillaWoodType()
    {
        return woodType;
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

    public static void registerBlockSetTypes()
    {
        for (AFCWood wood : VALUES)
        {
            BlockSetType.register(wood.blockSet);
            WoodType.register(wood.woodType);
        }
    }

}