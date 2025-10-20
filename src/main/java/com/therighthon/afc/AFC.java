package com.therighthon.afc;

import com.therighthon.afc.common.AFCCreativeModeTabs;
import com.therighthon.afc.common.blockentities.AFCBlockEntities;
import com.therighthon.afc.common.entities.AFCEntities;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.recipe.AFCRecipeSerializers;
import com.therighthon.afc.common.recipe.AFCRecipeTypes;
import com.therighthon.afc.mixin.BlockEntityTypeAccessor;
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
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.wood.Wood;

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
            //TODO: FirmaLife
//            FLCompatBlocks.BLOCKS.register(eventBus);
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
            eventBus.addListener(ModEventClientBusEvents::registerExtensions);

            if (ModList.get().isLoaded("firmalife"))
            {
                //TODO: FirmaLife
//                eventBus.addListener(ModEventClientBusEvents::clientFLCompatSetup);
            }
        }

        //TODO: Whatever replaces this
        final IEventBus forgeBus = NeoForge.EVENT_BUS;

        //TODO: Maybe re-enable, maybe was causing failure to start
//         Register ourselves for server and other game events we are interested in
//        forgeBus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("AFC COMMON SETUP");
        event.enqueueWork(AFCWood::registerBlockSetTypes);
        event.enqueueWork(() -> {
            AFCBlocks.registerFlowerPotFlowers();
            modifyBlockEntityTypes();
        });
    }

    private static void modifyBlockEntityTypes()
    {
        modifyWood(TFCBlockEntities.CHEST.get(), Wood.BlockType.CHEST);
        modifyWood(TFCBlockEntities.TRAPPED_CHEST.get(), Wood.BlockType.TRAPPED_CHEST);
        modifyWood(TFCBlockEntities.LOOM.get(), Wood.BlockType.LOOM);
        modifyWood(TFCBlockEntities.BARREL.get(), Wood.BlockType.BARREL);
        modifyWood(TFCBlockEntities.SLUICE.get(), Wood.BlockType.SLUICE);
        modifyWood(TFCBlockEntities.BOOKSHELF.get(), Wood.BlockType.BOOKSHELF);
        modifyWood(TFCBlockEntities.TOOL_RACK.get(), Wood.BlockType.TOOL_RACK);
        modifyWood(TFCBlockEntities.LECTERN.get(), Wood.BlockType.LECTERN);
        modifyWood(TFCBlockEntities.AXLE.get(), Wood.BlockType.AXLE);
        modifyWood(TFCBlockEntities.BLADED_AXLE.get(), Wood.BlockType.BLADED_AXLE);
        modifyWood(TFCBlockEntities.WATER_WHEEL.get(), Wood.BlockType.WATER_WHEEL);
        modifyWood(TFCBlockEntities.WINDMILL.get(), Wood.BlockType.WINDMILL);
        modifyWood(TFCBlockEntities.SIGN.get(), Wood.BlockType.SIGN);
        modifyWood(TFCBlockEntities.SIGN.get(), Wood.BlockType.WALL_SIGN);
    }

    private static void modifyWood(BlockEntityType<?> type, Wood.BlockType blockType)
    {
        modifyBlockEntityType(type, AFCBlocks.WOODS.values().stream().map(map -> map.get(blockType).get()));
    }

    private static void modifyBlockEntityType(BlockEntityType<?> type, Stream<Block> extraBlocks)
    {
        Set<Block> blocks = type.getValidBlocks();
        blocks = new HashSet<>(blocks);

        blocks.addAll(extraBlocks.toList());
        ((BlockEntityTypeAccessor) type).accessor$setValidBlocks(blocks);
    }

}
