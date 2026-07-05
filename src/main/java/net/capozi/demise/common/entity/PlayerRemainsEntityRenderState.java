package net.capozi.demise.common.entity;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

public class PlayerRemainsEntityRenderState extends LivingEntityRenderState {
    public PlayerRemainsEntity remains;
    public final ItemRenderState itemRenderState = new ItemRenderState();
    public PlayerRemainsEntityRenderState() {
    }
}
