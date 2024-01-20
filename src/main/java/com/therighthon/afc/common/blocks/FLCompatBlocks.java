package com.therighthon.afc.common.blocks;

import java.util.Map;
import com.eerussianguy.firmalife.common.blocks.FLBlocks;
import com.eerussianguy.firmalife.common.blocks.FoodShelfBlock;
import com.eerussianguy.firmalife.common.blocks.HangerBlock;
import com.eerussianguy.firmalife.common.blocks.JarbnetBlock;
import com.therighthon.afc.AFC;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.util.Helpers;

public class FLCompatBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AFC.MOD_ID);

    public static final Map<AFCWood, RegistryObject<Block>> FOOD_SHELVES = Helpers.mapOfKeys(AFCWood.class, wood -> AFCBlocks.register("wood/food_shelf/" + wood.getSerializedName(), () -> new FoodShelfBlock(FLBlocks.shelfProperties())));
    public static final Map<AFCWood, RegistryObject<Block>> HANGERS = Helpers.mapOfKeys(AFCWood.class, wood -> AFCBlocks.register("wood/hanger/" + wood.getSerializedName(), () -> new HangerBlock(FLBlocks.hangerProperties())));
    public static final Map<AFCWood, RegistryObject<Block>> JARBNETS = Helpers.mapOfKeys(AFCWood.class, wood -> AFCBlocks.register("wood/jarbnet/" + wood.getSerializedName(), () -> new JarbnetBlock(FLBlocks.jarbnetProperties())));

}
