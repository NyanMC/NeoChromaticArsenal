package page.chromanyan.chromaticarsenal.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HeadAccessoryRenderer implements AccessoryRenderer {

    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack matrixStack, EntityModel<M> model, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(model instanceof HumanoidModel<M> humanoidModel)) return;

        // Translate and rotate with our head
        matrixStack.pushPose();
        matrixStack.translate(humanoidModel.head.x / 16.0, humanoidModel.head.y / 16.0, humanoidModel.head.z / 16.0);
        matrixStack.mulPose(Axis.YP.rotation(humanoidModel.head.yRot));
        matrixStack.mulPose(Axis.XP.rotation(humanoidModel.head.xRot));

        matrixStack.translate(0, -0.25, 0);
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
        matrixStack.scale(0.625f, 0.625f, 0.625f);

        // Translate slightly higher if wearing a head item
        if (!reference.entity().getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            matrixStack.translate(0, 0.12, 0);
        }

        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, multiBufferSource, reference.entity().level(), 0);
        matrixStack.popPose();
    }
}
