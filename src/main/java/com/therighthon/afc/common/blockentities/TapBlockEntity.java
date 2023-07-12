package com.therighthon.afc.common.blockentities;

import java.util.Map;
import java.util.Optional;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.AFCTags;
import com.therighthon.afc.common.blocks.TapBlock;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.fluids.SimpleAFCFluid;
import com.therighthon.afc.common.recipe.TapInventory;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.io.input.TaggedInputStream;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.client.particle.FluidParticleOption;
import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.LogBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.fluids.Alcohol;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.MixingFluid;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.util.Helpers;

public class TapBlockEntity extends BlockEntity
{
    @Nullable private BlockPos pourPos = null;



    public TapBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(AFCBlockEntities.TAP_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TapBlockEntity tap) {
        final Direction facing = state.getValue(TapBlock.FACING);
        tap.tickPouring(level, pos, facing);
    }


    //IDEA: What if I reuse the pouring barrel code?

    public static boolean canPour(IFluidHandler to, FluidStack fluidStack) {
        return to.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) == 1;
    }

    public static boolean pour(IFluidHandler to, FluidStack fluidStack) {
        if (canPour(to, fluidStack)) {
            to.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
            return true;
        } else {
            return false;
        }
    }


    public void tickPouring(Level level, BlockPos pos, Direction facing)
    {
        //First bit checks if there is a valid container to pour into before starting the pour
        //Every 20 ticks...
        if (level.getGameTime() % 20 == 0)
        {
            //May need to replace this with something that checks the season/recipe?
//            if (!sealed && !this.inventory.tank.isEmpty() && facing != Direction.UP)
//            {
            final BlockPos pourPos = pos.below();
            final BlockEntity blockEntity = level.getBlockEntity(pourPos);
            if (blockEntity != null)
            {
                blockEntity.getCapability(Capabilities.FLUID, Direction.UP).ifPresent(cap -> {
                    //TODO: This is where we need to reference the recipe and get the fluid type to pass to canPour
                    if (canPour(cap, new FluidStack(TFCFluids.SALT_WATER.getSource(), 1) ))
                    {
                        this.pourPos = pourPos;
                    }
                });
            }
//                }

        }
        //Second part actually does the pouring, runs every 12 ticks to sync with sound effect
        //Maybe make this interval part of the recipe too?
        if (level.getGameTime() % 12 == 0)
        {
            //Check positional stuff
            BlockPos logPos;
            switch(facing)
            {
                case NORTH: logPos = pos.south();
                break;
                case SOUTH: logPos = pos.north();
                break;
                case EAST: logPos = pos.west();
                break;
                case WEST: logPos = pos.east();
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + facing);
            }
            BlockState logState = level.getBlockState(logPos);

            //TODO:
            AFC.LOGGER.debug(String.valueOf(logState));
            AFC.LOGGER.debug(String.valueOf(AFCTags.Blocks.TAPPABLE_LOGS));

            //Ensure block at position is natural, and has the tappable logs tag. TODO: Tappable logs could be added to the canSurvive check.
            if (Helpers.isBlock(logState, AFCTags.Blocks.TAPPABLE_LOGS) && logState.getValue(LogBlock.NATURAL))
            {
                //TODO:
                AFC.LOGGER.debug("Tappable log tag, Natural blockstate");
                //Hevea
                if (this.pourPos != null && Helpers.isBlock(level.getBlockState(logPos), AFCTags.Blocks.HEVEA_LOGS))
                {
                    //TODO:
                    AFC.LOGGER.debug("Hevea");
                    final BlockEntity blockEntity = level.getBlockEntity(this.pourPos);
                    //Needs to check if the block entity is removed every tick while pouring
                    if (blockEntity != null)
                    {
                        //TODO: Reference recipe
                        final FluidStack fluidStack = new FluidStack(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.LATEX).getSource(), 1);

                        if (blockEntity.getCapability(Capabilities.FLUID, Direction.UP).map(cap ->
                            pour(cap, fluidStack)).orElse(false))
                        {
                            if (level.getGameTime() % 12 == 0 && level instanceof ServerLevel server)
                            {
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
                        else
                        {
                            this.pourPos = null;
                        }
                    }
                    else
                    {
                        this.pourPos = null;
                    }
                }

                //Maple
                else if (this.pourPos != null && Helpers.isBlock(level.getBlockState(logPos), AFCTags.Blocks.MAPLE_LOGS))
                {
                    //TODO:
                    AFC.LOGGER.debug("Maplc");
                    final BlockEntity blockEntity = level.getBlockEntity(this.pourPos);
                    //Needs to check if the block entity is removed every tick while pouring
                    if (blockEntity != null)
                    {
                        //TODO: Reference recipe
                        final FluidStack fluidStack = new FluidStack(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.MAPLE_SAP).getSource(), 1);

                        if (blockEntity.getCapability(Capabilities.FLUID, Direction.UP).map(cap ->
                            pour(cap, fluidStack)).orElse(false))
                        {
                            if (level.getGameTime() % 12 == 0 && level instanceof ServerLevel server)
                            {
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
                        else
                        {
                            this.pourPos = null;
                        }
                    }
                    else
                    {
                        this.pourPos = null;
                    }
                }

                //Birch
                else if (this.pourPos != null && Helpers.isBlock(level.getBlockState(logPos), AFCTags.Blocks.BIRCH_LOGS))
                {
                    //TODO:
                    AFC.LOGGER.debug("Birch");
                    final BlockEntity blockEntity = level.getBlockEntity(this.pourPos);
                    //Needs to check if the block entity is removed every tick while pouring
                    if (blockEntity != null)
                    {
                        //TODO: Reference recipe
                        final FluidStack fluidStack = new FluidStack(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.BIRCH_SAP).getSource(), 1);

                        if (blockEntity.getCapability(Capabilities.FLUID, Direction.UP).map(cap ->
                            pour(cap, fluidStack)).orElse(false))
                        {
                            if (level.getGameTime() % 12 == 0 && level instanceof ServerLevel server)
                            {
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
                        else
                        {
                            this.pourPos = null;
                        }
                    }
                    else
                    {
                        this.pourPos = null;
                    }
                }
            }
        }
    }

    private boolean hasRecipe(Level level, BlockState state)
    {

        TapInventory inventory = new TapInventory(state, (BlockIngredient) state);

        Optional<TreeTapRecipe> match = level.getRecipeManager().getRecipeFor(TreeTapRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent())
        {
            AFC.LOGGER.debug("We're matching, I guess");
        } else {
            AFC.LOGGER.debug("No match");
        }
        return match.isPresent();
    }

}
