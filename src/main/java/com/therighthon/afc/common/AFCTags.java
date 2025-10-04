package com.therighthon.afc.common;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.util.Helpers;

public class AFCTags
{
    public static class Blocks
    {
        public static final TagKey<Block> TAPPABLE_LOGS = tag("tappable_logs");

        // Vanilla TFC Trees
        public static final TagKey<Block> ACACIA_LOGS = tag("acacia_logs");
        public static final TagKey<Block> ASH_LOGS = tag("ash_logs");
        public static final TagKey<Block> ASPEN_LOGS = tag("aspen_logs");
        public static final TagKey<Block> BIRCH_LOGS = tag("birch_logs");
        public static final TagKey<Block> BLACKWOOD_LOGS = tag("blackwood_logs");
        public static final TagKey<Block> CHESTNUT_LOGS = tag("chestnut_logs");
        public static final TagKey<Block> DOUGLAS_FIR_LOGS = tag("douglas_fir_logs");
        public static final TagKey<Block> HICKORY_LOGS = tag("hickory_logs");
        public static final TagKey<Block> KAPOK_LOGS = tag("kapok_logs");
        public static final TagKey<Block> MAPLE_LOGS = tag("maple_logs");
        public static final TagKey<Block> MANGROVE_LOGS = tag("mangrove_logs");
        public static final TagKey<Block> OAK_LOGS = tag("oak_logs");
        public static final TagKey<Block> PALM_LOGS = tag("palm_logs");
        public static final TagKey<Block> PINE_LOGS = tag("pine_logs");
        public static final TagKey<Block> ROSEWOOD_LOGS = tag("rosewood_logs");
        public static final TagKey<Block> SEQUOIA_LOGS = tag("sequoia_logs");
        public static final TagKey<Block> SPRUCE_LOGS = tag("spruce_logs");
        public static final TagKey<Block> SYCAMORE_LOGS = tag("sycamore_logs");
        public static final TagKey<Block> WHITE_CEDAR_LOGS = tag("white_cedar_logs");
        public static final TagKey<Block> WILLOW_LOGS = tag("maple_logs");

        // AFC Standard trees
        public static final TagKey<Block> ARAUCARIA_LOGS = tag("araucaria_logs");
        public static final TagKey<Block> BAOBAB_LOGS = tag("baobab_logs");
        public static final TagKey<Block> CYPRESS_LOGS = tag("cypress_logs");
        public static final TagKey<Block> EUCALYPTUS_LOGS = tag("eucalyptus_logs");
        public static final TagKey<Block> FIG_LOGS = tag("fig_logs");
        public static final TagKey<Block> HEVEA_LOGS = tag("hevea_logs");
        public static final TagKey<Block> IPE_LOGS = tag("ipe_logs");
        public static final TagKey<Block> IRONWOOD_LOGS = tag("ironwood_logs");
        public static final TagKey<Block> MAHOGANY_LOGS = tag("mahogany_logs");
        public static final TagKey<Block> TEAK_LOGS = tag("teak_logs");
        public static final TagKey<Block> TUALANG_LOGS = tag("tualang_logs");

        // AFC Special Woods
        public static final TagKey<Block> BLACK_OAK_LOGS = tag("black_oak_logs");
        public static final TagKey<Block> GUM_ARABIC_LOGS = tag("gum_arabic_logs");
        public static final TagKey<Block> POPLAR_LOGS = tag("poplar_logs");
        public static final TagKey<Block> RAINBOW_EUCALYPTUS_LOGS = tag("rainbow_eucalyptus_logs");
        public static final TagKey<Block> REDCEDAR_LOGS = tag("redcedar_logs");
        public static final TagKey<Block> RUBBER_FIG_LOGS = tag("rubber_fig_logs");


        public static final TagKey<Block> TREE_TAPS = tag("tree_taps");

        private static TagKey<Block> tag(String name)
        {
            return TagKey.create(Registries.BLOCK, Helpers.identifier(name));
        }


    }
}
