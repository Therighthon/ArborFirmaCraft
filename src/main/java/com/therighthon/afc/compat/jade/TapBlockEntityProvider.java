package com.therighthon.afc.compat.jade;

import com.therighthon.afc.common.blockentities.TapBlockEntity;
import com.therighthon.afc.common.blocks.TapBlock;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Jade tooltip provider for Tree Tap blocks.
 * Displays detailed information about tapping conditions, efficiency, and output.
 */
public class TapBlockEntityProvider implements IBlockComponentProvider {
    public static final ResourceLocation UID = new ResourceLocation("afc", "tap");
    public static final TapBlockEntityProvider INSTANCE = new TapBlockEntityProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Level level = accessor.getLevel();
        BlockState state = accessor.getBlockState();
        BlockPos pos = accessor.getPosition();

        // Get the log position based on tap facing direction
        BlockPos logPos = getLogPos(pos, state.getValue(TapBlock.FACING));
        BlockState logState = level.getBlockState(logPos);
        
        // Check environmental conditions
        boolean isSpring = TapBlockEntity.isSpring(level);
        float currentTemp = Climate.getTemperature(level, pos);

        // Count all taps on this tree for efficiency calculation
        int totalTapCount = countTapsOnTree(level, logPos);
        float efficiency = 1.0f / Math.max(1, totalTapCount);

        // Get the tapping recipe for this log type
        TreeTapRecipe recipe = TreeTapRecipe.getRecipe(logState);
        if (recipe == null) {
            tooltip.add(Component.translatable("tooltip.afc.tap.no_recipe").withStyle(ChatFormatting.RED));
            return;
        }

        // Display fluid output information
        FluidStack outputFluid = recipe.getOutput();
        if (!outputFluid.isEmpty()) {
            tooltip.add(
                    Component.translatable("tooltip.afc.tap.fluid")
                            .withStyle(ChatFormatting.GOLD)
                            .append(outputFluid.getDisplayName().copy().withStyle(ChatFormatting.GRAY))
            );

            int amount = outputFluid.getAmount();
            tooltip.add(
                    Component.translatable("tooltip.afc.tap.amount")
                            .withStyle(ChatFormatting.GOLD)
                            .append(Component.literal(amount + " mB").withStyle(ChatFormatting.BLUE))
            );
        }

        // Display temperature requirements
        tooltip.add(
                Component.translatable("tooltip.afc.tap.temp_range")
                        .withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(String.format("%.1f°C", recipe.getMinTemp())).withStyle(ChatFormatting.BLUE))
                        .append(Component.literal(" - ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(String.format("%.1f°C", recipe.getMaxTemp())).withStyle(ChatFormatting.RED))
        );

        // Display spring requirement and current status
        if (recipe.springOnly()) {
            tooltip.add(Component.translatable("tooltip.afc.tap.spring")
                    .withStyle(ChatFormatting.GOLD)
                    .append(Component.literal(isSpring ? "Yes" : "No")
                            .withStyle(isSpring ? ChatFormatting.GREEN : ChatFormatting.RED)));
        }

        // Display current temperature
        tooltip.add(Component.translatable("tooltip.afc.tap.current_temp",
                Component.literal(String.format("%.1f°C", currentTemp))
                        .withStyle(ChatFormatting.GRAY))
                .withStyle(ChatFormatting.GOLD));

        // Display efficiency based on tap count
        tooltip.add(
                Component.translatable("tooltip.afc.tap.efficiency")
                        .withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(String.format("%.2f", efficiency))
                                .withStyle(efficiency >= 1.0f ? ChatFormatting.GREEN : ChatFormatting.RED))
        );
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