package net.capozi.demise.common.entity;

import com.mojang.authlib.GameProfile;
import net.capozi.demise.common.GameruleRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import static java.lang.Math.abs;
import static java.lang.Math.sin;

@Environment(EnvType.CLIENT)
public class PlayerRemainsEntityRenderer extends LivingEntityRenderer<PlayerRemainsEntity, PlayerRemainsEntityModel<PlayerRemainsEntity>> {
    public PlayerRemainsEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerRemainsEntityModel<>(), 0.3f);
    }
    ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

    @Override
    public void render(PlayerRemainsEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);
        matrixStack.push();
        matrixStack.scale(1.5f, 1.5f, 1.5f);
        matrixStack.translate(0, 0.2, 0);
        matrixStack.translate(0, (abs(sin((float)entity.age / 15) + 1) / 7), 0);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation((float)entity.age / 20));
        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumers, light, 0, itemRenderer.getModel(stack, entity.getWorld(), null, 0));
        matrixStack.pop();
        if(entity.getCustomName() != null) {
            this.renderLabelIfPresent(entity, entity.getCustomName(), matrixStack, vertexConsumers, light);
        }
    }

    @Override
    public Identifier getTexture(PlayerRemainsEntity entity) {
        return null;
    }
}
