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
    REDCEDAR(MaterialColor.SAND, MaterialColor.STONE, AFCWood.CYPRESS),

    ANCIENT_EUCALYPTUS(MaterialColor.SAND, MaterialColor.STONE, AFCWood.EUCALYPTUS),
    ANCIENT_BAOBAB(MaterialColor.SAND, MaterialColor.STONE, AFCWood.BAOBAB),
    ANCIENT_MAHOGANY(MaterialColor.SAND, MaterialColor.STONE, AFCWood.MAHOGANY),
    ANCIENT_HEVEA(MaterialColor.SAND, MaterialColor.STONE, AFCWood.HEVEA),
    ANCIENT_TEAK(MaterialColor.SAND, MaterialColor.STONE, AFCWood.TEAK),
    ANCIENT_TUALANG(MaterialColor.SAND, MaterialColor.STONE, AFCWood.TUALANG),
    ANCIENT_CYPRESS(MaterialColor.SAND, MaterialColor.STONE, AFCWood.CYPRESS),
    ANCIENT_FIG(MaterialColor.SAND, MaterialColor.STONE, AFCWood.FIG),

    ANCIENT_ACACIA(MaterialColor.SAND, MaterialColor.STONE, Wood.ACACIA),
    ANCIENT_ASH(MaterialColor.SAND, MaterialColor.STONE, Wood.ASH),
    ANCIENT_ASPEN(MaterialColor.SAND, MaterialColor.STONE, Wood.ASPEN),
    ANCIENT_BIRCH(MaterialColor.SAND, MaterialColor.STONE, Wood.BIRCH),
    ANCIENT_BLACKWOOD(MaterialColor.SAND, MaterialColor.STONE, Wood.BLACKWOOD),
    ANCIENT_CHESTNUT(MaterialColor.SAND, MaterialColor.STONE, Wood.CHESTNUT),
    ANCIENT_DOUGLAS_FIR(MaterialColor.SAND, MaterialColor.STONE, Wood.DOUGLAS_FIR),
    ANCIENT_HICKORY(MaterialColor.SAND, MaterialColor.STONE, Wood.HICKORY),
    ANCIENT_KAPOK(MaterialColor.SAND, MaterialColor.STONE, Wood.KAPOK),
    ANCIENT_MAPLE(MaterialColor.SAND, MaterialColor.STONE, Wood.MAPLE),
    ANCIENT_OAK(MaterialColor.SAND, MaterialColor.STONE, Wood.OAK),
    ANCIENT_PALM(MaterialColor.SAND, MaterialColor.STONE, Wood.PALM),
    ANCIENT_PINE(MaterialColor.SAND, MaterialColor.STONE, Wood.PINE),
    ANCIENT_ROSEWOOD(MaterialColor.SAND, MaterialColor.STONE, Wood.ROSEWOOD),
    ANCIENT_SEQUOIA(MaterialColor.SAND, MaterialColor.STONE, Wood.SEQUOIA),
    ANCIENT_SPRUCE(MaterialColor.SAND, MaterialColor.STONE, Wood.SPRUCE),
    ANCIENT_SYCAMORE(MaterialColor.SAND, MaterialColor.STONE, Wood.SYCAMORE),
    ANCIENT_WHITE_CEDAR(MaterialColor.SAND, MaterialColor.STONE, Wood.WHITE_CEDAR),
    ANCIENT_WILLOW(MaterialColor.SAND, MaterialColor.STONE, Wood.WILLOW),

    ANCIENT_RAINBOW_EUCALYPTUS(MaterialColor.SAND, MaterialColor.STONE, AFCWood.EUCALYPTUS),
    ANCIENT_BLACK_OAK(MaterialColor.SAND, MaterialColor.STONE, Wood.OAK),
    ANCIENT_GUM_ARABIC(MaterialColor.SAND, MaterialColor.STONE, Wood.ACACIA),
    ANCIENT_REDCEDAR(MaterialColor.SAND, MaterialColor.STONE, AFCWood.CYPRESS);

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
