package com.therighthon.afc.common.blocks;

import com.therighthon.afc.common.blockentities.AFCBlockEntities;
import com.therighthon.afc.common.blockentities.TapBlockEntity;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.fluids.SimpleAFCFluid;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.items.AFCItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.TFCCeilingHangingSignBlock;
import net.dries007.tfc.common.blocks.wood.TFCStandingSignBlock;
import net.dries007.tfc.common.blocks.wood.TFCWallHangingSignBlock;
import net.dries007.tfc.common.blocks.wood.TFCWallSignBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.fluids.SimpleFluid;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class AFCBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(Registries.BLOCK, AFC.MOD_ID);

    // These are separate because they make the BlockLootProvider very angry when I try to pass them in
    public static final DeferredRegister<Block> FLUID_BLOCKS =
        DeferredRegister.create(Registries.BLOCK, AFC.MOD_ID);

    public static final Map<AFCWood, Map<Metal, TFCBlocks.Id<TFCCeilingHangingSignBlock>>> CEILING_HANGING_SIGNS = registerHangingSigns("hanging_sign", TFCCeilingHangingSignBlock::new);
    public static final Map<AFCWood, Map<Metal, TFCBlocks.Id<TFCWallHangingSignBlock>>> WALL_HANGING_SIGNS = registerHangingSigns("wall_hanging_sign", TFCWallHangingSignBlock::new);

    public static final Map<AFCWood, Map<Wood.BlockType, TFCBlocks.Id<Block>>> WOODS = Helpers.mapOf(AFCWood.class, wood ->
        Helpers.mapOf(Wood.BlockType.class, type ->
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

    public static final Map<TreeSpecies, Map<TreeSpecies.BlockType, TFCBlocks.Id<Block>>> TREE_SPECIES = Helpers.mapOf(TreeSpecies.class, wood ->
        Helpers.mapOf(TreeSpecies.BlockType.class, type ->
            register((String) type.nameFor(wood), createTreeSpecies(wood, type), type.createBlockItem(new Item.Properties()))
        )
    );

    public static Supplier<Block> createTreeSpecies(TreeSpecies wood, TreeSpecies.BlockType blockType)
    {
        return blockType.create(wood);
    }

    public static final Map<UniqueLogs, Map<UniqueLogs.BlockType, TFCBlocks.Id<Block>>> UNIQUE_LOGS = Helpers.mapOf(UniqueLogs.class, wood ->
        Helpers.mapOf(UniqueLogs.BlockType.class, type ->
            register(type.nameFor(wood), createUniqueLogs(wood, type), type.createBlockItem(new Item.Properties()))
        )
    );

    public static Supplier<Block> createUniqueLogs(UniqueLogs wood, UniqueLogs.BlockType blockType)
    {
        return blockType.create(wood);
    }

    public static final Map<AncientLogs, Map<AncientLogs.BlockType, TFCBlocks.Id<Block>>> ANCIENT_LOGS = Helpers.mapOf(AncientLogs.class, wood ->
        Helpers.mapOf(AncientLogs.BlockType.class, type ->
            register(type.nameFor(wood), createAncientLogs(wood, type), type.createBlockItem(new Item.Properties()))
        )
    );

    public static Supplier<Block> createAncientLogs(AncientLogs wood, AncientLogs.BlockType blockType)
    {
        return blockType.create(wood);
    }

    public static void registerFlowerPotFlowers()
    {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        WOODS.forEach((wood, map) -> pot.addPlant(map.get(Wood.BlockType.SAPLING).getId(), map.get(Wood.BlockType.POTTED_SAPLING)));
        TREE_SPECIES.forEach((wood, map) -> pot.addPlant(map.get(TreeSpecies.BlockType.SAPLING).getId(), map.get(TreeSpecies.BlockType.POTTED_SAPLING)));
    }

    public static final TFCBlocks.Id<Block> TREE_TAP = register("tree_tap",
        () -> new TapBlock(
            ExtendedProperties.of(Blocks.BRAIN_CORAL_FAN).noOcclusion().blockEntity(AFCBlockEntities.TAP_BLOCK_ENTITY).serverTicks(TapBlockEntity::serverTick)
        ));

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    protected static <T extends Block> TFCBlocks.Id<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> TFCBlocks.Id<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> TFCBlocks.Id<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new TFCBlocks.Id<>(RegistrationHelpers.registerBlock(AFCBlocks.BLOCKS, AFCItems.ITEMS, name, blockSupplier, blockItemFactory));
    }

    private static <B extends SignBlock> Map<AFCWood, Map<Metal, TFCBlocks.Id<B>>> registerHangingSigns(String variant, BiFunction<ExtendedProperties, WoodType, B> factory)
    {
        return Helpers.mapOf(AFCWood.class, wood ->
            Helpers.mapOf(Metal.class, Metal::allParts, metal -> register(
                "wood/planks/" + variant + "/" + metal.getSerializedName() + "/" + wood.getSerializedName(),
                () -> factory.apply(ExtendedProperties.of(wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(1F).flammableLikePlanks().blockEntity(AFCBlockEntities.HANGING_SIGN).ticks(SignBlockEntity::tick), wood.getVanillaWoodType()),
                (Function<B, BlockItem>) null)
            )
        );
    }

    public static final Map<SimpleAFCFluid, TFCBlocks.Id<LiquidBlock>> SIMPLE_FLUIDS = Helpers.mapOf(SimpleAFCFluid.class, fluid ->
        registerFluidNoItem("fluid/" + fluid.getId(), () -> new LiquidBlock(AFCFluids.SIMPLE_AFC_FLUIDS.get(fluid).getSource(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()))
    );

    private static <T extends Block> TFCBlocks.Id<T> registerFluidNoItem(String name, Supplier<T> blockSupplier)
    {
        return registerFluid(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> TFCBlocks.Id<T> registerFluid(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new TFCBlocks.Id<>(RegistrationHelpers.registerBlock(AFCBlocks.FLUID_BLOCKS, AFCItems.ITEMS, name, blockSupplier, blockItemFactory));
    }
}




