package com.therighthon.afc.common;

import java.util.EnumMap;
import java.util.function.Function;

import com.therighthon.afc.common.blocks.AFCWood;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig
{
    public final EnumMap<AFCWood, ForgeConfigSpec.IntValue> saplingGrowthDays;

    ServerConfig(ForgeConfigSpec.Builder innerBuilder)
    {

        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation("afc.config.server." + name);

        innerBuilder.pop().push("saplings");

        saplingGrowthDays = new EnumMap<>(AFCWood.class);
        for (AFCWood wood : AFCWood.VALUES)
        {
            final String valueName = String.format("%sSaplingGrowthDays", wood.getSerializedName());
            saplingGrowthDays.put(wood, builder.apply(valueName).comment(String.format("Days for a %s tree sapling to be ready to grow into a full tree.", wood.getSerializedName())).defineInRange(valueName, wood.defaultDaysToGrow(), 0, Integer.MAX_VALUE));
        }
    }
}
