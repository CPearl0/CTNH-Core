package io.github.cpearl0.ctnhcore.mixin.gtceu;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Supplier;

@Mixin(value = TagPrefix.class, remap = false)
public abstract class TagPrefixMixin {
    @Final
    @Shadow
    public String name;

    @ModifyVariable(method = "registerOre(Ljava/util/function/Supplier;Ljava/util/function/Supplier;Ljava/util/function/Supplier;Lnet/minecraft/resources/ResourceLocation;ZZZ)Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    public boolean CTNH$modifyShouldDropAsItem(boolean value) {
        return switch (name) {
            case "moon_stone",
                 "venus_stone",
                 "mars_stone",
                 "mercury_stone",
                 "glacio_stone",
                 "blackstone",
                 "soul_soil" -> true;
            default -> value;
        };
    }

    @ModifyVariable(method = "registerOre(Ljava/util/function/Supplier;Ljava/util/function/Supplier;Ljava/util/function/Supplier;Lnet/minecraft/resources/ResourceLocation;ZZZ)Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    public Supplier<Material> CTNH$modifyMaterial(Supplier<Material> value) {
        return switch (name) {
            case "moon_stone" -> () -> CTNHMaterials.Moonstone;
            case "mars_stone" -> () -> CTNHMaterials.Marsstone;
            case "venus_stone" -> () -> CTNHMaterials.Venusstone;
            case "mercury_stone" -> () -> CTNHMaterials.Mercurystone;
            case "glacio_stone" -> () -> CTNHMaterials.Glaciostone;
            default -> value;
        };
    }
}
