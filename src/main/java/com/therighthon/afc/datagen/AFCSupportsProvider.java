package com.therighthon.afc.datagen;

import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.util.data.Support;

public class AFCSupportsProvider extends DataManagerProvider<Support>
{
    public AFCSupportsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(Support.MANAGER, output, lookup);
    }

    @Override
    protected void addData(HolderLookup.Provider provider)
    {
        add("afc_horizontal_support_beam", new Support(BlockIngredient.of(
            Arrays.stream(AFCWood.values()).map(w -> AFCBlocks.WOODS.get(w).get(Wood.BlockType.HORIZONTAL_SUPPORT).get())),
            2, 2, 4
        ));
    }
}
