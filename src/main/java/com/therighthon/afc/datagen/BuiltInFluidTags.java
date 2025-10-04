package com.therighthon.afc.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.dries007.tfc.TerraFirmaCraft;

public class BuiltInFluidTags extends TagsProvider<Fluid>
{
    public BuiltInFluidTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(event.getGenerator().getPackOutput(), Registries.FLUID, provider, TerraFirmaCraft.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {

    }
}
