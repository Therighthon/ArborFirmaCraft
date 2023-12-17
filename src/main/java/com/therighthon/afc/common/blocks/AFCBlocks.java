package com.therighthon.afc.common.blocks;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blockentities.AFCBlockEntities;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.fluids.SimpleAFCFluid;
import com.therighthon.afc.common.items.AFCItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.TFCCeilingHangingSignBlock;
import net.dries007.tfc.common.blocks.wood.TFCStandingSignBlock;
import net.dries007.tfc.common.blocks.wood.TFCWallHangingSignBlock;
import net.dries007.tfc.common.blocks.wood.TFCWallSignBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.fluids.ExtendedFluidType;
import net.dries007.tfc.common.fluids.FluidId;
import net.dries007.tfc.common.fluids.FluidRegistryObject;
import net.dries007.tfc.common.fluids.FluidTypeClientProperties;
import net.dries007.tfc.common.fluids.MixingFluid;
import net.dries007.tfc.common.fluids.SimpleFluid;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryWood;

import static net.dries007.tfc.TerraFirmaCraft.*;

public class AFCBlocks
{
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, AFC.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, AFC.MOD_ID);

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, AFC.MOD_ID);
//TODO: Hanging signs
//    public static final Map<Wood, Map<Metal.Default, RegistryObject<AFCHangingSignBlock>>> HANGING_SIGNS = registerHangingSigns("hanging_sign", AFCHangingSignBlock::new);
//    public static final Map<Wood, Map<Metal.Default, RegistryObject<AFCWallHangingSignBlock>>> WALL_HANGING_SIGNS = registerHangingSigns("wall_hanging_sign", AFCWallHangingSignBlock::new);

    public static final Map<AFCWood, Map<Wood.BlockType, RegistryObject<Block>>> WOODS = Helpers.mapOfKeys(AFCWood.class, wood ->
        Helpers.mapOfKeys(Wood.BlockType.class, type ->
            register(type.nameFor(wood), createWood(wood, type), type.createBlockItem(wood, new Item.Properties()))

        )
    );

    public static Supplier<Block> createWood(AFCWood afcWood, Wood.BlockType blockType)
    {
        if (blockType == Wood.BlockType.SIGN)
        {
            return () -> new TFCStandingSignBlock(ExtendedProperties.of(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).flammableLikePlanks().blockEntity(AFCBlockEntities.SIGN), afcWood.getVanillaWoodType());
        }
        if (blockType == Wood.BlockType.WALL_SIGN)
        {
            return () -> new TFCWallSignBlock(ExtendedProperties.of(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(afcWood.getBlock(Wood.BlockType.SIGN)).flammableLikePlanks().blockEntity(AFCBlockEntities.SIGN), afcWood.getVanillaWoodType());
        }
        return blockType.create(afcWood);
    }

    public static final Map<TreeSpecies, Map<TreeSpecies.BlockType, RegistryObject<Block>>> TREE_SPECIES = Helpers.mapOfKeys(TreeSpecies.class, wood ->
        Helpers.mapOfKeys(TreeSpecies.BlockType.class, type ->
            register((String) type.nameFor(wood), createTreeSpecies(wood, type), type.createBlockItem(new Item.Properties()))
        )
    );

    public static Supplier<Block> createTreeSpecies(TreeSpecies wood, TreeSpecies.BlockType blockType)
    {
        return blockType.create(wood);
    }

    public static final Map<UniqueLogs, Map<UniqueLogs.BlockType, RegistryObject<Block>>> UNIQUE_LOGS = Helpers.mapOfKeys(UniqueLogs.class, wood ->
        Helpers.mapOfKeys(UniqueLogs.BlockType.class, type ->
            register(type.nameFor(wood), createUniqueLogs(wood, type), type.createBlockItem(new Item.Properties()))
        )
    );

    public static Supplier<Block> createUniqueLogs(UniqueLogs wood, UniqueLogs.BlockType blockType)
    {
        return blockType.create(wood);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, CreativeModeTab group) {
        return register(name, blockSupplier, (block) -> {
            return new BlockItem(block, (new Item.Properties()));
        });
    }

    public static void registerFlowerPotFlowers()
    {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        WOODS.forEach((wood, map) -> pot.addPlant(map.get(Wood.BlockType.SAPLING).getId(), map.get(Wood.BlockType.POTTED_SAPLING)));
        TREE_SPECIES.forEach((wood, map) -> pot.addPlant(map.get(TreeSpecies.BlockType.SAPLING).getId(), map.get(TreeSpecies.BlockType.POTTED_SAPLING)));
    }

    public static final RegistryObject<Block> TREE_TAP = register("tree_tap",
        () -> new TapBlock(
            BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL_FAN).noOcclusion()
        ));

    public static final Map<SimpleAFCFluid, RegistryObject<LiquidBlock>> SIMPLE_AFC_FLUIDS = Helpers.mapOfKeys(SimpleAFCFluid.class, fluid ->
        registerNoItem("fluid/" + fluid.getId(), () -> new LiquidBlock(AFCFluids.SIMPLE_AFC_FLUIDS.get(fluid).source(), BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()))
    );

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(AFCBlocks.BLOCKS, AFCItems.ITEMS, name, blockSupplier, blockItemFactory);
    }

    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        // Names `metal/foo` to `metal/flowing_foo`
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);

        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
    }

    private static FluidType.Properties waterLike()
    {
        return FluidType.Properties.create()
            .adjacentPathType(BlockPathTypes.WATER)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .canConvertToSource(true)
            .canDrown(true)
            .canExtinguish(true)
            .canHydrate(false)
            .canPushEntity(true)
            .canSwim(true)
            .supportsBoating(true);
    }

}




