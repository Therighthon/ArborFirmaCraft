package com.therighthon.afc.event;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;
import com.therighthon.afc.AFC;
import com.therighthon.afc.client.render.AFCSignRenderer;
import com.therighthon.afc.client.render.colors.AFCColors;
import com.therighthon.afc.client.render.colors.ColorScheme;
import com.therighthon.afc.common.blockentities.AFCBlockEntities;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.entities.AFCEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.antlr.runtime.tree.Tree;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.client.ColorMapReloadListener;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.client.model.entity.HorseChestLayer;
import net.dries007.tfc.client.render.entity.TFCBoatRenderer;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;

import static net.dries007.tfc.common.blocks.wood.Wood.BlockType.*;

@Mod.EventBusSubscriber(modid = AFC.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents
{
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event)
    {
        final BlockColors registry = event.getBlockColors();
//        final BlockColor grassColor = (state, level, pos, tintIndex) -> TFCColors.getGrassColor(pos, tintIndex);
//        final BlockColor tallGrassColor = (state, level, pos, tintIndex) -> TFCColors.getTallGrassColor(pos, tintIndex);
        final BlockColor foliageColor = (state, level, pos, tintIndex) -> TFCColors.getFoliageColor(pos, tintIndex);
        final BlockColor seasonalFoliageColor = (state, level, pos, tintIndex) -> TFCColors.getSeasonalFoliageColor(pos, tintIndex);
        final BlockColor yellowDeciduousFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getYellowDeciduousFoliageColor(pos, tintIndex);
        final BlockColor orangeDeciduousFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getOrangeDeciduousFoliageColor(pos, tintIndex);
        final BlockColor redDeciduousFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getRedDeciduousFoliageColor(pos, tintIndex);
        final BlockColor transitionalDeciduousFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getTransitionalDeciduousFoliageColor(pos, tintIndex);
        final BlockColor lightTransitionalDeciduousFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getLightTransitionalDeciduousFoliageColor(pos, tintIndex);
        final BlockColor kapokFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getKapokFoliageColor(pos, tintIndex);
        final BlockColor jacarandaFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getJacarandaFoliageColor(pos, tintIndex);
        final BlockColor tamarackFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getTamarackFoliageColor(pos, tintIndex);

        //ModBlocks.PLANTS.forEach((plant, reg) -> registry.register(plant.isTallGrass() ? tallGrassColor : plant.isSeasonal() ? seasonalFoliageColor : plant.isFoliage() ? foliageColor : grassColor, reg.get()));
        //ModBlocks.POTTED_PLANTS.forEach((plant, reg) -> registry.register(grassColor, reg.get()));
        AFCBlocks.WOODS.forEach((wood, reg) -> registry.register(
            (wood.getColorScheme()== ColorScheme.EVERGREEN) ? foliageColor :
            (wood.getColorScheme()== ColorScheme.TFC_DECIDUOUS) ? seasonalFoliageColor :
            (wood.getColorScheme()== ColorScheme.YELLOW_DECIDUOUS) ? yellowDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.ORANGE_DECIDUOUS) ? orangeDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.RED_DECIDUOUS) ? redDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.TRANSITIONAL_DECIDUOUS) ? transitionalDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.LIGHT_TRANSITIONAL_DECIDUOUS) ? lightTransitionalDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.KAPOK) ? kapokFoliageColor :
            (wood.getColorScheme()== ColorScheme.JACARANDA) ? jacarandaFoliageColor :
                tamarackFoliageColor,
            reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get()));
        AFCBlocks.TREE_SPECIES.forEach((wood, value) -> registry.register(
            (wood.getColorScheme()== ColorScheme.EVERGREEN) ? foliageColor :
            (wood.getColorScheme()== ColorScheme.TFC_DECIDUOUS) ? seasonalFoliageColor :
            (wood.getColorScheme()== ColorScheme.YELLOW_DECIDUOUS) ? yellowDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.ORANGE_DECIDUOUS) ? orangeDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.RED_DECIDUOUS) ? redDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.TRANSITIONAL_DECIDUOUS) ? transitionalDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.LIGHT_TRANSITIONAL_DECIDUOUS) ? lightTransitionalDeciduousFoliageColor :
            (wood.getColorScheme()== ColorScheme.KAPOK) ? kapokFoliageColor :
            (wood.getColorScheme()== ColorScheme.JACARANDA) ? jacarandaFoliageColor :
            tamarackFoliageColor,
            value.get(TreeSpecies.BlockType.LEAVES).get()));
    }

    public static void registerColorHandlerItems(ColorHandlerEvent.Item event)
    {
        final ItemColors registry = event.getItemColors();
//        final ItemColor grassColor = (stack, tintIndex) -> TFCColors.getGrassColor(null, tintIndex);
        final ItemColor seasonalFoliageColor = (stack, tintIndex) -> TFCColors.getFoliageColor(null, tintIndex);

//        ModBlocks.PLANTS.forEach((plant, reg) -> {
//            if (plant.isItemTinted())
//                registry.register(plant.isSeasonal() ? seasonalFoliageColor : grassColor, reg.get());
//        });
        AFCBlocks.WOODS.forEach((wood, reg) -> registry.register(seasonalFoliageColor, reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get()));
        AFCBlocks.TREE_SPECIES.forEach((key, value) -> registry.register(seasonalFoliageColor, value.get(TreeSpecies.BlockType.LEAVES).get()));
    }

    public static void clientSetup(FMLClientSetupEvent event)
    {
        // Render Types
        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();

        AFCBlocks.WOODS.values().forEach(map -> {
            Stream.of(SAPLING, DOOR, TRAPDOOR, FENCE, FENCE_GATE, BUTTON, PRESSURE_PLATE, SLAB, STAIRS, TWIG, BARREL, SCRIBING_TABLE, POTTED_SAPLING).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(LEAVES, FALLEN_LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == solid));
        });

        AFCBlocks.TREE_SPECIES.values().forEach(map -> {
            Stream.of(TreeSpecies.BlockType.SAPLING, TreeSpecies.BlockType.POTTED_SAPLING).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(TreeSpecies.BlockType.LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == solid));
        });

        event.enqueueWork(() -> {
            AFCBlocks.WOODS.values().forEach(map -> ItemProperties.register(map.get(BARREL).get().asItem(), Helpers.identifier("sealed"), (stack, level, entity, unused) -> stack.hasTag() ? 1.0f : 0f));

            AFCBlocks.WOODS.forEach((wood, map) -> {
                HorseChestLayer.registerChest(map.get(CHEST).get().asItem(), Helpers.identifier("textures/entity/chest/horse/" + wood.getSerializedName() + ".png"));
                HorseChestLayer.registerChest(map.get(TRAPPED_CHEST).get().asItem(), Helpers.identifier("textures/entity/chest/horse/" + wood.getSerializedName() + ".png"));
                HorseChestLayer.registerChest(map.get(BARREL).get().asItem(), Helpers.identifier("textures/entity/chest/horse/" + wood.getSerializedName() + "_barrel.png"));
            });
        });

        ItemBlockRenderTypes.setRenderLayer(AFCBlocks.TREE_TAP.get(), RenderType.cutout());


    }

    public static void onLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        final LayerDefinition boatLayer = BoatModel.createBodyModel();
        final LayerDefinition signLayer = SignRenderer.createSignLayer();
        for (AFCWood wood : AFCWood.VALUES)
        {
            event.registerLayerDefinition(TFCBoatRenderer.boatName(wood.getSerializedName()), () -> boatLayer);
            event.registerLayerDefinition(RenderHelpers.modelIdentifier("sign/" + wood.getSerializedName()), () -> signLayer);
        }

    }

    public static void onBlockColors(ColorHandlerEvent.Block event)
    {

    }

    public static void onTextureStitch(TextureStitchEvent.Pre event)
    {
        final ResourceLocation sheet = event.getAtlas().location();
        if (sheet.equals(Sheets.CHEST_SHEET))
        {
            Arrays.stream(AFCWood.VALUES).map(AFCWood::getSerializedName).forEach(name -> {
                event.addSprite(Helpers.identifier("entity/chest/normal/" + name));
                event.addSprite(Helpers.identifier("entity/chest/normal_left/" + name));
                event.addSprite(Helpers.identifier("entity/chest/normal_right/" + name));
                event.addSprite(Helpers.identifier("entity/chest/trapped/" + name));
                event.addSprite(Helpers.identifier("entity/chest/trapped_left/" + name));
                event.addSprite(Helpers.identifier("entity/chest/trapped_right/" + name));
            });
        }
        else if (sheet.equals(Sheets.SIGN_SHEET))
        {
            Arrays.stream(AFCWood.VALUES).map(AFCWood::getSerializedName).forEach(name -> event.addSprite(Helpers.identifier("entity/signs/" + name)));
        }
    }
    //Uncomment this when boat entities are added
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {

        for (AFCWood wood : AFCWood.VALUES)
        {
            event.registerEntityRenderer(AFCEntities.BOATS.get(wood).get(), ctx -> new TFCBoatRenderer(ctx, wood.getSerializedName()));
        }
        event.registerBlockEntityRenderer(AFCBlockEntities.SIGN.get(), AFCSignRenderer::new);
    }

    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
    {
        // Color maps
        // Based on TFC Custom colormaps
        event.registerReloadListener(new ColorMapReloadListener(AFCColors::setFoliageJacarandaColors, AFCColors.FOLIAGE_JACARANDA_COLORS_LOCATION));
        event.registerReloadListener(new ColorMapReloadListener(AFCColors::setFoliageYellowColors, AFCColors.FOLIAGE_YELLOW_COLORS_LOCATION));
        event.registerReloadListener(new ColorMapReloadListener(AFCColors::setFoliageOrangeColors, AFCColors.FOLIAGE_ORANGE_COLORS_LOCATION));
        event.registerReloadListener(new ColorMapReloadListener(AFCColors::setFoliageRedColors, AFCColors.FOLIAGE_RED_COLORS_LOCATION));
    }


}
