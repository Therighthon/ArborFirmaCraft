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
        public static final TagKey<Block> BIRCH_LOGS = tag("birch_logs");
        public static final TagKey<Block> MAPLE_LOGS = tag("maple_logs");
        public static final TagKey<Block> HEVEA_LOGS = tag("hevea_logs");
        public static final TagKey<Block> TREE_TAPS = tag("tree_taps");

        private static TagKey<Block> tag(String name)
        {
            return TagKey.create(Registries.BLOCK, Helpers.identifier(name));
        }


    }
}
