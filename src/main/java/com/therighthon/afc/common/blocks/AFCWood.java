package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryWood;

public enum AFCWood implements RegistryWood
{
    //Wood color, then bark color
    BAOBAB(false, MapColor.WOOD, MapColor.WOOD,10, 212),
    EUCALYPTUS(false, MapColor.WOOD, MapColor.WOOD,10, 150),
    MAHOGANY( false, MapColor.WOOD, MapColor.WOOD,10, 10),
    HEVEA(false, MapColor.WOOD, MapColor.WOOD,10, 130),
    TUALANG(false, MapColor.WOOD, MapColor.WOOD,10, 226),
    TEAK(false, MapColor.WOOD, MapColor.WOOD,10, 240),
    CYPRESS(true, MapColor.WOOD, MapColor.WOOD,10, 0),
    FIG(false, MapColor.WOOD, MapColor.WOOD,12, 250),
    IRONWOOD(false, MapColor.WOOD, MapColor.WOOD, 14, 200),
    IPE(false, MapColor.WOOD, MapColor.WOOD, 11, 254);

    public static final AFCWood[] VALUES = values();

    private final String serializedName;
    private final boolean conifer;
    private final MapColor woodColor;
    private final MapColor barkColor;
    private final TreeGrower tree;
    private final int daysToGrow;
    private final BlockSetType blockSet;
    private final WoodType woodType;
    private final int autumnIndex;

    AFCWood(boolean evergreen, MapColor woodColor, MapColor barkColor, int daysToGrow, int autumnIndex) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.conifer = evergreen;
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.autumnIndex = autumnIndex;
        this.tree = new TreeGrower(
            Helpers.identifier(this.serializedName).toString(),
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, Helpers.identifier("tree/" + this.serializedName))),
            Optional.empty()
        );
        this.daysToGrow = daysToGrow;
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
        return conifer;
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

    public TreeGrower tree() {
        return tree;
    }

    public int daysToGrow() {
        //return (Integer)((ForgeConfigSpec.IntValue) AFCConfig.SERVER.saplingGrowthDays.get(this)).get();
        return defaultDaysToGrow();
    }

    @Override
    public int autumnIndex()
    {
        return autumnIndex;
    }

    public int defaultDaysToGrow() {
        return daysToGrow;
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