package com.therighthon.afc.event;

//Copied pretty directly from EERussianguy's Beneath

import com.therighthon.afc.AFC;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.mixin.BlockEntityTypeAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.locating.IModFile;
import org.jetbrains.annotations.NotNull;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;

public class ModEvents
{
    public static void initFLCompat()
    {
//        final IEventBus bus = NeoForge.EVENT_BUS;
//TODO: Firmalife
//        bus.addListener(ModEvents::onFLCompatPackFinder);
//        bus.addListener(ModEvents::onFLCompatDataPackFinder);
    }

    //This is what allows the resources to load in the correct order, and needs to be rewritten for 1.20 unless things work out fo the box now.
    @SubscribeEvent
    public static void onPackFinder(AddPackFindersEvent event)
    {
        if (event.getPackType() == PackType.SERVER_DATA)
        {
            final ResourceLocation location = Helpers.resourceLocation(AFC.MOD_ID, "data");
            event.addPackFinders(location, PackType.SERVER_DATA, Component.literal("AFC: Data Overload"), PackSource.DEFAULT, true, Pack.Position.TOP);
        }
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
