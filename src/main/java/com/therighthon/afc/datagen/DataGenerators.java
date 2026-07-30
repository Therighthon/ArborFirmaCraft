package com.therighthon.afc.datagen;

import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCWood;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = AFC.MOD_ID)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Loot
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
            List.of(new LootTableProvider.SubProviderEntry(AFCBlockLootProvider::new, LootContextParamSets.BLOCK)),
            lookupProvider)
        );

        // Tags
        BlockTagsProvider blockTagsProvider = new AFCBlockTagProvider(packOutput, lookupProvider, AFC.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new AFCItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), AFC.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeServer(), new AFCFluidTagProvider(packOutput, lookupProvider, AFC.MOD_ID, existingFileHelper));

        // Recipes
        generator.addProvider(event.includeServer(), new AFCRecipeProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(), new AFCFuelProvider(packOutput, lookupProvider));

        // Misc

        generator.addProvider(event.includeServer(), new AFCSupportsProvider(packOutput, lookupProvider));
    }
}
