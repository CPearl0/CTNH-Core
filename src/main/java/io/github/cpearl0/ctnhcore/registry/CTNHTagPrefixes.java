package io.github.cpearl0.ctnhcore.registry;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialIconType;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHPropertyKeys;
import io.github.cpearl0.ctnhcore.data.CTNHMaterialFlags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.List;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials.Plutonium;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials.Uranium;

public class CTNHTagPrefixes {
    public static List<Material> nuclears = List.of(Uranium238, Uranium235, Plutonium239, Plutonium241);
    public static final TagPrefix oreHolystone = TagPrefix.oreTagPrefix("holystone", BlockTags.MINEABLE_WITH_PICKAXE)
            .registerOre(() -> AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true),
                    () -> CTNHMaterials.Holystone,
                    BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(3.0F, 3.0F),
                    new ResourceLocation(Aether.MODID, "block/holystone"), true, false, true);
    public static final TagPrefix oreMossyHolystone = TagPrefix.oreTagPrefix("mossy_holystone", BlockTags.MINEABLE_WITH_PICKAXE)
            .registerOre(() -> AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true),
                    () -> CTNHMaterials.Holystone,
                    BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(3.0F, 3.0F),
                    new ResourceLocation(Aether.MODID, "block/mossy_holystone"), true, false, true);
    public static final TagPrefix oreLivingrock = TagPrefix.oreTagPrefix("livingrock", BlockTags.MINEABLE_WITH_PICKAXE)
            .registerOre(() -> BotaniaBlocks.livingrock.defaultBlockState(),
                    () -> CTNHMaterials.Livingrock,
                    BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(3.0F, 3.0F),
                    ResourceLocation.tryParse("botania:block/polished_livingrock"), false, false, true);
    public static final TagPrefix oreIcestone = TagPrefix.oreTagPrefix("icestone", BlockTags.MINEABLE_WITH_PICKAXE)
            .registerOre(() -> AetherBlocks.ICESTONE.get().defaultBlockState(),
                    () -> CTNHMaterials.icestone,
                    BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(2.0F, 2.0F),
                    ResourceLocation.tryParse("aether:block/icestone"), false, false, true);

    public static final TagPrefix nuclear = new TagPrefix("nuclear")
            .idPattern("%s")
            .materialAmount(GTValues.M)
            .materialIconType(CTNHMaterialIconType.NUCLEAR)
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(material -> material.hasProperty(CTNHPropertyKeys.NUCLEAR) || nuclears.contains(material));
    public static final TagPrefix fuel = new TagPrefix("fuel")
            .idPattern("%s_fuel")
            .materialAmount(GTValues.M)
            .materialIconType(new MaterialIconType("fuel"))
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(material -> material.hasProperty(CTNHPropertyKeys.NUCLEAR) || nuclears.contains(material));
    public static final TagPrefix DepletedFuel = new TagPrefix("depleted_fuel")
            .idPattern("depleted_%s_fuel")
            .materialAmount(GTValues.M)
            .materialIconType(new MaterialIconType("depleted_fuel"))
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(material -> material.hasProperty(CTNHPropertyKeys.NUCLEAR) || nuclears.contains(material));
    public static final TagPrefix waste = new TagPrefix("waste")
            .idPattern("%s_waste")
            .materialAmount(GTValues.M)
            .materialIconType(new MaterialIconType("waste"))
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(material -> material.hasFlag(CTNHMaterialFlags.GENERATE_WASTE) || material.equals(Uranium) || material.equals(Plutonium));

    public static void init() {
        oreHolystone.addSecondaryMaterial(new MaterialStack(CTNHMaterials.Holystone, TagPrefix.dust.materialAmount()));
        oreMossyHolystone.addSecondaryMaterial(new MaterialStack(CTNHMaterials.Holystone, TagPrefix.dust.materialAmount()));
        oreLivingrock.addSecondaryMaterial(new MaterialStack(CTNHMaterials.Livingrock, TagPrefix.dust.materialAmount()));
        oreIcestone.addSecondaryMaterial(new MaterialStack(CTNHMaterials.icestone, TagPrefix.dust.materialAmount()));
    }
}
