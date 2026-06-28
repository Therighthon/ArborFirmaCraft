package com.therighthon.afc.common.blocks;

import com.therighthon.afc.AFCHelpers;
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
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.FallenLeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.calendar.ICalendar;

public enum TreeSpecies implements RegistryTreeSpecies
{

    //Acacia
    GUM_ARABIC(false, 0, 8, 196, 0.0292f),
    ACACIA_KOA(false, 0, 16, 180, 0.0196f),
    //Ash
    //Aspen
    POPLAR(false, 0, 8, 250, 0.0170f),
    //Birch
    //Blackwood
    MPINGO_BLACKWOOD(false, 0, 11, 200, 0.0292f),
    //Chestnut
    HARDY_CHESTNUT(false, 0, 8, 180, 0.0189f),
    //Fir
    MOUNTAIN_FIR( true, 0, 11, 0, 0.0543f),
    BALSAM_FIR( true, 0, 13, 0, 0.0511f),
    //Hickory
    SCRUB_HICKORY( false, 0, 7, 220, 0.078f),
    //Kapok
    RED_SILK_COTTON(false, 0, 18, 150, 0.0145f),
    //Maple
    BIGLEAF_MAPLE( false, 0, 9, 215, 0.0188f),
    WEEPING_MAPLE( true, 0, 9, 0, 0.0545f),
    //Oak
    BLACK_OAK( false, 0, 14, 180, 0.0187f),
    LIVE_OAK( false, 0, 10, 155, 0.0188f),
    //Palm
    JAGGERY_PALM(false, 0, 6, 249, 0.0447f),
    //Pine
    STONE_PINE( true, 0, 11, 0, 0.0283f),
    RED_PINE( true, 0, 8, 0, 0.0248f),
    TAMARACK(false, 0, 8, 254, 0.0511f),
    HUANGSHAN_PINE(true, 0, 9, 0, 0.0541f),

    //Rosewood
    GIANT_ROSEWOOD( false, 0, 16, 190, 0.0163f),
    //Sequoia
    COAST_REDWOOD( true, 0, 10, 0, 0.0166f),
    DAWN_REDWOOD(true, 0, 9, 0, 0.0248f),
    //Spruce
    COAST_SPRUCE(true, 0, 8, 0, 0.0238f),
    SITKA_SPRUCE( true, 0, 10, 0, 0.0543f),
    BLACK_SPRUCE(true, 0, 12, 0, 0.0318f),
    //Cedar
    ATLAS_CEDAR( true, 0, 10, 0, 0.0210f),
    //Willow
    WEEPING_WILLOW(false, 0, 16, 240, 0.0154f),
    //Eucalyptus
    RAINBOW_EUCALYPTUS(false, 0, 16, 30, 0.0173f),
    MOUNTAIN_ASH(false, 0, 13, 150, 0.0170f),
    //Fig
    RUBBER_FIG(false, 0, 13, 80, 0.0163f),
    //Cypress
    REDCEDAR(true, 0, 10, 0, 0.0166f),
    WEEPING_CYPRESS(true, 0, 7, 0, 0.0591f),
    BALD_CYPRESS(false, 0, 7, 130, 0.0543f),
    JUNIPER(true, 0, 8, 0, 0.0474f),
    //Mahogany
    SAPELE_MAHOGANY(false, 0, 14, 170, 0.0145f),
    SMALL_LEAF_MAHOGANY(false, 0, 11, 240, 0.0175f),
    //Teak
    IROKO_TEAK(false, 0, 13, 140, 0.0143f),
    FLAME_OF_THE_FOREST(false, 0, 11, 0, 0.0428f),
    //Ironwood
    LEBOMBO_IRONWOOD(false, 0, 8, 230, 0.0472f),
    HORSETAIL_IRONWOOD(false, 0, 10, 220, 0.0447f),
    //Araucaria
    KAURI(true, 0, 22, 0, 0.0240f),
    COLUMNAR_ARAUCARIA(true, 0, 9, 0, 0.0305f),
    PARANA(true, 0, 11, 0, 0.0554f),
    //Beech
    RAULI_BEECH(false, 0, 10, 40, 0.0209f),
    BLACK_BEECH(false, 0, 10, 200, 0.0201f),
    CHINQUAPIN(false, 0, 7, 130, 0.0350f);

    public static final TreeSpecies[] VALUES = values();
    private final String serializedName;
    private final int autumnIndex;
    private final TreeGrower tree;
    private final int daysToGrow;
    private final boolean conifer;
    private final float flowerOffset;
    private final float saplingDropRate;

    TreeSpecies(boolean conifer, float flowerOffset, int daysToGrow, int autumnIndex, float saplingDropChance) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.autumnIndex = autumnIndex;
        this.tree = new TreeGrower(
            AFCHelpers.modIdentifier(this.serializedName).toString(),
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, AFCHelpers.modIdentifier("tree/" + this.serializedName))),
            Optional.empty()
        );
        this.conifer = conifer;
        this.flowerOffset = flowerOffset;
        this.daysToGrow = daysToGrow;
        this.saplingDropRate = saplingDropChance;
    }

    public int autumnIndex()
    {
        return autumnIndex;
    }

    @Override
    public Supplier<Block> getBlock(Wood.BlockType blockType)
    {
        return null;
    }

    @Override
    public BlockSetType getBlockSet()
    {
        return null;
    }

    @Override
    public WoodType getVanillaWoodType()
    {
        return null;
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
    
    public float getFlowerOffset()
    {
        return flowerOffset;
    }

    @Override
    public MapColor woodColor()
    {
        return null;
    }

    @Override
    public MapColor barkColor()
    {
        return null;
    }

    @Override
    public TreeGrower tree()
    {
        return tree;
    }


    @Override
    public Supplier<Integer> ticksToGrow()
    {
        return () -> defaultDaysToGrow() * ICalendar.CALENDAR_TICKS_IN_DAY;
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
            return new TFCLeavesBlock(ExtendedProperties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.GRASS).defaultInstrument().randomTicks().noOcclusion().isViewBlocking(TFCBlocks::never).flammableLikeLeaves(), wood, wood.getBlock(self.fallenLeaves()), null) {};
        }),
        SAPLING(wood -> new TFCSaplingBlock(wood.tree(),
            ExtendedProperties.of(MapColor.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS)
                .flammableLikeLeaves().blockEntity(TFCBlockEntities.TICK_COUNTER),
                wood.ticksToGrow(), wood == TreeSpecies.JAGGERY_PALM)), // TODO: More robust sand handling?
        POTTED_SAPLING((self, wood) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT,
            wood.getBlock(SAPLING), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ACACIA_SAPLING))),
        FALLEN_LEAVES((self, wood) -> {
            return new FallenLeavesBlock(ExtendedProperties.of().strength(0.05F, 0.0F).noOcclusion().noCollission().isViewBlocking(TFCBlocks::never).sound(SoundType.CROP).flammableLikeWool(), wood.getBlock(self.leaves()));
        });

        private final BiFunction<Block, net.minecraft.world.item.Item.Properties, ? extends BlockItem> blockItemFactory;

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

        BlockType(Function<RegistryTreeSpecies, Block> blockFactory)
        {
            this((self, wood) -> blockFactory.apply(wood));
        }

        private BlockType(BiFunction<TreeSpecies.BlockType, RegistryTreeSpecies, Block> blockFactory) {
            this(blockFactory, BlockItem::new);
        }

        private BlockType(BiFunction<TreeSpecies.BlockType, RegistryTreeSpecies, Block> blockFactory, BiFunction<Block, net.minecraft.world.item.Item.Properties, ? extends BlockItem> blockItemFactory) {
            this.blockFactory = blockFactory;
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

        public Supplier<Block> create(RegistryTreeSpecies wood) {
            return () -> {
                return (Block)this.blockFactory.apply(this, wood);
            };
        }

        public String nameFor(TreeSpecies wood) {
            return ("wood/" + this.name() + "/" + wood.getSerializedName()).toLowerCase(Locale.ROOT);
        }

    }

    public boolean hasBlossomingSeason ()
    {
        return (this.serializedName.equals("flame_of_the_forest") || this.serializedName.equals("giant_rosewood") || this.serializedName.equals("red_silk_cotton"));
    }
}
