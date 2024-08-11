package com.therighthon.afc.common.blockentities;

import java.util.function.Supplier;
import java.util.stream.Stream;
//import com.eerussianguy.firmalife.common.blockentities.FLBlockEntities;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class AFCBlockEntities
{
    //TODO: Tree taps
//    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AFC.MOD_ID);
//
//    public static final TFCBlockEntities.Id<BlockEntityType<TapBlockEntity>> TAP_BLOCK_ENTITY = register("tap_block_entity", TapBlockEntity::new, AFCBlocks.TREE_TAP);
//
//    public static final TFCBlockEntities.Id<BlockEntityType<AFCSignBlockEntity>> SIGN = register("sign", AFCSignBlockEntity::new, Stream.concat(
//        afcWoodBlocks(Wood.BlockType.SIGN),
//        afcWoodBlocks(Wood.BlockType.WALL_SIGN)
//    ));
//    public static final TFCBlockEntities.Id<BlockEntityType<AFCHangingSignBlockEntity>> HANGING_SIGN = register("hanging_sign", AFCHangingSignBlockEntity::new, Stream.of(
//        AFCBlocks.CEILING_HANGING_SIGNS,
//        AFCBlocks.WALL_HANGING_SIGNS
//    ).flatMap(woodMap -> woodMap.values().stream().flatMap(metalMap -> metalMap.values().stream())));
//
//    private static <T extends BlockEntity> TFCBlockEntities.Id<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
//    {
//        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
//    }
//
//    public static void register (IEventBus eventBus) {
//        BLOCK_ENTITIES.register(eventBus);
//    }
//
//    private static <T extends BlockEntity> TFCBlockEntities.Id<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
//    {
//        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
//    }
//
//    private static Stream<? extends Supplier<? extends Block>> afcWoodBlocks(Wood.BlockType type)
//    {
//        return AFCBlocks.WOODS.values().stream().map(map -> map.get(type));
//    }
}
