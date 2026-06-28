package com.therighthon.afc.event;

import com.therighthon.afc.AFCHelpers;
import com.therighthon.afc.client.render.blockentities.AFCHangingSignBlockEntityRenderer;
import com.therighthon.afc.client.render.blockentities.AFCSignBlockEntityRenderer;
import com.therighthon.afc.common.blocks.FLCompatBlocks;
import com.therighthon.afc.common.entities.AFCEntities;
import com.therighthon.afc.common.fluids.AFCFluids;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.TreeSpecies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.Nullable;


import net.dries007.tfc.client.ClientEventHandler;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.client.extensions.FluidRendererExtension;
import net.dries007.tfc.client.extensions.ItemRendererExtension;
import net.dries007.tfc.client.model.entity.HorseChestLayer;
import net.dries007.tfc.client.render.entity.TFCBoatRenderer;
import net.dries007.tfc.client.render.entity.TFCChestBoatRenderer;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.component.TFCComponents;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.client.render.blockentity.ChestItemRenderer;

import static net.dries007.tfc.common.blocks.wood.Wood.BlockType.*;

public final class ModEventClientBusEvents
{
    public static void registerColorHandlerBlocks(RegisterColorHandlersEvent.Block event)
    {
        final BlockColor foliageColor = (state, level, pos, tintIndex) -> TFCColors.getFoliageColor(pos, tintIndex);

        AFCBlocks.WOODS.forEach((wood, reg) -> event.register(
            wood.isConifer() ?
                foliageColor : (state, level, pos, tintIndex) -> TFCColors.getSeasonalFoliageColor(pos, tintIndex, wood.autumnIndex()),
            reg.get(Wood.BlockType.LEAVES).get(), reg.get(Wood.BlockType.FALLEN_LEAVES).get()));

        AFCBlocks.TREE_SPECIES.forEach((wood, reg) -> event.register(
            wood.isConifer() ?
                foliageColor : (state, level, pos, tintIndex) -> TFCColors.getSeasonalFoliageColor(pos, tintIndex, wood.autumnIndex()),
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
            Stream.of(SAPLING, DOOR, TRAPDOOR, FENCE, FENCE_GATE, BUTTON, PRESSURE_PLATE, SLAB, STAIRS, TWIG, BARREL, SCRIBING_TABLE, SEWING_TABLE, SHELF, POTTED_SAPLING, ENCASED_AXLE, CLUTCH, GEAR_BOX).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(LEAVES, FALLEN_LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), leafPredicate));
        });

        AFCBlocks.TREE_SPECIES.values().forEach(map -> {
            Stream.of(TreeSpecies.BlockType.SAPLING, TreeSpecies.BlockType.POTTED_SAPLING).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
            Stream.of(TreeSpecies.BlockType.LEAVES, TreeSpecies.BlockType.FALLEN_LEAVES).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), leafPredicate));
        });

        event.enqueueWork(() -> {
//            TODO: Barrels, Maybe important?
//            AFCBlocks.WOODS.values().forEach(map -> ItemProperties.register(map.get(BARREL).get().asItem(), Helpers.identifier("sealed"), (stack, level, entity, unused) -> stack.hasTag() ? 1.0f : 0f));

            AFCBlocks.WOODS.forEach((wood, map) -> {
                HorseChestLayer.registerChest(map.get(CHEST).get().asItem(), AFCHelpers.modIdentifier("textures/entity/chest/horse/" + wood.getSerializedName() + ".png"));
                HorseChestLayer.registerChest(map.get(TRAPPED_CHEST).get().asItem(), AFCHelpers.modIdentifier("textures/entity/chest/horse/" + wood.getSerializedName() + ".png"));
                HorseChestLayer.registerChest(map.get(BARREL).get().asItem(), AFCHelpers.modIdentifier("textures/entity/chest/horse/" + wood.getSerializedName() + "_barrel.png"));
            });
        });

        for (AFCWood wood : AFCWood.VALUES)
        {
            Sheets.addWoodType(wood.getVanillaWoodType());
        }

        ItemBlockRenderTypes.setRenderLayer(AFCBlocks.TREE_TAP.get(), RenderType.cutout());

        AFCBlocks.WOODS.values().forEach(map -> registerSealedProperty(map.get(BARREL), TFCComponents.BARREL));
    }

    public static void clientFLCompatSetup(FMLClientSetupEvent event)
    {
        // Render Types
        final RenderType cutout = RenderType.cutout();

        FLCompatBlocks.JARBNETS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(), cutout);
        });
        FLCompatBlocks.FOOD_SHELVES.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(), cutout);
        });
        FLCompatBlocks.HANGERS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(), cutout);
        });

    }

    //Equiv to TFC's registerLayerDefinitions
    public static void onLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        LayerDefinition boatLayer = BoatModel.createBodyModel();
        LayerDefinition chestLayer = ChestBoatModel.createBodyModel();
        for (AFCWood wood : AFCWood.VALUES)
        {
            event.registerLayerDefinition(TFCBoatRenderer.boatName(wood.getSerializedName()), () -> boatLayer);
            event.registerLayerDefinition(TFCChestBoatRenderer.chestBoatName(wood.getSerializedName()), () -> chestLayer);
        }
    }

    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        for (AFCWood wood : AFCWood.VALUES)
        {
            event.registerEntityRenderer(AFCEntities.BOATS.get(wood).get(), ctx -> new TFCBoatRenderer(ctx, wood.getSerializedName()));
            event.registerEntityRenderer(AFCEntities.CHEST_BOATS.get(wood).get(), ctx -> new TFCChestBoatRenderer(ctx, wood.getSerializedName()));
        }
    }

    public static void registerEntityLayers(EntityRenderersEvent.RegisterRenderers event)
    {
        // Hanging Signs
        // The trick here ended up being that we can register our own block entity renderers for existing block entities, and only apply them to our blocks
        event.registerBlockEntityRenderer(TFCBlockEntities.SIGN.get(), AFCSignBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TFCBlockEntities.HANGING_SIGN.get(), AFCHangingSignBlockEntityRenderer::new);
    }

    public static void registerExtensions(RegisterClientExtensionsEvent event)
    {
        AFCBlocks.WOODS.values().forEach(map -> registerCustomItemRenderer(event, map.get(CHEST), ChestItemRenderer::new));
        AFCBlocks.WOODS.values().forEach(map -> registerCustomItemRenderer(event, map.get(TRAPPED_CHEST), ChestItemRenderer::new));


        // Fluids
        AFCFluids.SIMPLE_AFC_FLUIDS.forEach((fluid, holder) -> event.registerFluidType(
            new FluidRendererExtension(fluid.isTransparent() ? AFCFluids.ALPHA_MASK | fluid.getColor() : fluid.getColor(), ClientEventHandler.WATER_STILL, ClientEventHandler.WATER_FLOW, ClientEventHandler.WATER_OVERLAY, ClientEventHandler.UNDERWATER_LOCATION),
            holder.getType()
        ));
    }

    private static <T> void registerCustomItemRenderer(RegisterClientExtensionsEvent event, @Nullable Supplier<? extends ItemLike> item, Function<T, BlockEntityWithoutLevelRenderer> renderer)
    {
        if (item != null) event.registerItem(ItemRendererExtension.cached(() -> renderer.apply((T) item.get().asItem())), item.get().asItem());
    }

    private static final ResourceLocation SEALED = Helpers.identifier("sealed");

    private static void registerSealedProperty(ItemLike item, Supplier<? extends DataComponentType<?>> type)
    {
        ItemProperties.register(item.asItem(), SEALED, (stack, level, entity, unused) -> stack.has(type) ? 1.0f : 0f);
    }
}
