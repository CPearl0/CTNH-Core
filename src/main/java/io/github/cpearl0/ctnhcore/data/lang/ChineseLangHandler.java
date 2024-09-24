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
        provider.add("multiblock.ctnhcore.underfloor_heating_system.efficiency", "效率：%d");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate", "速率：%s");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate.tooltip", "减少蒸汽的消耗来降低地暖的发热功率");
        provider.add("ctnh.multiblock.underfloor_heating_system.rate_modify", "调节速率：");
        provider.add("ctnh.multiblock.underfloor_heating_system.steam_consumption", "蒸汽消耗速率：%d");
        provider.add("multiblock.ctnh.photovoltaic_power_station_invalid", "有方块阻挡");
        provider.add("multiblock.ctnh.photovoltaic_power_station_night", "光照过于微弱");
        provider.add("multiblock.ctnh.photovoltaic_power_station1", "发电效率：%s%%");
        provider.add("multiblock.ctnh.photovoltaic_power_station2", "产能功率：%s/%s EU/t");
        provider.add("info.ctnhcore.network_machine","发电网络机器数：%d");
        provider.add("info.ctnhcore.network_machine_efficiency","发电效率：%d");

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
        provider.addBlock(CTNHBlocks.ENERGETIC_PHOTOVOLTAIC_BLOCK, "充能光伏方块");
        provider.addBlock(CTNHBlocks.PULSATING_PHOTOVOLTAIC_BLOCK, "脉冲光伏方块");
        provider.addBlock(CTNHBlocks.VIBRANT_PHOTOVOLTAIC_BLOCK, "振动光伏方块");
        provider.add(CTNHMultiblockMachines.UNDERFLOOR_HEATING_SYSTEM.getBlock(), "地暖");
        provider.add(CTNHMultiblockMachines.ASTRONOMICAL_OBSERVATORY.getBlock(), "天文台");
        provider.add(CTNHMultiblockMachines.PHOTOVOLTAIC_POWER_STATION_ENERGETIC.getBlock(), "充能光伏发电站");
        provider.add(CTNHMultiblockMachines.PHOTOVOLTAIC_POWER_STATION_PULSATING.getBlock(), "脉冲光伏发电站");
        provider.add(CTNHMultiblockMachines.PHOTOVOLTAIC_POWER_STATION_VIBRANT.getBlock(), "振动光伏发电站");
        provider.add(CTNHMultiblockMachines.WIND_POWER_ARRAY.getBlock(), "风力发电阵列");
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
