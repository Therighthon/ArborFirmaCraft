package com.therighthon.afc.common.blockentities;

import java.util.function.Supplier;
import java.util.stream.Stream;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class AFCBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AFC.MOD_ID);

    public static final RegistryObject<BlockEntityType<TapBlockEntity>> TAP_BLOCK_ENTITY =
        BLOCK_ENTITIES.register("tap_block_entity", () ->
            BlockEntityType.Builder.of(TapBlockEntity::new,
                AFCBlocks.TREE_TAP.get()).build(null));

    public static final RegistryObject<BlockEntityType<AFCSignBlockEntity>> SIGN = register("sign", AFCSignBlockEntity::new, AFCBlocks.WOODS.values().stream().flatMap(map -> Stream.of(Wood.BlockType.SIGN, Wood.BlockType.WALL_SIGN).map(map::get)));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }

    public static void register (IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
