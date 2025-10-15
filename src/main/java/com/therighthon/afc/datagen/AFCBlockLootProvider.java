package com.therighthon.afc.datagen;

import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.items.AFCItems;
import java.util.Set;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.wood.Wood;

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
        // TODO: Need to add all block loot individually, but doing this allows me to run the code without crashing
        AFCBlocks.BLOCKS.getEntries().forEach(b -> dropSelf(b.get()));

        AFCBlocks.WOODS.forEach(
            (species, map) -> add(species.getBlock(Wood.BlockType.LEAVES).get(),
                block -> createAFCLeavesDrops(block, species.getBlock(Wood.BlockType.SAPLING).get(), species.getSaplingDropRate())
            )
        );
        AFCBlocks.TREE_SPECIES.forEach(
            (species, map) -> add(species.getBlock(TreeSpecies.BlockType.LEAVES).get(),
                block -> createAFCLeavesDrops(block, species.getBlock(TreeSpecies.BlockType.SAPLING).get(), species.getSaplingDropRate())
            )
        );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks()
    {
        return AFCBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    protected LootTable.Builder createAFCLeavesDrops(Block leavesBlock, Block saplingBlock, float saplingDropRate)
    {
        return LootTable.lootTable()
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
                        LootItem.lootTableItem(Items.STICK)
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(TFCTags.Items.TOOLS_SHARP)))
                            .when(InvertedLootItemCondition.invert(hasShearsOrSilkTouch()))
                            .when(randomChance(0.2f))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))),
                        LootItem.lootTableItem(Items.STICK)
                            .when(InvertedLootItemCondition.invert(hasShearsOrSilkTouch()))
                            .when(randomChance(0.05f))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                    ))
                    .when(survivesExplosion())
            );
    }

    private LootItemCondition.Builder hasShearsOrSilkTouch() {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR)).or(this.hasSilkTouch());
    }

}
