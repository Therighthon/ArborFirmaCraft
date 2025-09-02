package com.therighthon.afc;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mojang.logging.LogUtils;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.commands.AFCCommands;
import com.therighthon.afc.common.items.AFCItems;
import com.therighthon.afc.event.ModEventClientBusEvents;
import com.therighthon.afc.event.ModEvents;

import com.therighthon.afc.mixin.BlockEntityTypeAccessor;
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
import net.neoforged.neoforge.event.RegisterCommandsEvent;
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
        // Register the setup method for modloading

        //TODO: Try un-commenting
//        eventBus.addListener(this::setup);

//        ModEvents.init();

        AFCBlocks.BLOCKS.register(eventBus);
        AFCItems.ITEMS.register(eventBus);
        //TODO: Fluids
//        AFCFluids.FLUIDS.register(eventBus);
        AFCCommands.ARGUMENT_TYPES.register(eventBus);
        //TODO: Boats
//        AFCEntities.ENTITIES.register(eventBus);
        //TODO: Tree taps
//        AFCBlockEntities.BLOCK_ENTITIES.register(eventBus);
//        AFCRecipeTypes.RECIPE_TYPES.register(eventBus);
//        TFCRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
//        AFCRecipes.register(eventBus);
//        AFCCreativeModeTabs.CREATIVE_TABS.register(eventBus);

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

            if (ModList.get().isLoaded("firmalife"))
            {
                //TODO: FirmaLife
//                eventBus.addListener(ModEventClientBusEvents::clientFLCompatSetup);
            }
        }

        //TODO: Whatever replaces this
        final IEventBus forgeBus = NeoForge.EVENT_BUS;
        forgeBus.addListener(AFC::registerCommands);

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
    }

    private static void modifyWood(BlockEntityType<?> type, Wood.BlockType blockType)
    {
        modifyBlockEntityType(type, AFCBlocks.WOODS.values().stream().map(map -> map.get(blockType).get()));
    }

    private static void modifyBlockEntityType(BlockEntityType<?> type, Stream<Block> extraBlocks)
    {
        Set<Block> blocks = ((BlockEntityTypeAccessor) (Object) type).accessor$getValidBlocks();
        blocks = new HashSet<>(blocks);

        blocks.addAll(extraBlocks.collect(Collectors.toList())); //Autocompleted, could cause problems?
        ((BlockEntityTypeAccessor) (Object) type).accessor$setValidBlocks(blocks);
    }

}
