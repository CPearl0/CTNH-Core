package io.github.cpearl0.ctnhcore.data.lang;

import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHTagPrefixes;

public class EnglishLangHandler {
    public static void init(RegistrateLangProvider provider) {
        LangHandler.replace(provider, CTNHTagPrefixes.oreHolystone.getUnlocalizedName(), "Holystone %s Ore");
        LangHandler.replace(provider, CTNHTagPrefixes.oreMossyHolystone.getUnlocalizedName(), "Mossy Holystone %s Ore");

        LangHandler.replace(provider, CTNHMaterials.Moonstone.getUnlocalizedName(), "Moon Stone");
        LangHandler.replace(provider, CTNHMaterials.Marsstone.getUnlocalizedName(), "Mars Stone");
        LangHandler.replace(provider, CTNHMaterials.Venusstone.getUnlocalizedName(), "Venus Stone");
        LangHandler.replace(provider, CTNHMaterials.Mercurystone.getUnlocalizedName(), "Mercury Stone");
        LangHandler.replace(provider, CTNHMaterials.Glaciostone.getUnlocalizedName(), "Glacio Stone");

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
        provider.add("ctnh.machine.naq_reactor_machine.tooltip.boost_mk3","Supply §f20 mB/s§7 of Nickel Plasma to produce up to §f%s EU/t§7 at §f4x§7 fuel consumption.");
        provider.add("ctnh.test_terminal.lack_error","At %s, you need");
        provider.add("ctnh.test_terminal.wrong_error","At %s, it should be");
        provider.add("ctnh.test_terminal.position","(%s,%s,%s)");
        provider.add("ctnh.test_terminal.error_info","(%s)");
        provider.add("ctnh.test_terminal.success","Everything is OK！");
        provider.add("ctnh.testing_terminal.tooltip.1","Use to check the error when building the multiblock");
        provider.add("ctnh.testing_terminal.tooltip.2","Right-click the controller to show the error info");
        provider.add("ctnh.plasma_condenser.tooltips.1","The dense air condenses into frost and dew");
        provider.add("ctnh.sweat_shop.tooltips.1", "Means of Production and Surplus Value");
        provider.add("ctnh.sweat_shop.tooltips.2", "The number of villagers in the factory determines efficiency. Recipe time x (1 / number of villagers)");
        provider.add("ctnh.sweat_shop.tooltips.3", "The effective number of workers in the factory is limited by the factory size. Initial limit: 4 workers; for every 4 blocks added to the factory length, the limit increases by 1.");
        provider.add("ctnh.sweat_shop.tooltips.4", "The production materials (machines) placed determine the available recipes:\nPowered Rolling Machine ---- Rolling Mill Recipes\nPowered Mixer ---- Mixer Recipes\nLathe ---- Lathe Recipes\nCentrifuge ---- Extractor Recipes\nBlaze Burner ---- Extractor Recipes\nWork Basin ---- Fluid Forming Recipes\nCrushing Wheel ---- Grinder Recipes\nPowered Saw ---- Wire Rolling Machine Recipes\nLaser Processor ---- Laser Etching Recipes");
        provider.add("ctnh.sweat_shop.tooltips.5", "The number of production materials (machines) placed determines the parallelism of corresponding recipes: Parallelism = sqrt(number of machines)");
        provider.add("ctnh.sweat_shop.tooltips.6", "Adding robotic arms improves the overall recipe execution speed. Recipe time x (1 / 1 + sqrt(number of robotic arms))");
        provider.add("ctnh.sweat_shop.tooltips.7", "The diversity of machines placed improves recipe execution speed.");
        provider.add("ctnh.sweat_shop.tooltips.8", "Every 5 seconds, machines consume (number of workers) servings of simple worker meals.");
        provider.add("ctnh.demon_will_generator.tooltips.1", "Harnessing demonic power");
        provider.add("ctnh.demon_will_generator.tooltips.2", "Generates power by utilizing the difference in demonic will concentration between the chunks on either side of the machine. The power generation increases exponentially with the concentration difference.");
        provider.add("ctnh.demon_will_generator.tooltips.3", "Calculations are based on the will concentration at the demonic alloy blocks on either side of the machine.");
        provider.add("ctnh.demon_will_generator.tooltips.4", "The diversity of various demonic wills in the chunks on both sides affects power generation efficiency.");
        provider.add("ctnh.demon_will_generator.tooltips.5", "Will cores can be placed inside the machine, transforming it into a specialized mode focused on a specific type of will.");
        provider.add("ctnh.demon_will_generator.tooltips.6",
                "The rune blocks inside the machine can be replaced to provide different enhancements:\n" +
                        "§4Sacrifice Runes and Self-Sacrifice Runes§r----Increase the power generation multiplier for the Life Essence Fortified Mode§r\n" +
                        "§3Speed Runes§r----Increase the duration of a single recipe operation (saving demonic will consumption)§r\n" +
                        "§eAugment Runes§r----Each rune increases the demonic will concentration difference by 1§r\n" +
                        "§cSupercharge Runes§r----Each rune increases the demonic will concentration difference by 5% (multiplied)§r\n" +
                        "=============================="
        );
        provider.add("ctnh.demon_will_generator.tooltips.7",
                "Insert §4Life Essence§r to activate the Fortified Mode, doubling power output while consuming §a100mb§r of Life Essence per second."
        );
        provider.add("ctnh.multiblock.demon_generator.info.default", "Specialization Boost: None");
        provider.add("ctnh.multiblock.demon_generator.info.vengeful", "Specialization Boost: Vengeful");
        provider.add("ctnh.multiblock.demon_generator.info.corrosive", "Specialization Boost: Corrosive");
        provider.add("ctnh.multiblock.demon_generator.info.steadfast", "Specialization Boost: Steadfast");
        provider.add("ctnh.multiblock.demon_generator.info.destructive", "Specialization Boost: Destructive");
        provider.add("ctnh.multiblock.demon_generator.info.1", "Concentration Difference: %s");
        provider.add("ctnh.multiblock.demon_generator.info.boosted", "§bLife Essence Boost Active");
        provider.add("meadow", "Automated Pasture");
        provider.add("ctnh.meadow.basic", "Can only raise chickens, cows, pigs and sheep. Cows produce leather, sheep produce wool, pigs produce pork and chickens produce eggs.");
        provider.add("ctnh.meadow.mechanism", "The more animals in the pasture, the more waste they generate. The more animals you have, the more byproducts are produced.");
        provider.add("ctnh.fermenting_tank.bio_growth_mechanism", "Biological Growth Mechanism of the Fermenting Tank:");
        provider.add("ctnh.fermenting_tank.bio_growth_temperature", "The optimal growth temperature is between §236§r and §238§r degrees. Recipes get 1.2x efficiency at optimal temperature. The further it deviates, the lower the efficiency, down to one-third.");
        provider.add("ctnh.fermenting_tank.bio_growth", "Microbial growth follows the logistic equation. When the liquid volume in the input tank is half of its capacity, §2growth efficiency doubles§r. Efficiency is lowest when the tank is full or empty, with a minimum of 20%.");
        provider.add("subtick_overclock", "When recipe runtime is less than 1 tick, parallel calculations will be performed automatically.");
        provider.add("fermenting_introduction", "A tank designed specifically for microbial growth. Always keep an eye on it!");
        provider.add("large_fermenting_tank", "Efficient Industrial Fermentation");
        provider.add("ctnh.large_fermenting_tank.bio_growth", "Can connect auxiliary structures. By attaching a large fermentation bottle with a specific liquid type, the minimum efficiency increases: Water (50%), Basic Medium (150%), Sterile Medium (200%).");
        provider.add("large_bottle", "This is truly a large container.");
        provider.add("ctnh.large_bottle.basic", "Can store up to 10,000 buckets of liquid.");
        provider.add("ctnh.large_bottle.consume", "When used with a large fermenting tank, its liquid will be consumed at a rate of §e100mb/s§r.");
        provider.add("digestion_tank_introduction", "Actually, it produces very valuable materials...");
        provider.add("ctnh.digestion_tank.bio_growth_mechanism", "Composting Mechanism of the Digestion Tank:");
        provider.add("ctnh.digestion_tank.bio_growth_temperature", "The optimal growth temperature is between §236§r and §238§r degrees. Recipes get 1.2x efficiency at optimal temperature. The further it deviates, the lower the efficiency, down to one-third.");
        provider.add("ctnh.blaze_blast_furnace.pyrotheum", "Blazing Pyrotheum: %d mB");
        provider.add("ctnh.void_miner.cryotheum", "Cryotheum：%d mB");
        provider.add("ctnh.blaze_blast_furnace.consume", "Base consumption is §a10mB§r of Blazing Pyrotheum per second. For each voltage tier above §6HV§r, the consumption doubles.");
        provider.add("ctnh.blaze_blast_furnace.energy", "Consumes 0.75x energy.");
        provider.add("ctnh.blaze_blast_furnace.parallel", "Allows processing of 8 recipes simultaneously.");
        provider.add("blaze_blast_furnace", "Faster than an electric blast furnace.");
        provider.add("mana_machine", "Magic, isn't it?");
        provider.add("ctnh.basic_mana_machine.mana_consume", "Base consumption is 4mB of Liquid Mana per second. For each voltage tier above §7LV§r, the consumption doubles.");
        provider.add("ctnh.advanced_mana_machine.mana_consume", "Base consumption is 10mB of Liquid Mana per second. For each voltage tier above §7LV§r, the consumption doubles.");
        provider.add("ctnh.mana_macerator", "Machine Type: §eMacerator§r");
        provider.add("ctnh.mana_bender", "Machine Type: §eBender§r");
        provider.add("ctnh.mana_lathe", "Machine Type: §eLathe§r");
        provider.add("ctnh.mana_assembler", "Machine Type: §eAssembler§r");
        provider.add("ctnh.mana_wiremill", "Machine Type: §eWire Mill§r");
        provider.add("ctnh.mana_mixer", "Machine Type: §eMixer§r");
        provider.add("ctnh.perfect_overclock", "§aPerfect Overclock!§r");
        provider.add("mana_generator_turbine_tier1", "Basic Mana Converter");
        provider.add("ctnh.mana_generator_turbine_tier1.basic_power", "§eBase Power Output:§r 256 EU/t");
        provider.add("ctnh.mana_generator_turbine_tier1.restriction", "Rotor frame tier cannot exceed §bMV§r");
        provider.add("mana_generator_turbine_tier2", "Advanced Mana Converter");
        provider.add("ctnh.mana_generator_turbine_tier2.basic_power", "§eBase Power Output:§r 256 EU/t");
        provider.add("ctnh.mana_generator_turbine_tier2.restriction", "Rotor frame tier cannot exceed §5EV§r");
        provider.add("mana_generator_turbine_tier3", "Precision Mana Converter");
        provider.add("ctnh.mana_generator_turbine_tier3.basic_power", "§eBase Power Output:§r 1024 EU/t");
        provider.add("ctnh.mana_generator_turbine_tier3.restriction", "Rotor frame tier cannot exceed §dLuV§r");
        provider.add("mana_generator_turbine_tier4", "Magical Energy Conservation");
        provider.add("ctnh.mana_generator_turbine_tier4.basic_power", "§eBase Power Output:§r 4096 EU/t");
        provider.add("ctnh.mana_generator_turbine_tier4.restriction", "Rotor frame tier cannot exceed §3UV§r");
        provider.add("ctnh.mana_generator_turbine_rune", "Insert runes into the machine to increase power generation efficiency:\n  Tier 1 Rune: Power x1.5, Consumption x0.9\n  Tier 2 Rune: Power x2, Consumption x0.75\n  Tier 3 Rune: Power x3, Consumption x0.6\n  Tier 4 Rune: Power x4, Consumption x0.4");
        provider.add("ctnh.machine.super_ebf.tooltip1", "All recipes are 50% faster!");
        provider.add("ctnh.mega_lcr.recipe_type","Chemical Reactor / LCR");
        provider.add("ctnh.mega_oil_cracker.recipe_type", "Oil Cracker");
        provider.add("ctnh.super_ebf.recipe_type","Electric Blast Furnace");
        provider.add("decay_pools_machine", "Decay");
        provider.add("ctnh.decay_pools_machine.tooltip.0", "When the circuit board is set to 0, the machine is unpowered and world acceleration is disabled.");
        provider.add("ctnh.decay_pools_machine.tooltip.1", "When the circuit board is set to 1, the machine is powered and world acceleration is enabled.");
        provider.add("ctnh.decay_pools_machine.tooltip.2", "Accelerates the decay process.");
        provider.add("vacuum_sintering_tower", "Vacuum Sintering");
        provider.add("crystallizer", "Professional Crystallization");
        provider.add("ctnh.crystallizer.basic", "The crystallizer completes crystal recipes more efficiently.");
        provider.add("ctnh.crystallizer.coolant", "Efficiency improves as the coil level increases.");
        provider.add("ctnh.crystallizer.overclock", "Can process chemical vapor deposition recipes and some autoclave recipes.");
        provider.add("ctnh.crystallizer.safe", "The best assistant for saving materials.");
        provider.add("desalting_introduction", "Drying salt out of seawater—eco-friendly, isn't it?");
        provider.add("multiblock.ctnh.water_power_station1", "Water Flow: %d");
        provider.add("multiblock.ctnh.water_power_station.efficiency", "Coil Efficiency: %d%%");
        provider.add("multiblock.ctnh.water_power_station2", "Power Output: %d/%d EU/t");
        provider.add("ctnh.water_power_station.mechanism", "Power generation is proportional to the amount of water within a radius equal to the machine length and height of 4, centered on the controller.");
        provider.add("ctnh.water_power_station.random", "Power output fluctuates randomly between a multiplier of 0.6 to 1.");
        provider.add("water_power_station", "Eco-Friendly Energy!");
        provider.add("bio_reactor", "A big tank");

    }
}
