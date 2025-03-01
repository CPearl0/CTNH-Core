package io.github.cpearl0.ctnhcore.registry;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import io.github.cpearl0.ctnhcore.api.NuclearMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.Conditions.hasIngotProperty;

public class CTNHTagPrefixes {
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
//    public static final TagPrefix carbide_nuclear = new TagPrefix("carbide_nuclear")
//            //.idPattern("%s")
//            .materialAmount(GTValues.M)
//            .materialIconType(new MaterialIconType("carbide_nuclear"))
//            .unificationEnabled(true)
//            .generateItem(true)
//            .generationCondition(material -> material instanceof NuclearMaterial);
//    public static final TagPrefix nitride_nuclear = new TagPrefix("nitride_nuclear")
//            //.idPattern("%s")
//            .materialAmount(GTValues.M)
//            .materialIconType(new MaterialIconType("nitride_nuclear"))
//            .unificationEnabled(true)
//            .generateItem(true)
//            .generationCondition(material -> material instanceof NuclearMaterial);
//    public static final TagPrefix oxide_nuclear = new TagPrefix("oxide_nuclear")
//            //.idPattern("%s")
//            .materialAmount(GTValues.M)
//            .materialIconType(new MaterialIconType("oxide_nuclear"))
//            .unificationEnabled(true)
//            .generateItem(true)
//            .generationCondition(material -> material instanceof NuclearMaterial);
//    public static final TagPrefix zirconium_alloy_nuclear = new TagPrefix("oxide_nuclear")
//            //.idPattern("%s")
//            .materialAmount(GTValues.M)
//            .materialIconType(new MaterialIconType("oxide_nuclear"))
//            .unificationEnabled(true)
//            .generateItem(true)
//            .generationCondition(material -> material instanceof NuclearMaterial);
//    public static final TagPrefix waste = new TagPrefix("waste")
//            //.idPattern("%s")
//            .materialAmount(GTValues.M)
//            .materialIconType(new MaterialIconType(""))
//            .unificationEnabled(true)
//            .generateItem(true)
//            .generationCondition(material -> material instanceof NuclearMaterial);

    public static void init() {
        oreHolystone.addSecondaryMaterial(new MaterialStack(CTNHMaterials.Holystone, TagPrefix.dust.materialAmount()));
        oreMossyHolystone.addSecondaryMaterial(new MaterialStack(CTNHMaterials.Holystone, TagPrefix.dust.materialAmount()));
    }
}
