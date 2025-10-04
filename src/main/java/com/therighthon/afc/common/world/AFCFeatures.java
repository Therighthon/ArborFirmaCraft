package com.therighthon.afc.common.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.therighthon.afc.AFC.*;

public class AFCFeatures
{
    // TODO: Custom forest features that track extra params for tree selection
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, MOD_ID);
}
