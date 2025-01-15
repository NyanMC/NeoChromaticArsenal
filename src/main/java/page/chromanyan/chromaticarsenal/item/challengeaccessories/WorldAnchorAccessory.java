package page.chromanyan.chromaticarsenal.item.challengeaccessories;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CARarities;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorldAnchorAccessory extends ChromaAccessory {

    public WorldAnchorAccessory() {
        super(Rarity.valueOf(CARarities.CHALLENGE));
        setEquipSound(SoundEvents.ANVIL_LAND);
        enableJankAttributeFix();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
    }

    @Override
    public DropRule getDropRule(ItemStack stack, SlotReference reference, DamageSource source) {
        return DropRule.KEEP;
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        LivingEntity entity = reference.entity();

        if (entity == null) {
            ChromaticArsenal.LOGGER.warn("entity is null");
            super.getDynamicModifiers(stack, reference, builder);
            return;
        }

        Level level = entity.getCommandSenderWorld();
        double relativeY = entity.getY() - level.getMinBuildHeight(); // the entity's y position relative to the bottom of the world, e.g. y position + 64 in the overworld
        int worldHeight = level.getMaxBuildHeight() - level.getMinBuildHeight();
        double gravityMod = Math.clamp(relativeY / worldHeight, 0, 1);

        builder.addStackable(Attributes.GRAVITY, new AttributeModifier(ChromaticArsenal.of("world_anchor_gravity"), gravityMod, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.MOVEMENT_SPEED, new AttributeModifier(ChromaticArsenal.of("world_anchor_speed"), -gravityMod, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ChromaticArsenal.of("world_anchor_knockback"), gravityMod, AttributeModifier.Operation.ADD_VALUE));
        builder.addStackable(Attributes.ARMOR, new AttributeModifier(ChromaticArsenal.of("world_anchor_armor"), ChromaticArsenal.CONFIG.COMMON.worldAnchorArmor(), AttributeModifier.Operation.ADD_VALUE));

        super.getDynamicModifiers(stack, reference, builder);
    }
}
