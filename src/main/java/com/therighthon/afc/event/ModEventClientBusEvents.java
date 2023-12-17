package com.therighthon.afc.event;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Predicate;
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
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.dries007.tfc.client.ColorMapReloadListener;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.client.model.entity.HorseChestLayer;
import net.dries007.tfc.client.render.blockentity.AxleBlockEntityRenderer;
import net.dries007.tfc.client.render.blockentity.BladedAxleBlockEntityRenderer;
import net.dries007.tfc.client.render.blockentity.TFCHangingSignBlockEntityRenderer;
import net.dries007.tfc.client.render.blockentity.WaterWheelBlockEntityRenderer;
import net.dries007.tfc.client.render.blockentity.WindmillBlockEntityRenderer;
import net.dries007.tfc.client.render.entity.TFCBoatRenderer;
import net.dries007.tfc.client.render.entity.TFCChestBoatRenderer;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.util.Helpers;

import static net.dries007.tfc.common.blocks.wood.Wood.BlockType.*;

@Mod.EventBusSubscriber(modid = AFC.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents
{
    public static void registerColorHandlerBlocks(RegisterColorHandlersEvent.Block event)
    {
        final BlockColor foliageColor = (state, level, pos, tintIndex) -> TFCColors.getFoliageColor(pos, tintIndex);
        final BlockColor kapokFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getKapokFoliageColor(pos, tintIndex, TreeSpecies.RED_SILK_COTTON.autumnIndex());
        final BlockColor jacarandaFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getJacarandaFoliageColor(pos, tintIndex, TreeSpecies.GIANT_ROSEWOOD.autumnIndex());
        final BlockColor yellowIpeFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getYellowIpeFoliageColor(pos, tintIndex, AFCWood.IPE.autumnIndex());
        final BlockColor flameOfTheForestFoliageColor = (state, level, pos, tintIndex) -> AFCColors.getFlameOfTheForestFoliageColor(pos, tintIndex, TreeSpecies.FLAME_OF_THE_FOREST.autumnIndex());

        AFCBlocks.WOODS.forEach((wood, reg) -> event.register(
            wood.isConifer() ?
                foliageColor : wood == AFCWood.IPE ? yellowIpeFoliageColor :
                (state, level, pos, tintIndex) -> TFCColors.getSeasonalFoliageColor(pos, tintIndex, wood.autumnIndex()),
            reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get()));

        AFCBlocks.TREE_SPECIES.forEach((wood, reg) -> event.register(
            wood.isConifer() ?
                foliageColor : wood == TreeSpecies.RED_SILK_COTTON ? kapokFoliageColor :
                wood == TreeSpecies.FLAME_OF_THE_FOREST ? flameOfTheForestFoliageColor :
                wood == TreeSpecies.GIANT_ROSEWOOD ? jacarandaFoliageColor :
                (state, level, pos, tintIndex) -> TFCColors.getSeasonalFoliageColor(pos, tintIndex, wood.autumnIndex()),
            reg.get(TreeSpecies.BlockType.LEAVES).get(), reg.get(TreeSpecies.BlockType.FALLEN_LEAVES).get()));
    }

    public static void registerColorHandlerItems(RegisterColorHandlersEvent.Item event)
    {
        final ItemColors registry = event.getItemColors();
        final ItemColor seasonalFoliageColor = (stack, tintIndex) -> TFCColors.getFoliageColor(null, tintIndex);

        AFCBlocks.WOODS.forEach((wood, reg) -> registry.register(seasonalFoliageColor, reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get()));
        AFCBlocks.TREE_SPECIES.forEach((key, value) -> registry.register(seasonalFoliageColor, value.get(TreeSpecies.BlockType.LEAVES).get(), value.get(TreeSpecies.BlockType.FALLEN_LEAVES).get()));
    }

    public static void clientSetup(FMLClientSetupEvent event)
    {
        // Render Types
        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();
        final Predicate<RenderType> ghostBlock = rt -> rt == cutoutMipped || rt == Sheets.translucentCullBlockSheet();

        final Predicate<RenderType> leafPredicate = layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == solid;
        AFCBlocks.WOODS.values().forEach(map -> {
            Stream.of(SAPLING, DOOR, TRAPDOOR, FENCE, FENCE_GATE, BUTTON, PRESSURE_PLATE, SLAB, STAIRS, TWIG, BARREL, SCRIBING_TABLE, JAR_SHELF, POTTED_SAPLING, ENCASED_AXLE, CLUTCH, GEAR_BOX).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(LEAVES, FALLEN_LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), leafPredicate));
        });

        AFCBlocks.TREE_SPECIES.values().forEach(map -> {
            Stream.of(TreeSpecies.BlockType.SAPLING, TreeSpecies.BlockType.POTTED_SAPLING).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(TreeSpecies.BlockType.LEAVES, TreeSpecies.BlockType.FALLEN_LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), leafPredicate));
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

    public static void onBlockColors(RegisterColorHandlersEvent.Block event)
    {

    }

//    public static void onTextureStitch(TextureStitchEvent.Pre event)
//    {
//        final ResourceLocation sheet = event.getAtlas().location();
//        if (sheet.equals(Sheets.CHEST_SHEET))
//        {
//            Arrays.stream(AFCWood.VALUES).map(AFCWood::getSerializedName).forEach(name -> {
//                event.addSprite(Helpers.identifier("entity/chest/normal/" + name));
//                event.addSprite(Helpers.identifier("entity/chest/normal_left/" + name));
//                event.addSprite(Helpers.identifier("entity/chest/normal_right/" + name));
//                event.addSprite(Helpers.identifier("entity/chest/trapped/" + name));
//                event.addSprite(Helpers.identifier("entity/chest/trapped_left/" + name));
//                event.addSprite(Helpers.identifier("entity/chest/trapped_right/" + name));
//            });
//        }
//        else if (sheet.equals(Sheets.SIGN_SHEET))
//        {
//            Arrays.stream(AFCWood.VALUES).map(AFCWood::getSerializedName).forEach(name -> event.addSprite(Helpers.identifier("entity/signs/" + name)));
//        }
//    }
    //Uncomment this when boat entities are added
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {

        for (AFCWood wood : AFCWood.VALUES)
        {
            event.registerEntityRenderer(AFCEntities.BOATS.get(wood).get(), ctx -> new TFCBoatRenderer(ctx, wood.getSerializedName()));
            //TODO: Chest boats
//            event.registerEntityRenderer(AFCEntities.CHEST_BOATS.get(wood).get(), ctx -> new TFCChestBoatRenderer(ctx, wood.getSerializedName()));
        }
        event.registerBlockEntityRenderer(AFCBlockEntities.SIGN.get(), AFCSignRenderer::new);
        //event.registerBlockEntityRenderer(TFCBlockEntities.HANGING_SIGN.get(), TFCHangingSignBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(AFCBlockEntities.AXLE.get(), ctx -> new AxleBlockEntityRenderer());
        event.registerBlockEntityRenderer(AFCBlockEntities.BLADED_AXLE.get(), ctx -> new BladedAxleBlockEntityRenderer());
        event.registerBlockEntityRenderer(AFCBlockEntities.WATER_WHEEL.get(), WaterWheelBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(AFCBlockEntities.WINDMILL.get(), WindmillBlockEntityRenderer::new);
        //TODO: Make things render
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
