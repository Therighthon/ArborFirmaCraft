package com.therighthon.afc.common.commands;

import java.util.function.Supplier;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.therighthon.afc.AFC;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.DeferredRegister;

public class AFCCommands
{
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, AFC.MOD_ID);

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context)
    {
        // Register all new commands as sub commands of the `afc` root
        dispatcher.register(Commands.literal("afc")
            .then(AFCTreeCommand.create())
            .then(AFCTreeVariantCommand.create())
        );

    }

    public static <S extends SharedSuggestionProvider> Supplier<SuggestionProvider<S>> register(String id, SuggestionProvider<SharedSuggestionProvider> provider)
    {
        return Lazy.of(() -> SuggestionProviders.register(new ResourceLocation("afc", id), provider));
    }

}
