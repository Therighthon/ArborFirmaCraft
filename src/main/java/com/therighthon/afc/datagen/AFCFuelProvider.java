package com.therighthon.afc.datagen;

import com.therighthon.afc.AFCHelpers;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.data.Fuel;

public class AFCFuelProvider extends DataManagerProvider<Fuel>
{
    public AFCFuelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(Fuel.MANAGER, output, lookup);
    }


    @Override
    protected void addData(HolderLookup.Provider provider)
    {
        add(AFCWood.CYPRESS, 650, 1000, 0.8f);
        add(AFCWood.TUALANG, 696, 1300, 0.9f);
        add(AFCWood.HEVEA, 700, 1800, 0.85f);
        add(AFCWood.TEAK, 720, 1750, 0.95f);
        add(AFCWood.EUCALYPTUS, 720, 2100, 0.9f);
        add(AFCWood.BAOBAB, 710, 1100, 0.85f);
        add(AFCWood.FIG, 715, 1900, 0.95f);
        add(AFCWood.MAHOGANY, 790, 1600, 1f);
        add(AFCWood.IRONWOOD, 800, 1400, 1f);
        add(AFCWood.IPE, 710, 1700, 0.85f);
        add(AFCWood.ARAUCARIA, 690, 1000, 0.75f);
        add(AFCWood.BEECH, 750, 1800, 0.95f);
        add(AFCWood.GINKGO, 720, 1800, 0.8f);
        add(AFCWood.MAHOE, 730, 1700, 0.85f);
    }

    private void add(AFCWood wood, float temperature, int duration, float purity)
    {
        final Map<Wood.BlockType, AFCBlocks.Id<Block>> blocks = AFCBlocks.WOODS.get(wood);
        add(wood.getSerializedName() + "_logs", Ingredient.of(logsTagOf(Registries.ITEM, wood)), duration, temperature, purity);
        add(wood.getSerializedName() + "_planks", Ingredient.of(blocks.get(Wood.BlockType.PLANKS)), (int) (duration * 0.4f), temperature + 50f, purity);
    }

    private void add(String name, Ingredient item, int duration, float temperature, float purity)
    {
        add(name, new Fuel(item, duration, temperature, purity));
    }

    private <T> TagKey<T> logsTagOf(ResourceKey<Registry<T>> registry, AFCWood wood)
    {
        return TagKey.create(registry, AFCHelpers.modIdentifier(wood.getSerializedName() + "_logs"));
    }
}
