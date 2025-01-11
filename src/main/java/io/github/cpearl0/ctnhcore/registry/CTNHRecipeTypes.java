package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.utils.CycleItemStackHandler;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import com.simibubi.create.AllBlocks;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class CTNHRecipeTypes {
    public static final GTRecipeType UNDERFLOOR_HEATING_SYSTEM = GTRecipeTypes.register("underfloor_heating_system", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(0, 0, 1, 1)
            .setEUIO(IO.NONE)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static final GTRecipeType ASTRONOMICAL_OBSERVATORY = GTRecipeTypes.register("astronomical_observatory", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(1, 0, 0, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static final GTRecipeType PHOTOVOLTAIC_POWER = GTRecipeTypes.register("photovoltaic_power", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(1, 0, 0, 0)
            .setEUIO(IO.OUT)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);

    public static final GTRecipeType WIND_POWER_ARRAY = GTRecipeTypes.register("wind_power_array", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(1, 0, 1, 0)
            .setEUIO(IO.OUT)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);

    public static final GTRecipeType PERSONAL_COMPUTER = GTRecipeTypes.register("personal_computer", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(9, 2, 0, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static final GTRecipeType SLAUGHTER_HOUSE = GTRecipeTypes.register("slaughter_house", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(4, 4, 2, 2)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);

    public static final GTRecipeType BIG_DAM = GTRecipeTypes.register("big_dam", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(0, 0, 1, 0)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING)
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_output", String.format("%.1f", data.getFloat("output_stress"))));

    public static final  GTRecipeType FERMENTING = GTRecipeTypes.register("fermenting", "multiblock")
            .setEUIO(IO.IN)
            .setMaxIOSize(4, 4, 1, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.temperature", FormattingUtil.formatNumbers(data.getInt("ebf_temp"))))
            .addDataInfo(data -> {
                var requiredCoil = ICoilType.getMinRequiredType(data.getInt("ebf_temp"));
                if (LDLib.isClient() && requiredCoil != null && requiredCoil.getMaterial() != null) {
                    return LocalizationUtils.format("gtceu.recipe.coil.tier", I18n.get(requiredCoil.getMaterial().getUnlocalizedName()));
                }
                return "";
            })
            .setUiBuilder((recipe, widgetGroup) -> {
                var temp = recipe.data.getInt("ebf_temp");
                var items = new ArrayList<List<ItemStack>>();
                items.add(GTCEuAPI.HEATING_COILS.entrySet().stream().filter(coil -> coil.getKey().getCoilTemperature() >= temp).map(coil -> new ItemStack(coil.getValue().get())).toList());
            widgetGroup.addWidget(new SlotWidget(new CycleItemStackHandler(items), 0, widgetGroup.getSize().width - 25, widgetGroup.getSize().height - 32, false, false));
            });
    public static final GTRecipeType DIGESTING = GTRecipeTypes.register("digesting", MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 1, 2, 4)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType BEDROCK_DRILLING_RIGS = GTRecipeTypes.register("bedrock_drilling_rigs", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(1, 3, 1, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ELECTROLYZER);

    public static final GTRecipeType NAQ_MK1 = GTRecipeTypes.register("naq_mk1", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.OUT)
            .setMaxIOSize(0, 0, 2, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);
    public static final GTRecipeType PHASE_INVERSION = GTRecipeTypes.register("phase_inversion", GTRecipeTypes.ELECTRIC)
        .setEUIO(IO.IN)
        .setMaxIOSize(6,6,2,2)
        .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
        .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
        .setSound(GTSoundEntries.ELECTROLYZER);
public static final GTRecipeType RESONANT_MAGICAL_ASSEMBLY = GTRecipeTypes.register("resonant_assemble", GTRecipeTypes.ELECTRIC)
        .setEUIO(IO.IN)
        .setMaxIOSize(9,9,2,2)
        .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
        .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
        .setSound(GTSoundEntries.ELECTROLYZER);

    public static final GTRecipeType PLASMA_CONDENSER_RECIPES = GTRecipeTypes.register("plasma_condenser", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 2, 2, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);

    public static final GTRecipeType DEMON_WILL_GENERATOR_RECIPE = GTRecipeTypes.register("demon_will_generator", GTRecipeTypes.GENERATOR)
            .setEUIO(IO.OUT)
            .setMaxIOSize(1, 2, 1, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);

    public static final GTRecipeType MEADOW = GTRecipeTypes.register("meadow", MULTIBLOCK)
            .setMaxIOSize(3, 3, 3, 3)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.BATH)
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f",data.getFloat("input_stress"))));
    public static final GTRecipeType MANA_GENERATOR = GTRecipeTypes.register("mana_generator",GENERATOR)
            .setEUIO(IO.OUT)
            .setMaxIOSize(2, 0, 1, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);
    public static final GTRecipeType CHEMICAL_GENERATOR = GTRecipeTypes.register("chemical_generator",GENERATOR)
            .setEUIO(IO.OUT)
            .setMaxIOSize(2,0,2,0)
            .setSlotOverlay(false, false, GuiTextures.BOX_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COMBUSTION);
    public static final GTRecipeType VOID_MINER = GTRecipeTypes.register("void_miner",GENERATOR)
            .setEUIO(IO.IN)
            .setMaxIOSize(2,36,2,0)
            .setSlotOverlay(false, false, GuiTextures.BOX_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COMBUSTION);
    public static final GTRecipeType SINTERING_KILN = GTRecipeTypes.register("sintering_kiln", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(4, 4, 2, 0)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType CHEMICAL_VAPOR_DEPOSITION = GTRecipeTypes.register("chemical_vapor_deposition", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(2, 2, 2, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType MARTIAL_MORALITY_EYE = GTRecipeTypes.register("martial_morality_eye", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(2, 27, 1, 3)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType DIMENSIONAL_GAS_COLLECTION = GTRecipeTypes.register("dimensional_gas_collection", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(2, 2, 2, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType CONDENSING_DISCRETE = GTRecipeTypes.register("condensing_discrete", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(6, 6, 6, 6)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType ION_EXCHANGER = GTRecipeTypes.register("ion_exchanger", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(6, 6, 6, 6)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType DECAY_VESSEL = GTRecipeTypes.register("decay_vessel", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(2, 1, 0, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MINER);
    public static final GTRecipeType DECAY_POOLS = GTRecipeTypes.register("decay_pools", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 1, 0, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MINER);
    public static final GTRecipeType SILICA_ROCK_FUEL_REFINERY = GTRecipeTypes.register("silica_rock_fuel_refinery", ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(3, 0, 3, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType FUEL_REFINING = GTRecipeTypes.register("fuel_refining", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(3, 3, 3, 3)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ELECTROLYZER)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.temperature", FormattingUtil.formatNumbers(data.getInt("ebf_temp"))))
            .addDataInfo(data -> {
                var requiredCoil = ICoilType.getMinRequiredType(data.getInt("ebf_temp"));
                if (LDLib.isClient() && requiredCoil != null && requiredCoil.getMaterial() != null) {
                    return LocalizationUtils.format("gtceu.recipe.coil.tier", I18n.get(requiredCoil.getMaterial().getUnlocalizedName()));
                }
                return "";
            })
            .setUiBuilder((recipe, widgetGroup) -> {
                var temp = recipe.data.getInt("ebf_temp");
                var items = new ArrayList();
                items.add(GTCEuAPI.HEATING_COILS.entrySet().stream().filter(coil -> coil.getKey().getCoilTemperature() >= temp).map(coil -> new ItemStack(coil.getValue().get())).toList());
                widgetGroup.addWidget(new SlotWidget(new CycleItemStackHandler(items), 0, widgetGroup.getSize().width - 25, widgetGroup.getSize().height - 32, false, false));
            });
    public static final GTRecipeType VACUUM_SINTERING = GTRecipeTypes.register("vacuum_sintering", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(6, 6, 6, 6)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ELECTROLYZER)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.temperature", FormattingUtil.formatNumbers(data.getInt("ebf_temp"))))
            .addDataInfo(data -> {
                var requiredCoil = ICoilType.getMinRequiredType(data.getInt("ebf_temp"));
                if (LDLib.isClient() && requiredCoil != null && requiredCoil.getMaterial() != null) {
                    return LocalizationUtils.format("gtceu.recipe.coil.tier", I18n.get(requiredCoil.getMaterial().getUnlocalizedName()));
                }
                return "";
            })
            .setUiBuilder((recipe, widgetGroup) -> {
                var temp = recipe.data.getInt("ebf_temp");
                var items = new ArrayList();
                items.add(GTCEuAPI.HEATING_COILS.entrySet().stream().filter(coil -> coil.getKey().getCoilTemperature() >= temp).map(coil -> new ItemStack(coil.getValue().get())).toList());
                widgetGroup.addWidget(new SlotWidget(new CycleItemStackHandler(items), 0, widgetGroup.getSize().width - 25, widgetGroup.getSize().height - 32, false, false));
            });
    public static final GTRecipeType CRYSTALLIZER = GTRecipeTypes.register("crystallizer", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(6, 6, 6, 6)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.temperature", FormattingUtil.formatNumbers(data.getInt("ebf_temp"))))
            .addDataInfo(data -> {
                var requiredCoil = ICoilType.getMinRequiredType(data.getInt("ebf_temp"));
                if (LDLib.isClient() && requiredCoil != null && requiredCoil.getMaterial() != null) {
                    return LocalizationUtils.format("gtceu.recipe.coil.tier", I18n.get(requiredCoil.getMaterial().getUnlocalizedName()));
                }
                return "";
            })
            .setUiBuilder((recipe, widgetGroup) -> {
                var temp = recipe.data.getInt("ebf_temp");
                var items = new ArrayList();
                items.add(GTCEuAPI.HEATING_COILS.entrySet().stream().filter(coil -> coil.getKey().getCoilTemperature() >= temp).map(coil -> new ItemStack(coil.getValue().get())).toList());
                widgetGroup.addWidget(new SlotWidget(new CycleItemStackHandler(items), 0, widgetGroup.getSize().width - 25, widgetGroup.getSize().height - 32, false, false));
            });
    public static final GTRecipeType DESALTING = GTRecipeTypes.register("desalting", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(1, 4, 1, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ELECTROLYZER)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.temperature", FormattingUtil.formatNumbers(data.getInt("ebf_temp"))))
            .addDataInfo(data -> {
                var requiredCoil = ICoilType.getMinRequiredType(data.getInt("ebf_temp"));
                if (LDLib.isClient() && requiredCoil != null && requiredCoil.getMaterial() != null) {
                    return LocalizationUtils.format("gtceu.recipe.coil.tier", I18n.get(requiredCoil.getMaterial().getUnlocalizedName()));
                }
                return "";
            })
            .setUiBuilder((recipe, widgetGroup) -> {
                var temp = recipe.data.getInt("ebf_temp");
                var items = new ArrayList();
                items.add(GTCEuAPI.HEATING_COILS.entrySet().stream().filter(coil -> coil.getKey().getCoilTemperature() >= temp).map(coil -> new ItemStack(coil.getValue().get())).toList());
                widgetGroup.addWidget(new SlotWidget(new CycleItemStackHandler(items), 0, widgetGroup.getSize().width - 25, widgetGroup.getSize().height - 32, false, false));
            });
    public static final GTRecipeType WATER_POWER = GTRecipeTypes.register("water_power", GENERATOR)
            .setEUIO(IO.OUT)
            .setMaxIOSize(0, 0, 1, 0)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType BIO_REACTOR = GTRecipeTypes.register("bio_reactor", ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(4, 4, 2, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final String KINETIC = "kinetic";
    public static final GTRecipeType SEASON_STEAM_RECIPES = GTRecipeTypes.register("season_steam",KINETIC)
            .setMaxIOSize(1, 0, 0, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new com.gregtechceu.gtceu.api.gui.widget.SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctpp.stress_output",String.format("%.1f",data.getFloat("stress"))));
    public static void init() {

    }
}
