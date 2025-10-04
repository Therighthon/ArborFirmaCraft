package com.therighthon.afc.datagen;

import com.therighthon.afc.AFC;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class BuiltInBlockTags extends TagsProvider<Block>
{
    private final ExistingFileHelper.IResourceType resourceType;

    public BuiltInBlockTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(event.getGenerator().getPackOutput(), Registries.BLOCK, lookup, AFC.MOD_ID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }
    @Override
    protected void addTags(HolderLookup.Provider provider)
    {

    }

}
