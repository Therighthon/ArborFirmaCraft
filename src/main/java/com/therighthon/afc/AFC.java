package com.therighthon.afc;

import com.mojang.logging.LogUtils;
import com.therighthon.afc.common.AFCCreativeModeTabs;
import com.therighthon.afc.common.AFCFeatures;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.FLCompatBlocks;
import com.therighthon.afc.common.commands.AFCCommands;
import com.therighthon.afc.common.entities.AFCEntities;
import com.therighthon.afc.common.items.AFCItems;
import com.therighthon.afc.common.recipe.AFCRecipes;
import com.therighthon.afc.event.ModEventClientBusEvents;
import com.therighthon.afc.event.ModEvents;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod(AFC.MOD_ID)
public final class AFC
{
    public static final String MOD_ID = "afc";
    public static final String MOD_NAME = "ArborFirmaCraft";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public AFC(ModContainer modContainer, IEventBus eventBus)
    {
        // Register the setup method for modloading

        eventBus.addListener(this::setup);
        ModEvents.init();

        AFCBlocks.BLOCKS.register(eventBus);
        AFCItems.ITEMS.register(eventBus);
        //TODO: Fluids
//        AFCFluids.FLUIDS.register(eventBus);
        AFCCommands.ARGUMENT_TYPES.register(eventBus);
        AFCEntities.ENTITIES.register(eventBus);
        AFCFeatures.FEATURES.register(eventBus);
        //TODO: Tree taps
//        AFCBlockEntities.BLOCK_ENTITIES.register(eventBus);
//        AFCRecipeTypes.RECIPE_TYPES.register(eventBus);
//        TFCRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
        AFCRecipes.register(eventBus);
        AFCCreativeModeTabs.CREATIVE_TABS.register(eventBus);

        if (ModList.get().isLoaded("firmalife"))
        {
            FLCompatBlocks.BLOCKS.register(eventBus);
            ModEvents.initFLCompat();
        }
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            eventBus.addListener(ModEventClientBusEvents::clientSetup);
            eventBus.addListener(ModEventClientBusEvents::registerClientReloadListeners);
            eventBus.addListener(ModEventClientBusEvents::onEntityRenderers);
            eventBus.addListener(ModEventClientBusEvents::registerColorHandlerBlocks);
            eventBus.addListener(ModEventClientBusEvents::registerColorHandlerItems);
            eventBus.addListener(ModEventClientBusEvents::onLayers);

            if (ModList.get().isLoaded("firmalife"))
            {
                eventBus.addListener(ModEventClientBusEvents::clientFLCompatSetup);
            }
        }

        //TODO: Whatever replaces this
//        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
//        forgeBus.addListener(AFC::registerCommands);

        // Register ourselves for server and other game events we are interested in
//        forgeBus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("AFC COMMON SETUP");
        event.enqueueWork(AFCWood::registerBlockSetTypes);
    }

    //TODO: Maybe need this?
//    public static ResourceLocation treeIdentifier(String path)
//    {
//        return new ResourceLocation("tfc", path);
//    }

    public static void registerCommands(RegisterCommandsEvent event)
    {
        LOGGER.debug("Registering AFC Commands");
        AFCCommands.registerCommands(event.getDispatcher(), event.getBuildContext());
    }

}
