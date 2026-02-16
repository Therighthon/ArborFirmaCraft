package com.therighthon.afc.common.blockentities;

import com.therighthon.afc.common.AFCTags;
import com.therighthon.afc.common.blocks.TapBlock;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.client.overworld.SolarCalculator;
import net.dries007.tfc.client.particle.FluidParticleOption;
import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.blocks.wood.BranchDirection;
import net.dries007.tfc.common.blocks.wood.LogBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.Climate;

public class TapBlockEntity extends BlockEntity
{
    private long lastUpdateTick;

    public TapBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(AFCBlockEntities.TAP_BLOCK_ENTITY.get(), pPos, pBlockState);
        lastUpdateTick = Calendars.SERVER.getTicks();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TapBlockEntity tap)
    {
        final Direction facing = state.getValue(TapBlock.FACING);
        tap.tickPouring(level, pos, facing);
    }

    public static boolean isSpring(Level level, BlockPos pos)
    {
        Month currentMonth = Calendars.SERVER.getHemispheralCalendarMonthOfYear(SolarCalculator.getInNorthernHemisphere(pos, level));
        return (currentMonth == Month.FEBRUARY || currentMonth == Month.MARCH || currentMonth == Month.APRIL || currentMonth == Month.MAY || currentMonth == Month.JUNE);
    }

    public static boolean isSpring(Level level, BlockPos pos, long tick, int daysInMonth)
    {
        Month currentMonth = Calendars.SERVER.getHemispheralCalendarMonthOfYear(SolarCalculator.getInNorthernHemisphere(pos, level), tick, daysInMonth);
        return (currentMonth == Month.JANUARY || currentMonth == Month.FEBRUARY || currentMonth == Month.MARCH || currentMonth == Month.APRIL || currentMonth == Month.MAY || currentMonth == Month.JUNE);
    }

    public static boolean isTempOkay(Level level, BlockPos pos, float minTemp, float maxTemp)
    {
        final float currentTemp = Climate.getInstantTemperature(level, pos);
        return (currentTemp >= minTemp && currentTemp <= maxTemp);
    }

    public static boolean isTempOkay(Level level, BlockPos pos, float minTemp, float maxTemp, long tick, int daysInMonth)
    {
        final float currentTemp = Climate.getInstantTemperature(level, pos, tick, daysInMonth);
        return (currentTemp >= minTemp && currentTemp <= maxTemp);
    }

    public static boolean hasValidTrunk(Level level, BlockPos pos, BlockState state)
    {
        final BlockState aboveState = level.getBlockState(pos.above());
        final BlockState belowState = level.getBlockState(pos.below());
        return (aboveState == state && belowState == state);
    }

    //Counts how many taps are adjacent to the input position. Returns at least 1 to avoid a crash from an improperly tagged block
    public static int getTapCount(Level level, BlockPos pos)
    {
        int tapCount = 0;
        if (Helpers.isBlock(level.getBlockState(pos.north()), AFCTags.Blocks.TREE_TAPS))
        {
            tapCount++;
        }
        if (Helpers.isBlock(level.getBlockState(pos.east()), AFCTags.Blocks.TREE_TAPS))
        {
            tapCount++;
        }
        if (Helpers.isBlock(level.getBlockState(pos.south()), AFCTags.Blocks.TREE_TAPS))
        {
            tapCount++;
        }
        if (Helpers.isBlock(level.getBlockState(pos.west()), AFCTags.Blocks.TREE_TAPS))
        {
            tapCount++;
        }
        if (tapCount == 0)
        {
            return 1;
        }
        else
        {
            return tapCount;
        }
    }


    // This is the part where everything happens
    // Based on Terrafirmacraft's barrel dripping mechanic
    public void tickPouring(Level level, BlockPos pos, Direction facing)
    {
        //First bit checks if there is a valid container to pour into before starting the pour
        //Every 20 ticks...
        final long thisTick = Calendars.SERVER.getCalendarTicks();
        if (thisTick % 20 == 0)
        {
            //Get the position of the log block
            BlockPos logPos = switch (facing)
            {
                case NORTH -> pos.south();
                case SOUTH -> pos.north();
                case EAST -> pos.west();
                case WEST -> pos.east();
                default -> throw new IllegalStateException("Unexpected value: " + facing);
            };

            //Check for a valid recipe for said log block before worrying about blockentities and nonsense
            BlockState logState = level.getBlockState(logPos);
            final TreeTapRecipe recipe = TreeTapRecipe.getRecipe(logState);

            if (recipe != null)
            {
                final int dripPeriod = 20 * getTapCount(level, logPos);

                if (thisTick % dripPeriod == 0)
                {
                    final BlockPos pourPos = pos.below();
                    final BlockEntity blockEntity = level.getBlockEntity(pourPos);

                    //Needs to check if the block entity is removed every tick while pouring to avoid a crash
                    if (blockEntity != null)
                    {
                        final @Nullable IFluidHandler fluidHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, pourPos, Direction.UP);

                        //Check that the block the tap is on is natural, if required by the recipe. The idea is to support blocks other than TFC logs
                        //Also checks that the tap is attached to a trunk with at least one log block above and at least one below.
                        //Ternary is used (sloppily) to ensure that we don't ask for a "natural" logblock from a block that can't have it
                        //It's sloppy, because if someone doesn't write the recipe correctly, then it will crash the game, but it should be a helpful crash, so...
                        if (fluidHandler != null && (!recipe.requiresNaturalLog() || !(logState.getValue(LogBlock.BRANCH_DIRECTION) == BranchDirection.NONE))
                            && hasValidTrunk(level, logPos, logState))
                        {
                            int dripCount = 0;
                            final long skippedTime = thisTick - lastUpdateTick;
                            // Greater than 1 hour time skip simulation
                            if (skippedTime > ICalendar.CALENDAR_TICKS_IN_HOUR)
                            {
                                int daysInMonth = Calendars.SERVER.getCalendarDaysInMonth();
                                // Simulate at least 24 time skips, and at least two per month of time skipped
                                final int skipInterval = (int) Math.min(skippedTime / 24, (long) daysInMonth * ICalendar.CALENDAR_TICKS_IN_DAY / 4);
                                final int dripsPerSkip = skipInterval / dripPeriod; // The number of times syrup would drip in the course of a skip interval

                                long simTick = lastUpdateTick;
                                while (simTick < thisTick)
                                {
                                    if (isTempOkay(level, pos, recipe.getMinTemp(), recipe.getMaxTemp(), simTick, daysInMonth) && (!recipe.springOnly() || isSpring(level, pos, simTick, daysInMonth)))
                                    {
                                        dripCount += dripsPerSkip;
                                    }

                                    simTick += skipInterval;
                                }
                            }
                            else if (isTempOkay(level, pos, recipe.getMinTemp(), recipe.getMaxTemp()) && (!recipe.springOnly() || isSpring(level, pos)))
                            {
                                dripCount = (int) (skippedTime / dripPeriod);
                            }

                            if (dripCount > 0 && level instanceof ServerLevel server)
                            {
                                FluidStack fluidStack = recipe.getOutput().copy();
                                fluidStack.setAmount(fluidStack.getAmount() * dripCount);
                                fluidHandler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

                                final double offset = -0.2;
                                final double dx = facing.getStepX() > 0 ? offset : facing.getStepX() < 0 ? -offset : 0;
                                final double dz = facing.getStepZ() > 0 ? offset : facing.getStepZ() < 0 ? -offset : 0;
                                final double x = pos.getX() + 0.5f + dx;
                                final double y = pos.getY() + 0.125f;
                                final double z = pos.getZ() + 0.5f + dz;

                                Helpers.playSound(level, pos, TFCSounds.BARREL_DRIP.get());
                                server.sendParticles(new FluidParticleOption(TFCParticles.BARREL_DRIP.get(), fluidStack.getFluid()), x, y, z, 1, 0, 0, 0, 1f);
                            }
                        }
                    }
                    lastUpdateTick = thisTick;
                }
            }
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider)
    {
        lastUpdateTick = nbt.getLong("tick");
        super.loadAdditional(nbt, provider);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider)
    {
        nbt.putLong("tick", lastUpdateTick);
        super.saveAdditional(nbt, provider);
    }
}
