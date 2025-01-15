package page.chromanyan.chromaticarsenal.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ChromaAccessoryHelper {

    /**
     * Gets the first ItemStack for the specified Item equipped by the specified Entity.
     * Returns null if the entity lacks an accessory capability. Returns null if the entity does not have the item equipped.
     */
    public static @Nullable ItemStack tryGetFirstEquipped(LivingEntity entity, Item item) {
        AccessoriesCapability cap = AccessoriesCapability.get(entity);
        if (cap == null) return null;
        SlotEntryReference ref = cap.getFirstEquipped(item);
        if (ref == null) return null;
        return ref.stack();
    }

    public static boolean isAccessoryEquipped(LivingEntity entity, Item item) {
        ItemStack stack = ChromaAccessoryHelper.tryGetFirstEquipped(entity, item);
        return stack != null;
    }
}
