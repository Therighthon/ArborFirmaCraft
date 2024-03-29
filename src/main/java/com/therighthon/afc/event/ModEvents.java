package com.therighthon.afc.event;

//Copied pretty directly from EERussianguy's Beneath

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.mixin.BlockEntityTypeAccessor;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.PathResourcePack;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.wood.Wood;

public class ModEvents
{
    public static void init()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(ModEvents::setup);
        bus.addListener(ModEvents::onPackFinder);
    }

    private static void setup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            AFCBlocks.registerFlowerPotFlowers();

            modifyBlockEntityTypes();
        });
    }

    //This is copied wholesale from FirmaLife
    public static void onPackFinder(AddPackFindersEvent event)
    {
        try
        {
            if (event.getPackType() == PackType.CLIENT_RESOURCES)
            {
                var modFile = ModList.get().getModFileById(AFC.MOD_ID).getFile();
                var resourcePath = modFile.getFilePath();
                var pack = new PathResourcePack(modFile.getFileName() + ":overload", resourcePath)
                {
                    @Nonnull
                    @Override
                    protected Path resolve(@Nonnull String... paths)
                    {
                        return modFile.findResource(paths);
                    }
                };
                var metadata = pack.getMetadataSection(PackMetadataSection.SERIALIZER);
                if (metadata != null)
                {
                    AFC.LOGGER.info("Injecting AFC override pack");
                    event.addRepositorySource((consumer, constructor) ->
                        consumer.accept(constructor.create("builtin/afc_data", new TextComponent("ArborFirmaCraft Resources"), true, () -> pack, metadata, Pack.Position.TOP, PackSource.BUILT_IN, false))
                    );
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void modifyBlockEntityTypes()
    {
        modifyWood(TFCBlockEntities.CHEST.get(), Wood.BlockType.CHEST);
        modifyWood(TFCBlockEntities.TRAPPED_CHEST.get(), Wood.BlockType.TRAPPED_CHEST);
        modifyWood(TFCBlockEntities.LOOM.get(), Wood.BlockType.LOOM);
        //modifyWood(TFCBlockEntities.TICK_COUNTER.get(), Wood.BlockType.SAPLING);
        modifyWood(TFCBlockEntities.BARREL.get(), Wood.BlockType.BARREL);
        modifyWood(TFCBlockEntities.SLUICE.get(), Wood.BlockType.SLUICE);
        modifyWood(TFCBlockEntities.BOOKSHELF.get(), Wood.BlockType.BOOKSHELF);
        modifyWood(TFCBlockEntities.TOOL_RACK.get(), Wood.BlockType.TOOL_RACK);
        modifyWood(TFCBlockEntities.LECTERN.get(), Wood.BlockType.LECTERN);
    }

    private static void modifyWood(BlockEntityType<?> type, Wood.BlockType blockType)
    {
        modifyBlockEntityType(type, AFCBlocks.WOODS.values().stream().map(map -> map.get(blockType).get()));
    }

    private static void modifyBlockEntityType(BlockEntityType<?> type, Stream<Block> extraBlocks)
    {
        AFC.LOGGER.debug("Modifying block entity type: " + ForgeRegistries.BLOCK_ENTITIES.getKey(type));
        Set<Block> blocks = ((BlockEntityTypeAccessor) (Object) type).accessor$getValidBlocks();
        blocks = new HashSet<>(blocks);

        blocks.addAll(extraBlocks.toList());
        ((BlockEntityTypeAccessor) (Object) type).accessor$setValidBlocks(blocks);
    }

}
