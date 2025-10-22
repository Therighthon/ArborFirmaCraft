package com.therighthon.afc.mixin;

import com.therighthon.afc.client.render.blockentities.AFCHangingSignBlockEntityRenderer;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(HangingSignEditScreen.class)
public abstract class HangingSignEditScreenMixin
{
    @Mutable
    @Final
    @Shadow
    private ResourceLocation texture;

    @Inject(method = "<init>(Lnet/minecraft/world/level/block/entity/SignBlockEntity;ZZ)V", at = @At("TAIL"))
    public void inject$constructor(SignBlockEntity signBlockEntity, boolean isFrontText, boolean filter, CallbackInfo ci)
    {
        final var modelData = AFCHangingSignBlockEntityRenderer.MODELS.get(signBlockEntity.getBlockState().getBlock());
        if (modelData != null)
        {
            this.texture = modelData.textureLocation();
        }
    }
}

