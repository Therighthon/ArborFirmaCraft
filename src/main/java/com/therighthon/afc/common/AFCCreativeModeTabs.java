package com.therighthon.afc.common;

import com.therighthon.afc.common.blocks.FLCompatBlocks;
import com.therighthon.afc.common.fluids.AFCFluids;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.TreeSpecies;
import com.therighthon.afc.common.blocks.UniqueLogs;
import com.therighthon.afc.common.items.AFCItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


import net.dries007.tfc.util.Metal;

public final class AFCCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AFC.MOD_ID);

    public static final Id AFC_TAB = register("arborfirmacraft", () -> new ItemStack(AFCBlocks.TREE_SPECIES.get(TreeSpecies.TAMARACK).get(TreeSpecies.BlockType.SAPLING).get()), AFCCreativeModeTabs::fillTab);

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        out.accept(AFCItems.RUBBER_BAR.get());
        out.accept(AFCItems.MAPLE_SUGAR.get());
        out.accept(AFCItems.BIRCH_SUGAR.get());
        out.accept(AFCBlocks.TREE_TAP.get());
        AFCFluids.FLUIDS.getEntries().forEach(fluid -> out.accept(fluid.value().getBucket()));
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
            for (Metal metal : Metal.values())
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
                accept(out, FLCompatBlocks.KEGS, wood);
                accept(out, FLCompatBlocks.STOMPING_BARRELS, wood);
                accept(out, FLCompatBlocks.BARREL_PRESSES, wood);
                accept(out, FLCompatBlocks.WINE_SHELVES, wood);
            }
        }

    }


    //Helpers from TFC
    private static Id register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final var holder = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
            .icon(icon)
            .title(Component.translatable("afc.creative_tab." + name))
            .displayItems(displayItems)
            .build());
        return new Id(holder, displayItems);
    }

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
            AFC.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            return;
        }
        out.accept(reg.get());
    }

    public static record Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {
        public Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {
            this.tab = tab;
            this.generator = generator;
        }

        public DeferredHolder<CreativeModeTab, CreativeModeTab> tab() {
            return this.tab;
        }

        public CreativeModeTab.DisplayItemsGenerator generator() {
            return this.generator;
        }
    }

}