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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.LogBlock;
import net.dries007.tfc.common.blocks.wood.Wood;

public enum UniqueLogs implements RegistryUniqueLogs
{
    RAINBOW_EUCALYPTUS(MaterialColor.SAND, MaterialColor.STONE, AFCWood.EUCALYPTUS),
    BLACK_OAK(MaterialColor.SAND, MaterialColor.STONE, Wood.OAK),
    GUM_ARABIC(MaterialColor.SAND, MaterialColor.STONE, Wood.ACACIA),
    REDCEDAR(MaterialColor.SAND, MaterialColor.STONE, AFCWood.CYPRESS);

    public static final UniqueLogs[] VALUES = values();
    private final String serializedName;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;
    @Nullable
    private final AFCWood AFCWoodType;
    @Nullable
    private final Wood TFCWoodType;
    private final boolean hasAFCWoodType;

    UniqueLogs(MaterialColor woodColor, MaterialColor barkColor, Wood WoodType) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.hasAFCWoodType = false;
        this.TFCWoodType = WoodType;
        this.AFCWoodType = null;
    }

    UniqueLogs(MaterialColor woodColor, MaterialColor barkColor, AFCWood AFCWoodType) {
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
    public MaterialColor woodColor()
    {
        return woodColor;
    }

    @Override
    public MaterialColor barkColor()
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
        return ModBlocks.UNIQUE_LOGS.get(this).get(type);
    }

    public enum BlockType {
        LOG((self, unique_log) -> new LogBlock(ExtendedProperties.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? unique_log.woodColor() : unique_log.barkColor()).strength(8f).sound(SoundType.WOOD).requiresCorrectToolForDrops().flammableLikeLogs(), unique_log.isAFCWoodType() ? unique_log.AFCWoodType().getBlock(Wood.BlockType.STRIPPED_LOG) : unique_log.TFCWoodType().getBlock(Wood.BlockType.STRIPPED_LOG))),
        WOOD((self, unique_log) -> new LogBlock(properties(unique_log).strength(8f).requiresCorrectToolForDrops().flammableLikeLogs(), unique_log.isAFCWoodType() ? unique_log.AFCWoodType().getBlock(Wood.BlockType.STRIPPED_WOOD) : unique_log.TFCWoodType().getBlock(Wood.BlockType.STRIPPED_WOOD)));

        private static ExtendedProperties properties(RegistryUniqueLogs unique_log)
        {
            return ExtendedProperties.of(Material.WOOD, unique_log.woodColor()).sound(SoundType.WOOD);
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
