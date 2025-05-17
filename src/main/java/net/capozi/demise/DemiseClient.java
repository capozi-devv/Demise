package net.capozi.demise;

import net.capozi.demise.common.entity.EntityTypeRegistry;
import net.capozi.demise.common.entity.PlayerRemainsEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DemiseClient implements ClientModInitializer {
     @Override
     public void onInitializeClient() {
          EntityRendererRegistry.register(EntityTypeRegistry.PLAYER_REMAINS_TYPE, PlayerRemainsEntityRenderer::new);
     }
}
