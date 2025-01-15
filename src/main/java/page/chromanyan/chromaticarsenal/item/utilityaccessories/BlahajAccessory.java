package page.chromanyan.chromaticarsenal.item.utilityaccessories;

import page.chromanyan.chromaticarsenal.init.CABlocks;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSpawnPhantomsEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@EventBusSubscriber
public class BlahajAccessory extends BlockItem implements Accessory {

    public BlahajAccessory() {
        super(CABlocks.BLAHAJ.get(), new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        TooltipHelper.itemTooltipLine("can_place", list);
        if (!Screen.hasShiftDown()) {
            TooltipHelper.itemTooltipLine("shift", list);
            return;
        }
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
    }

    @SubscribeEvent
    public static void playerSpawnPhantoms(PlayerSpawnPhantomsEvent event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.BLAHAJ.get())) return;

        event.setResult(PlayerSpawnPhantomsEvent.Result.DENY);
    }

    @SubscribeEvent
    public static void livingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.BLAHAJ.get())) return;

        if (event.getLookingEntity() instanceof Phantom) {
            event.modifyVisibility(0);
        }
    }

    // thanks neoforge for adding an event for this
    @SubscribeEvent
    public static void canPlayerSleep(CanPlayerSleepEvent event) {
        if (!(event.getProblem() == Player.BedSleepingProblem.NOT_SAFE)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.BLAHAJ.get())) return;

        event.setProblem(null);
    }

    @Override
    public @Nullable SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
        return new SoundEventData(Holder.direct(SoundEvents.WOOL_PLACE), 0.5f, 1);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        AccessoriesCapability cap = AccessoriesCapability.get(reference.entity());
        if (cap == null) return false;

        return !cap.isAnotherEquipped(stack, reference, this);
    }

    public static class BlahajRenderer implements AccessoryRenderer {

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
            if(!reference.entity().getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                matrixStack.translate(0, 0.12, 0);
            }

            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, multiBufferSource, reference.entity().level(), 0);
            matrixStack.popPose();
        }
    }
}
