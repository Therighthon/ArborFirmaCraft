package com.therighthon.afc.common.entities;

import java.util.Locale;
import java.util.Map;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.items.AFCItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.entities.TFCBoat;
import net.dries007.tfc.util.Helpers;

public class AFCEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, AFC.MOD_ID);

    public static final Map<AFCWood, RegistryObject<EntityType<TFCBoat>>> BOATS = Helpers.mapOfKeys(AFCWood.class, wood ->
        register("boat/" + wood.name(), EntityType.Builder.<TFCBoat>of((type, level) -> new TFCBoat(type, level, AFCItems.BOATS.get(wood)), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10))
    );

    public static <E extends Entity> RegistryObject<EntityType<E>> register(String name, EntityType.Builder<E> builder)
    {
        return register(name, builder, true);
    }

    public static <E extends Entity> RegistryObject<EntityType<E>> register(String name, EntityType.Builder<E> builder, boolean serialize)
    {
        final String id = name.toLowerCase(Locale.ROOT);
        return ENTITIES.register(id, () -> {
            if (!serialize) builder.noSave();
            return builder.build(AFC.MOD_ID + ":" + id);
        });
    }
}
