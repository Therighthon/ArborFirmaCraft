package com.therighthon.afc.common.fluids;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.material.Fluid;

public enum SimpleAFCFluid implements StringRepresentable
{
    MAPLE_SAP(0xFFC39E37),
    MAPLE_SAP_CONCENTRATE(0xFFC39E37),
    BIRCH_SAP(0xFFDCDCDC),
    BIRCH_SAP_CONCENTRATE(0xFFDCDCDC),
    LATEX(0xFFB7D9BC),
    MAPLE_SYRUP(0xBBC39E37),
    BIRCH_SYRUP(0xBBDCDCDC);

    private final String id;
    private final int color;

    SimpleAFCFluid(int color)
    {
        this.id = name().toLowerCase(Locale.ROOT);
        this.color = color;
    }

    public String getId()
    {
        return id;
    }

    public int getColor()
    {
        return color;
    }

    @Override
    public String getSerializedName()
    {
        return id;
    }

    public boolean isTransparent()
    {
        return true;
    }

}
