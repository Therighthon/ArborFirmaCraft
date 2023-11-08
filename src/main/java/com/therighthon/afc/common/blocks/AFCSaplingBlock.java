package com.therighthon.afc.common.blocks;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

public class AFCSaplingBlock extends TFCSaplingBlock
{
    private final ExtendedProperties properties;
    private final Supplier<Integer> daysToGrow;

    public AFCSaplingBlock(TFCTreeGrower tree, ExtendedProperties properties, Supplier<Integer> days, boolean sand)
    {
        super(tree, properties, days, sand);
        this.properties = properties;
        this.daysToGrow = days;
    }

    @Override
    public int getDaysToGrow()
    {
        return daysToGrow.get();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(7) == 0)
        {
            if (!level.isAreaLoaded(pos, 1))
            {
                return;
            }
            if (level.getBlockEntity(pos) instanceof TickCounterBlockEntity counter)
            {
                if (counter.getTicksSinceUpdate() > ICalendar.TICKS_IN_DAY *  getDaysToGrow() * TFCConfig.SERVER.globalSaplingGrowthModifier.get())
                {
                    this.advanceTree(level, pos, state.setValue(STAGE, 1), random);
                }
            }
        }
    }
}
