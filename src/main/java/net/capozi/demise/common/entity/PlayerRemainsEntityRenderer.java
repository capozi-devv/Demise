package net.capozi.demise.common.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import static java.lang.Math.abs;
import static java.lang.Math.sin;

@Environment(EnvType.CLIENT)
public class PlayerRemainsEntityRenderer extends EntityRenderer<PlayerRemainsEntity, PlayerRemainsEntityRenderState> {
    private final ItemModelManager resolver;
    public PlayerRemainsEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        resolver = ctx.getItemModelManager();
    }

    @Override
    public PlayerRemainsEntityRenderState createRenderState() {
        return new PlayerRemainsEntityRenderState();
    }

    @Override
    public void render(PlayerRemainsEntityRenderState renderState, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrixStack.push();
        matrixStack.scale(1.7f, 1.7f, 1.7f);
        matrixStack.translate(0, 0.2, 0);
        matrixStack.translate(0, (abs(sin((float)renderState.age / 15) + 1) / 7), 0);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation((float)renderState.age / 20));
        renderState.itemRenderState.render(matrixStack, queue, renderState.light, OverlayTexture.DEFAULT_UV, 0xffffff);
        matrixStack.pop();
        if(renderState.displayName != null) {
            this.renderLabelIfPresent(renderState, matrixStack, queue, cameraState);
        }
    }

    @Override
    public void updateRenderState(PlayerRemainsEntity livingEntity, PlayerRemainsEntityRenderState livingEntityRenderState, float f) {
        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);
        resolver.clearAndUpdate(livingEntityRenderState.itemRenderState, stack, ItemDisplayContext.GROUND, null, null, 0);
    }
}
