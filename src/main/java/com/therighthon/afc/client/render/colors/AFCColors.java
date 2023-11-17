package com.therighthon.afc.client.render.colors;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.calendar.Season;
import net.dries007.tfc.util.climate.Climate;

public final class AFCColors
{
    public static final ResourceLocation FOLIAGE_JACARANDA_COLORS_LOCATION = Helpers.identifier("textures/colormap/foliage_jacaranda.png");

    public static final ResourceLocation FOLIAGE_YELLOW_COLORS_LOCATION = Helpers.identifier("textures/colormap/foliage_yellow.png");
    public static final ResourceLocation FOLIAGE_ORANGE_COLORS_LOCATION = Helpers.identifier("textures/colormap/foliage_orange.png");
    public static final ResourceLocation FOLIAGE_RED_COLORS_LOCATION = Helpers.identifier("textures/colormap/foliage_red.png");


    private static int[] FOLIAGE_JACARANDA_COLORS_CACHE = new int[65536];
    private static int[] FOLIAGE_YELLOW_COLORS_CACHE = new int[65536];
    private static int[] FOLIAGE_ORANGE_COLORS_CACHE = new int[65536];
    private static int[] FOLIAGE_RED_COLORS_CACHE = new int[65536];

    public static void setFoliageJacarandaColors(int[] foliageColorsCache) {
        FOLIAGE_JACARANDA_COLORS_CACHE = foliageColorsCache;
    }
    public static void setFoliageYellowColors(int[] foliageColorsCache) {
        FOLIAGE_YELLOW_COLORS_CACHE = foliageColorsCache;
    }
    public static void setFoliageOrangeColors(int[] foliageColorsCache) {
        FOLIAGE_ORANGE_COLORS_CACHE = foliageColorsCache;
    }
    public static void setFoliageRedColors(int[] foliageColorsCache) {
        FOLIAGE_RED_COLORS_CACHE = foliageColorsCache;
    }


    public AFCColors() {
    }

    public static int getYellowIpeFoliageColor(@Nullable BlockPos pos, int tintIndex, int autumnIndex) {
        if (pos != null && tintIndex == 0) {
            int index;
            switch(getAdjustedNoisyMonth(pos)) {
                case MARCH:
                case APRIL:
                    index = Helpers.hash(91273491823412341L, pos);
                    return FOLIAGE_YELLOW_COLORS_CACHE[index & '\uffff'];
                case DECEMBER:
                case JANUARY:
                case FEBRUARY:
                case MAY:
                case JUNE:
                case JULY:
                case AUGUST:
                case SEPTEMBER:
                case OCTOBER:
                case NOVEMBER:
                    return TFCColors.getSeasonalFoliageColor(pos, tintIndex, autumnIndex);
            }
        }

        return -1;
    }

    public static int getJacarandaFoliageColor(@Nullable BlockPos pos, int tintIndex, int autumnIndex) {
        if (pos != null && tintIndex == 0) {
            int index;
            switch(getAdjustedNoisyMonth(pos)) {
                case MARCH:
                case APRIL:
                    index = Helpers.hash(91273491823412341L, pos);
                    return FOLIAGE_JACARANDA_COLORS_CACHE[index & '\uffff'];
                case DECEMBER:
                case JANUARY:
                case FEBRUARY:
                case MAY:
                case JUNE:
                case JULY:
                case AUGUST:
                case SEPTEMBER:
                case OCTOBER:
                case NOVEMBER:
                    return TFCColors.getSeasonalFoliageColor(pos, tintIndex, autumnIndex);
            }
        }

        return -1;
    }

    public static int getKapokFoliageColor(@Nullable BlockPos pos, int tintIndex, int autumnIndex) {
        if (pos != null && tintIndex == 0) {
            int index;
            switch(getAdjustedNoisyMonth(pos)) {
                case APRIL:
                case MAY:
                    index = Helpers.hash(91273491823412341L, pos);
                    return FOLIAGE_RED_COLORS_CACHE[index & '\uffff'];
                case DECEMBER:
                case JANUARY:
                case FEBRUARY:
                case MARCH:
                case JUNE:
                case JULY:
                case AUGUST:
                case SEPTEMBER:
                case OCTOBER:
                case NOVEMBER:
                    return TFCColors.getSeasonalFoliageColor(pos, tintIndex, autumnIndex);
            }
        }

        return -1;
    }

    public static int getFlameOfTheForestFoliageColor(@Nullable BlockPos pos, int tintIndex, int autumnIndex) {
        if (pos != null && tintIndex == 0) {
            int index;
            switch(getAdjustedNoisyMonth(pos)) {
                case MARCH:
                case APRIL:
                    index = Helpers.hash(91273491823412341L, pos);
                    return FOLIAGE_ORANGE_COLORS_CACHE[index & '\uffff'];
                case DECEMBER:
                case JANUARY:
                case FEBRUARY:
                case MAY:
                case JUNE:
                case JULY:
                case AUGUST:
                case SEPTEMBER:
                case OCTOBER:
                case NOVEMBER:
                    return TFCColors.getSeasonalFoliageColor(pos, tintIndex, autumnIndex);
            }
        }

        return -1;
    }

    private static Season getAdjustedNoisySeason(BlockPos pos) {
        Month currentMonth = Calendars.CLIENT.getCalendarMonthOfYear();
        Season season = currentMonth.getSeason();
        float seasonDelta = 0.0F;
        float monthDelta = Calendars.CLIENT.getCalendarFractionOfMonth();
        switch(currentMonth) {
            case FEBRUARY:
            case MAY:
            case AUGUST:
            case NOVEMBER:
                seasonDelta = 0.5F * monthDelta;
                break;
            case MARCH:
            case JUNE:
            case SEPTEMBER:
            case DECEMBER:
                season = season.previous();
                seasonDelta = 0.5F + 0.5F * monthDelta;
        }

        int positionDeltaHash = Helpers.hash(836494186029734123L, pos) & 255;
        if ((float)positionDeltaHash < 256.0F * seasonDelta) {
            season = season.next();
        }

        return season;
    }

    private static Month getAdjustedNoisyMonth(BlockPos pos) {
        Month currentMonth = Calendars.CLIENT.getCalendarMonthOfYear();
        Month month = currentMonth;
        float monthT = Calendars.CLIENT.getCalendarFractionOfMonth() - 0.5f;
        float monthDelta = 16*(monthT*monthT*monthT*monthT);

        int positionDeltaHash = Helpers.hash(836494186029734123L, pos) & 255;
        if ((float)positionDeltaHash < 256.0F * monthDelta) {
            if (monthT>=0.5) {
                month = month.next();
            } else {
                month = getPreviousMonth(month);
            }

        }

        return month;
    }

    private static Month getPreviousMonth (Month month)
    {
        return switch (month)
            {
                case JANUARY -> Month.DECEMBER;
                case FEBRUARY -> Month.JANUARY;
                case MARCH -> Month.FEBRUARY;
                case APRIL -> Month.MARCH;
                case MAY -> Month.APRIL;
                case JUNE -> Month.MAY;
                case JULY -> Month.JUNE;
                case AUGUST -> Month.JULY;
                case SEPTEMBER -> Month.AUGUST;
                case OCTOBER -> Month.SEPTEMBER;
                case NOVEMBER -> Month.OCTOBER;
                case DECEMBER -> Month.NOVEMBER;
            };
    }

    private static int getClimateColor(int[] colorCache, BlockPos pos) {
        Level level = ClientHelpers.getLevel();
        if (level != null) {
            float temperature = Climate.getTemperature(level, pos);
            float rainfall = Climate.getRainfall(level, pos);
            return getClimateColor(colorCache, temperature, rainfall);
        } else {
            return 0;
        }
    }

    private static int getClimateColor(int[] colorCache, float temperature, float rainfall) {
        int temperatureIndex = 255 - Mth.clamp((int)((temperature + 30.0F) * 255.0F / 60.0F), 0, 255);
        int rainfallIndex = 255 - Mth.clamp((int)(rainfall * 255.0F / 500.0F), 0, 255);
        return colorCache[temperatureIndex | rainfallIndex << 8];
    }
}
