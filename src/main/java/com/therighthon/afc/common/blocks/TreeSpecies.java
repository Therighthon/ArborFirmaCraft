package com.therighthon.afc.common.blocks;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
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
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public enum TreeSpecies implements RegistryTreeSpecies
{

    //Acacia
    GUM_ARABIC(true, 7, 8),
    ACACIA_KOA(true, 8, 16),
    //Ash
    EVERGREEN_ASH(true, 7, 9),
    //Blackwood
    MPINGO_BLACKWOOD(true, 7, 9),
    //Fir
    MOUNTAIN_FIR(true, 7, 11),
    BALSAM_FIR(true, 7, 13),
    //Hickory
    SCRUB_HICKORY(true, 7, 7),
    //Maple
    BIGLEAF_MAPLE(false, 7, 9),
    WEEPING_MAPLE(false, 7, 9),
    //Oak
    BLACK_OAK(true, 7, 14),
    LIVE_OAK(false, 7, 10),
    //Pine
    STONE_PINE(true, 7, 11),
    RED_PINE(true, 7, 7),
    TAMARACK(false, 7, 10),

    //Rosewood
    GIANT_ROSEWOOD(true, 7, 16),
    //Spruce
    COAST_SPRUCE(true, 7, 8),
    SITKA_SPRUCE(true, 7, 10),
    BLACK_SPRUCE(true, 7, 12),
    //Cedar
    ATLAS_CEDAR(true, 7, 10),
    //Willow
    WEEPING_WILLOW(true, 7, 7),
    //Eucalyptus
    RAINBOW_EUCALYPTUS(true, 8, 16),
    MOUNTAIN_ASH(true, 7, 13),
    //Cypress
    REDCEDAR(false, 7, 10),
    WEEPING_CYPRESS(true, 7, 7),
    BALD_CYPRESS(true, 7, 7);

    public static final TreeSpecies[] VALUES = values();
    private final String serializedName;
    private final boolean conifer;
    private final TFCTreeGrower tree;
    private final int maxDecayDistance;
    private final int daysToGrow;

    TreeSpecies(boolean conifer, int maxDecayDistance, int daysToGrow) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.conifer = conifer;
        this.tree = new TFCTreeGrower(Helpers.identifier("tree/" + this.serializedName), Helpers.identifier("tree/" + this.serializedName + "_large"));
        this.maxDecayDistance = maxDecayDistance;
        this.daysToGrow = daysToGrow;
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
        return ModBlocks.TREE_SPECIES.get(this).get(type);
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
