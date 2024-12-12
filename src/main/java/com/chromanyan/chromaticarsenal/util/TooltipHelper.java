package com.chromanyan.chromaticarsenal.util;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TooltipHelper {

    private static String getItemInternalName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public static void itemTooltipLine(String itemName, Object append, @NotNull List<Component> list, Object... strings) {
        list.add(Component.translatable("tooltip.chromaticarsenal." + itemName + "." + append, strings));
    }

    public static void itemTooltipLine(String suffix, @NotNull List<Component> list, Object... strings) {
        list.add(Component.translatable("tooltip.chromaticarsenal." + suffix, strings));
    }

    public static void itemTooltipLine(ItemStack stack, Object append, @NotNull List<Component> list, Object... strings) {
        list.add(Component.translatable("tooltip.chromaticarsenal." + getItemInternalName(stack.getItem()) + "." + append, strings));
    }

    public static String valueTooltip(Object object) {
        return "§b" + object + "§r";
    }

    public static String potionAmplifierTooltip(int level) {
        return valueTooltip(level + 1);
    }

    public static String ticksToSecondsTooltip(int ticks) {
        if (ticks / 20 >= ChromaticArsenal.CONFIG.CLIENT.tooltipDecimalThreshold())
            return valueTooltip(ticks / 20);
        else // add the decimal places to the value as they might be needed (e.g. lunar crystal)
            return valueTooltip((float) ticks / 20F);
    }

    public static String percentTooltip(float decimal) {
        return valueTooltip(Math.round(100 * decimal));
    }

    public static String percentTooltip(double decimal) {
        return percentTooltip((float) decimal);
    }

    public static String multiplierAsPercentTooltip(float multiplier) {
        if (multiplier < 1) { // there's definitely a better way to do this but i don't feel like working through the math in my head right now
            return percentTooltip(1.0F - multiplier);
        } else {
            return percentTooltip(multiplier - 1.0F);
        }
    }

    public static String multiplierAsPercentTooltip(double multiplier) {
        return multiplierAsPercentTooltip((float) multiplier);
    }
}
