package com.therighthon.afc.common.blocks;

import com.eerussianguy.firmalife.common.blockentities.BarrelPressBlockEntity;
import com.eerussianguy.firmalife.common.blockentities.FLBlockEntities;
import com.eerussianguy.firmalife.common.blockentities.KegBlockEntity;
import com.eerussianguy.firmalife.common.blocks.BarrelPressBlock;
import com.eerussianguy.firmalife.common.blocks.FLBlocks;
import com.eerussianguy.firmalife.common.blocks.FoodShelfBlock;
import com.eerussianguy.firmalife.common.blocks.HangerBlock;
import com.eerussianguy.firmalife.common.blocks.JarbnetBlock;
import com.eerussianguy.firmalife.common.blocks.KegCoreBlock;
import com.eerussianguy.firmalife.common.blocks.KegSubBlock;
import com.eerussianguy.firmalife.common.blocks.StompingBarrelBlock;
import com.eerussianguy.firmalife.common.blocks.WineShelfBlock;
import com.eerussianguy.firmalife.common.items.FLItems;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.items.AFCItems;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class FLCompatBlocks
{
    public static final DeferredRegister<Block> FL_COMPAT_BLOCKS =
        DeferredRegister.create(Registries.BLOCK, AFC.MOD_ID);

    public static final Map<AFCWood, TFCBlocks.Id<Block>> FOOD_SHELVES = Helpers.mapOf(AFCWood.class, wood -> register("wood/food_shelf/" + wood.getSerializedName(), () -> new FoodShelfBlock(FLBlocks.shelfProperties().mapColor(wood.woodColor()))));
    public static final Map<AFCWood, TFCBlocks.Id<Block>> HANGERS = Helpers.mapOf(AFCWood.class, wood -> register("wood/hanger/" + wood.getSerializedName(), () -> new HangerBlock(FLBlocks.hangerProperties().mapColor(wood.woodColor()))));
    public static final Map<AFCWood, TFCBlocks.Id<Block>> JARBNETS = Helpers.mapOf(AFCWood.class, wood -> register("wood/jarbnet/" + wood.getSerializedName(), () -> new JarbnetBlock(FLBlocks.jarbnetProperties().mapColor(wood.woodColor()))));
    public static final Map<AFCWood, TFCBlocks.Id<Block>> KEG_SUBS = Helpers.mapOf(AFCWood.class, wood -> register("wood/keg_sub/" + wood.getSerializedName(), () -> new KegSubBlock(ExtendedProperties.of().noLootTable().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(10f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.KEG_SUB))));
    public static final Map<AFCWood, TFCBlocks.Id<Block>> KEGS = Helpers.mapOf(AFCWood.class, wood -> register("wood/keg/" + wood.getSerializedName(), () -> new KegCoreBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(10f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.KEG).serverTicks(KegBlockEntity::serverTick), KEG_SUBS.get(wood))));
    public static final Map<AFCWood, TFCBlocks.Id<Block>> WINE_SHELVES = Helpers.mapOf(AFCWood.class, wood -> register("wood/wine_shelf/" + wood.getSerializedName(), () -> new WineShelfBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(4f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.WINE_SHELF))));
    public static final Map<AFCWood, TFCBlocks.Id<Block>> STOMPING_BARRELS = Helpers.mapOf(AFCWood.class, wood -> register("wood/stomping_barrel/" + wood.getSerializedName(), () -> new StompingBarrelBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(4f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.STOMPING_BARREL))));
    public static final Map<AFCWood, TFCBlocks.Id<Block>> BARREL_PRESSES = Helpers.mapOf(AFCWood.class, wood -> register("wood/barrel_press/" + wood.getSerializedName(), () -> new BarrelPressBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(4f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.BARREL_PRESS).ticks(BarrelPressBlockEntity::tick))));

    private static <T extends Block> TFCBlocks.Id<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> TFCBlocks.Id<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> TFCBlocks.Id<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new TFCBlocks.Id<>(RegistrationHelpers.registerBlock(FL_COMPAT_BLOCKS, AFCItems.ITEMS, name, blockSupplier, blockItemFactory));
    }
}

