package page.chromanyan.chromaticarsenal.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import page.chromanyan.chromaticarsenal.init.CAItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CuriosRenderers {
    public static void register() {
        CuriosRendererRegistry.register(CAItems.BLAHAJ.get(), () -> new HeadAccessoryRenderer(Minecraft.getInstance().getEntityModels().bakeLayer(HeadAccessoryRenderer.LAYER)));
        CuriosRendererRegistry.register(CAItems.CHROMANYAN.get(), () -> new HeadAccessoryRenderer(Minecraft.getInstance().getEntityModels().bakeLayer(HeadAccessoryRenderer.LAYER)));
    }

    @SubscribeEvent
    public static void onLayerRegister(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HeadAccessoryRenderer.LAYER, () -> LayerDefinition.create(HeadAccessoryRenderer.mesh(), 1, 1));
        event.registerLayerDefinition(HeadAccessoryRenderer.LAYER, () -> LayerDefinition.create(HeadAccessoryRenderer.mesh(), 1, 1));
    }
}
