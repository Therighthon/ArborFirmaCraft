package com.therighthon.afc.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static com.therighthon.afc.AFC.*;
import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class DataEntryPoint
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        final PackOutput output = event.getGenerator().getPackOutput();

        final var lookup = add(event, new DatapackBuiltinEntriesProvider(
            event.getGenerator().getPackOutput(), event.getLookupProvider(),
            new RegistrySetBuilder(),
            Set.of(MOD_ID, "minecraft"))).getRegistryProvider();

        final var blockTags = add(event, new BuiltInBlockTags(event, lookup)).contentsGetter();
        add(event, new BuiltInItemTags(event, lookup, blockTags));
        add(event, new BuiltInFluidTags(event, lookup));
        add(event, new BuiltInRecipes(output, lookup);

    }

    private static <T extends DataProvider> T add(GatherDataEvent event, T provider)
    {
        return event.getGenerator().addProvider(true, provider);
    }

}
