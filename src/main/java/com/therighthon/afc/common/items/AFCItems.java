package com.therighthon.afc.common.items;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AFCBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.items.TFCMinecartItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;


public class AFCItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, AFC.MOD_ID);
    
    // Wood

    public static final Map<AFCWood, TFCItems.ItemId> LUMBER = Helpers.mapOf(AFCWood.class, wood -> register("wood/lumber/" + wood.name()));

    public static final Map<AFCWood, TFCItems.ItemId> SUPPORTS = Helpers.mapOf(AFCWood.class, wood ->
        register("wood/support/" + wood.name(), () -> new StandingAndWallBlockItem(AFCBlocks.WOODS.get(wood).get(Wood.BlockType.VERTICAL_SUPPORT).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties(), Direction.DOWN))
    );

    //TODO: Boats
//    public static final Map<AFCWood, TFCItems.ItemId> BOATS = Helpers.mapOf(AFCWood.class, wood -> register("wood/boat/" + wood.name(), () -> new TFCBoatItem(AFCEntities.BOATS.get(wood), new Item.Properties())));

    public static final Map<AFCWood, TFCItems.ItemId> CHEST_MINECARTS = Helpers.mapOf(AFCWood.class, wood -> register("wood/chest_minecart/" + wood.name(), () -> new TFCMinecartItem(new Item.Properties(), TFCEntities.CHEST_MINECART, () -> AFCBlocks.WOODS.get(wood).get(Wood.BlockType.CHEST).get().asItem())));

    public static final Map<AFCWood, TFCItems.ItemId> SIGNS = Helpers.mapOf(AFCWood.class, wood -> register("wood/sign/" + wood.name(), () -> new SignItem(new Item.Properties(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.SIGN).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.WALL_SIGN).get())));

    //TODO: Hanging signs
//    public static final Map<AFCWood, Map<Metal, TFCItems.ItemId>> HANGING_SIGNS = Helpers.mapOf(AFCWood.class, wood ->
//        Helpers.mapOf(Metal.class, Metal::hasUtilities, metal ->
//            register("wood/hanging_sign/" + metal.name() + "/" + wood.name(), () -> new HangingSignItem(AFCBlocks.CEILING_HANGING_SIGNS.get(wood).get(metal).get(), AFCBlocks.WALL_HANGING_SIGNS.get(wood).get(metal).get(), new Item.Properties()))
//        )
//    );

    //TODO: Fluids
//    public static final Map<SimpleAFCFluid, RegistryObject<BucketItem>> SIMPLE_AFC_FLUID_BUCKETS = Helpers.mapOf(SimpleAFCFluid.class, fluid ->
//        register("bucket/" + fluid.getSerializedName(), () -> new BucketItem(AFCFluids.SIMPLE_AFC_FLUIDS.get(fluid).source(), new Item.Properties()))
//    );

    //Normal items
    public static final TFCItems.ItemId RUBBER_BAR = register("rubber_bar");
    public static final TFCItems.ItemId MAPLE_SUGAR = register("maple_sugar");
    public static final TFCItems.ItemId BIRCH_SUGAR = register("birch_sugar");

    private static TFCItems.ItemId register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }
    
    private static <T extends Item> TFCItems.ItemId register(String name, Supplier<T> item)
    {
        return new TFCItems.ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), item));
    }
}

