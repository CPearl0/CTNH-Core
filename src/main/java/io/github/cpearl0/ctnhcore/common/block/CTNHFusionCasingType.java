package io.github.cpearl0.ctnhcore.common.block;

import io.github.cpearl0.ctnhcore.CTNHCore;

import com.gregtechceu.gtceu.api.block.IFusionCasingType;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.block.FusionCasingBlock;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMaterialBlocks.MATERIAL_BLOCKS;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Duranium;

public class CTNHFusionCasingType extends FusionCasingBlock {

    public CTNHFusionCasingType(Properties properties, IFusionCasingType casingType) {
        super(properties, casingType);
    }

    public static Block getCompressedCoilState(int tier) {
        return switch (tier) {
            case LuV -> SUPERCONDUCTING_COIL.get();
            case ZPM -> FUSION_COIL.get();
            default -> FUSION_COIL.get();
        };
    }

    public static Block getFrameState(int tier) {
        return switch (tier) {
            case LuV -> MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Naquadah).get();
            case ZPM -> MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.NaquadahEnriched).get();
            default -> MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Naquadria).get();
        };
    }

    public static Block getCasingState(int tier) {
        return switch (tier) {
            case LuV -> FUSION_CASING.get();
            case ZPM -> FUSION_CASING_MK2.get();
            default -> FUSION_CASING_MK3.get();
        };
    }

    public static IFusionCasingType getCasingType(int tier) {
        return switch (tier) {
            case LuV -> FusionCasingBlock.CasingType.FUSION_CASING;
            case ZPM -> FusionCasingBlock.CasingType.FUSION_CASING_MK2;
            default -> FusionCasingBlock.CasingType.FUSION_CASING_MK3;
        };
    }

    public enum CasingType implements IFusionCasingType, StringRepresentable {;

        private final String name;
        @Getter
        private final int harvestLevel;

        CasingType(String name, int harvestLevel) {
            this.name = name;
            this.harvestLevel = harvestLevel;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        @Override
        public ResourceLocation getTexture() {
            return CTNHCore.id("block/casings/fusion/%s".formatted(this.name));
        }
    }
}
