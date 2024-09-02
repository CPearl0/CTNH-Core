package io.github.cpearl0.ctnhcore.registry;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import io.github.cpearl0.ctnhcore.CTNHCore;
import teamrazor.deepaether.init.DABlocks;
import teamrazor.deepaether.init.DAItems;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.DECOMPOSITION_BY_CENTRIFUGING;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;

public class CTNHMaterials {
    public static final Material Holystone = new Material.Builder(CTNHCore.id("holystone"))
            .dust()
            .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
            .flags(DECOMPOSITION_BY_CENTRIFUGING)
            .buildAndRegister();

    public static final Material Zanite = new Material.Builder(CTNHCore.id("zanite"))
            .gem()
            .color(0x9254ef).iconSet(EMERALD)
            .buildAndRegister();

    public static final Material Ambrosium = new Material.Builder(CTNHCore.id("ambrosium"))
            .gem()
            .color(0xf1ef5f).iconSet(GEM_HORIZONTAL)
            .buildAndRegister();

    public static final Material Skyjade = new Material.Builder(CTNHCore.id("skyjade"))
            .gem()
            .color(0xb0e564).iconSet(GEM_HORIZONTAL)
            .buildAndRegister();

    public static final Material Stratus = new Material.Builder(CTNHCore.id("stratus"))
            .ingot().liquid()
            .color(0xeac1d9).iconSet(METALLIC)
            .buildAndRegister();

    public static void init() {
        TagPrefix.block.setIgnored(Holystone, AetherBlocks.HOLYSTONE);

        TagPrefix.gem.setIgnored(Zanite, AetherItems.ZANITE_GEMSTONE);
        TagPrefix.block.setIgnored(Zanite, AetherBlocks.ZANITE_BLOCK);

        TagPrefix.gem.setIgnored(Ambrosium, AetherItems.AMBROSIUM_SHARD);
        TagPrefix.block.setIgnored(Ambrosium, AetherBlocks.AMBROSIUM_BLOCK);

        TagPrefix.gem.setIgnored(Skyjade, DAItems.SKYJADE);
        TagPrefix.block.setIgnored(Skyjade, DABlocks.SKYJADE_BLOCK);

        TagPrefix.ingot.setIgnored(Stratus, DAItems.STRATUS_INGOT);
        TagPrefix.block.setIgnored(Stratus, DABlocks.STRATUS_BLOCK);
    }
}
