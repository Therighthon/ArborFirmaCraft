package com.therighthon.afc.common.items;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.entities.AFCEntities;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.fluids.SimpleAFCFluid;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.fluids.FluidId;
import net.dries007.tfc.common.items.TFCBoatItem;
import net.dries007.tfc.common.items.TFCMinecartItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;


public class AFCItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AFC.MOD_ID);
    
    // Wood

    public static final Map<AFCWood, RegistryObject<Item>> LUMBER = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/lumber/" + wood.name()));

    public static final Map<AFCWood, RegistryObject<Item>> SUPPORTS = Helpers.mapOfKeys(AFCWood.class, wood ->
        register("wood/support/" + wood.name(), () -> new StandingAndWallBlockItem(AFCBlocks.WOODS.get(wood).get(Wood.BlockType.VERTICAL_SUPPORT).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties(), Direction.DOWN))
    );

    public static final Map<AFCWood, RegistryObject<Item>> BOATS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/boat/" + wood.name(), () -> new TFCBoatItem(AFCEntities.BOATS.get(wood), new Item.Properties())));

    public static final Map<AFCWood, RegistryObject<Item>> CHEST_MINECARTS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/chest_minecart/" + wood.name(), () -> new TFCMinecartItem(new Item.Properties(), TFCEntities.CHEST_MINECART, () -> AFCBlocks.WOODS.get(wood).get(Wood.BlockType.CHEST).get().asItem())));

    public static final Map<AFCWood, RegistryObject<Item>> SIGNS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/sign/" + wood.name(), () -> new SignItem(new Item.Properties(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.SIGN).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.WALL_SIGN).get())));

    public static final Map<AFCWood, Map<Metal.Default, RegistryObject<Item>>> HANGING_SIGNS = Helpers.mapOfKeys(AFCWood.class, wood ->
        Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasUtilities, metal ->
            register("wood/hanging_sign/" + metal.name() + "/" + wood.name(), () -> new HangingSignItem(AFCBlocks.CEILING_HANGING_SIGNS.get(wood).get(metal).get(), AFCBlocks.WALL_HANGING_SIGNS.get(wood).get(metal).get(), new Item.Properties()))
        )
    );
    public static final Map<SimpleAFCFluid, RegistryObject<BucketItem>> SIMPLE_AFC_FLUID_BUCKETS = Helpers.mapOfKeys(SimpleAFCFluid.class, fluid ->
        register("bucket/" + fluid.getSerializedName(), () -> new BucketItem(AFCFluids.SIMPLE_AFC_FLUIDS.get(fluid).source(), new Item.Properties()))
    );

    //Normal items
    public static final RegistryObject<Item> RUBBER_BAR = register("rubber_bar");
    public static final RegistryObject<Item> MAPLE_SUGAR = register("maple_sugar");
    public static final RegistryObject<Item> BIRCH_SUGAR = register("birch_sugar");

    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }
    
    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}

