package com.chromanyan.chromaticarsenal;

import com.chromanyan.chromaticarsenal.data.CAModels;
import com.chromanyan.chromaticarsenal.data.CARecipes;
import com.chromanyan.chromaticarsenal.data.tags.CABlockTags;
import com.chromanyan.chromaticarsenal.data.tags.CAItemTags;
import com.chromanyan.chromaticarsenal.init.*;
import com.chromanyan.chromaticarsenal.item.ShadowTreadsAccessory;
import com.chromanyan.chromaticarsenal.item.superaccessories.IlluminatedSoulAccessory;
import com.chromanyan.chromaticarsenal.item.utilityaccessories.BlahajAccessory;
import com.mojang.logging.LogUtils;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(ChromaticArsenal.MODID)
public class ChromaticArsenal {
    public static final String MODID = "chromaticarsenal";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final com.chromanyan.chromaticarsenal.CAConfig CONFIG = com.chromanyan.chromaticarsenal.CAConfig.createAndLoad();

    public ChromaticArsenal(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);

        NeoForge.EVENT_BUS.register(this);

        CAAttachments.ATTACHMENT_TYPES.register(modEventBus);
        CAEffects.MOB_EFFECTS.register(modEventBus);
        CABlocks.BLOCKS.register(modEventBus);
        CAItems.ITEMS.register(modEventBus);
        CACreativeTabs.CREATIVE_TABS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput output = gen.getPackOutput();
        if (event.includeClient()) {
            gen.addProvider(true, new CAModels(output, efh));
        }
        if (event.includeServer()) {
            CABlockTags blockTags = new CABlockTags(output, lookupProvider, efh);
            gen.addProvider(true, blockTags);
            gen.addProvider(true, new CAItemTags(output, lookupProvider, blockTags.contentsGetter(), efh));
            gen.addProvider(true, new CARecipes(output, lookupProvider));
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ShadowTreadsAccessory.clientInit();
            // consider moving this renderer to its own file, especially if i use it more in the future
            AccessoriesRendererRegistry.registerRenderer(CAItems.BLAHAJ.get(), BlahajAccessory.BlahajRenderer::new);
            AccessoriesRendererRegistry.registerRenderer(CAItems.CHROMANYAN.get(), BlahajAccessory.BlahajRenderer::new);

            IlluminatedSoulAccessory.registerVariants();
        }
    }

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
