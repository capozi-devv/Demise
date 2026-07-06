package net.capozi.demise;

import net.capozi.demise.common.entity.EntityTypeRegistry;
import net.capozi.demise.common.entity.PlayerRemainsEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.fabricmc.fabric.mixin.client.rendering.EntityRenderersMixin;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class DemiseClient implements ClientModInitializer {
     @Override
     public void onInitializeClient() {
         EntityRendererRegistryImpl.register(EntityTypeRegistry.PLAYER_REMAINS_TYPE, PlayerRemainsEntityRenderer::new);
     }
}
