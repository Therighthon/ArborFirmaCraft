package com.therighthon.afc.common.world;

import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class AFCPlacements
{
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, AFC.MOD_ID);

    public static final RegistryObject<PlacementModifierType<ElevationRestrictedPlacement>> ELEVATION_RESTRICTED = register("elevation_restricted", ()-> ElevationRestrictedPlacement.PLACEMENT_CODEC);

    private static <C extends PlacementModifier> RegistryObject<PlacementModifierType<C>> register(String name, PlacementModifierType<C> codec)
    {
        return PLACEMENT_MODIFIERS.register(name, () -> codec);
    }
}
