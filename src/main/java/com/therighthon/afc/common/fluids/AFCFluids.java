package com.therighthon.afc.common.fluids;

import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class AFCFluids
{
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AFC.MOD_ID);
//TODO: Fluids
//    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
//    public static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");
//    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");

    public static final int ALPHA_MASK = 0xFF000000;

//    public static final Map<SimpleAFCFluid, FluidRegistryObject<ForgeFlowingFluid>> SIMPLE_AFC_FLUIDS = Helpers.mapOf(SimpleAFCFluid.class, fluid -> register(
//        fluid.getId(),
//        properties -> properties
//            .block(AFCBlocks.SIMPLE_AFC_FLUIDS.get(fluid))
//            .bucket(AFCItems.SIMPLE_AFC_FLUID_BUCKETS.get(fluid)), //Not quite how TFC implements it, TFC uses a FluidId enum
//        waterLike()
//            .descriptionId("fluid.afc." + fluid.getId())
//            .canConvertToSource(false),
//        new FluidTypeClientProperties(fluid.isTransparent() ? ALPHA_MASK | fluid.getColor() : fluid.getColor(), WATER_STILL, WATER_FLOW, WATER_OVERLAY, null),
//        MixingFluid.Source::new,
//        MixingFluid.Flowing::new
//    ));
//
//    private static FluidType.Properties waterLike()
//    {
//        return FluidType.Properties.create()
//            .adjacentPathType(BlockPathTypes.WATER)
//            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
//            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
//            .canConvertToSource(true)
//            .canDrown(true)
//            .canExtinguish(true)
//            .canHydrate(false)
//            .canPushEntity(true)
//            .canSwim(true)
//            .supportsBoating(true);
//    }
//
//    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
//    {
//        // Names `metal/foo` to `metal/flowing_foo`
//        final int index = name.lastIndexOf('/');
//        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);
//
//        return RegistrationHelpers.registerFluid(TFCFluids.FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
//    }

}
