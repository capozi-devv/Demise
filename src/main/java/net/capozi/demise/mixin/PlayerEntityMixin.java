package net.capozi.demise.mixin;

import net.capozi.demise.common.GameruleRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    public void dropInventory(CallbackInfo ci) {
        if((Object)this instanceof PlayerEntity player) {
            if (player.getWorld().getGameRules().getBoolean(GameruleRegistry.CREATE_GRAVE)) {
                ci.cancel();
            }
        }
    }
}

