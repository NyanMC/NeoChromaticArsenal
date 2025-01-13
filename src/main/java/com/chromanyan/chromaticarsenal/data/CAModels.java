package com.chromanyan.chromaticarsenal.data;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CAModels extends ItemModelProvider {

    public CAModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ChromaticArsenal.MODID, existingFileHelper);
    }

    private void basicModel(String name) {
        this.singleTexture(name,
                mcLoc("item/generated"),
                "layer0",
                modLoc("item/" + name));
    }

    private void basicModelWithSuper(String name) {
        basicModel(name);
        basicModel("super_" + name);
    }

    public void registerModels() {
        // crafting materials
        basicModel("chroma_shard");
        basicModel("ascension_essence");

        // regular chromatic curios
        basicModelWithSuper("golden_heart");
        basicModelWithSuper("glass_shield");
        basicModelWithSuper("ward_crystal");
        basicModelWithSuper("shadow_treads");
        basicModel("duality_rings");
        basicModelWithSuper("friendly_fire_flower");
        basicModelWithSuper("lunar_crystal");
        basicModel("cryo_ring");
        basicModel("bubble_amulet");
        basicModel("momentum_stone");
        basicModel("advancing_heart");
        basicModel("thunderguard");

        // challenge curios
        basicModel("ascended_star");
        basicModel("world_anchor");
        basicModel("cursed_totem");
        basicModel("luna_flower");

        // utility curios
        basicModel("gravity_stone");
        basicModel("vertical_stasis_stone");
        basicModel("anonymity_umbrella");

        // basic curios
        basicModel("amethyst_ring");
        basicModel("copper_ring");
        basicModel("vital_stone");

        // compatibility
        //basicModel("omni_ring");

        // other stuff
        basicModel("magic_garlic_bread");
        //basicModel("cosmicola");

        // miscellaneous
        basicModel("super_glow_ring_active");
    }
}
