package com.therighthon.afc.common;

import java.util.function.Function;

import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.world.feature.tree.*;

public class AFCFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, AFC.MOD_ID);
    public static final DeferredHolder<Feature<?>, NoTrunkStackedTreeFeature> STACKED_TREE = register("trunkless_stacked_tree", NoTrunkStackedTreeFeature::new, StackedTreeConfig.CODEC);


    private static <C extends FeatureConfiguration, F extends Feature<C>> DeferredHolder<Feature<?>, F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return FEATURES.register(name, () -> factory.apply(codec));
    }
}
