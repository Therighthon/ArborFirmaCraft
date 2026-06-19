package com.therighthon.afc.common;

import com.therighthon.afc.AFCHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.util.Helpers;

public class AFCTags
{
    public static class Blocks
    {
        public static final TagKey<Block> TAPPABLE_LOGS = blockTag("tappable_logs");

        // Vanilla TFC Trees - Only add block tags, and add under the same names as the existing item tags
        public static final TagKey<Block> ACACIA_LOGS = tfcBlockTag("acacia_logs");
        public static final TagKey<Block> ASH_LOGS = tfcBlockTag("ash_logs");
        public static final TagKey<Block> ASPEN_LOGS = tfcBlockTag("aspen_logs");
        public static final TagKey<Block> BIRCH_LOGS = tfcBlockTag("birch_logs");
        public static final TagKey<Block> BLACKWOOD_LOGS = tfcBlockTag("blackwood_logs");
        public static final TagKey<Block> CHESTNUT_LOGS = tfcBlockTag("chestnut_logs");
        public static final TagKey<Block> DOUGLAS_FIR_LOGS = tfcBlockTag("douglas_fir_logs");
        public static final TagKey<Block> HICKORY_LOGS = tfcBlockTag("hickory_logs");
        public static final TagKey<Block> KAPOK_LOGS = tfcBlockTag("kapok_logs");
        public static final TagKey<Block> MAPLE_LOGS = tfcBlockTag("maple_logs");
        public static final TagKey<Block> MANGROVE_LOGS = tfcBlockTag("mangrove_logs");
        public static final TagKey<Block> OAK_LOGS = tfcBlockTag("oak_logs");
        public static final TagKey<Block> PALM_LOGS = tfcBlockTag("palm_logs");
        public static final TagKey<Block> PINE_LOGS = tfcBlockTag("pine_logs");
        public static final TagKey<Block> ROSEWOOD_LOGS = tfcBlockTag("rosewood_logs");
        public static final TagKey<Block> SEQUOIA_LOGS = tfcBlockTag("sequoia_logs");
        public static final TagKey<Block> SPRUCE_LOGS = tfcBlockTag("spruce_logs");
        public static final TagKey<Block> SYCAMORE_LOGS = tfcBlockTag("sycamore_logs");
        public static final TagKey<Block> WHITE_CEDAR_LOGS = tfcBlockTag("white_cedar_logs");
        public static final TagKey<Block> WILLOW_LOGS = tfcBlockTag("maple_logs");

        // AFC Standard trees
        public static final TagKey<Block> ARAUCARIA_LOGS = blockTag("araucaria_logs");
        public static final TagKey<Block> BAOBAB_LOGS = blockTag("baobab_logs");
        public static final TagKey<Block> BEECH_LOGS = blockTag("beech_logs");
        public static final TagKey<Block> CYPRESS_LOGS = blockTag("cypress_logs");
        public static final TagKey<Block> EUCALYPTUS_LOGS = blockTag("eucalyptus_logs");
        public static final TagKey<Block> FIG_LOGS = blockTag("fig_logs");
        public static final TagKey<Block> GINKGO_LOGS = blockTag("ginkgo_logs");
        public static final TagKey<Block> HEVEA_LOGS = blockTag("hevea_logs");
        public static final TagKey<Block> IPE_LOGS = blockTag("ipe_logs");
        public static final TagKey<Block> IRONWOOD_LOGS = blockTag("ironwood_logs");
        public static final TagKey<Block> MAHOE_LOGS = blockTag("mahoe_logs");
        public static final TagKey<Block> MAHOGANY_LOGS = blockTag("mahogany_logs");
        public static final TagKey<Block> TEAK_LOGS = blockTag("teak_logs");
        public static final TagKey<Block> TUALANG_LOGS = blockTag("tualang_logs");

        // AFC Special Woods
        public static final TagKey<Block> BLACK_OAK_LOGS = blockTag("black_oak_logs");
        public static final TagKey<Block> GUM_ARABIC_LOGS = blockTag("gum_arabic_logs");
        public static final TagKey<Block> POPLAR_LOGS = blockTag("poplar_logs");
        public static final TagKey<Block> RAINBOW_EUCALYPTUS_LOGS = blockTag("rainbow_eucalyptus_logs");
        public static final TagKey<Block> REDCEDAR_LOGS = blockTag("redcedar_logs");
        public static final TagKey<Block> RUBBER_FIG_LOGS = blockTag("rubber_fig_logs");
        public static final TagKey<Block> KAURI_LOGS = blockTag("kauri_logs");

        // Misc
        public static final TagKey<Block> TREE_TAPS = blockTag("tree_taps");

        private static TagKey<Block> tfcBlockTag(String name)
        {
            return TagKey.create(Registries.BLOCK, Helpers.identifier(name));
        }

        private static TagKey<Block> blockTag(String name)
        {
            return TagKey.create(Registries.BLOCK, AFCHelpers.modIdentifier(name));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> ARAUCARIA_LOGS = itemTag("araucaria_logs");
        public static final TagKey<Item> BAOBAB_LOGS = itemTag("baobab_logs");
        public static final TagKey<Item> BEECH_LOGS = itemTag("beech_logs");
        public static final TagKey<Item> CYPRESS_LOGS = itemTag("cypress_logs");
        public static final TagKey<Item> EUCALYPTUS_LOGS = itemTag("eucalyptus_logs");
        public static final TagKey<Item> FIG_LOGS = itemTag("fig_logs");
        public static final TagKey<Item> GINKGO_LOGS = itemTag("ginkgo_logs");
        public static final TagKey<Item> HEVEA_LOGS = itemTag("hevea_logs");
        public static final TagKey<Item> IPE_LOGS = itemTag("ipe_logs");
        public static final TagKey<Item> IRONWOOD_LOGS = itemTag("ironwood_logs");
        public static final TagKey<Item> MAHOE_LOGS = itemTag("mahoe_logs");
        public static final TagKey<Item> MAHOGANY_LOGS = itemTag("mahogany_logs");
        public static final TagKey<Item> TEAK_LOGS = itemTag("teak_logs");
        public static final TagKey<Item> TUALANG_LOGS = itemTag("tualang_logs");

        // AFC Special Woods
        public static final TagKey<Item> BLACK_OAK_LOGS = itemTag("black_oak_logs");
        public static final TagKey<Item> GUM_ARABIC_LOGS = itemTag("gum_arabic_logs");
        public static final TagKey<Item> POPLAR_LOGS = itemTag("poplar_logs");
        public static final TagKey<Item> RAINBOW_EUCALYPTUS_LOGS = itemTag("rainbow_eucalyptus_logs");
        public static final TagKey<Item> REDCEDAR_LOGS = itemTag("redcedar_logs");
        public static final TagKey<Item> RUBBER_FIG_LOGS = itemTag("rubber_fig_logs");
        public static final TagKey<Item> KAURI_LOGS = itemTag("kauri_logs");

        private static TagKey<Item> itemTag(String name)
        {
            return TagKey.create(Registries.ITEM, AFCHelpers.modIdentifier(name));
        }


    }
}
