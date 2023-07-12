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
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.items.TFCBoatItem;
import net.dries007.tfc.common.items.TFCMinecartItem;
import net.dries007.tfc.util.Helpers;

import static net.dries007.tfc.common.TFCItemGroup.*;


public class AFCItems
{
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, AFC.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


    // Wood

    public static final Map<AFCWood, RegistryObject<Item>> LUMBER = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/lumber/" + wood.name(), WOOD));

    public static final Map<AFCWood, RegistryObject<Item>> SUPPORTS = Helpers.mapOfKeys(AFCWood.class, wood ->
        register("wood/support/" + wood.name(), () -> new StandingAndWallBlockItem(AFCBlocks.WOODS.get(wood).get(Wood.BlockType.VERTICAL_SUPPORT).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties().tab(WOOD)))
    );

    public static final Map<AFCWood, RegistryObject<Item>> BOATS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/boat/" + wood.name(), () -> new TFCBoatItem(AFCEntities.BOATS.get(wood), new Item.Properties().tab(WOOD))));

    public static final Map<AFCWood, RegistryObject<Item>> CHEST_MINECARTS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/chest_minecart/" + wood.name(), () -> new TFCMinecartItem(new Item.Properties().tab(WOOD), TFCEntities.CHEST_MINECART, () -> AFCBlocks.WOODS.get(wood).get(Wood.BlockType.CHEST).get().asItem())));

    public static final Map<AFCWood, RegistryObject<Item>> SIGNS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/sign/" + wood.name(), () -> new SignItem(new Item.Properties().tab(WOOD), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.SIGN).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.WALL_SIGN).get())));


    private static RegistryObject<Item> register(String name, CreativeModeTab group)
    {
        return register(name, () -> new Item(new Item.Properties().tab(group)));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }

    public static final Map<SimpleAFCFluid, RegistryObject<BucketItem>> SIMPLE_AFC_FLUID_BUCKETS = Helpers.mapOfKeys(SimpleAFCFluid.class, fluid ->
        register("bucket/" + fluid.getSerializedName(), () -> new BucketItem(AFCFluids.SIMPLE_AFC_FLUIDS.get(fluid).source(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(MISC)))
    );
}

