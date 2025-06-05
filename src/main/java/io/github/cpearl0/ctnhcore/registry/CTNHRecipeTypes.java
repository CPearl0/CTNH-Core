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
import io.github.cpearl0.ctnhcore.data.CreateRecipeTypes;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
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
            .setMaxIOSize(2, 1, 2, 2)
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
            .setMaxIOSize(3,42,3,3)
            .setSlotOverlay(false, false, GuiTextures.BOX_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COMBUSTION);
    public static final GTRecipeType SINTERING_KILN = GTRecipeTypes.register("sintering_kiln", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(4, 4, 2, 0)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType CHEMICAL_VAPOR_DEPOSITION = GTRecipeTypes.register("chemical_vapor_deposition", ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 2, 2, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType MARTIAL_MORALITY_EYE = GTRecipeTypes.register("martial_morality_eye", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 27, 1, 3)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType DIMENSIONAL_GAS_COLLECTION = GTRecipeTypes.register("dimensional_gas_collection", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 2, 2, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType CONDENSING_DISCRETE = GTRecipeTypes.register("condensing_discrete", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(6, 6, 6, 6)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType ION_EXCHANGER = GTRecipeTypes.register("ion_exchanger", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
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
    public static final GTRecipeType WOOD_BIONICS = GTRecipeTypes.register("wood_bionics", ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(1, 4, 1, 0)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CUT);
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
    public static final GTRecipeType ALTER = GTRecipeTypes.register("alter", ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(4, 4, 2, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new com.gregtechceu.gtceu.api.gui.widget.SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.lp_consumption",String.format("%.1f",data.getFloat("addlp"))));
    public static final GTRecipeType QUASAR_EYE = GTRecipeTypes.register("quasar_eye", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.OUT)
            .setMaxIOSize(1, 0, 2, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE)
            .addDataInfo(data -> LocalizationUtils.format("ctnh.eye_consumption",String.format("%.1f",data.getFloat("consumption"))))
      .addDataInfo(data -> LocalizationUtils.format("ctnh.quasar.tip.1",String.format("%d",data.getInt("tier"))))
            .addDataInfo(data -> LocalizationUtils.format("ctnh.quasar.tip.2",String.format("%d",data.getInt("active"))));
    public static final GTRecipeType DIGITAL_WELL_OF_SUFFER = GTRecipeTypes.register("digital_well_of_suffer", ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(1,0,0,1)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.BATH);
    public static final GTRecipeType HELLFORGE = GTRecipeTypes.register("hellforge",ELECTRIC)
            .setMaxIOSize(4, 1, 0, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE)
            .addDataInfo(data -> Component.translatable("ctnh.gtceu.hellforge.minimumdrain",data.getInt("minimumDrain")).getString())
            .addDataInfo(data -> Component.translatable("ctnh.gtceu.hellforge.drain",data.getInt("drain")).getString());
    public static final GTRecipeType BEAMS = GTRecipeTypes.register("beams", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(9, 2, 1, 2)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .addDataInfo(data -> LocalizationUtils.format("ctnh.beams_mana_required",String.format("%.2f",data.getFloat("required_mana")/1000000)))
            .addDataInfo(data -> LocalizationUtils.format("ctnh.beams_mana_consumption",String.format("%.2f",data.getFloat("mana")/1000000)));
    public static final GTRecipeType TWISTED_FUSION = GTRecipeTypes.register("twisted_fusion",ELECTRIC)
                .setMaxIOSize(0, 0, 2, 2)
                .setEUIO(IO.IN)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
                .setSound(GTSoundEntries.TURBINE);
    public static final GTRecipeType NANO_GENERATOR = GTRecipeTypes.register("nano_generator",GENERATOR)
            .setMaxIOSize(4, 4, 0, 0)
            .setEUIO(IO.OUT)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);
    public static final GTRecipeType SINOPE = GTRecipeTypes.register("sinope", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(4, 4, 4, 4)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);
    public static final GTRecipeType MANAREACTOR = GTRecipeTypes.register("mana_reactor",ELECTRIC)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType ACCELERATOR_UP =GTRecipeTypes.register("accelerator_upmode", ELECTRIC)
            .setMaxIOSize(9,9,3,3)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_FUSION,ProgressTexture.FillDirection.DOWN_TO_UP)
            .setSound(GTSoundEntries.FIRE)
            .addDataInfo(data->
            {
                if(data.getString("type").equals("addnu"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.nu");
                }
                else if(data.getString("type").equals("addproton"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.proton");
                }
                else if(data.getString("type").equals("addelement"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.element");
                }
                else if(data.getString("type").equals("element"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.element.consume");
                }
                else if(data.getString("type").equals("nu"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.nu.consume");
                }
                else if(data.getString("type").equals("proton"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.proton.consume");
                }
                return  "";
            })
            .addDataInfo(data->{

                if(data.getString("type").equals("element")||data.getString("type").equals("nu")||data.getString("type").equals("proton"))
                {
                    var speed=data.getDouble("speed");
                    if(data.getDouble("speed")<1000)
                    {
                        return LocalizationUtils.format("ctnh.accelerator.mode.speed.m",String.format("%.2f",speed));
                    }
                    if(speed>=1000)
                    {
                        return LocalizationUtils.format("ctnh.accelerator.mode.speed.g",String.format("%.2f",speed/1000));
                    }

                }
                return "";
            }
            )
            .addDataInfo(data->{
                if(data.contains("darkmatter"))
                {
                    return LocalizationUtils.format("ctnh.acc.danger");
                }
                return "";

            })
            ;

    public static final GTRecipeType ACCELERATOR_DOWN =GTRecipeTypes.register("accelerator_downmode", ELECTRIC)
            .setMaxIOSize(9,9,3,3)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_FUSION,ProgressTexture.FillDirection.DOWN_TO_UP)
            .setSound(GTSoundEntries.FIRE)
            .addDataInfo(data->
            {
                if(data.getString("type").equals("addnu"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.nu");
                }
                else if(data.getString("type").equals("addproton"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.proton");
                }
                else if(data.getString("type").equals("addelement"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.element");
                }
                else if(data.getString("type").equals("element"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.element.consume");
                }
                else if(data.getString("type").equals("nu"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.nu.consume");
                }
                else if(data.getString("type").equals("proton"))
                {
                    return LocalizationUtils.format("ctnh.accelerator.mode.proton.consume");
                }

                return "";
            })
            .addDataInfo(data->{

                if(data.getString("type").equals("element")||data.getString("type").equals("nu")||data.getString("type").equals("proton"))
                {
                    var speed=data.getDouble("speed");
                    if(data.getDouble("speed")<1000)
                    {
                        return LocalizationUtils.format("ctnh.accelerator.mode.speed.m",String.format("%.2f",speed));
                    }
                    if(speed>=1000)
                    {
                        return LocalizationUtils.format("ctnh.accelerator.mode.speed.g",String.format("%.2f",speed/1000));
                    }


                }
                return "";
            })
            .addDataInfo(data->{
                if(data.contains("darkmatter"))
                {
                    return LocalizationUtils.format("ctnh.acc.danger");
                }
                return "";

            })
            ;
    public static final GTRecipeType ARC_GENERATOR = GTRecipeTypes.register("arc_generator",GENERATOR)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.OUT)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .addDataInfo(data -> LocalizationUtils.format("ctnh.arc.require",String.format("%d",data.getInt("requirearc"))))
            .addDataInfo(data -> LocalizationUtils.format("ctnh.arc.max",String.format("%d",data.getInt("maxarc"))))
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType ARC_REACTOR = GTRecipeTypes.register("arc_reactor",ELECTRIC)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType MAGIC_FUEL_GENERATOR = GTRecipeTypes.register("magic_fuel_generator",ELECTRIC)
            .setMaxIOSize(6, 6, 6, 6)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType TRAP_ENERGY = GTRecipeTypes.register("trap_energy",ELECTRIC)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType CULTIVATION_ROOM = GTRecipeTypes.register("cultivation_room",ELECTRIC)
            .setMaxIOSize(3, 3, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BATH,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MIXER);
    public static final GTRecipeType ETERNAL_GARDEN = GTRecipeTypes.register("eternal_garden",ELECTRIC)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType CT_ASSEMBLY_LINE = GTRecipeTypes.register("ct_assembly_line",ELECTRIC)
            .setMaxIOSize(6, 1, 3, 0)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType CT_ASSEMBLY_LINE_MAKER = GTRecipeTypes.register("ct_assembly_line_maker",ELECTRIC)
            .setMaxIOSize(6, 1, 0, 0)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType PVB_RECIPE = GTRecipeTypes.register("pvb_recipe",ELECTRIC)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType LS_RECIPE = GTRecipeTypes.register("laser_sorter_recipe",ELECTRIC)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .addDataInfo(data->
            {
                if(data.contains("cwut"))
                {
                    return LocalizationUtils.format("ctnh.ls.cwut",String.format("%d",data.getInt("cwut")));
                }
                return "";
            })
            .setSound(GTSoundEntries.CHEMICAL);

    public static final GTRecipeType PHOTOVOLTAIC_GENERATOR = GTRecipeTypes.register("photovoltaic_generator",GENERATOR)
            .setMaxIOSize(2, 2, 2, 2)
            .setEUIO(IO.OUT)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType PHOTOVOLTAIC_ASSEMBER = GTRecipeTypes.register("photovoltaic_assember",GENERATOR)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .addDataInfo(data->
            {
                if(data.contains("tier"))
                {
                    return LocalizationUtils.format("ctnh.pvc_tier",String.format("%d",data.getInt("tier")));
                }
                return "";
            })
            .addDataInfo(data->
            {
                if(data.contains("input"))
                {
                    return LocalizationUtils.format("ctnh.eut_model",String.format("%d",data.getInt("input")));
                }
                return "";
            })
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType PVDRONE=GTRecipeTypes.register("pv_drone_recipe",ELECTRIC)
            .setMaxIOSize(6, 6, 3, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType NUCLEAR_REACTOR_RECIPES = GTRecipeTypes.register("nuclear_reactor", MULTIBLOCK)
            .setMaxIOSize(6,3,3,3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .addDataInfo(data -> LocalizationUtils.format("ctnh.nuclear_reactor_heat", String.format("%.1f",data.getFloat("heat"))));
    public static final GTRecipeType GAS_CENTRIFUGE_RECIPES = GTRecipeTypes.register("gas_centrifuge", MULTIBLOCK)
            .setMaxIOSize(0, 0, 1, 3)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CENTRIFUGE);
    public static final GTRecipeType HOT_COOLANT_TURBINE_RECIPES = GTRecipeTypes.register("hot_coolant_turbine", MULTIBLOCK)
            .setMaxIOSize(0, 0, 1, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);
    public static final GTRecipeType MANA_CONDENSER_RECIPES = GTRecipeTypes.register("mana_condenser", MULTIBLOCK)
            .setMaxIOSize(0, 0, 1, 1)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING);
    public static final GTRecipeType  COMPILER_RECIPE = GTRecipeTypes.register("compiler_recipe",ELECTRIC)
            .setMaxIOSize(5, 1, 0, 0)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType QUASAR_CREATE=GTRecipeTypes.register("quasar_create",GENERATOR)
            .setMaxIOSize(1, 3, 1, 21)
            .setEUIO(IO.OUT)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,  ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL);
    public static final GTRecipeType SCALABLE_RESERVOIR_COMPUTING = GTRecipeTypes.register("scalable_reservoir_computing",ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(1,0,1,0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSlotOverlay(false,false,GuiTextures.RESEARCH_STATION_OVERLAY)
            .setSlotOverlay(false,true,GuiTextures.FLUID_SLOT)
            .setSound(GTSoundEntries.COMPUTATION)
            .addDataInfo(data->LocalizationUtils.format("gtceu.machine.hpca.component_type.computation_cwut",data.getInt("maxCWUt")))
            .addDataInfo(data->LocalizationUtils.format("ctnhcore.src.wetware_duration",data.getInt("wetwareDuration")))
            //TODO: 渲染以后写
            .addDataInfo(data->LocalizationUtils.format("ctnhcore.src.sacrifice",data.getString("sacrifice")))
            ;
    public static final GTRecipeType DIFFERENTIAL_CENTRIFUGE_RECIPES = GTRecipeTypes.register("differential_centrifuge", ELECTRIC)
                .setEUIO(IO.IN)
                .setMaxIOSize(6, 6, 6, 6)
                .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
                .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
                .setSound(GTSoundEntries.CENTRIFUGE);
    public static final GTRecipeType ULTRASONICATION_RECIPES = GTRecipeTypes.register("ultrasonication", ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(3, 3, 3, 3)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR);

    public static void init() {
        CreateRecipeTypes.init();
    }
}