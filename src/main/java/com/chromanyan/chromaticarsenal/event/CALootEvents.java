package com.chromanyan.chromaticarsenal.event;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class CALootEvents {

    @SuppressWarnings("SameParameterValue")
    private static void injectInto(LootTableLoadEvent event, String poolName, LootPoolEntryContainer... entries) {
        LootPool pool = event.getTable().getPool(poolName);
        if (pool != null) {
            ArrayList<LootPoolEntryContainer> newEntries = new ArrayList<>(pool.entries);
            newEntries.addAll(List.of(entries));
            pool.entries = newEntries;
        } else {
            ChromaticArsenal.LOGGER.warn("Failed to insert into {} because it was null", poolName);
        }
    }

    private static LootItemConditionalFunction.Builder<?> exactlyOne() {
        return SetItemCountFunction.setCount(ConstantValue.exactly(1));
    }

    private static LootPool singleItem(ItemLike item, int airWeight) {
        LootPool.Builder singleItemBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).name("chromatic_arsenal_loot");
        singleItemBuilder.add(LootItem.lootTableItem(item).apply(exactlyOne()));
        if (airWeight > 0) {
            singleItemBuilder.add(EmptyLootItem.emptyItem().setWeight(airWeight));
        }
        return singleItemBuilder.build();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void lootTableLoad(LootTableLoadEvent event) {
        if (!ChromaticArsenal.CONFIG.COMMON.lootTableInsertion()) return;

        switch (event.getName().getPath()) {
            case "gameplay/piglin_bartering" ->
                    injectInto(event, "main", LootItem.lootTableItem(CAItems.GOLDEN_HEART).setWeight(8)
                            .apply(exactlyOne()).build());
            case "chests/bastion_treasure" -> event.getTable().addPool(singleItem(CAItems.GOLDEN_HEART, 7));
            case "chests/end_city_treasure" -> {
                LootPool.Builder endCityBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(2)).name("chromatic_arsenal_loot");
                endCityBuilder.add(LootItem.lootTableItem(CAItems.LUNAR_CRYSTAL).setWeight(1).apply(exactlyOne()));
                endCityBuilder.add(LootItem.lootTableItem(CAItems.MAGIC_GARLIC_BREAD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5))));
                endCityBuilder.add(EmptyLootItem.emptyItem().setWeight(5));
                event.getTable().addPool(endCityBuilder.build());
            }
            case "chests/igloo_chest" -> event.getTable().addPool(singleItem(CAItems.CRYO_RING, 0));
            case "chests/village/village_shepherd" -> event.getTable().addPool(singleItem(CAItems.BLAHAJ, 2));
            case "chests/village/village_armorer" -> event.getTable().addPool(singleItem(CAItems.MOMENTUM_STONE, 2));
            case "chests/trial_chambers/reward_common" ->
                    injectInto(event, "main", LootItem.lootTableItem(CAItems.CHROMA_SHARD)
                            .apply(exactlyOne()).setWeight(1).build());
            case "chests/trial_chambers/reward_ominous_unique" ->
                    injectInto(event, "main", LootItem.lootTableItem(CAItems.ASCENSION_ESSENCE)
                            .apply(exactlyOne()).setWeight(1).build());
            case "archaeology/ocean_ruin_warm" ->
                    injectInto(event, "main", LootItem.lootTableItem(CAItems.BUBBLE_AMULET)
                            .apply(exactlyOne()).setWeight(1).build());
        }

        if (event.getName().getPath().contains("chests")) {
            for (String lootName : ChromaticArsenal.CONFIG.COMMON.chromaShardBlacklist()) {
                if (event.getName().getPath().contains(lootName)) return;
            }
            LootPool.Builder builder = LootPool.lootPool().setRolls(UniformGenerator.between(-2, 1)).name("chroma_shards");

            builder.add(LootItem.lootTableItem(CAItems.CHROMA_SHARD).apply(exactlyOne()).setWeight(3));

            event.getTable().addPool(builder.build());
        }
    }
}
