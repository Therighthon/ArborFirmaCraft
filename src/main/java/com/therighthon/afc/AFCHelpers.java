package com.therighthon.afc;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static net.dries007.tfc.util.Helpers.*;


public class AFCHelpers
{
    public static ResourceLocation modIdentifier(String name) {
        return resourceLocation("afc", name);
    }

    public static ModelLayerLocation layerId(String name)
    {
        return layerId(name, "main");
    }

    /**
     * Creates {@link ModelLayerLocation} in the default manner
     */
    public static ModelLayerLocation layerId(String name, String part)
    {
        return new ModelLayerLocation(modIdentifier(name), part);
    }
}
