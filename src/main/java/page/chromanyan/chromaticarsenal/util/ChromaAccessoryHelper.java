package page.chromanyan.chromaticarsenal.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class ChromaAccessoryHelper {

    /**
     * Gets the first ItemStack for the specified Item equipped by the specified Entity.
     * Returns null if the entity lacks an accessory capability. Returns null if the entity does not have the item equipped.
     */
    public static @Nullable ItemStack tryGetFirstEquipped(LivingEntity entity, Item item) {
        Optional<SlotResult> result = getCurio(entity, item);
        return result.map(SlotResult::stack).orElse(null);
    }

    public static Optional<SlotResult> getCurio(LivingEntity livingEntity, Item item) {
        return CuriosApi.getCuriosInventory(livingEntity).flatMap(inv -> inv.findFirstCurio(item));
    }

    public static boolean isAccessoryEquipped(LivingEntity entity, Item item) {
        ItemStack stack = ChromaAccessoryHelper.tryGetFirstEquipped(entity, item);
        return stack != null;
    }
}
