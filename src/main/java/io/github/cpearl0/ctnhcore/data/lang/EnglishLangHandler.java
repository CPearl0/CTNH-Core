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
        provider.add("gtceu.naq_mk1", "Super Fuel");
        provider.add("gtceu.bedrock_drilling_rigs", "Bedrock Drilling Rigs");
        provider.add("gtceu.plasma_condenser", "Plasma Condensation");
        provider.add("ctnh.multiblock.underfloor_heating_system.efficiency", "Efficiency: %d");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate", "Rate: %s");
        provider.add("gtceu.multiblock.laser.tooltip", "The use of the laser chamber is permitted");
        provider.add("ctnh.multiblock.parallelize.tooltip", "Increases in coil and voltage levels increase the number of parallels");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate.tooltip", "Reduce the consumption of steam to reduce the heating power of the floor heating");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate_modify", "Adjust rate: ");
        provider.add("ctnh.multiblock.underfloor_heating_system.steam_consumption", "Steam consumption rate: %d");
        provider.add("ctnh.multiblock.photovoltaic_power_station_night", "At night");
        provider.add("ctnh.multiblock.photovoltaic_power_station_invalid", "Shadowed");
        provider.add("ctnh.multiblock.photovoltaic_power_station1", "Efficiency: %s%%");
        provider.add("ctnh.multiblock.photovoltaic_power_station2", "Generating: %s/%s EU/t");
        provider.add("info.ctnhcore.network_machine","Network Machine Count：%d");
        provider.add("info.ctnhcore.network_machine_efficiency","Generating Efficiency: %d");
        provider.add("ctnh.multiblock.slaughter_house.mobcount","Mob Types: %d (%s)");
        provider.add("ctnh.stress_output", "Stress output: %ssu");
        provider.add("ctnh.stress_input", "Stress input: %ssu");
        provider.add("ctnh.fermenting_tank.growing_temperature", "Growth Temperature：§2%d°C§r");
        provider.add("ctnh.fermenting_tank.growth_efficiency", "Growth Efficiency：%d%%");
        provider.add("ctnh.manaturbine.efficiency", "Generating Efficiency：%d%%");
        provider.add("ctnh.manaturbine.consumption_rate", "Consumption Rate：%d");
        provider.add("ctnh.multiblock.naq_reactor_machine.boost_disallowed", "§bUpgrade the Dynamo Hatch to enable Plasma Boosting.");
        provider.add("ctnh.multiblock.naq_reactor_machine.oxygen_plasma_boosted","§bOxygen Plasma boosted.");
        provider.add("ctnh.multiblock.naq_reactor_machine.supply_oxygen_plasma_to_boost","Supply Oxygen Plasma to boost.");
        provider.add("ctnh.multiblock.naq_reactor_machine.iron_plasma_boosted","§bIron Plasma boosted.");
        provider.add("ctnh.multiblock.naq_reactor_machine.supply_iron_plasma_to_boost","Supply Iron Plasma to boost.");
        provider.add("ctnh.multiblock.naq_reactor_machine.nickel_plasma_boosted","§bNickel Plasma boosted.");
        provider.add("ctnh.multiblock.naq_reactor_machine.supply_nickel_plasma_to_boost","Supply Nickel Plasma to boost.");
        provider.add("ctnh.machine.naq_reactor_machine.tooltip.boost_mk1","Supply §f20 mB/s§7 of Oxygen Plasma to produce up to §f%s EU/t§7 at §f4x§7 fuel consumption.");
        provider.add("ctnh.machine.naq_reactor_machine.tooltip.boost_mk2","Supply §f20 mB/s§7 of Iron Plasma to produce up to §f%s EU/t§7 at §f4x§7 fuel consumption.");
        provider.add("ctnh.test_terminal.lack_error","At %s, you need");
        provider.add("ctnh.test_terminal.wrong_error","At %s, it should be");
        provider.add("ctnh.test_terminal.position","(%s,%s,%s)");
        provider.add("ctnh.test_terminal.error_info","(%s)");
        provider.add("ctnh.test_terminal.success","Everything is OK！");
        provider.add("ctnh.testing_terminal.tooltip.1","Use to check the error when building the multiblock");
        provider.add("ctnh.testing_terminal.tooltip.2","Right-click the controller to show the error info");
        provider.add("ctnh.sweat_shop.tooltips.1", "Means of Production and Surplus Value");
        provider.add("ctnh.sweat_shop.tooltips.2", "The number of villagers in the factory determines efficiency. Recipe time x (1 / number of villagers)");
        provider.add("ctnh.sweat_shop.tooltips.3", "The effective number of workers in the factory is limited by the factory size. Initial limit: 4 workers; for every 4 blocks added to the factory length, the limit increases by 1.");
        provider.add("ctnh.sweat_shop.tooltips.4", "The production materials (machines) placed determine the available recipes:\nPowered Rolling Machine ---- Rolling Mill Recipes\nPowered Mixer ---- Mixer Recipes\nLathe ---- Lathe Recipes\nCentrifuge ---- Extractor Recipes\nBlaze Burner ---- Extractor Recipes\nWork Basin ---- Fluid Forming Recipes\nCrushing Wheel ---- Grinder Recipes\nPowered Saw ---- Wire Rolling Machine Recipes\nLaser Processor ---- Laser Etching Recipes");
        provider.add("ctnh.sweat_shop.tooltips.5", "The number of production materials (machines) placed determines the parallelism of corresponding recipes: Parallelism = sqrt(number of machines)");
        provider.add("ctnh.sweat_shop.tooltips.6", "Adding robotic arms improves the overall recipe execution speed. Recipe time x sqrt(number of robotic arms)");
        provider.add("ctnh.sweat_shop.tooltips.7", "The diversity of machines placed improves recipe execution speed.");
        provider.add("ctnh.sweat_shop.tooltips.8", "Every 5 seconds, machines consume (number of workers) servings of simple worker meals.");

    }
}
