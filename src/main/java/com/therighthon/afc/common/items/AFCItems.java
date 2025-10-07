package com.therighthon.afc.common.items;

import com.therighthon.afc.common.fluids.AFCFluidId;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.fluids.SimpleAFCFluid;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AFCBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.items.TFCMinecartItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryHolder;


public class AFCItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, AFC.MOD_ID);
    
    // Wood

    public static final Map<AFCWood, AFCItems.ItemId> LUMBER = Helpers.mapOf(AFCWood.class, wood -> register("wood/lumber/" + wood.name()));

    public static final Map<AFCWood, AFCItems.ItemId> SUPPORTS = Helpers.mapOf(AFCWood.class, wood ->
        register("wood/support/" + wood.name(), () -> new StandingAndWallBlockItem(AFCBlocks.WOODS.get(wood).get(Wood.BlockType.VERTICAL_SUPPORT).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties(), Direction.DOWN))
    );

    //TODO: Boats
//    public static final Map<AFCWood, TFCItems.ItemId> BOATS = Helpers.mapOf(AFCWood.class, wood -> register("wood/boat/" + wood.name(), () -> new TFCBoatItem(AFCEntities.BOATS.get(wood), new Item.Properties())));

    public static final Map<AFCWood, AFCItems.ItemId> CHEST_MINECARTS = Helpers.mapOf(AFCWood.class, wood -> register("wood/chest_minecart/" + wood.name(), () -> new TFCMinecartItem(new Item.Properties(), TFCEntities.CHEST_MINECART, () -> AFCBlocks.WOODS.get(wood).get(Wood.BlockType.CHEST).get().asItem())));

    public static final Map<AFCWood, AFCItems.ItemId> SIGNS = Helpers.mapOf(AFCWood.class, wood -> register("wood/sign/" + wood.name(), () -> new SignItem(new Item.Properties(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.SIGN).get(), AFCBlocks.WOODS.get(wood).get(Wood.BlockType.WALL_SIGN).get())));

    //TODO: Hanging signs
//    public static final Map<AFCWood, Map<Metal, AFCItems.ItemId>> HANGING_SIGNS = Helpers.mapOf(AFCWood.class, wood ->
//        Helpers.mapOf(Metal.class, Metal::hasUtilities, metal ->
//            register("wood/hanging_sign/" + metal.name() + "/" + wood.name(), () -> new HangingSignItem(AFCBlocks.CEILING_HANGING_SIGNS.get(wood).get(metal).get(), AFCBlocks.WALL_HANGING_SIGNS.get(wood).get(metal).get(), new Item.Properties()))
//        )
//    );

    public static final Map<AFCFluidId, AFCItems.ItemId> FLUID_BUCKETS = AFCFluidId.mapOf(fluid ->
        register("bucket/" + fluid.name(), () -> new BucketItem(fluid.fluid().get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );

    //Normal items
    public static final AFCItems.ItemId RUBBER_BAR = register("rubber_bar");
    public static final AFCItems.ItemId MAPLE_SUGAR = register("maple_sugar");
    public static final AFCItems.ItemId BIRCH_SUGAR = register("birch_sugar");

    private static AFCItems.ItemId register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }
    
    private static <T extends Item> AFCItems.ItemId register(String name, Supplier<T> item)
    {
        return new AFCItems.ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), item));
    }

    public record ItemId(DeferredHolder<Item, Item> holder) implements RegistryHolder<Item, Item>, ItemLike
    {
        @Override
        public Item asItem()
        {
            return get();
        }
    }
}

