package com.therighthon.afc.datagen;

import com.therighthon.afc.AFC;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeItemTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.dries007.tfc.util.Helpers;

public class BuiltInItemTags extends TagsProvider<Item>
{
    private static final Field TAGS_TO_COPY = Helpers.uncheck(() -> {
        final Field field = ItemTagsProvider.class.getDeclaredField("tagsToCopy");
        field.setAccessible(true);
        return field;
    });

    private final ExistingFileHelper.IResourceType resourceType;
    private final Function<HolderLookup.Provider, ItemTagsProvider> vanillaItemTags;
    private final Function<HolderLookup.Provider, ItemTagsProvider> neoItemTags;
    private final CompletableFuture<TagsProvider.TagLookup<Block>> blockTags;
    private final Map<TagKey<Block>, TagKey<Item>> tagsToCopy = new HashMap<>();

    public BuiltInItemTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<TagLookup<Block>> blockTags)
    {
        super(event.getGenerator().getPackOutput(), Registries.ITEM, lookup, AFC.MOD_ID, event.getExistingFileHelper());
        this.blockTags = blockTags;
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
        this.vanillaItemTags = provider -> new VanillaItemTagsProvider(event.getGenerator().getPackOutput(), lookup, blockTags)
        {{
            addTags(provider);
        }};
        this.neoItemTags = provider -> {
            final var tags = new NeoForgeItemTagsProvider(event.getGenerator().getPackOutput(), lookup, blockTags, event.getExistingFileHelper());
            tags.addTags(provider);
            return tags;
        };
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {

    }
}
