package com.therighthon.afc.common;

/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

import java.util.function.Function;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.mojang.serialization.Codec;
import net.dries007.tfc.world.feature.tree.*;

import static com.therighthon.afc.AFC.MOD_ID;

@SuppressWarnings("unused")
public class AFCFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);
    public static final RegistryObject<StackedTreeFeature> STACKED_TREE = register("trunkless_stacked_tree", NoTrunkStackedTreeFeature::new, StackedTreeConfig.CODEC);


    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return FEATURES.register(name, () -> factory.apply(codec));
    }
}