package com.therighthon.afc.event;

//Copied pretty directly from EERussianguy's Beneath

import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;

public class ModEvents
{
    public static void initFLCompat()
    {
//        final IEventBus bus = NeoForge.EVENT_BUS;
//TODO: Firmalife
//        bus.addListener(ModEvents::onFLCompatPackFinder);
//        bus.addListener(ModEvents::onFLCompatDataPackFinder);
    }

    //This is what allows the resources to load in the correct order
    @SubscribeEvent
    public static void onPackFinder(AddPackFindersEvent event)
    {
        if (event.getPackType() == PackType.SERVER_DATA)
        {
            final ResourceLocation location = Helpers.resourceLocation(AFC.MOD_ID, "data");
            event.addPackFinders(location, PackType.SERVER_DATA, Component.literal("AFC: Data Overload"), PackSource.DEFAULT, true, Pack.Position.TOP);
        }
    }

    // This is where we add new AFC wood blocks to existing TFC block entity types
    @SubscribeEvent
    public static void addToBlockEntities(BlockEntityTypeAddBlocksEvent event)
    {
        modifyWood(TFCBlockEntities.CHEST.get(), Wood.BlockType.CHEST, event);
        modifyWood(TFCBlockEntities.TRAPPED_CHEST.get(), Wood.BlockType.TRAPPED_CHEST, event);
        modifyWood(TFCBlockEntities.LOOM.get(), Wood.BlockType.LOOM, event);
        modifyWood(TFCBlockEntities.BARREL.get(), Wood.BlockType.BARREL, event);
        modifyWood(TFCBlockEntities.SLUICE.get(), Wood.BlockType.SLUICE, event);
        modifyWood(TFCBlockEntities.BOOKSHELF.get(), Wood.BlockType.BOOKSHELF, event);
        modifyWood(TFCBlockEntities.TOOL_RACK.get(), Wood.BlockType.TOOL_RACK, event);
        modifyWood(TFCBlockEntities.LECTERN.get(), Wood.BlockType.LECTERN, event);
        modifyWood(TFCBlockEntities.AXLE.get(), Wood.BlockType.AXLE, event);
        modifyWood(TFCBlockEntities.BLADED_AXLE.get(), Wood.BlockType.BLADED_AXLE, event);
        modifyWood(TFCBlockEntities.WATER_WHEEL.get(), Wood.BlockType.WATER_WHEEL, event);
        modifyWood(TFCBlockEntities.WINDMILL.get(), Wood.BlockType.WINDMILL, event);
        modifyWood(TFCBlockEntities.TICK_COUNTER.get(), Wood.BlockType.SAPLING, event);
        modifyWood(TFCBlockEntities.SIGN.get(), Wood.BlockType.SIGN, event);
        modifyWood(TFCBlockEntities.SIGN.get(), Wood.BlockType.WALL_SIGN, event);
        modifyWood(TFCBlockEntities.CLUTCH.get(), Wood.BlockType.CLUTCH, event);
        modifyWood(TFCBlockEntities.GEAR_BOX.get(), Wood.BlockType.GEAR_BOX, event);
        modifyWood(TFCBlockEntities.SHELF.get(), Wood.BlockType.SHELF, event);
        modifyWood(TFCBlockEntities.ENCASED_AXLE.get(), Wood.BlockType.ENCASED_AXLE, event);

        for (Metal metal : Metal.values())
        {
            if (metal.allParts())
            {
                for (AFCWood wood : AFCWood.values())
                {
                    modifyBlockEntityType(TFCBlockEntities.HANGING_SIGN.get(), Stream.of(AFCBlocks.CEILING_HANGING_SIGNS.get(wood).get(metal).get()), event);
                    modifyBlockEntityType(TFCBlockEntities.HANGING_SIGN.get(), Stream.of(AFCBlocks.WALL_HANGING_SIGNS.get(wood).get(metal).get()), event);
                }
            }
        }
    }

    private static void modifyWood(BlockEntityType<?> type, Wood.BlockType blockType,BlockEntityTypeAddBlocksEvent event)
    {
        modifyBlockEntityType(type, AFCBlocks.WOODS.values().stream().map(map -> map.get(blockType).get()), event);
    }

    private static void modifyBlockEntityType(BlockEntityType<?> type, Stream<Block> extraBlocks, BlockEntityTypeAddBlocksEvent event)
    {
        extraBlocks.forEach(
            (block -> event.modify(type, block))
        );
    }

    //TODO: Firmalife
//    public static void onFLCompatPackFinder(AddPackFindersEvent event)
//    {
//        try
//        {
//            if (event.getPackType() == PackType.CLIENT_RESOURCES)
//            {
//                final Path resourcePath = ModList.get().getModFileById(AFC.MOD_ID).getFile().findResource("firmalife_compat_assets");
//                try (PathPackResources pack = new PathPackResources("firmalife_compat_assets", true, resourcePath))
//                {
//                    final PackMetadataSection metadata = pack.getMetadataSection(PackMetadataSection.TYPE);
//                    if (metadata != null)
//                    {
//                        AFC.LOGGER.info("Adding FirmaLife compatibility resource pack");
//                        event.addRepositorySource(consumer ->
//                            consumer.accept(Pack.readMetaAndCreate("firmalife_compat_assets", Component.literal("FirmaLife Compat Resources"), true, id -> pack, PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN))
//                        );
//                    }
//                }
//            }
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void onFLCompatDataPackFinder(AddPackFindersEvent event)
//    {
//        try
//        {
//            if (event.getPackType() == PackType.SERVER_DATA)
//            {
//                final Path resourcePath = ModList.get().getModFileById(AFC.MOD_ID).getFile().findResource("firmalife_compat_data");
//                try (PathPackResources pack = new PathPackResources("firmalife_compat_data", true, resourcePath))
//                {
//                    final PackMetadataSection metadata = pack.getMetadataSection(PackMetadataSection.TYPE);
//                    if (metadata != null)
//                    {
//                        AFC.LOGGER.info("Adding FirmaLife compatibility data pack");
//                        event.addRepositorySource(consumer ->
//                            consumer.accept(Pack.readMetaAndCreate("firmalife_compat_data", Component.literal("FirmaLife Compat Data"), true, id -> pack, PackType.SERVER_DATA, Pack.Position.TOP, PackSource.BUILT_IN))
//                        );
//                    }
//                }
//            }
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }





}
