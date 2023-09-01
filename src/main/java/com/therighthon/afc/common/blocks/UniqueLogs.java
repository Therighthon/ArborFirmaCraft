package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.LogBlock;
import net.dries007.tfc.common.blocks.wood.Wood;

public enum UniqueLogs implements RegistryUniqueLogs
{
    RAINBOW_EUCALYPTUS(MapColor.WOOD, MapColor.WOOD, AFCWood.EUCALYPTUS),
    BLACK_OAK(MapColor.WOOD, MapColor.WOOD, Wood.OAK),
    GUM_ARABIC(MapColor.WOOD, MapColor.WOOD, Wood.ACACIA),
    REDCEDAR(MapColor.WOOD, MapColor.WOOD, AFCWood.CYPRESS),
    RUBBER_FIG(MapColor.WOOD, MapColor.WOOD, AFCWood.FIG),

    ANCIENT_EUCALYPTUS(MapColor.WOOD, MapColor.WOOD, AFCWood.EUCALYPTUS),
    ANCIENT_BAOBAB(MapColor.WOOD, MapColor.WOOD, AFCWood.BAOBAB),
    ANCIENT_MAHOGANY(MapColor.WOOD, MapColor.WOOD, AFCWood.MAHOGANY),
    ANCIENT_HEVEA(MapColor.WOOD, MapColor.WOOD, AFCWood.HEVEA),
    ANCIENT_TEAK(MapColor.WOOD, MapColor.WOOD, AFCWood.TEAK),
    ANCIENT_TUALANG(MapColor.WOOD, MapColor.WOOD, AFCWood.TUALANG),
    ANCIENT_CYPRESS(MapColor.WOOD, MapColor.WOOD, AFCWood.CYPRESS),
    ANCIENT_FIG(MapColor.WOOD, MapColor.WOOD, AFCWood.FIG),

    ANCIENT_ACACIA(MapColor.WOOD, MapColor.WOOD, Wood.ACACIA),
    ANCIENT_ASH(MapColor.WOOD, MapColor.WOOD, Wood.ASH),
    ANCIENT_ASPEN(MapColor.WOOD, MapColor.WOOD, Wood.ASPEN),
    ANCIENT_BIRCH(MapColor.WOOD, MapColor.WOOD, Wood.BIRCH),
    ANCIENT_BLACKWOOD(MapColor.WOOD, MapColor.WOOD, Wood.BLACKWOOD),
    ANCIENT_CHESTNUT(MapColor.WOOD, MapColor.WOOD, Wood.CHESTNUT),
    ANCIENT_DOUGLAS_FIR(MapColor.WOOD, MapColor.WOOD, Wood.DOUGLAS_FIR),
    ANCIENT_HICKORY(MapColor.WOOD, MapColor.WOOD, Wood.HICKORY),
    ANCIENT_KAPOK(MapColor.WOOD, MapColor.WOOD, Wood.KAPOK),
    ANCIENT_MAPLE(MapColor.WOOD, MapColor.WOOD, Wood.MAPLE),
    ANCIENT_OAK(MapColor.WOOD, MapColor.WOOD, Wood.OAK),
    ANCIENT_PALM(MapColor.WOOD, MapColor.WOOD, Wood.PALM),
    ANCIENT_PINE(MapColor.WOOD, MapColor.WOOD, Wood.PINE),
    ANCIENT_ROSEWOOD(MapColor.WOOD, MapColor.WOOD, Wood.ROSEWOOD),
    ANCIENT_SEQUOIA(MapColor.WOOD, MapColor.WOOD, Wood.SEQUOIA),
    ANCIENT_SPRUCE(MapColor.WOOD, MapColor.WOOD, Wood.SPRUCE),
    ANCIENT_SYCAMORE(MapColor.WOOD, MapColor.WOOD, Wood.SYCAMORE),
    ANCIENT_WHITE_CEDAR(MapColor.WOOD, MapColor.WOOD, Wood.WHITE_CEDAR),
    ANCIENT_WILLOW(MapColor.WOOD, MapColor.WOOD, Wood.WILLOW),

    ANCIENT_RAINBOW_EUCALYPTUS(MapColor.WOOD, MapColor.WOOD, AFCWood.EUCALYPTUS),
    ANCIENT_BLACK_OAK(MapColor.WOOD, MapColor.WOOD, Wood.OAK),
    ANCIENT_GUM_ARABIC(MapColor.WOOD, MapColor.WOOD, Wood.ACACIA),
    ANCIENT_REDCEDAR(MapColor.WOOD, MapColor.WOOD, AFCWood.CYPRESS),
    ANCIENT_RUBBER_FIG(MapColor.WOOD, MapColor.WOOD, AFCWood.FIG);

    public static final UniqueLogs[] VALUES = values();
    private final String serializedName;
    private final MapColor woodColor;
    private final MapColor barkColor;
    @Nullable
    private final AFCWood AFCWoodType;
    @Nullable
    private final Wood TFCWoodType;
    private final boolean hasAFCWoodType;

    UniqueLogs(MapColor woodColor, MapColor barkColor, Wood WoodType) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.hasAFCWoodType = false;
        this.TFCWoodType = WoodType;
        this.AFCWoodType = null;
    }

    UniqueLogs(MapColor woodColor, MapColor barkColor, AFCWood AFCWoodType) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.hasAFCWoodType = true;
        this.TFCWoodType = null;
        this.AFCWoodType = AFCWoodType;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
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

    public AFCWood AFCWoodType()
    {
        return AFCWoodType;
    }

    public Wood TFCWoodType()
    {
        return TFCWoodType;
    }

    public boolean isAFCWoodType()
    {
        return hasAFCWoodType;
    }

    @Override
    public Supplier<Block> getBlock(UniqueLogs.BlockType type) {
        return AFCBlocks.UNIQUE_LOGS.get(this).get(type);
    }

    public enum BlockType {
        LOG((self, unique_log) -> new LogBlock(ExtendedProperties.of(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? unique_log.woodColor() : unique_log.barkColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops().flammableLikeLogs(), unique_log.isAFCWoodType() ? unique_log.AFCWoodType().getBlock(Wood.BlockType.STRIPPED_LOG) : unique_log.TFCWoodType().getBlock(Wood.BlockType.STRIPPED_LOG))),
        WOOD((self, unique_log) -> new LogBlock(properties(unique_log).strength(8f).requiresCorrectToolForDrops().flammableLikeLogs(), unique_log.isAFCWoodType() ? unique_log.AFCWoodType().getBlock(Wood.BlockType.STRIPPED_WOOD) : unique_log.TFCWoodType().getBlock(Wood.BlockType.STRIPPED_WOOD)));

        private static ExtendedProperties properties(RegistryUniqueLogs unique_log)
        {
            return ExtendedProperties.of(unique_log.woodColor()).sound(SoundType.WOOD);
        }

        private final BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory;
        private final BiFunction<UniqueLogs.BlockType, RegistryUniqueLogs, Block> blockFactory;

        BlockType(Function<RegistryUniqueLogs, Block> blockFactory)
        {
            this((self, unique_log) -> blockFactory.apply(unique_log));
        }

        BlockType(BiFunction<UniqueLogs.BlockType, RegistryUniqueLogs, Block> blockFactory)
        {
            this(blockFactory, BlockItem::new);
        }

        BlockType(BiFunction<UniqueLogs.BlockType, RegistryUniqueLogs, Block> blockFactory, BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory)
        {
            this.blockFactory = blockFactory;
            this.blockItemFactory = blockItemFactory;
        }

        public Supplier<Block> create(RegistryUniqueLogs unique_log) {
            return () -> (Block)this.blockFactory.apply(this, unique_log);
        }

        @Nullable
        public Function<Block, BlockItem> createBlockItem(net.minecraft.world.item.Item.Properties properties) {
            return (block) -> (BlockItem)this.blockItemFactory.apply(block, properties);
        }

        public String nameFor(UniqueLogs wood) {
            return ("wood/" + this.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT);
        }

    }
}
