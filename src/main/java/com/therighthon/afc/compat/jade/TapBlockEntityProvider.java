package com.therighthon.afc.compat.jade;

import com.therighthon.afc.common.blockentities.TapBlockEntity;
import com.therighthon.afc.common.blocks.TapBlock;
import com.therighthon.afc.common.recipe.TreeTapRecipe;

import net.dries007.tfc.util.climate.Climate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.config.IPluginConfig;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Jade tooltip provider for Tree Tap blocks.
 * Displays detailed information about tapping conditions, efficiency, and output.
 */
public enum TapBlockEntityProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final ResourceLocation UID = new ResourceLocation("afc", "tap");

    TapBlockEntityProvider() {}

    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        TapBlockEntity tap = (TapBlockEntity) accessor.getBlockEntity();
        Level level = accessor.getLevel();

        BlockPos tapBlockPos = tap.getBlockPos();
        BlockState tapBlockState = tap.getBlockState();
        Direction tapDirection = tapBlockState.getValue(TapBlock.FACING);

        BlockPos logPos = this.getLogPos(tapBlockPos, tapDirection);
        BlockState logState = level.getBlockState(logPos);

        TreeTapRecipe recipe = TreeTapRecipe.getRecipe(logState);

        CompoundTag compound = new CompoundTag();

        if (recipe != null) {
            FluidStack fluid = recipe.getOutput();
            if (!fluid.isEmpty()) {
                CompoundTag fluidTag = new CompoundTag();
                fluid.writeToNBT(fluidTag);
                compound.put("Fluid", fluidTag);
            }

            float currentTemp = Climate.getTemperature(level, tapBlockPos);

            int totalTapCount = countTapsOnTree(level, logPos);

            float efficiency = 1.0F / (float) Math.max(1, totalTapCount);

            compound.putFloat("MinTemp", recipe.getMinTemp());
            compound.putFloat("MaxTemp", recipe.getMaxTemp());
            compound.putBoolean("IsSpring", TapBlockEntity.isSpring(level));
            compound.putBoolean("SpringOnly", recipe.springOnly());
            compound.putFloat("CurrentTemp", currentTemp);
            compound.putFloat("Efficiency", efficiency);
        }

        tag.put("TreeTapData", compound);

    }

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        CompoundTag treeTapData = accessor.getServerData().getCompound("TreeTapData");

        if (treeTapData.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.afc.tap.no_recipe").withStyle(ChatFormatting.RED));
            return;
        }

        FluidStack fluid = FluidStack.loadFluidStackFromNBT(treeTapData.getCompound("Fluid"));

        float minTemp = treeTapData.getFloat("MinTemp");
        float maxTemp = treeTapData.getFloat("MaxTemp");
        boolean isSpring = treeTapData.getBoolean("IsSpring");
        boolean springOnly = treeTapData.getBoolean("SpringOnly");
        float currentTemp = treeTapData.getFloat("CurrentTemp");
        float efficiency = treeTapData.getFloat("Efficiency");

        tooltip.add(Component.translatable("tooltip.afc.tap.fluid").withStyle(ChatFormatting.GOLD).append(fluid.getDisplayName().copy().withStyle(ChatFormatting.GRAY)));
        tooltip.add(Component.translatable("tooltip.afc.tap.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(fluid.getAmount() + " mB").withStyle(ChatFormatting.BLUE)));
        tooltip.add(Component.translatable("tooltip.afc.tap.temp_range").withStyle(ChatFormatting.GOLD).append(Component.literal(String.format("%.1f°C", minTemp)).withStyle(ChatFormatting.BLUE))
                .append(Component.literal(" - ").withStyle(ChatFormatting.GRAY)).append(Component.literal(String.format("%.1f°C", maxTemp)).withStyle(ChatFormatting.RED)));

        if (springOnly) {
            tooltip.add(Component.translatable("tooltip.afc.tap.spring").withStyle(ChatFormatting.GOLD)
                    .append(Component.literal(isSpring ? "Yes" : "No").withStyle(isSpring ? ChatFormatting.GREEN : ChatFormatting.RED)));
        }

        boolean isOkayTemp = (minTemp <= currentTemp && currentTemp <= maxTemp);

        tooltip.add(Component
                .translatable("tooltip.afc.tap.current_temp",
                        new Object[] { Component.literal(String.format("%.1f°C", currentTemp)).withStyle(isOkayTemp ? ChatFormatting.WHITE : ChatFormatting.GRAY) })
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.afc.tap.efficiency").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(String.format("%.2f", efficiency)).withStyle(efficiency >= 1.0F ? ChatFormatting.GREEN : ChatFormatting.RED)));
    }

    /**
     * Counts all taps connected to the same tree by scanning all connected log blocks.
     * Uses breadth-first search to find all logs of the same type connected to the starting position.
     * 
     * @param level The world level
     * @param startPos Starting log position
     * @return Total number of taps found on this tree
     */
    private int countTapsOnTree(Level level, BlockPos startPos) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        BlockState logState = level.getBlockState(startPos);
        int tapCount = 0;

        // Start BFS from the initial log position
        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();

            // Check for taps around the current log block (all 6 directions)
            for (Direction dir : Direction.values()) {
                BlockPos tapPos = currentPos.relative(dir);
                BlockState tapState = level.getBlockState(tapPos);
                if (tapState.getBlock() instanceof TapBlock) {
                    tapCount++;
                }
            }

            // Search for connected log blocks of the same type
            for (Direction dir : Direction.values()) {
                BlockPos nextPos = currentPos.relative(dir);
                if (!visited.contains(nextPos) && level.getBlockState(nextPos).is(logState.getBlock())) {
                    visited.add(nextPos);
                    queue.add(nextPos);
                }
            }
        }
        return tapCount;
    }

    /**
     * Gets the log position based on the tap's facing direction.
     * The tap faces towards the log it's attached to.
     * 
     * @param tapPos Position of the tap block
     * @param facing Direction the tap is facing
     * @return Position of the log block the tap is attached to
     */
    private BlockPos getLogPos(BlockPos tapPos, Direction facing) {
        // The tap faces towards the log, so we move in the opposite direction to find the log
        return tapPos.relative(facing.getOpposite());
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
} 
