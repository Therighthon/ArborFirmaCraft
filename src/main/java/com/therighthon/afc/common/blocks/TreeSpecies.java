package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.FallenLeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.dries007.tfc.util.Helpers;

public enum TreeSpecies implements RegistryTreeSpecies
{

    //Acacia
    GUM_ARABIC(false, 8, 196, 0.0292f),
    ACACIA_KOA(false, 16, 180, 0.0193f),
    //Ash
    //Aspen
    POPLAR(false, 8, 250, 0.0140f),
    //Birch
    //Blackwood
    MPINGO_BLACKWOOD(false, 11, 200, 0.0292f),
    //Chestnut
    HARDY_CHESTNUT(false, 8, 180, 0.0179f),
    //Fir
    MOUNTAIN_FIR( true, 11, 0, 0.0543f),
    BALSAM_FIR( true, 13, 0, 0.0511f),
    //Hickory
    SCRUB_HICKORY( false, 7, 220, 0.078f),
    //Kapok
    RED_SILK_COTTON(false, 18, 150, 0.0089f),
    //Maple
    BIGLEAF_MAPLE( false, 9, 215, 0.0175f),
    WEEPING_MAPLE( true, 9, 0, 0.0545f),
    //Oak
    BLACK_OAK( false, 14, 180, 0.0174f),
    LIVE_OAK( false, 10, 155, 0.0175f),
    //Palm
    JAGGERY_PALM(false, 6, 249, 0.0447f),
    //Pine
    STONE_PINE( true, 11, 0, 0.0283f),
    RED_PINE( true, 8, 0, 0.0248f),
    TAMARACK(false, 8, 254, 0.0511f),
    HUANGSHAN_PINE(true, 9, 0, 0.0541f),

    //Rosewood
    GIANT_ROSEWOOD( false, 16, 190, 0.0127f),
    //Sequoia
    COAST_REDWOOD( true, 10, 0, 0.0132f),
    DAWN_REDWOOD(true, 9, 0, 0.0248f),
    //Spruce
    COAST_SPRUCE(true, 8, 0, 0.0238f),
    SITKA_SPRUCE( true, 10, 0, 0.0543f),
    BLACK_SPRUCE(true, 12, 0, 0.0318f),
    //Cedar
    ATLAS_CEDAR( true, 10, 0, 0.0210f),
    //Willow
    WEEPING_WILLOW(false, 16, 240, 0.0107f),
    //Eucalyptus
    RAINBOW_EUCALYPTUS(false, 16, 30, 0.0145f),
    MOUNTAIN_ASH(false, 13, 150, 0.0140f),
    //Fig
    RUBBER_FIG(false, 13, 80, 0.0127f),
    //Cypress
    REDCEDAR(true, 10, 0, 0.0132f),
    WEEPING_CYPRESS(true, 7, 0, 0.0591f),
    BALD_CYPRESS(false, 7, 130, 0.0543f),
    JUNIPER(true, 8, 0, 0.0474f),
    //Mahogany
    SAPELE_MAHOGANY(false, 14, 170, 0.0089f),
    SMALL_LEAF_MAHOGANY(false, 11, 240, 0.0175f),
    //Teak
    IROKO_TEAK(false, 13, 140, 0.0089f),
    FLAME_OF_THE_FOREST(false, 11, 0, 0.0428f),
    //Ironwood
    LEBOMBO_IRONWOOD(false, 8, 230, 0.0472f),
    HORSETAIL_IRONWOOD(false, 10, 220, 0.0447f),
    //Araucaria
    KAURI(true, 22, 0, 0.0240f),
    COLUMNAR_ARAUCARIA(true, 9, 0, 0.0305f),
    PARANA(true, 11, 0, 0.0554f),
    //Beech
    RAULI_BEECH(false, 10, 40, 0.0209f),
    BLACK_BEECH(false, 10, 200, 0.0201f),
    CHINQUAPIN(false, 7, 130, 0.0350f);

    public static final TreeSpecies[] VALUES = values();
    private final String serializedName;
    private final int autumnIndex;
    private final TreeGrower tree;
    private final int daysToGrow;
    private final boolean conifer;
    private final float saplingDropRate;

    TreeSpecies(boolean conifer, int daysToGrow, int autumnIndex, float saplingDropChance) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.autumnIndex = autumnIndex;
        this.tree = new TreeGrower(
            Helpers.identifier(this.serializedName).toString(),
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, Helpers.identifier("tree/" + this.serializedName))),
            Optional.empty()
        );
        this.conifer = conifer;
        this.daysToGrow = daysToGrow;
        this.saplingDropRate = saplingDropChance;
    }

    public int autumnIndex()
    {
        return autumnIndex;
    }

    public float getSaplingDropRate()
    {
        return saplingDropRate;
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
    public TreeGrower tree()
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
            return new TFCLeavesBlock(ExtendedProperties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.GRASS).defaultInstrument().randomTicks().noOcclusion().isViewBlocking(TFCBlocks::never).flammableLikeLeaves(), wood.autumnIndex(), wood.getBlock(self.fallenLeaves()), null) {};
        }, false),
        SAPLING((self, wood) -> new TFCSaplingBlock(wood.tree(),
            ExtendedProperties.of(MapColor.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS)
                .flammableLikeLeaves().blockEntity(TFCBlockEntities.TICK_COUNTER),
            wood::daysToGrow, wood == TreeSpecies.JAGGERY_PALM), false),
        POTTED_SAPLING((self, wood) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT,
            wood.getBlock(SAPLING), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ACACIA_SAPLING)), false),
        FALLEN_LEAVES((self, wood) -> {
            return new FallenLeavesBlock(ExtendedProperties.of().strength(0.05F, 0.0F).noOcclusion().noCollission().isViewBlocking(TFCBlocks::never).sound(SoundType.CROP).flammableLikeWool(), wood.getBlock(self.leaves()));
        }, false);

        private final BiFunction<Block, net.minecraft.world.item.Item.Properties, ? extends BlockItem> blockItemFactory;
        private final boolean isPlanksVariant;
        private final BiFunction<TreeSpecies.BlockType, RegistryTreeSpecies, Block> blockFactory;

        private static ExtendedProperties properties(RegistryTreeSpecies wood) {
            return ExtendedProperties.of(MapColor.WOOD).sound(SoundType.WOOD);
        }

        private static ExtendedProperties properties(AFCWood wood) {
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
