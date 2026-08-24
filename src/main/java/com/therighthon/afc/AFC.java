package com.therighthon.afc;

import com.therighthon.afc.common.AFCCreativeModeTabs;
import com.therighthon.afc.common.blockentities.AFCBlockEntities;
import com.therighthon.afc.common.blocks.FLCompatBlocks;
import com.therighthon.afc.common.entities.AFCEntities;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.items.AFCItemCapabilities;
import com.therighthon.afc.common.recipe.AFCRecipeSerializers;
import com.therighthon.afc.common.recipe.AFCRecipeTypes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import com.mojang.logging.LogUtils;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.items.AFCItems;
import com.therighthon.afc.event.ModEventClientBusEvents;
import com.therighthon.afc.event.ModEvents;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import net.dries007.tfc.common.capabilities.ItemCapabilities;

@Mod(AFC.MOD_ID)
public final class AFC
{
    public static final String MOD_ID = "afc";
    public static final String MOD_NAME = "ArborFirmaCraft";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public AFC(ModContainer modContainer, IEventBus eventBus)
    {
        eventBus.addListener(this::setup);

        // Data overload
        eventBus.addListener(ModEvents::onPackFinder);

        eventBus.addListener(ModEvents::addToBlockEntities);

        AFCBlocks.BLOCKS.register(eventBus);
        AFCBlocks.FLUID_BLOCKS.register(eventBus);
        AFCItems.ITEMS.register(eventBus);
        AFCFluids.FLUID_TYPES.register(eventBus);
        AFCFluids.FLUIDS.register(eventBus);
        AFCEntities.ENTITIES.register(eventBus);
        AFCBlockEntities.BLOCK_ENTITIES.register(eventBus);
        AFCRecipeTypes.RECIPE_TYPES.register(eventBus);
        AFCRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
        AFCCreativeModeTabs.CREATIVE_TABS.register(eventBus);

        if (ModList.get().isLoaded("firmalife"))
        {
            FLCompatBlocks.FL_COMPAT_BLOCKS.register(eventBus);
            eventBus.addListener(ModEvents::onFLCompatPackFinder);
        }

        eventBus.addListener(AFCItemCapabilities::register);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            eventBus.addListener(ModEventClientBusEvents::clientSetup);
            eventBus.addListener(ModEventClientBusEvents::registerEntityLayers);
            eventBus.addListener(ModEventClientBusEvents::registerColorHandlerBlocks);
            eventBus.addListener(ModEventClientBusEvents::registerColorHandlerItems);
            eventBus.addListener(ModEventClientBusEvents::onLayers);
            eventBus.addListener(ModEventClientBusEvents::onRegisterEntityRenderers);
            eventBus.addListener(ModEventClientBusEvents::registerExtensions);
            if (ModList.get().isLoaded("firmalife"))
            {
                eventBus.addListener(ModEventClientBusEvents::clientFLCompatSetup);
            }
        }
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("AFC COMMON SETUP");
        event.enqueueWork(AFCWood::registerBlockSetTypes);
        event.enqueueWork(AFCBlocks::registerFlowerPotFlowers);
    }


}
