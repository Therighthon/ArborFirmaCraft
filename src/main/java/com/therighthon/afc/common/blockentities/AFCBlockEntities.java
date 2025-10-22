package com.therighthon.afc.common.blockentities;

import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryHolder;

public class AFCBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AFC.MOD_ID);

    public static final Id<TapBlockEntity> TAP_BLOCK_ENTITY = register("tap_block_entity", TapBlockEntity::new, AFCBlocks.TREE_TAP);

    private static <T extends BlockEntity> Id<T> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return new Id<>(RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block));
    }

    private static <T extends BlockEntity> Id<T> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return new Id<>(RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks));
    }

    private static Stream<? extends Supplier<? extends Block>> afcWoodBlocks(Wood.BlockType type)
    {
        return AFCBlocks.WOODS.values().stream().map(map -> map.get(type));
    }

    public record Id<T extends BlockEntity>(DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> holder)
        implements RegistryHolder<BlockEntityType<?>, BlockEntityType<T>> {}
}
