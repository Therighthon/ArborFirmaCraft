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

import net.dries007.tfc.common.blockentities.LogPileBlockEntity;
import net.dries007.tfc.common.blockentities.TFCSignBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.AxleBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.BladedAxleBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.ClutchBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.EncasedAxleBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.GearBoxBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.HandWheelBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.WaterWheelBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.WindmillBlockEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import static net.dries007.tfc.TerraFirmaCraft.*;

public class AFCBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AFC.MOD_ID);

    public static final RegistryObject<BlockEntityType<TapBlockEntity>> TAP_BLOCK_ENTITY = register("tap_block_entity", TapBlockEntity::new, AFCBlocks.TREE_TAP);

    public static final RegistryObject<BlockEntityType<AFCSignBlockEntity>> SIGN = register("sign", AFCSignBlockEntity::new, Stream.concat(
        woodBlocks(Wood.BlockType.SIGN),
        woodBlocks(Wood.BlockType.WALL_SIGN)
    ));

    public static final RegistryObject<BlockEntityType<AxleBlockEntity>> AXLE = register("axle", AxleBlockEntity::new, woodBlocks(Wood.BlockType.AXLE));
    public static final RegistryObject<BlockEntityType<BladedAxleBlockEntity>> BLADED_AXLE = register("bladed_axle", BladedAxleBlockEntity::new, woodBlocks(Wood.BlockType.BLADED_AXLE));
    public static final RegistryObject<BlockEntityType<AxleBlockEntity>> CLUTCH = register("clutch", ClutchBlockEntity::new, woodBlocks(Wood.BlockType.CLUTCH));
    public static final RegistryObject<BlockEntityType<EncasedAxleBlockEntity>> ENCASED_AXLE = register("encased_axle", EncasedAxleBlockEntity::new, woodBlocks(Wood.BlockType.ENCASED_AXLE));
    public static final RegistryObject<BlockEntityType<GearBoxBlockEntity>> GEAR_BOX = register("gear_box", GearBoxBlockEntity::new, woodBlocks(Wood.BlockType.GEAR_BOX));
    public static final RegistryObject<BlockEntityType<WindmillBlockEntity>> WINDMILL = register("windmill", WindmillBlockEntity::new, woodBlocks(Wood.BlockType.WINDMILL));
    public static final RegistryObject<BlockEntityType<WaterWheelBlockEntity>> WATER_WHEEL = register("water_wheel", WaterWheelBlockEntity::new, woodBlocks(Wood.BlockType.WATER_WHEEL));


    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }

    public static void register (IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static Stream<? extends Supplier<? extends Block>> woodBlocks(Wood.BlockType type)
    {
        return AFCBlocks.WOODS.values().stream().map(map -> map.get(type));
    }
}
