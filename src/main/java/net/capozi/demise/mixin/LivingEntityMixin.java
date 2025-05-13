package net.capozi.demise.mixin;

import net.capozi.demise.common.GameruleRegistry;
import net.capozi.demise.common.TwoHanded;
import net.capozi.demise.common.entity.EntityTypeRegistry;
import net.capozi.demise.common.entity.PlayerRemainsEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected abstract void consumeItem();

    @Inject(method = "getOffHandStack", at = @At("HEAD"), cancellable = true)
    public void grimoire$getOffHandStack(CallbackInfoReturnable<ItemStack> cir) {
        if((LivingEntity)(Object)this instanceof PlayerEntity player) {
            if(player.getMainHandStack().getItem() instanceof TwoHanded) cir.setReturnValue(new ItemStack(Items.AIR));
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void grimoire$onDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(((LivingEntity)(Object)this).getWorld().getGameRules().getBoolean(GameruleRegistry.CREATE_GRAVE) && (Object)this instanceof PlayerEntity player) {
            if(player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

            PlayerRemainsEntity playerRemainsEntity = new PlayerRemainsEntity(EntityTypeRegistry.PLAYER_REMAINS_TYPE, player.world);
            playerRemainsEntity.setPosition(player.getPos());

            playerRemainsEntity.resetInventory();

            for(int i = 0;i<player.getInventory().main.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().main.get(i).copy());
            }
            for(int i = 0;i<player.getInventory().offHand.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().offHand.get(i).copy());
            }

            for(int i = 0;i<player.getInventory().armor.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().armor.get(i).copy());
            }

            if(((LivingEntity)(Object)this).getWorld().getGameRules().getBoolean(GameruleRegistry.SAVE_TRINKETS)) {
                if(FabricLoader.getInstance().isModLoaded("trinkets")) {
                    try{
                        TrinketsHelper.findAllEquippedBy(player).forEach(playerRemainsEntity::addInventoryStack);
                        TrinketsHelper.clearAllEquippedTrinkets(player);
                    } catch (Exception ingore) {}
                }
            }

            playerRemainsEntity.setCustomName(player.getDisplayName());
            playerRemainsEntity.setCustomNameVisible(true);

            player.world.spawnEntity(playerRemainsEntity);
            player.getInventory().clear();
        }
    }
}
