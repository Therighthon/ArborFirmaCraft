package com.therighthon.afc.common.fluids;

import java.util.Locale;

public enum Syrup
{
    MAPLE_SYRUP(0xFFC39E37),
    BIRCH_SYRUP(0xFFB0AE32);

    private final String id;
    private final int color;

    Syrup(int color)
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
    }
