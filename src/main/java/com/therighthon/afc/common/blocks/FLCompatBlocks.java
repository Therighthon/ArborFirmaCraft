package com.therighthon.afc.common.blocks;

import java.util.Map;
//import com.eerussianguy.firmalife.common.blockentities.BarrelPressBlockEntity;
//import com.eerussianguy.firmalife.common.blockentities.FLBlockEntities;
//import com.eerussianguy.firmalife.common.blocks.BarrelPressBlock;
//import com.eerussianguy.firmalife.common.blocks.BigBarrelBlock;
//import com.eerussianguy.firmalife.common.blocks.FLBlocks;
//import com.eerussianguy.firmalife.common.blocks.FoodShelfBlock;
//import com.eerussianguy.firmalife.common.blocks.HangerBlock;
//import com.eerussianguy.firmalife.common.blocks.JarbnetBlock;
//import com.eerussianguy.firmalife.common.blocks.StompingBarrelBlock;
//import com.eerussianguy.firmalife.common.blocks.WineShelfBlock;
import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;

public class FLCompatBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, AFC.MOD_ID);

    //TODO: Frmalife
//    public static final Map<AFCWood, TFCBlocks.Id<Block>> FOOD_SHELVES = Helpers.mapOf(AFCWood.class, wood -> AFCBlocks.register("wood/food_shelf/" + wood.getSerializedName(), () -> new FoodShelfBlock(FLBlocks.shelfProperties().mapColor(wood.woodColor()))));
//    public static final Map<AFCWood, TFCBlocks.Id<Block>> HANGERS = Helpers.mapOf(AFCWood.class, wood -> AFCBlocks.register("wood/hanger/" + wood.getSerializedName(), () -> new HangerBlock(FLBlocks.hangerProperties().mapColor(wood.woodColor()))));
//    public static final Map<AFCWood, TFCBlocks.Id<Block>> JARBNETS = Helpers.mapOf(AFCWood.class, wood -> AFCBlocks.register("wood/jarbnet/" + wood.getSerializedName(), () -> new JarbnetBlock(FLBlocks.jarbnetProperties().mapColor(wood.woodColor()))));
//    public static final Map<AFCWood, TFCBlocks.Id<Block>> BIG_BARRELS = Helpers.mapOf(AFCWood.class, wood -> AFCBlocks.register("wood/big_barrel/" + wood.getSerializedName(), () -> new BigBarrelBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(10f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.BIG_BARREL))));
//    public static final Map<AFCWood, TFCBlocks.Id<Block>> WINE_SHELVES = Helpers.mapOf(AFCWood.class, wood -> AFCBlocks.register("wood/wine_shelf/" + wood.getSerializedName(), () -> new WineShelfBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(4f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.WINE_SHELF))));
//    public static final Map<AFCWood, TFCBlocks.Id<Block>> STOMPING_BARRELS = Helpers.mapOf(AFCWood.class, wood -> AFCBlocks.register("wood/stomping_barrel/" + wood.getSerializedName(), () ->new StompingBarrelBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(4f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.STOMPING_BARREL))));
//    public static final Map<AFCWood, TFCBlocks.Id<Block>> BARREL_PRESSES = Helpers.mapOf(AFCWood.class, wood -> AFCBlocks.register("wood/barrel_press/" + wood.getSerializedName(), () ->new BarrelPressBlock(ExtendedProperties.of().mapColor(wood.woodColor()).sound(SoundType.WOOD).noOcclusion().strength(4f).pushReaction(PushReaction.BLOCK).flammableLikeLogs().blockEntity(FLBlockEntities.BARREL_PRESS).ticks(BarrelPressBlockEntity::tick))));
}
