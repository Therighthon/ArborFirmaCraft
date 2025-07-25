package com.therighthon.afc.common.blockentities;

import com.eerussianguy.firmalife.FirmaLife;
import com.eerussianguy.firmalife.common.blockentities.BarrelPressBlockEntity;
import com.eerussianguy.firmalife.common.blockentities.StompingBarrelBlockEntity;
import com.eerussianguy.firmalife.common.blocks.FLBlocks;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.FLCompatBlocks;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class FLCompatBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FirmaLife.MOD_ID);

    public static final RegistryObject<BlockEntityType<StompingBarrelBlockEntity>> STOMPING_BARREL = register("stomping_barrel", StompingBarrelBlockEntity::new, Stream.of(FLCompatBlocks.STOMPING_BARRELS.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten));
    public static final RegistryObject<BlockEntityType<BarrelPressBlockEntity>> BARREL_PRESS = register("barrel_press", BarrelPressBlockEntity::new, Stream.of(FLCompatBlocks.BARREL_PRESSES.values()).<Supplier<? extends Block>>flatMap(Helpers::flatten));


    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }

    public static void register (IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
