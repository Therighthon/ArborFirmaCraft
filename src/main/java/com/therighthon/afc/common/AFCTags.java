package com.therighthon.afc.common;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.util.Helpers;

public class AFCTags
{
    public static class Blocks
    {
        public static final TagKey<Block> TAPPABLE_LOGS = create("tappable_logs");
        public static final TagKey<Block> BIRCH_LOGS = create("birch_logs");
        public static final TagKey<Block> MAPLE_LOGS = create("maple_logs");
        public static final TagKey<Block> HEVEA_LOGS = create("hevea_logs");

        private static TagKey<Block> create(String id)
        {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("afc", id));
        }

    }
}
