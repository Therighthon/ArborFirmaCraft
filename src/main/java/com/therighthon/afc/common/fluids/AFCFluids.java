package com.therighthon.afc.common.fluids;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.items.AFCItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.fluids.FlowingFluidRegistryObject;
import net.dries007.tfc.common.fluids.FluidType;
import net.dries007.tfc.common.fluids.MixingFluid;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public final class AFCFluids
{
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, AFC.MOD_ID);

    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");

    public static final int ALPHA_MASK = 0xFF000000;

    public static final Map<SimpleAFCFluid, FlowingFluidRegistryObject<ForgeFlowingFluid>> SIMPLE_AFC_FLUIDS = Helpers.mapOfKeys(SimpleAFCFluid.class, fluid -> register(
        fluid.getSerializedName(),
        "flowing_" + fluid.getSerializedName(),
        properties -> properties.block(AFCBlocks.SIMPLE_AFC_FLUIDS.get(fluid)).bucket(AFCItems.SIMPLE_AFC_FLUID_BUCKETS.get(fluid)),
        FluidAttributes.builder(WATER_STILL, WATER_FLOW)
            .translationKey("fluid.afc." + fluid.getSerializedName())
            .color(fluid.isTransparent() ? ALPHA_MASK | fluid.getColor() : fluid.getColor())
            .overlay(WATER_OVERLAY)
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY),
        MixingFluid.Source::new,
        MixingFluid.Flowing::new
    ));

    private static <F extends FlowingFluid> FlowingFluidRegistryObject<F> register(String sourceName, String flowingName, Consumer<ForgeFlowingFluid.Properties> builder, FluidAttributes.Builder attributes, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        return RegistrationHelpers.registerFluid(FLUIDS, sourceName, flowingName, builder, attributes, sourceFactory, flowingFactory);
    }
}
