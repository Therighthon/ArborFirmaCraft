package com.therighthon.afc.common.blocks;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.items.AFCItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCItemGroup;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryWood;

public class AFCBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, AFC.MOD_ID);

    private static<T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab)
    {
        return AFCItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }


    public static final Map<AFCWood, Map<Wood.BlockType, RegistryObject<Block>>> WOODS = Helpers.mapOfKeys(AFCWood.class, wood ->
        Helpers.mapOfKeys(Wood.BlockType.class, type ->
            register(type.nameFor(wood), type.create(wood), type.createBlockItem(new Item.Properties().tab(TFCItemGroup.WOOD)))
        )
    );

    public static final Map<TreeSpecies, Map<TreeSpecies.BlockType, RegistryObject<Block>>> TREE_SPECIES = Helpers.mapOfKeys(TreeSpecies.class, wood ->
        Helpers.mapOfKeys(TreeSpecies.BlockType.class, type ->
            register((String) type.nameFor(wood), createTreeSpecies(wood, type), type.createBlockItem(new Item.Properties().tab(TFCItemGroup.WOOD)))
        )
    );

    public static Supplier<Block> createTreeSpecies(TreeSpecies wood, TreeSpecies.BlockType blockType)
    {
        return blockType.create(wood);
    }

    public static final Map<UniqueLogs, Map<UniqueLogs.BlockType, RegistryObject<Block>>> UNIQUE_LOGS = Helpers.mapOfKeys(UniqueLogs.class, wood ->
        Helpers.mapOfKeys(UniqueLogs.BlockType.class, type ->
            register(type.nameFor(wood), createUniqueLogs(wood, type), type.createBlockItem(new Item.Properties().tab(TFCItemGroup.WOOD)))
        )
    );

    public static Supplier<Block> createUniqueLogs(UniqueLogs wood, UniqueLogs.BlockType blockType)
    {
        return blockType.create(wood);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier) {
        return register(name, blockSupplier, (Function)null);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, CreativeModeTab group) {
        return register(name, blockSupplier, (block) -> {
            return new BlockItem(block, (new Item.Properties()).tab(group));
        });
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties) {
        return register(name, blockSupplier, (block) -> {
            return new BlockItem(block, blockItemProperties);
        });
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory) {
        return RegistrationHelpers.registerBlock(BLOCKS, AFCItems.ITEMS, name, blockSupplier, blockItemFactory);
    }

    private static ExtendedProperties woodProperties(RegistryWood wood)
    {
        return ExtendedProperties.of(Material.WOOD, wood.woodColor()).sound(SoundType.WOOD);
    }

    public static void registerFlowerPotFlowers()
    {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        WOODS.forEach((wood, map) -> pot.addPlant(map.get(Wood.BlockType.SAPLING).getId(), map.get(Wood.BlockType.POTTED_SAPLING)));
        TREE_SPECIES.forEach((wood, map) -> pot.addPlant(map.get(TreeSpecies.BlockType.SAPLING).getId(), map.get(TreeSpecies.BlockType.POTTED_SAPLING)));
    }

    public static final RegistryObject<Block> TREE_TAP = registerBlock("tree_tap",
        () -> new TapBlock(
            BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL_FAN).noOcclusion()), TFCItemGroup.MISC);


}




