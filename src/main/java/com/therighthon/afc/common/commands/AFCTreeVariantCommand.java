package com.therighthon.afc.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.TreeSpecies;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.server.command.EnumArgument;

import net.dries007.tfc.world.feature.tree.TFCTreeGrower;


//Copies from TFC TreeCommand.java
public final class AFCTreeVariantCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> create()
    {
        return Commands.literal("species")
            .requires(source -> source.hasPermission(2))
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .then(Commands.argument("wood", EnumArgument.enumArgument(TreeSpecies.class))
                    .then(Commands.argument("variant", EnumArgument.enumArgument(Variant.class))
                        .executes(context -> placeTree(context.getSource().getLevel(), BlockPosArgument.getLoadedBlockPos(context, "pos"), context.getArgument("wood", TreeSpecies.class), context.getArgument("variant", Variant.class)))
                    )
                    .executes(context -> placeTree(context.getSource().getLevel(), BlockPosArgument.getLoadedBlockPos(context, "pos"), context.getArgument("wood", TreeSpecies.class), Variant.NORMAL))
                )
            );
    }

    private static int placeTree(ServerLevel world, BlockPos pos, TreeSpecies wood, Variant variant)
    {
        TFCTreeGrower tree = wood.tree();
        Registry<ConfiguredFeature<?, ?>> registry = world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        ConfiguredFeature<?, ?> feature = variant == Variant.NORMAL ? tree.getNormalFeature(registry) : tree.getOldGrowthFeature(registry);
        feature.place(world, world.getChunkSource().getGenerator(), world.getRandom(), pos);
        return Command.SINGLE_SUCCESS;
    }

    private enum Variant
    {
        NORMAL, LARGE
    }
}
