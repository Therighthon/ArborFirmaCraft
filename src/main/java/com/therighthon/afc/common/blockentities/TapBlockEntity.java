package com.therighthon.afc.common.blockentities;

import java.util.Random;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.TapBlock;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.client.particle.FluidParticleOption;
import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.fluids.Alcohol;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.TFCFluids;
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
        if (this.pourPos != null && level.getGameTime() % 12 == 0)
        {
            final BlockEntity blockEntity = level.getBlockEntity(this.pourPos);
            //Needs to check if the block entity is removed every tick while pouring
            if (blockEntity != null)
            {
                //TODO: Reference recipe
                final FluidStack fluidStack = new FluidStack(TFCFluids.SALT_WATER.getSource(), 1);

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
