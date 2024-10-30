package io.github.cpearl0.ctnhcore.data.lang;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTMachines;
import io.github.cpearl0.ctnhcore.registry.*;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;

public class ChineseLangHandler {
    public static void init(RegistrateCNLangProvider provider) {
        replace(provider, CTNHTagPrefixes.oreHolystone.getUnlocalizedName(), "圣石%s矿石");
        replace(provider, CTNHTagPrefixes.oreMossyHolystone.getUnlocalizedName(), "覆苔圣石%s矿石");

        replace(provider, CTNHMaterials.Holystone.getUnlocalizedName(), "圣石");
        replace(provider, CTNHMaterials.Zanite.getUnlocalizedName(), "紫晶石");
        replace(provider, CTNHMaterials.Ambrosium.getUnlocalizedName(), "神能晶");
        replace(provider, CTNHMaterials.Skyjade.getUnlocalizedName(), "穹玉");
        replace(provider, CTNHMaterials.Stratus.getUnlocalizedName(), "云母钢");

        provider.add("gtceu.underfloor_heating_system", "地暖");
        provider.add("gtceu.astronomical_observatory", "天文台");
        provider.add("gtceu.photovoltaic_power", "光伏发电");
        provider.add("gtceu.slaughter_house", "屠宰场");
        provider.add("gtceu.big_dam", "三峡大坝");
        provider.add("gtceu.coke_oven", "焦化塔");
        provider.add("block.ctnhcore.luv_compressed_fusion_reactor", "压缩核聚变反应堆控制电脑 MK-I");
        provider.add("block.ctnhcore.zpm_compressed_fusion_reactor", "压缩核聚变反应堆控制电脑 MK-II");
        provider.add("block.ctnhcore.uv_compressed_fusion_reactor", "压缩核聚变反应堆控制电脑 MK-III");

        provider.add("gtceu.naq_mk1", "超能燃料");
        provider.add("gtceu.bedrock_drilling_rigs", "基岩钻机");
        provider.add("gtceu.multiblock.laser.tooltip", "允许使用激光仓");
        provider.add("ctnh.multiblock.parallelize.tooltip", "线圈等级和电压等级的提升会提高并行数");
        provider.add("ctnh.multiblock.underfloor_heating_system.efficiency", "效率：%d");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate", "速率：%s");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate.tooltip", "减少蒸汽的消耗来降低地暖的发热功率");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate_modify", "调节速率：");
        provider.add("ctnh.multiblock.underfloor_heating_system.steam_consumption", "蒸汽消耗速率：%d");
        provider.add("ctnh.multiblock.photovoltaic_power_station_invalid", "有方块阻挡");
        provider.add("ctnh.multiblock.photovoltaic_power_station_night", "光照过于微弱");
        provider.add("ctnh.multiblock.photovoltaic_power_station1", "发电效率：%s%%");
        provider.add("ctnh.multiblock.photovoltaic_power_station2", "产能功率：%s/%s EU/t");
        provider.add("info.ctnhcore.network_machine", "发电网络机器数：%d");
        provider.add("info.ctnhcore.network_machine_efficiency", "发电效率：%d");
        provider.add("ctnh.multiblock.slaughter_house.mobcount", "怪物种类：%d (%s)");
        provider.add("ctnh.stress_output", "产出应力：%ssu");
        provider.add("ctnh.stress_input", "输入应力：%ssu");
        provider.add("ctnh.fermenting_tank.growing_temperature", "生长温度：§2%d°C§r");
        provider.add("ctnh.fermenting_tank.growth_efficiency", "生长效率：%d%%");
        provider.add("ctnh.manaturbine.efficiency", "发电效率：%d%%");
        provider.add("ctnh.manaturbine.consumption_rate", "消耗倍率：%d");
        provider.add("ctnh.multiblock.naq_reactor_machine.boost_disallowed", "§b升级你的动力仓以激活等离子体加速");
        provider.add("ctnh.multiblock.naq_reactor_machine.oxygen_plasma_boosted","§b氧等离子体加速中");
        provider.add("ctnh.multiblock.naq_reactor_machine.supply_oxygen_plasma_to_boost","提供氧等离子体以加速");
        provider.add("ctnh.multiblock.naq_reactor_machine.iron_plasma_boosted","§b铁等离子体加速中");
        provider.add("ctnh.multiblock.naq_reactor_machine.supply_iron_plasma_to_boost","提供铁等离子体以加速");
        provider.add("ctnh.multiblock.naq_reactor_machine.nickel_plasma_boosted","§b镍等离子体加速中");
        provider.add("ctnh.multiblock.naq_reactor_machine.supply_nickel_plasma_to_boost","提供镍等离子体以加速");
        provider.add("ctnh.machine.naq_reactor_machine.tooltip.boost_mk1","提供§f20 mB/s§7的氧等离子体，并消耗§f四倍§7燃料以产生高达§f%s§7EU/t的功率。");
        provider.add("ctnh.machine.naq_reactor_machine.tooltip.boost_mk2","提供§f20 mB/s§7的铁等离子体，并消耗§f四倍§7燃料以产生高达§f%s§7EU/t的功率。");

        for (var tier : GTMachines.ALL_TIERS) {
            provider.add(CTNHMachines.CIRCUIT_BUS[tier].getBlock(), GTValues.VNF[tier] + " 芯片总线");
        }
        for (var tier : GTMachines.ELECTRIC_TIERS) {
            provider.add(CTNHMachines.PERSONAL_COMPUTER[tier].getBlock(), GTValues.VNF[tier] + " 个人计算机");
        }

        provider.add(CTNHCreativeModeTabs.MACHINE.get(), "CTNH机器");
        provider.add(CTNHCreativeModeTabs.ITEM.get(), "CTNH物品");
        provider.add(CTNHCreativeModeTabs.BLOCK.get(), "CTNH方块");

        provider.addItem(CTNHItems.GREAT_ASTRONOMY_CIRCUIT_1, "完善的一阶航天数据芯片");
        provider.addItem(CTNHItems.ASTRONOMY_CIRCUIT_1, "一阶航天数据芯片");
        provider.addBlock(CTNHBlocks.CASING_REFLECT_LIGHT, "反光机械方块");
        provider.addBlock(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING, "W-Cu覆膜金刚石机械方块");
        provider.addBlock(CTNHBlocks.ENERGETIC_PHOTOVOLTAIC_BLOCK, "充能光伏方块");
        provider.addBlock(CTNHBlocks.PULSATING_PHOTOVOLTAIC_BLOCK, "脉冲光伏方块");
        provider.addBlock(CTNHBlocks.VIBRANT_PHOTOVOLTAIC_BLOCK, "振动光伏方块");
        provider.addBlock(CTNHBlocks.CASING_NAQUADAH_BLOCK,"铿铀强化硅岩铕机械方块");
        provider.addBlock(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK,"三钛强化中子素硅岩合金机械方块");
        provider.addBlock(CTNHBlocks.ANNIHILATE_CORE,"反应堆核心 MKI");
        provider.addBlock(CTNHBlocks.ANNIHILATE_CORE1,"反应堆核心 MKII");
        provider.addBlock(CTNHBlocks.ANNIHILATE_CORE2,"反应堆核心 MKIII");
        provider.addBlock(CTNHBlocks.CASING_ADVANCED_HYPER,"黑矮星强化超能硅岩机械方块");
        provider.addBlock(CTNHBlocks.CASING_HYPER,"黑钚强化硅岩合金机械方块");
        provider.add(CTNHMultiblockMachines.UNDERFLOOR_HEATING_SYSTEM.getBlock(), "地暖");
        provider.add(CTNHMultiblockMachines.ASTRONOMICAL_OBSERVATORY.getBlock(), "天文台");
        provider.add(CTNHMultiblockMachines.PHOTOVOLTAIC_POWER_STATION_ENERGETIC.getBlock(), "充能光伏发电站");
        provider.add(CTNHMultiblockMachines.PHOTOVOLTAIC_POWER_STATION_PULSATING.getBlock(), "脉冲光伏发电站");
        provider.add(CTNHMultiblockMachines.PHOTOVOLTAIC_POWER_STATION_VIBRANT.getBlock(), "振动光伏发电站");
        provider.add(CTNHMultiblockMachines.WIND_POWER_ARRAY.getBlock(), "风力发电阵列");
        provider.add(CTNHMultiblockMachines.ADVANCED_WIND_POWER_ARRAY.getBlock(), "进阶风力发电阵列");
        provider.add(CTNHMultiblockMachines.SUPER_WIND_POWER_ARRAY.getBlock(), "超级风力发电阵列");
        provider.add(CTNHMultiblockMachines.SLAUGHTER_HOUSE.getBlock(), "屠宰场");
        provider.add(CTNHMultiblockMachines.BIG_DAM.getBlock(), "三峡大坝");
        provider.add(CTNHMultiblockMachines.COKE_OVEN.getBlock(), "焦化塔");
        provider.add(CTNHMultiblockMachines.BEDROCK_DRILLING_RIGS.getBlock(), "基岩钻机");
        provider.add(CTNHMultiblockMachines.NAQ_REACTOR_MK1.getBlock(),"超能反应堆 MKI");
        provider.add(CTNHMultiblockMachines.NAQ_REACTOR_MK2.getBlock(),"超能反应堆 MKII");

    }

    public static void replace(@NotNull RegistrateCNLangProvider provider, @NotNull String key,
                               @NotNull String value) {
        try {
            // the regular lang mappings
            Field field = LanguageProvider.class.getDeclaredField("data");
            field.setAccessible(true);
            // noinspection unchecked
            Map<String, String> map = (Map<String, String>) field.get(provider);
            map.put(key, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error replacing entry in datagen.", e);
        }
    }
}
