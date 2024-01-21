package com.therighthon.afc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.FLCompatBlocks;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.blocks.UniqueLogs;
import com.therighthon.afc.common.items.AFCItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.blocks.DecorationBlockRegistryObject;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.SelfTests;

public final class AFCCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, com.therighthon.afc.AFC.MOD_ID);

    public static final RegistryObject<CreativeModeTab> AFC_TAB = CREATIVE_TABS.register("arborfirmacraft",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.afc_creative_mode_tab"))
            .icon(() -> new ItemStack(AFCBlocks.TREE_SPECIES.get(TreeSpecies.TAMARACK).get(TreeSpecies.BlockType.SAPLING).get()))
            .displayItems(AFCCreativeModeTabs::fillTab)
            .build()
    );

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        out.accept(AFCItems.RUBBER_BAR.get());
        out.accept(AFCItems.MAPLE_SUGAR.get());
        out.accept(AFCItems.BIRCH_SUGAR.get());
        out.accept(AFCBlocks.TREE_TAP.get());
        for (AFCWood wood : AFCWood.VALUES)
        {
            AFCBlocks.WOODS.get(wood).forEach((type, reg) -> {
                if (type.needsItem())
                {
                    accept(out, reg);
                }
            });
            accept(out, AFCItems.LUMBER, wood);
            accept(out, AFCItems.BOATS, wood);
            accept(out, AFCItems.SUPPORTS, wood);
            accept(out, AFCItems.CHEST_MINECARTS, wood);
            accept(out, AFCItems.SIGNS, wood);
            for (Metal.Default metal : Metal.Default.values())
            {
                accept(out, AFCItems.HANGING_SIGNS.get(wood), metal);
            }
        }
        for (TreeSpecies wood : TreeSpecies.VALUES)
        {
            AFCBlocks.TREE_SPECIES.get(wood).forEach((type, reg) -> {
                if (type.needsItem())
                {
                    accept(out, reg);
                }
            });
        }
        for (UniqueLogs wood : UniqueLogs.VALUES)
        {
            AFCBlocks.UNIQUE_LOGS.get(wood).forEach((type, reg) -> {
                accept(out, reg);
            });
        }
        if (ModList.get().isLoaded("firmalife"))
        {
            for (AFCWood wood : AFCWood.VALUES)
            {
                accept(out, FLCompatBlocks.FOOD_SHELVES, wood);
                accept(out, FLCompatBlocks.HANGERS, wood);
                accept(out, FLCompatBlocks.JARBNETS, wood);
            }
        }

    }


    //Helpers from TFC
    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }

}