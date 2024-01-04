package com.therighthon.afc.common;

import java.util.function.Function;

import com.therighthon.afc.AFC;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.mojang.serialization.Codec;
import net.dries007.tfc.world.feature.tree.*;

public class AFCFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, AFC.MOD_ID);
    public static final RegistryObject<StackedTreeFeature> STACKED_TREE = register("trunkless_stacked_tree", NoTrunkStackedTreeFeature::new, StackedTreeConfig.CODEC);


    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return FEATURES.register(name, () -> factory.apply(codec));
    }
}
