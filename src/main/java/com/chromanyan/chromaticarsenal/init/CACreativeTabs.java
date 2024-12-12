package com.chromanyan.chromaticarsenal.init;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CACreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChromaticArsenal.MODID);

    public static final Supplier<CreativeModeTab> TAB_CHROMATIC_ARSENAL = CREATIVE_TABS.register(ChromaticArsenal.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.chromaticarsenal"))
                    .icon(() -> new ItemStack(CAItems.CHROMA_SHARD.get()))
                    .displayItems(((itemDisplayParameters, output) -> CAItems.ITEMS.getEntries().forEach((item) -> output.accept(item.get()))))
                    .build());
}
