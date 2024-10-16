package io.github.cpearl0.ctnhcore.data.lang;

import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHTagPrefixes;

public class EnglishLangHandler {
    public static void init(RegistrateLangProvider provider) {
        LangHandler.replace(provider, CTNHTagPrefixes.oreHolystone.getUnlocalizedName(), "Holystone %s Ore");
        LangHandler.replace(provider, CTNHTagPrefixes.oreMossyHolystone.getUnlocalizedName(), "Mossy Holystone %s Ore");

        LangHandler.replace(provider, CTNHMaterials.Holystone.getUnlocalizedName(), "Holystone");
        LangHandler.replace(provider, CTNHMaterials.Zanite.getUnlocalizedName(), "Zanite");
        LangHandler.replace(provider, CTNHMaterials.Ambrosium.getUnlocalizedName(), "Ambrosium");
        LangHandler.replace(provider, CTNHMaterials.Skyjade.getUnlocalizedName(), "Skyjade");
        LangHandler.replace(provider, CTNHMaterials.Stratus.getUnlocalizedName(), "Stratus");

        provider.add("gtceu.underfloor_heating_system", "Underfloor Heating");
        provider.add("gtceu.astronomical_observatory", "Astronomical Observatory");
        provider.add("gtceu.photovoltaic_power", "Photovoltaic Powering");
        provider.add("gtceu.slaughter_house", "Slaughter House");
        provider.add("gtceu.big_dam", "Big Dam");
        provider.add("gtceu.coke_oven", "Coke Oven");
        provider.add("ctnh.multiblock.underfloor_heating_system.efficiency", "Efficiency: %d");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate", "Rate: %s");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate.tooltip", "Reduce the consumption of steam to reduce the heating power of the floor heating");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate_modify", "Adjust rate: ");
        provider.add("ctnh.multiblock.underfloor_heating_system.steam_consumption", "Steam consumption rate: %d");
        provider.add("ctnh.multiblock.photovoltaic_power_station_night", "At night");
        provider.add("ctnh.multiblock.photovoltaic_power_station_invalid", "Shadowed");
        provider.add("ctnh.multiblock.photovoltaic_power_station1", "Efficiency: %s%%");
        provider.add("ctnh.multiblock.photovoltaic_power_station2", "Generating: %s/%s EU/t");
        provider.add("info.ctnhcore.network_machine","Network Machine Count：%d");
        provider.add("info.ctnhcore.network_machine_efficiency","Generating Efficiency: %d");
        provider.add("ctnh.multiblock.slaughter_house.mobcount","Mob Types: %d");
        provider.add("ctnh.stress_output", "Stress output: %ssu");
        provider.add("ctnh.stress_input", "Stress input: %ssu");
        provider.add("ctnh.fermenting_tank.growing_temperature", "Growth Temperature：§2%d°C§r");
        provider.add("ctnh.fermenting_tank.growth_efficiency", "Growth Efficiency：%d%%");
        provider.add("ctnh.manaturbine.efficiency", "Generating Efficiency：%d%%");
        provider.add("ctnh.manaturbine.consumption_rate", "Consumption Rate：%d");
    }
}
