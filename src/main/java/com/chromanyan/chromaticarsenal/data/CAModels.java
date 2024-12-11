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
        //basicModel("ascension_essence");
        //basicModel("champion_catalyst");
        //basicModel("chromatic_upgrade_smithing_template");

        // regular chromatic curios
        basicModel("golden_heart");
        basicModel("glass_shield"); //TODO super
        //basicModelWithSuper("ward_crystal");
        //basicModelWithSuper("shadow_treads");
        //basicModel("duality_rings");
        //basicModelWithSuper("friendly_fire_flower");
        //basicModelWithSuper("lunar_crystal");
        //basicModel("cryo_ring");
        //basicModel("bubble_amulet");
        //basicModel("momentum_stone");
        //basicModel("advancing_heart");
        //basicModel("thunderguard");

        // super curios
        //basicModel("super_golden_heart"); // can't datagen the regular golden heart because it does special stuff

        // challenge curios
        //basicModel("ascended_star");
        //basicModel("world_anchor");
        //basicModel("cursed_totem");

        // utility curios
        //basicModel("gravity_stone");
        //basicModel("vertical_stasis_stone");
        //basicModel("anonymity_umbrella");

        // basic curios
        //basicModel("amethyst_ring");
        //basicModel("copper_ring");
        //basicModel("vital_stone");

        // compatibility
        //basicModel("omni_ring");

        // other stuff
        //basicModel("magic_garlic_bread");
        //basicModel("cosmicola");
        //basicModel("chroma_salvager");
        //basicModel("viewer_item");

        // miscellaneous
        //basicModel("golden_heart_nyans");
        //basicModel("super_glow_ring_active");
        //basicModel("ca_book");
        //basicModel("lore_book");
    }
}
