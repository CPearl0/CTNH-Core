package io.github.cpearl0.ctnhcore.common.block;

import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@Getter
public enum CoilType implements StringRepresentable, ICoilType {

    ABYSSALALLOY("abyssalalloy", 12600, 16, 8, CTNHMaterials.Abyssalalloy, CTNHCore.id("block/casings/coils/abyssalalloy_coil_block")),
    TITANSTEEL("titansteel", 14400, 32, 8, CTNHMaterials.Titansteel, CTNHCore.id("block/casings/coils/titansteel_coil_block")),
    PIKYONIUM("pikyonium", 16200, 32, 9, CTNHMaterials.Pikyonium, CTNHCore.id("block/casings/coils/pikyonium_coil_block")),
    BLACKTITANIUM("black_titanium", 18900, 64, 9, CTNHMaterials.BlackTitanium, CTNHCore.id("block/casings/coils/black_titanium_coil_block")),
    STARMETAL("starmetal", 21600, 64, 9, CTNHMaterials.Starmetal, CTNHCore.id("block/casings/coils/starmetal_coil_block")),
    INFINITYY("infinity", 36000, 128, 9, CTNHMaterials.Infinity, CTNHCore.id("block/casings/coils/infinity_coil_block")),
    ULTRA_MANA("ulta_mana",7200,16,6,CTNHMaterials.QUASER_MANA,CTNHCore.id("block/casings/coils/ultra_mana_coil_block"));

    @NotNull
    private final String name;
    // electric blast furnace properties
    private final int coilTemperature;
    // multi smelter properties
    private final int level;
    private final int energyDiscount;
    @NotNull
    private final Material material;
    @NotNull
    private final ResourceLocation texture;

    CoilType(String name, int coilTemperature, int level, int energyDiscount, Material material,
             ResourceLocation texture) {
        this.name = name;
        this.coilTemperature = coilTemperature;
        this.level = level;
        this.energyDiscount = energyDiscount;
        this.material = material;
        this.texture = texture;
    }

    public int getTier() {
        return this.ordinal();
    }

    @NotNull
    @Override
    public String toString() {
        return getName();
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return name;
    }
}
