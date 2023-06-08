package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.client.render.colors.ColorScheme;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public enum TreeSpecies implements RegistryTreeSpecies
{

    //Acacia
    GUM_ARABIC(7, 8, ColorScheme.EVERGREEN),
    ACACIA_KOA(8, 16, ColorScheme.EVERGREEN),
    //Ash
    BLACK_ASH(7, 7, ColorScheme.TRANSITIONAL_DECIDUOUS),
    EVERGREEN_ASH(7, 9, ColorScheme.EVERGREEN),
    //Aspen
    QUAKING_ASPEN(7, 8, ColorScheme.YELLOW_DECIDUOUS),
    //Birch
    PAPER_BIRCH(7, 7, ColorScheme.LIGHT_TRANSITIONAL_DECIDUOUS),
    //Blackwood
    MPINGO_BLACKWOOD(7, 9, ColorScheme.EVERGREEN),
    //Fir
    MOUNTAIN_FIR( 7, 11, ColorScheme.EVERGREEN),
    BALSAM_FIR( 7, 13, ColorScheme.EVERGREEN),
    //Hickory
    BITTERNUT_HICKORY(7, 10, ColorScheme.YELLOW_DECIDUOUS),
    SCRUB_HICKORY( 7, 7, ColorScheme.EVERGREEN),
    //Kapok
    RED_SILK_COTTON(7, 18, ColorScheme.KAPOK),
    //Maple
    SUGAR_MAPLE(7, 7, ColorScheme.RED_DECIDUOUS),
    BIGLEAF_MAPLE( 7, 9, ColorScheme.LIGHT_TRANSITIONAL_DECIDUOUS),
    WEEPING_MAPLE( 7, 9, ColorScheme.EVERGREEN),
    //Oak
    BLACK_OAK( 8, 14, ColorScheme.EVERGREEN),
    LIVE_OAK( 7, 10, ColorScheme.EVERGREEN), //LIGHT_TRANSITIONAL_DECIDUOUS
    //Pine
    STONE_PINE( 7, 11, ColorScheme.EVERGREEN),
    RED_PINE( 7, 7, ColorScheme.EVERGREEN),
    TAMARACK(7, 10, ColorScheme.YELLOW_DECIDUOUS),

    //Rosewood
    GIANT_ROSEWOOD( 7, 16, ColorScheme.JACARANDA),
    //Sequoia
    COAST_REDWOOD( 7, 10, ColorScheme.EVERGREEN),
    //Spruce
    COAST_SPRUCE(7, 8, ColorScheme.EVERGREEN),
    SITKA_SPRUCE( 7, 10, ColorScheme.EVERGREEN),
    BLACK_SPRUCE(7, 12, ColorScheme.EVERGREEN),
    //Cedar
    ATLAS_CEDAR( 7, 10, ColorScheme.EVERGREEN),
    //Willow
    CORKSCREW_WILLOW(7, 11, ColorScheme.YELLOW_DECIDUOUS),
    WEEPING_WILLOW(7, 7, ColorScheme.EVERGREEN),
    //Eucalyptus
    RAINBOW_EUCALYPTUS(8, 16, ColorScheme.EVERGREEN),
    MOUNTAIN_ASH(7, 13, ColorScheme.EVERGREEN),
    //Fig
    RUBBER_FIG(7, 13, ColorScheme.EVERGREEN),
    //Cypress
    REDCEDAR(7, 10, ColorScheme.EVERGREEN),
    WEEPING_CYPRESS(7, 7, ColorScheme.EVERGREEN),
    BALD_CYPRESS(7, 7, ColorScheme.ORANGE_DECIDUOUS),
    //Mahogany
    SAPELE_MAHOGANY(7, 14, ColorScheme.EVERGREEN),
    SMALL_LEAF_MAHOGANY(7, 11, ColorScheme.EVERGREEN);

    public static final TreeSpecies[] VALUES = values();
    private final String serializedName;
    private final ColorScheme colorScheme;
    private final TFCTreeGrower tree;
    private final int maxDecayDistance;
    private final int daysToGrow;

    TreeSpecies(int maxDecayDistance, int daysToGrow, ColorScheme colorScheme) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.colorScheme = colorScheme;
        this.tree = new TFCTreeGrower(AFC.treeIdentifier("tree/" + this.serializedName), AFC.treeIdentifier("tree/" + this.serializedName + "_large"));
        this.maxDecayDistance = maxDecayDistance;
        this.daysToGrow = daysToGrow;
    }

    public ColorScheme getColorScheme()
    {
        return colorScheme;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public boolean isConifer()
    {
        return colorScheme==ColorScheme.EVERGREEN;
    }

    @Override
    public TFCTreeGrower tree()
    {
        return tree;
    }

    @Override
    public int maxDecayDistance()
    {
        return maxDecayDistance;
    }

    @Override
    public int daysToGrow()
    {
        return defaultDaysToGrow();
    }



    public int defaultDaysToGrow()
    {
        return daysToGrow;
    }

    @Override
    public Supplier<Block> getBlock(TreeSpecies.BlockType type)
    {
        return AFCBlocks.TREE_SPECIES.get(this).get(type);
    }

    public enum BlockType {
        LEAVES((self, wood) -> {
            return TFCLeavesBlock.create(ExtendedProperties.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCBlocks::never).flammableLikeLeaves(), wood.maxDecayDistance());
        }, false),
        SAPLING((self, wood) -> {
            TFCTreeGrower var10002 = wood.tree();
            ExtendedProperties var10003 = ExtendedProperties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).flammableLikeLeaves().blockEntity(TFCBlockEntities.TICK_COUNTER);
            Objects.requireNonNull(wood);
            return new TFCSaplingBlock(var10002, var10003, wood::daysToGrow);
        }, false),
        POTTED_SAPLING((self, wood) -> {
            return new FlowerPotBlock(() -> {
                return (FlowerPotBlock)Blocks.FLOWER_POT;
            }, wood.getBlock(SAPLING), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion());
        }, false);

        private final BiFunction<Block, net.minecraft.world.item.Item.Properties, ? extends BlockItem> blockItemFactory;
        private final boolean isPlanksVariant;
        private final BiFunction<TreeSpecies.BlockType, RegistryTreeSpecies, Block> blockFactory;

        private static ExtendedProperties properties(RegistryTreeSpecies wood) {
            return ExtendedProperties.of(Material.WOOD, MaterialColor.COLOR_GREEN).sound(SoundType.WOOD);
        }


        private BlockType(BiFunction<TreeSpecies.BlockType, RegistryTreeSpecies, Block> blockFactory, boolean isPlanksVariant) {
            this(blockFactory, isPlanksVariant, BlockItem::new);
        }

        private BlockType(BiFunction<TreeSpecies.BlockType, RegistryTreeSpecies, Block> blockFactory, boolean isPlanksVariant, BiFunction<Block, net.minecraft.world.item.Item.Properties, ? extends BlockItem> blockItemFactory) {
            this.blockFactory = blockFactory;
            this.isPlanksVariant = isPlanksVariant;
            this.blockItemFactory = blockItemFactory;
        }

        @Nullable
        public Function<Block, BlockItem> createBlockItem(net.minecraft.world.item.Item.Properties properties) {
            return this.needsItem() ? (block) -> {
                return (BlockItem)this.blockItemFactory.apply(block, properties);
            } : null;
        }

        public boolean needsItem() {
            return this != POTTED_SAPLING;
        }

        public Object nameFor(TreeSpecies wood)
        {
            return (this.isPlanksVariant ? "wood/planks/" + wood.getSerializedName() + "_" + this.name() : "wood/" + this.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT);
        }

        public Supplier<Block> create(RegistryTreeSpecies wood) {
            return () -> {
                return (Block)this.blockFactory.apply(this, wood);
            };
        }

        public String nameFor(RegistryTreeSpecies wood) {
            return ("wood/" + this.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT);
        }

    }
}
