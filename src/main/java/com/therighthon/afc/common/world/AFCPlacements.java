package com.therighthon.afc.common.world;

import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.world.placement.TFCPlacements;


public final class AFCPlacements
{
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, AFC.MOD_ID);

    public static final TFCPlacements.Id<ElevationRestrictedPlacement> ELEVATION_RESTRICTED = register("elevation_restricted", ()-> ElevationRestrictedPlacement.CODEC);

    private static <C extends PlacementModifier> TFCPlacements.Id<C> register(String name, PlacementModifierType<C> codec)
    {
        return new TFCPlacements.Id<>(PLACEMENT_MODIFIERS.register(name, () -> codec));
    }
}
