package net.capozi.demise.common.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;


@Environment(EnvType.CLIENT)
public class PlayerRemainsEntityRenderer extends LivingEntityRenderer<PlayerRemainsEntity, PlayerRemainsEntityModel<PlayerRemainsEntity>> {
    public PlayerRemainsEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerRemainsEntityModel<PlayerRemainsEntity>(), 0.3f);
    }

    @Override
    public EntityRenderState createRenderState() {
        return null;
    }

    public void render(PlayerRemainsEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);

        float angle = (entity.getWorld().getTime() + tickDelta) * 2.5f;
        BakedModel model = itemRenderer.getModel(stack, entity.getWorld(), null, 0);

        // 1.21.4 change: use accessor method instead of .ground
        // (If you don’t use groundTransform later, this can be removed entirely)
        model.getTransformation().getTransformation(ModelTransformationMode.GROUND);

        matrixStack.push();
        matrixStack.scale(1.5f, 1.5f, 1.5f);
        matrixStack.translate(0, 0.25, 0);
        matrixStack.translate(0, Math.sin((entity.getWorld().getTime() + tickDelta) * 0.1f) * 0.1f, 0);
        matrixStack.multiply(new Quaternionf().rotateY((float) Math.toRadians(angle)));

        itemRenderer.renderItem(
                stack,
                ModelTransformationMode.GROUND,
                false,
                matrixStack,
                vertexConsumers,
                light,
                0,
                itemRenderer.getModel(stack, entity.getWorld(), null, 0)
        );

        matrixStack.pop();

        if (entity.getCustomName() != null) {
            this.renderLabelIfPresent(new EntityRenderState(), entity.getCustomName(), matrixStack, vertexConsumers, light);
        }
    }

    public Identifier getTexture(PlayerRemainsEntity entity) {
        return null;
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return null;
    }
}
