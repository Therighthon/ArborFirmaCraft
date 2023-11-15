package com.therighthon.afc.common.world;

import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ElevationRestrictedPlacement extends PlacementModifier
{
    public static final Codec<ElevationRestrictedPlacement> PLACEMENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.optionalFieldOf("min_elevation", Integer.MIN_VALUE).forGetter(c -> c.minElev),
        Codec.INT.optionalFieldOf("max_elevation", Integer.MAX_VALUE).forGetter(c -> c.maxElev)
    ).apply(instance, ElevationRestrictedPlacement::new));

    private final int minElev;
    private final int maxElev;

    public ElevationRestrictedPlacement(int minElev, int maxElev)
    {
        this.minElev = minElev;
        this.maxElev = maxElev;
    }

    public int getMinElev()
    {
        return this.minElev;
    }

    public int getMaxElev()
    {
        return this.maxElev;
    }

    @Override
    public PlacementModifierType<?> type()
    {
        return AFCPlacements.ELEVATION_RESTRICTED.get();
    }

    public boolean isValid(BlockPos pos)
    {
        final int elev = pos.getY();
        return (elev >= this.minElev && elev <= this.maxElev);
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos)
    {
        return isValid(pos) ? Stream.of(pos) : Stream.empty();
    }
}
