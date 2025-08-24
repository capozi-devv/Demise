package net.capozi.demise.mixin;

import net.capozi.demise.TrinketsHelper;
import net.capozi.demise.common.GameruleRegistry;
import net.capozi.demise.common.entity.EntityTypeRegistry;
import net.capozi.demise.common.entity.PlayerRemainsEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"
            )
    )
    private void grimoire$onDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!((Object)this instanceof PlayerEntity player)) return;

        if (player.getWorld().getGameRules().getBoolean(GameruleRegistry.CREATE_GRAVE)) {
            if (player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

            PlayerRemainsEntity remains = new PlayerRemainsEntity(EntityTypeRegistry.PLAYER_REMAINS_TYPE, player.getWorld());
            remains.setPosition(player.getPos());
            remains.resetInventory();

            player.getInventory().main.forEach(stack -> remains.addInventoryStack(stack.copy()));
            player.getInventory().offHand.forEach(stack -> remains.addInventoryStack(stack.copy()));
            player.getInventory().armor.forEach(stack -> remains.addInventoryStack(stack.copy()));

            if (player.getWorld().getGameRules().getBoolean(GameruleRegistry.SAVE_TRINKETS) &&
                    FabricLoader.getInstance().isModLoaded("trinkets")) {
                try {
                    TrinketsHelper.findAllEquippedBy(player).forEach(remains::addInventoryStack);
                    TrinketsHelper.clearAllEquippedTrinkets(player);
                } catch (Exception ignore) {}
            }
            remains.setCustomName(player.getDisplayName());
            remains.setCustomNameVisible(true);
            player.getWorld().spawnEntity(remains);
            player.getInventory().clear();
        }
    }
}

