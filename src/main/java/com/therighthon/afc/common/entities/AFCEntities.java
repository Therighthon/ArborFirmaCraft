package com.therighthon.afc.common.entities;

import com.therighthon.afc.AFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AFCEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, AFC.MOD_ID);

    //TODO: Boats
//    public static final Map<AFCWood, TFCEntities.Id<EntityType<TFCChestBoat>>> CHEST_BOATS = Helpers.mapOf(AFCWood.class, wood ->
//        register("chest_boat/" + wood.name(), EntityType.Builder.<TFCChestBoat>of((type, level) -> new TFCChestBoat(type, level, AFCItems.BOATS.get(wood)), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10))
//    );
//    public static final Map<AFCWood, TFCEntities.Id<EntityType<TFCBoat>>> BOATS = Helpers.mapOf(AFCWood.class, wood ->
//        register("boat/" + wood.name(), EntityType.Builder.<TFCBoat>of((type, level) -> new TFCBoat(type, level, CHEST_BOATS.get(wood), AFCItems.BOATS.get(wood)), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10))
//    );


//    public static <E extends Entity> TFCEntities.Id<EntityType<E>> register(String name, EntityType.Builder<E> builder)
//    {
//        return register(name, builder, true);
//    }
//
//    public static <E extends Entity> TFCEntities.Id<EntityType<E>> register(String name, EntityType.Builder<E> builder, boolean serialize)
//    {
//        final String id = name.toLowerCase(Locale.ROOT);
//        return ENTITIES.register(id, () -> {
//            if (!serialize) builder.noSave();
//            return builder.build(AFC.MOD_ID + ":" + id);
//        });
//    }
}
