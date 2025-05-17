package net.capozi.demise.common.entity;

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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class PlayerRemainsEntityRenderer extends LivingEntityRenderer<PlayerRemainsEntity, PlayerRemainsEntityModel<PlayerRemainsEntity>> {
    public PlayerRemainsEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerRemainsEntityModel<>(), 0.3f);
    }


    @Override
    public void render(PlayerRemainsEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);
        float angle = (entity.getWorld().getTime() + tickDelta) * 2.5f;
        BakedModel model = itemRenderer.getModel(stack, entity.getWorld(), null, 0);
        Transformation groundTransform = model.getTransformation().ground;

        matrixStack.push();
        matrixStack.scale(1.5f, 1.5f, 1.5f);
        matrixStack.translate(0, 0.25, 0);
        matrixStack.translate(0, Math.sin((entity.getWorld().getTime() + tickDelta)*0.1f)*0.1f, 0);
        matrixStack.multiply(new Quaternionf().fromAxisAngleDeg(0, 1, 0, angle));

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
