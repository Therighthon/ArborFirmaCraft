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
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.wood.LogBlock;
import net.dries007.tfc.common.blocks.wood.Wood;

public enum UniqueLogs implements RegistryUniqueLogs
{
    RAINBOW_EUCALYPTUS(MapColor.WOOD, MapColor.WOOD, AFCWood.EUCALYPTUS),
    BLACK_OAK(MapColor.WOOD, MapColor.WOOD, Wood.OAK),
    GUM_ARABIC(MapColor.WOOD, MapColor.WOOD, Wood.ACACIA),
    REDCEDAR(MapColor.WOOD, MapColor.WOOD, AFCWood.CYPRESS),
    RUBBER_FIG(MapColor.WOOD, MapColor.WOOD, AFCWood.FIG),
    POPLAR(MapColor.WOOD, MapColor.WOOD, Wood.ASPEN);

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

    @Nullable
    public AFCWood AFCWoodType()
    {
        return AFCWoodType;
    }

    @Nullable
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
        WOOD((self, unique_log) -> new LogBlock(properties(unique_log).strength(8f).requiresCorrectToolForDrops().flammableLikeLogs(), unique_log.isAFCWoodType() ? unique_log.AFCWoodType().getBlock(Wood.BlockType.STRIPPED_WOOD) : unique_log.TFCWoodType().getBlock(Wood.BlockType.STRIPPED_WOOD))),
        TWIG((self, unique_log) -> GroundcoverBlock.twig(ExtendedProperties.of().strength(0.05F, 0.0F).sound(SoundType.WOOD).noCollission().flammableLikeWool()));

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
