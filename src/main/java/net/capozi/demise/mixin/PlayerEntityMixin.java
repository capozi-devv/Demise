package net.capozi.demise.mixin;

import net.capozi.demise.common.GameruleRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.rule.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    public void demise$dropInventory(CallbackInfo ci) {
        if((Object)this instanceof PlayerEntity player) {
            if (player.getEntityWorld() instanceof ServerWorld world) {
                if (world.getGameRules().getValue(GameRules.KEEP_INVENTORY)) return;
                if (world.getGameRules().getValue(GameruleRegistry.CREATE_GRAVE)) {
                    ci.cancel();
                }
            }
        }
    }
}

