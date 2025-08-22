package net.capozi.demise.common.entity;

import net.capozi.demise.Demise;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class EntityTypeRegistry {
    public static RegistryKey<EntityType<?>> key;
    //public static final EntityModelLayer PLAYER_REMAINS_MODEL_LAYER = new EntityModelLayer(Grimoire.id("player_remains"), "main");
    public static final EntityType<PlayerRemainsEntity> PLAYER_REMAINS_TYPE = Registry.register(Registries.ENTITY_TYPE,
            Demise.id("player_remains"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlayerRemainsEntity::new).dimensions(EntityDimensions.changing(0.8f, 0.8f)).build(key));

    public static void register() {
        FabricDefaultAttributeRegistry.register(PLAYER_REMAINS_TYPE, PlayerRemainsEntity.createAttributes());
    }
}
