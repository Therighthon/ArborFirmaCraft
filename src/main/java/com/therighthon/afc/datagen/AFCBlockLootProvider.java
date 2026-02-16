package com.therighthon.afc.datagen;

import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AncientLogs;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.blocks.UniqueLogs;
import com.therighthon.afc.common.items.AFCItems;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.datafix.fixes.BlockEntityCustomNameToComponentFix;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.devices.SluiceBlock;
import net.dries007.tfc.common.blocks.wood.BranchDirection;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.component.TFCComponents;
import net.dries007.tfc.common.component.block.BarrelComponent;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.loot.ApplyStackSizeFunction;
import net.dries007.tfc.util.loot.TFCLoot;

import static net.minecraft.world.level.storage.loot.LootPool.*;
import static net.minecraft.world.level.storage.loot.entries.LootItem.*;
import static net.minecraft.world.level.storage.loot.predicates.ExplosionCondition.*;
import static net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.*;

public class AFCBlockLootProvider extends BlockLootSubProvider
{

    protected AFCBlockLootProvider(HolderLookup.Provider registries)
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate()
    {
        // Blocks in the Wood BlockType registry or a similar registry
        for (Wood.BlockType type : Wood.BlockType.values())
        {
            AFCBlocks.WOODS.forEach(
                (species, map) ->
                {
                    createWood(species, type);
                }
            );
        }
        for (TreeSpecies.BlockType type : TreeSpecies.BlockType.values())
        {
            AFCBlocks.TREE_SPECIES.forEach(
                (species, map) ->
                {
                    createSpecies(species, type);
                }
            );
        }
        for (UniqueLogs.BlockType type : UniqueLogs.BlockType.values())
        {
            AFCBlocks.UNIQUE_LOGS.forEach(
                (species, map) ->
                {
                    createUnique(species, type);
                }
            );
        }
        for (AncientLogs.BlockType type : AncientLogs.BlockType.values())
        {
            AFCBlocks.ANCIENT_LOGS.forEach(
                (species, map) ->
                {
                    createAncient(species, type);
                }
            );
        }

        // Hanging Signs
        for (Metal metal : Metal.values())
        {
            if (metal.allParts())
            {
                for (AFCWood wood : AFCWood.values())
                {
                    dropSelf(AFCBlocks.CEILING_HANGING_SIGNS.get(wood).get(metal).get());
                    dropSelf(AFCBlocks.WALL_HANGING_SIGNS.get(wood).get(metal).get());
                }
            }
        }

        // Misc
        dropSelf(AFCBlocks.TREE_TAP.get());
    }

    protected void createWood(AFCWood species, Wood.BlockType blockType)
    {
        switch (blockType)
        {
            case LOG, STRIPPED_LOG, WOOD, STRIPPED_WOOD:
                createLogDrops(species, blockType);
                break;
            case LEAVES:
                createAFCLeavesDrops(species.getBlock(blockType).get(), species.getBlock(Wood.BlockType.SAPLING).get(), species.getSaplingDropRate());
                break;
            case POTTED_SAPLING:
                add(species.getBlock(blockType).get(), createPotFlowerItemTable(species.getBlock(Wood.BlockType.SAPLING).get().asItem()));
                break;
            case VERTICAL_SUPPORT, HORIZONTAL_SUPPORT:
                dropOther(species.getBlock(blockType).get(), AFCItems.SUPPORTS.get(species));
                break;
            case BARREL:
                createBarrelDrop(species);
                break;
            case DOOR:
                createDoorDrop(species);
                break;
            case SLUICE:
                createSluiceDrop(species);
                break;
            default:
                dropSelf(species.getBlock(blockType).get());
        }
    }

    protected void createSpecies(TreeSpecies species, TreeSpecies.BlockType blockType)
    {
        switch (blockType)
        {
            case LEAVES:
                createAFCLeavesDrops(species.getBlock(blockType).get(), species.getBlock(TreeSpecies.BlockType.SAPLING).get(), species.getSaplingDropRate());
                break;
            case POTTED_SAPLING:
                add(species.getBlock(blockType).get(), createPotFlowerItemTable(species.getBlock(TreeSpecies.BlockType.SAPLING).get().asItem()));
                break;
            default:
                dropSelf(species.getBlock(blockType).get());
        }
    }

    protected void createUnique(UniqueLogs species, UniqueLogs.BlockType blockType)
    {
        switch (blockType)
        {
            case LOG, WOOD:
                createUniqueLogDrops(species, blockType);
                break;
            default:
                dropSelf(species.getBlock(blockType).get());
        }
    }

    protected void createAncient(AncientLogs species, AncientLogs.BlockType blockType)
    {
        final Item droppedLog;
        if (species.isAFCWoodType())
        {
            assert species.AFCWoodType() != null;
            droppedLog = species.AFCWoodType().getBlock(Wood.BlockType.LOG).get().asItem();
        }
        else
        {
            assert species.TFCWoodType() != null;
            droppedLog = species.TFCWoodType().getBlock(Wood.BlockType.LOG).get().asItem();
        }

        switch (blockType)
        {
            case LOG, WOOD:
                createAncientLogDrops(species.getBlock(blockType).get(), droppedLog, species.getDropChance());
                break;
            default:
                dropSelf(species.getBlock(blockType).get());
        }
    }


    @Override
    protected @NotNull Iterable<Block> getKnownBlocks()
    {
        return AFCBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    protected void createLogDrops(AFCWood species, Wood.BlockType blockType)
    {
        Block thisBlock = species.getBlock(blockType).get();
        Item logItem = species.getBlock(Wood.BlockType.LOG).get().asItem();

        add(thisBlock, LootTable.lootTable()
            .withPool(
                lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(
                        lootTableItem(Items.STICK)
                            .when(isHammer())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))),
                        lootTableItem(logItem)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(thisBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TFCBlockStateProperties.BRANCH_DIRECTION, BranchDirection.NONE)).invert()),
                        lootTableItem(thisBlock.asItem())
                    )).when(survivesExplosion())
            )
        );
    }

    protected void createSluiceDrop(AFCWood species)
    {
        Block sluiceBlock = species.getBlock(Wood.BlockType.SLUICE).get();
        LootTable.Builder table =  LootTable.lootTable().withPool((LootPool.Builder) this.applyExplosionCondition(sluiceBlock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(sluiceBlock).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(sluiceBlock).setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(SluiceBlock.UPPER, true))))));
        add(sluiceBlock, table);
    }

    protected void createDoorDrop(AFCWood species)
    {
        Block doorBlock = species.getBlock(Wood.BlockType.DOOR).get();
        LootTable.Builder table = LootTable.lootTable().withPool((LootPool.Builder) this.applyExplosionCondition(doorBlock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(doorBlock).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorBlock).setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER))))));
        add(doorBlock, table);
    }

    protected void createUniqueLogDrops(UniqueLogs species, UniqueLogs.BlockType blockType)
    {
        Block thisBlock = species.getBlock(blockType).get();
        Item logItem = species.getBlock(UniqueLogs.BlockType.LOG).get().asItem();

        add(thisBlock, LootTable.lootTable()
            .withPool(
                lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(
                        lootTableItem(Items.STICK)
                            .when(isHammer())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))),
                        lootTableItem(logItem)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(thisBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TFCBlockStateProperties.BRANCH_DIRECTION, BranchDirection.NONE)).invert()),
                        lootTableItem(thisBlock.asItem())
                    )).when(survivesExplosion())
            )
        );
    }

    protected void createAncientLogDrops(Block block, Item drop, float dropChance)
    {
        add(block, LootTable.lootTable()
            .withPool(
                lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(
                        lootTableItem(Items.STICK)
                            .when(isHammer())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))),
                        lootTableItem(drop)
                            .when(randomChance(dropChance))
                    )).when(survivesExplosion())
            )
        );
    }

    protected void createAFCLeavesDrops(Block leavesBlock, Block saplingBlock, float saplingDropRate)
    {
        add(leavesBlock, LootTable.lootTable()
            .withPool(
                lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .name("self_pool")
                    .add(lootTableItem(leavesBlock)
                        .when(hasShearsOrSilkTouch()))
                    .when(survivesExplosion())
            ).withPool(
                lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .name("sapling_pool")
                    .add(lootTableItem(saplingBlock.asItem())
                        .when(survivesExplosion())
                        .when(randomChance(saplingDropRate))
                    )
                    .when(survivesExplosion())
            )
            .withPool(
                lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .name("stick_pool")
                    .add(AlternativesEntry.alternatives(
                        lootTableItem(Items.STICK)
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(TFCTags.Items.TOOLS_SHARP)))
                            .when(InvertedLootItemCondition.invert(hasShearsOrSilkTouch()))
                            .when(randomChance(0.2f))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))),
                        lootTableItem(Items.STICK)
                            .when(InvertedLootItemCondition.invert(hasShearsOrSilkTouch()))
                            .when(randomChance(0.05f))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                    ))
                    .when(survivesExplosion())
            )
        );
    }

    protected void createBarrelDrop(AFCWood wood)
    {
        Block barrelBlock = wood.getBlock(Wood.BlockType.BARREL).get();

        add(barrelBlock, LootTable.lootTable()
            .withPool(
                lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(
                        lootTableItem(barrelBlock)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(barrelBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TFCBlockStateProperties.SEALED, true)))
                            .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(DataComponents.CUSTOM_NAME).include(TFCComponents.BARREL.get()))
                            .apply(ApplyStackSizeFunction.simpleBuilder(ApplyStackSizeFunction::new)),
                        lootTableItem(barrelBlock.asItem())
                    )).when(survivesExplosion())
            )
        );
    }

    private LootItemCondition.Builder hasShearsOrSilkTouch()
    {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR)).or(this.hasSilkTouch());
    }

    private LootItemCondition.Builder isHammer()
    {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(TFCTags.Items.TOOLS_HAMMER));
    }

}
