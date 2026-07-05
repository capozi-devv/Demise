package net.capozi.demise.mixin.access;

import net.minecraft.entity.EntityEquipment;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerInventory.class)
public interface PlayerInventoryAccessor {
    @Accessor("equipment")
    EntityEquipment equipment();
}
