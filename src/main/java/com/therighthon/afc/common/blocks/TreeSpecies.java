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
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.FallenLeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public enum TreeSpecies implements RegistryTreeSpecies
{

    //Acacia
    GUM_ARABIC(false, 8, 196),
    ACACIA_KOA(false, 16, 180),
    //Ash
    //Aspen
    POPLAR(false, 8, 250),
    //Birch
    //Blackwood
    MPINGO_BLACKWOOD(false, 11, 200),
    //Fir
    MOUNTAIN_FIR( true, 11, 0),
    BALSAM_FIR( true, 13, 0),
    //Hickory
    SCRUB_HICKORY( false, 7, 220),
    //Kapok
    RED_SILK_COTTON(false, 18, 150),
    //Maple
    BIGLEAF_MAPLE( false, 9, 215),
    WEEPING_MAPLE( true, 9, 0),
    //Oak
    BLACK_OAK( false, 14, 180),
    LIVE_OAK( false, 10, 155),
    //Palm
    JAGGERY_PALM(false, 6, 249),
    //Pine
    STONE_PINE( true, 11, 0),
    RED_PINE( true, 7, 0),
    TAMARACK(false, 10, 254),

    //Rosewood
    SHISHAM_ROSEWOOD( false, 8, 165),
    GIANT_ROSEWOOD( false, 16, 190),
    //Sequoia
    COAST_REDWOOD( true, 10, 0),
    //Spruce
    COAST_SPRUCE(true, 8, 0),
    SITKA_SPRUCE( true, 10, 0),
    BLACK_SPRUCE(true, 12, 0),
    //Cedar
    ATLAS_CEDAR( true, 10, 0),
    //Willow
    WEEPING_WILLOW(false, 7, 240),
    //Eucalyptus
    RAINBOW_EUCALYPTUS(false, 16, 30),
    MOUNTAIN_ASH(false, 13, 150),
    //Fig
    RUBBER_FIG(false, 13, 80),
    //Cypress
    REDCEDAR(true, 10, 0),
    WEEPING_CYPRESS(true, 7, 0),
    BALD_CYPRESS(false, 7, 130),
    //Mahogany
    SAPELE_MAHOGANY(false, 14, 170),
    SMALL_LEAF_MAHOGANY(false, 11, 240),
    //Teak
    IROKO_TEAK(false, 13, 140),
    FLAME_OF_THE_FOREST(false, 11, 0),
    //Ironwood
    LEBOMBO_IRONWOOD(false, 8, 230),
    HORSETAIL_IRONWOOD(false, 10, 220);

    public static final TreeSpecies[] VALUES = values();
    private final String serializedName;
    private final int autumnIndex;
    private final TFCTreeGrower tree;
    private final int daysToGrow;
    private final boolean conifer;

    TreeSpecies(boolean conifer, int daysToGrow, int autumnIndex) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.autumnIndex = autumnIndex;
        this.tree = new TFCTreeGrower(AFC.treeIdentifier("tree/" + this.serializedName), AFC.treeIdentifier("tree/" + this.serializedName + "_large"));
        this.conifer = conifer;
        this.daysToGrow = daysToGrow;
    }

    public int autumnIndex()
    {
        return autumnIndex;
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
    public TFCTreeGrower tree()
    {
        return tree;
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
            //TODO: Remove jank
            return new TFCLeavesBlock(ExtendedProperties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.GRASS).defaultInstrument().randomTicks().noOcclusion().isViewBlocking(TFCBlocks::never).flammableLikeLeaves(), wood.autumnIndex(), wood.getBlock(self.fallenLeaves()), null) {};
        }, false),
        SAPLING((self, wood) -> new TFCSaplingBlock(wood.tree(),
            ExtendedProperties.of(MapColor.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS)
                .flammableLikeLeaves().blockEntity(TFCBlockEntities.TICK_COUNTER),
            wood::daysToGrow, wood == TreeSpecies.JAGGERY_PALM), false),
        POTTED_SAPLING((self, wood) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT,
            wood.getBlock(SAPLING), BlockBehaviour.Properties.copy(Blocks.POTTED_ACACIA_SAPLING)), false),
        FALLEN_LEAVES((self, wood) -> {
            return new FallenLeavesBlock(ExtendedProperties.of().strength(0.05F, 0.0F).noOcclusion().noCollission().isViewBlocking(TFCBlocks::never).sound(SoundType.CROP).flammableLikeWool(), wood.getBlock(self.leaves()));
        }, false);

        private final BiFunction<Block, net.minecraft.world.item.Item.Properties, ? extends BlockItem> blockItemFactory;
        private final boolean isPlanksVariant;
        private final BiFunction<TreeSpecies.BlockType, RegistryTreeSpecies, Block> blockFactory;

        private static ExtendedProperties properties(RegistryTreeSpecies wood) {
            return ExtendedProperties.of(MapColor.WOOD).sound(SoundType.WOOD);
        }

        private TreeSpecies.BlockType fallenLeaves() {
            return FALLEN_LEAVES;
        }

        private TreeSpecies.BlockType leaves() {
            return LEAVES;
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

    public boolean hasBlossomingSeason ()
    {
        return (this.serializedName.equals("flame_of_the_forest") || this.serializedName.equals("giant_rosewood") || this.serializedName.equals("red_silk_cotton"));
    }
}
