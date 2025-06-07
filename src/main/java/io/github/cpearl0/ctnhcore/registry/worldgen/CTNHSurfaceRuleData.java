package io.github.cpearl0.ctnhcore.registry.worldgen;

import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class CTNHSurfaceRuleData {
    public static SurfaceRules.RuleSource customSurface() {
        SurfaceRules.ConditionSource biome = SurfaceRules.isBiome(CTNHBiomes.PLAGUE_WASTELAND);
        SurfaceRules.ConditionSource surface4 = SurfaceRules.stoneDepthCheck(0, true, 4, CaveSurface.FLOOR);
        SurfaceRules.ConditionSource surface1 = SurfaceRules.stoneDepthCheck(0, true, 1, CaveSurface.FLOOR);
        SurfaceRules.ConditionSource belowWater = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.ConditionSource surface10 = SurfaceRules.stoneDepthCheck(0, true, 10, CaveSurface.FLOOR);
        SurfaceRules.ConditionSource hole = SurfaceRules.hole();
        SurfaceRules.ConditionSource gradient = SurfaceRules.verticalGradient("ctnhcore:astral_stone", VerticalAnchor.belowTop(13), VerticalAnchor.belowTop(9));
        return SurfaceRules.ifTrue(biome, SurfaceRules.sequence(
                SurfaceRules.ifTrue(surface4, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(belowWater, SurfaceRules.state(CTNHBlocks.ASTRAL_DIRT.getDefaultState())),
                                SurfaceRules.ifTrue(surface1, SurfaceRules.state(CTNHBlocks.ASTRAL_GRASS_BLOCK.getDefaultState())),
                                SurfaceRules.state(CTNHBlocks.ASTRAL_DIRT.getDefaultState()))),
                SurfaceRules.ifTrue(gradient, SurfaceRules.state(CTNHBlocks.ASTRAL_STONE.getDefaultState()))
        ));
    }
}
