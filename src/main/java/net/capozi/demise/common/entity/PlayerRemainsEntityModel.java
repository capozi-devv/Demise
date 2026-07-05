package net.capozi.demise.common.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class PlayerRemainsEntityModel<T extends PlayerRemainsEntity> extends EntityModel<PlayerRemainsEntityRenderState> {
    protected PlayerRemainsEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(null, 8, 8);
    }
}
