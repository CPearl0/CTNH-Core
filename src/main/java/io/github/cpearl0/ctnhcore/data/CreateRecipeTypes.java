package io.github.cpearl0.ctnhcore.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.sound.ExistingSoundEntry;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import com.mo_guang.ctpp.recipe.CTPPRecipeBuilder;
import com.simibubi.create.AllBlocks;
import io.github.cpearl0.ctnhcore.CTNHConfig;
import io.github.cpearl0.ctnhcore.registry.CTNHChanceLogic;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.UP_TO_DOWN;
import static io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes.KINETIC;

public class CreateRecipeTypes {
    public static void init() {
        BENDER_RECIPES.onRecipeBuild((builder, provider) ->{
            assert MECHANICAL_PRESSOR_RECIPES != null;
            if(GTUtil.getTierByVoltage(builder.EUt()) <= GTValues.LV) {
                var newrecipe = MECHANICAL_PRESSOR_RECIPES.copyFrom(builder)
                        .duration(Math.max((int)(builder.duration / CTNHConfig.INSTANCE.kinetic.pressorSpeedMultiplier), 1))
                        .buildRawRecipe();
                new CTPPRecipeBuilder(newrecipe, MECHANICAL_PRESSOR_RECIPES).rpm(CTNHConfig.INSTANCE.kinetic.pressorRpmRequirement)
                        .noEUt()
                        .inputStress(builder.EUt() * CTNHConfig.INSTANCE.kinetic.pressorStressRequirement)
                        .chancedOutputLogic(ItemRecipeCapability.CAP, ChanceLogic.NONE)
                        .save(provider);
            }
        });
        MIXER_RECIPES.onRecipeBuild((builder, provider) ->{
            assert MECHANICAL_MIXER_RECIPES != null;
            if(GTUtil.getTierByVoltage(builder.EUt()) <= GTValues.LV) {
                var newrecipe = MECHANICAL_MIXER_RECIPES.copyFrom(builder)
                        .duration(Math.max((int)(builder.duration / CTNHConfig.INSTANCE.kinetic.mixerSpeedMultiplier), 1))
                        .buildRawRecipe();
                new CTPPRecipeBuilder(newrecipe, MECHANICAL_MIXER_RECIPES).rpm(CTNHConfig.INSTANCE.kinetic.mixerRpmRequirement)
                        .noEUt()
                        .inputStress(builder.EUt() * CTNHConfig.INSTANCE.kinetic.mixerStressRequirement)
                        .chancedOutputLogic(ItemRecipeCapability.CAP, ChanceLogic.NONE)
                        .save(provider);
            }
        });
        CENTRIFUGE_RECIPES.onRecipeBuild((builder, provider) ->{
            assert MECHANICAL_CENTRIFUGE_RECIPES != null;
            if(GTUtil.getTierByVoltage(builder.EUt()) <= GTValues.LV) {
                var newrecipe = MECHANICAL_CENTRIFUGE_RECIPES.copyFrom(builder)
                        .duration(Math.max((int)(builder.duration / CTNHConfig.INSTANCE.kinetic.centrifugeSpeedMultiplier), 1))
                        .buildRawRecipe();
                new CTPPRecipeBuilder(newrecipe, MECHANICAL_CENTRIFUGE_RECIPES).rpm(CTNHConfig.INSTANCE.kinetic.centrifugeRpmRequirement)
                        .noEUt()
                        .inputStress(builder.EUt() * CTNHConfig.INSTANCE.kinetic.centrifugeStressRequirement)
                        .chancedOutputLogic(ItemRecipeCapability.CAP, ChanceLogic.NONE)
                        .save(provider);
            }
        });
        SIFTER_RECIPES.onRecipeBuild((builder, provider) ->{
            assert MECHANICAL_SIFTER_RECIPES != null;
            if(GTUtil.getTierByVoltage(builder.EUt()) <= GTValues.LV) {
                var newrecipe = MECHANICAL_SIFTER_RECIPES.copyFrom(builder)
                        .duration(Math.max((int)(builder.duration / CTNHConfig.INSTANCE.kinetic.sifterSpeedMultiplier), 1))
                        .buildRawRecipe();
                new CTPPRecipeBuilder(newrecipe, MECHANICAL_SIFTER_RECIPES).rpm(CTNHConfig.INSTANCE.kinetic.sifterRpmRequirement)
                        .noEUt()
                        .inputStress(builder.EUt() * CTNHConfig.INSTANCE.kinetic.sifterStressRequirement)
                        .chancedOutputLogic(ItemRecipeCapability.CAP, CTNHChanceLogic.BASIC)
                        .save(provider);
            }
        });
        EXTRACTOR_RECIPES.onRecipeBuild((builder, provider) ->{
            assert MECHANICAL_EXTRACTOR_RECIPES != null;
            if(GTUtil.getTierByVoltage(builder.EUt()) <= GTValues.LV) {
                var newrecipe = MECHANICAL_EXTRACTOR_RECIPES.copyFrom(builder)
                        .duration(Math.max((int)(builder.duration / CTNHConfig.INSTANCE.kinetic.extractorSpeedMultiplier), 1))
                        .buildRawRecipe();
                new CTPPRecipeBuilder(newrecipe, MECHANICAL_EXTRACTOR_RECIPES).rpm(CTNHConfig.INSTANCE.kinetic.extractorRpmRequirement)
                        .noEUt()
                        .inputStress(builder.EUt() * CTNHConfig.INSTANCE.kinetic.extractorStressRequirement)
                        .chancedOutputLogic(ItemRecipeCapability.CAP, ChanceLogic.NONE)
                        .save(provider);
            }
        });
        LATHE_RECIPES.onRecipeBuild((builder, provider) ->{
            assert MECHANICAL_LATHE_RECIPES != null;
            if(GTUtil.getTierByVoltage(builder.EUt()) <= GTValues.LV) {
                var newrecipe = MECHANICAL_LATHE_RECIPES.copyFrom(builder)
                        .duration(Math.max((int)(builder.duration / CTNHConfig.INSTANCE.kinetic.latheSpeedMultiplier), 1))
                        .buildRawRecipe();
                new CTPPRecipeBuilder(newrecipe, MECHANICAL_LATHE_RECIPES).rpm(CTNHConfig.INSTANCE.kinetic.latheRpmRequirement)
                        .noEUt()
                        .inputStress(builder.EUt() * CTNHConfig.INSTANCE.kinetic.latheStressRequirement)
                        .chancedOutputLogic(ItemRecipeCapability.CAP, ChanceLogic.NONE)
                        .save(provider);
            }
        });
        LASER_ENGRAVER_RECIPES.onRecipeBuild((builder, provider) ->{
            assert MECHANICAL_LASER_RECIPES != null;
            if(GTUtil.getTierByVoltage(builder.EUt()) <= GTValues.LV) {
                var newrecipe = MECHANICAL_LASER_RECIPES.copyFrom(builder)
                        .duration(Math.max((int)(builder.duration / CTNHConfig.INSTANCE.kinetic.laserSpeedMultiplier), 1))
                        .buildRawRecipe();
                new CTPPRecipeBuilder(newrecipe, MECHANICAL_LASER_RECIPES).rpm(CTNHConfig.INSTANCE.kinetic.laserRpmRequirement)
                        .noEUt()
                        .inputStress(builder.EUt() * CTNHConfig.INSTANCE.kinetic.laserStressRequirement)
                        .chancedOutputLogic(ItemRecipeCapability.CAP, ChanceLogic.NONE)
                        .save(provider);
            }
        });
    }
    public static final GTRecipeType MECHANICAL_PRESSOR_RECIPES = GTRecipeTypes.register("mechanical_pressor_recipes", KINETIC)
            .setMaxIOSize(2,1,0,0)
            .setSlotOverlay(false, false, false, GuiTextures.BENDER_OVERLAY)
            .setSlotOverlay(false, false, true, GuiTextures.INT_CIRCUIT_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CUT)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f", data.getFloat("input_stress"))));
    public static final GTRecipeType MECHANICAL_MIXER_RECIPES = GTRecipeTypes.register("mechanical_mixer_recipes", KINETIC)
            .setMaxIOSize(6,6,3,3)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MIXER, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MIXER)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f", data.getFloat("input_stress"))));
    public static final GTRecipeType MECHANICAL_CENTRIFUGE_RECIPES = GTRecipeTypes.register("mechanical_centrifuge_recipes", KINETIC)
            .setMaxIOSize(2,6,1,6)
            .setSlotOverlay(false, false, false, GuiTextures.EXTRACTOR_OVERLAY)
            .setSlotOverlay(false, false, true, GuiTextures.CANISTER_OVERLAY)
            .setSlotOverlay(false, true, true, GuiTextures.CENTRIFUGE_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CENTRIFUGE)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f", data.getFloat("input_stress"))));
    public static final GTRecipeType MECHANICAL_SIFTER_RECIPES = GTRecipeTypes.register("mechanical_sifter_recipes", KINETIC)
            .setMaxIOSize(1,6,0,0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_SIFT, UP_TO_DOWN)
            .setSound(new ExistingSoundEntry(SoundEvents.SAND_PLACE, SoundSource.BLOCKS))
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f", data.getFloat("input_stress"))));
    public static final GTRecipeType MECHANICAL_EXTRACTOR_RECIPES = GTRecipeTypes.register("mechanical_extractor_recipes", KINETIC)
            .setMaxIOSize(1,1,0,1)
            .setSlotOverlay(false, false, GuiTextures.EXTRACTOR_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, LEFT_TO_RIGHT)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f", data.getFloat("input_stress"))));
    public static final GTRecipeType MECHANICAL_LATHE_RECIPES = GTRecipeTypes.register("mechanical_lathe_recipes", KINETIC)
            .setMaxIOSize(1,2,0,0)
            .setSlotOverlay(false, false, GuiTextures.PIPE_OVERLAY_1)
            .setSlotOverlay(true, false, false, GuiTextures.PIPE_OVERLAY_2)
            .setSlotOverlay(true, false, true, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_LATHE, LEFT_TO_RIGHT)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f", data.getFloat("input_stress"))));
    public static final GTRecipeType MECHANICAL_LASER_RECIPES = GTRecipeTypes.register("mechanical_laser_recipes", KINETIC)
            .setMaxIOSize(2,1,0,0)
            .setSlotOverlay(false, false, true, GuiTextures.LENS_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setUiBuilder((recipe, group) -> {
                var handler = new CustomItemStackHandler(AllBlocks.SHAFT.asStack());
                group.addWidget(new SlotWidget(handler, 0, group.getSize().width - 30,
                        group.getSize().height - 30, false, false));
            })
            .addDataInfo(data -> LocalizationUtils.format("ctnh.stress_input", String.format("%.1f", data.getFloat("input_stress"))));
}
