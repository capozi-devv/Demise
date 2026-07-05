package net.capozi.demise.mixin;

import net.capozi.demise.common.GameruleRegistry;
import net.capozi.demise.common.entity.EntityTypeRegistry;
import net.capozi.demise.common.entity.PlayerRemainsEntity;
import net.capozi.demise.mixin.access.EntityEquipmentAccessor;
import net.capozi.demise.mixin.access.PlayerInventoryAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityEquipment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.rule.GameRules;
import org.spongepowered.asm.mixin.Mixin;
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
    private void demise$onDeath(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!((LivingEntity)(Object)this instanceof PlayerEntity player)) return;
        if (world.getGameRules().getValue(GameruleRegistry.CREATE_GRAVE)) {
            if (world.getGameRules().getValue(GameRules.KEEP_INVENTORY)) return;
            PlayerRemainsEntity remains = new PlayerRemainsEntity(EntityTypeRegistry.PLAYER_REMAINS_TYPE, player.getEntityWorld());
            remains.setPosition(player.getEntityPos());
            remains.resetInventory();
            player.getInventory().getMainStacks().forEach(stack -> remains.addInventoryStack(stack.copy()));
            EntityEquipment equipment = ((PlayerInventoryAccessor)player.getInventory()).equipment();
            ((EntityEquipmentAccessor)equipment).map().forEach((equipmentSlot, stack) -> remains.addInventoryStack(stack));
            remains.setCustomName(player.getDisplayName());
            remains.setCustomNameVisible(true);
            remains.player = player;
            player.getEntityWorld().spawnEntity(remains);
            player.getInventory().clear();
        }
    }
}

