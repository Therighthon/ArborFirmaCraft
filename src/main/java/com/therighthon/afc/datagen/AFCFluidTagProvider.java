package com.therighthon.afc.datagen;

import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.afc.common.fluids.SimpleAFCFluid;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import static net.dries007.tfc.common.TFCTags.Fluids.*;

public class AFCFluidTagProvider extends FluidTagsProvider
{
    public AFCFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, provider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(DRINKABLES) // Automatically added to INGREDIENTS
            .add(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.MAPLE_SAP).getSource())
            .add(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.MAPLE_SAP_CONCENTRATE).getSource())
            .add(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.MAPLE_SYRUP).getSource())
            .add(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.BIRCH_SAP).getSource())
            .add(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.BIRCH_SAP_CONCENTRATE).getSource())
            .add(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.BIRCH_SYRUP).getSource());

        tag(INGREDIENTS) // Automatically added to usable in barrels
            .add(AFCFluids.SIMPLE_AFC_FLUIDS.get(SimpleAFCFluid.LATEX).getSource());
    }
}
