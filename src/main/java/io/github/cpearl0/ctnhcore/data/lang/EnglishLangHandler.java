package io.github.cpearl0.ctnhcore.data.lang;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import io.github.cpearl0.ctnhcore.registry.CTNHMachines;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;

public class EnglishLangHandler {
    public static void init(RegistrateLangProvider provider) {
        LangHandler.replace(provider, CTNHMaterials.Holystone.getUnlocalizedName(), "Holystone");
        LangHandler.replace(provider, CTNHMaterials.Zanite.getUnlocalizedName(), "Zanite");
        LangHandler.replace(provider, CTNHMaterials.Ambrosium.getUnlocalizedName(), "Ambrosium");
        LangHandler.replace(provider, CTNHMaterials.Skyjade.getUnlocalizedName(), "Skyjade");
        LangHandler.replace(provider, CTNHMaterials.Stratus.getUnlocalizedName(), "Stratus");

        provider.add("gtceu.underfloor_heating_system", "Underfloor Heating");
        provider.add("gtceu.astronomical_observatory", "Astronomical Observatory");

        for (var tier : GTMachines.ALL_TIERS) {
            provider.add(CTNHMachines.CIRCUIT_BUS[tier].getBlock(), GTValues.VNF[tier] + " Circuit Bus");
        }
    }
}
