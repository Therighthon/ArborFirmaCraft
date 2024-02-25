package com.therighthon.afc.common.blocks;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
import com.therighthon.afc.common.AFCTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.DeviceBlock;
import net.dries007.tfc.util.Helpers;

public class TapBlock extends DeviceBlock
{
    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public TapBlock(ExtendedProperties properties)
    {
        super(properties, InventoryRemoveBehavior.NOOP);
    }

    protected static final VoxelShape NORTH_SHAPE = box(6.0, 0.0, 11.0, 10.0, 6.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE = box(6.0, 0.0, 0.0, 10.0, 6.0, 5.0);
    protected static final VoxelShape WEST_SHAPE = box(11.0, 0.0, 6.0, 16.0, 6.0, 10.0);
    protected static final VoxelShape EAST_SHAPE = box(0.0, 0.0, 6.0, 5.0, 6.0, 10.0);

    protected static final Map<Direction, VoxelShape> SHAPES = ImmutableMap.of(Direction.NORTH, NORTH_SHAPE, Direction.SOUTH, SOUTH_SHAPE, Direction.WEST, WEST_SHAPE, Direction.EAST, EAST_SHAPE);

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState attachedState = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
        return Helpers.isBlock(attachedState, AFCTags.Blocks.TAPPABLE_LOGS);
    }

    /* FACING */

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        if (direction.getAxis() != Direction.Axis.Y)
        {
            return defaultBlockState().setValue(FACING, direction);
        }
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    /* BLOCK ENTITY */
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}
