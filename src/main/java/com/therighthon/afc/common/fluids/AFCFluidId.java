package com.therighthon.afc.common.fluids;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.material.Fluid;

public record AFCFluidId(String name, OptionalInt color, Supplier<? extends Fluid> fluid)
{
    private static final Map<Enum<?>, AFCFluidId> IDENTITY = new HashMap<>();
    private static final List<AFCFluidId> VALUES = Stream.of(
            Arrays.stream(SimpleAFCFluid.values()).map(fluid -> fromEnum(fluid, fluid.getColor(), fluid.getId(), AFCFluids.SIMPLE_AFC_FLUIDS.get(fluid).source()))
        )
        .flatMap(Function.identity())
        .toList();

    public static <R> Map<AFCFluidId, R> mapOf(Function<? super AFCFluidId, ? extends R> map)
    {
        return VALUES.stream().collect(Collectors.toMap(Function.identity(), map));
    }

    public static AFCFluidId asType(Enum<?> identity)
    {
        return IDENTITY.get(identity);
    }

    private static AFCFluidId fromEnum(Enum<?> identity, int color, String name, Supplier<? extends Fluid> fluid)
    {
        final AFCFluidId type = new AFCFluidId(name, OptionalInt.of(AFCFluids.ALPHA_MASK | color), fluid);
        IDENTITY.put(identity, type);
        return type;
    }
}
