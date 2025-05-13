package net.capozi.demise.mixin;

import net.capozi.demise.common.GameruleRegistry;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    public void dropInventory(CallbackInfo ci) {
        if(((PlayerEntity)(Object)this).getWorld().getGameRules().getBoolean(GameruleRegistry.CREATE_GRAVE)) ci.cancel();
    }
}
