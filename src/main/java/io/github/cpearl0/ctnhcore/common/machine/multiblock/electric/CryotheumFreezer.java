package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import io.github.cpearl0.ctnhcore.common.gui.MachineModeFancyConfiguratorTest;
import io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials;

import com.gregtechceu.gtceu.api.gui.fancy.TabsWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.fancyconfigurator.CombinedDirectionalFancyConfigurator;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;
import tech.vixhentx.mcmod.ctnhlib.utils.MachineUtils;

import java.util.List;
import java.util.function.DoubleSupplier;

public class CryotheumFreezer extends RecipeElectricMultiblockMachine implements IFancyUIMachine {

    @CN("当前泪之晶点数:%d")
    @EN("Current Tear Crystal points: %d")
    public static Lang cryotheumFreezerUi0;

    @CN("§b当前消耗的凛冰:%d / %d")
    @EN("§bCurrent Cryotheum consumption: %d / %d")
    public static Lang cryotheumFreezerUi5;

    public CryotheumFreezer(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Persisted
    public int a = 3;
    @Persisted
    public double speed_up = 1.0;
    @Persisted
    public double energy_muti = 1.0;
    @Persisted
    public int parallel_muti = 1;
    public DoubleSupplier JEIProgress = () -> (double) Math.abs(speed_up - 1) / (double) 2.5F;
    public DoubleSupplier JEIProgress2 = () -> (double) Math.abs(energy_muti - 1) / (double) 2.5F;
    public DoubleSupplier JEIProgress3 = () -> (double) Math.abs(parallel_muti) / (double) 10F;
    @Persisted
    public long used_energy = 0;

    public long store_energy_now = 0;
    @Persisted
    public long target = 100000L;

    public MutableComponent provider_a() {
        return cryotheumFreezerUi0.translate(a);
    }

    @CN("极寒之凛冰不足")
    @EN("Insufficient Cryotheum")
    static Lang insufficient_cryotheum;

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        var tier = getTier();
        final double amount = Math.pow(4, Math.max((tier - 4), 0));
        if (MachineUtils.inputFluids(this,
                CTNHMaterials.Cryotheum.getFluid((int) (amount * 10)))) {
            used_energy += (long) amount * 10;
            if (used_energy >= target) {
                a += 1;
                used_energy -= target;
                target *= 4;
            }
            return super.beforeWorking(recipe);
        }
        getRecipeLogic().interruptRecipe();
        return insufficient_cryotheum.translate();
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (machine instanceof CryotheumFreezer cmachine) {
            int parallel = ParallelLogic.getParallelAmount(group, recipe,
                    (int) (2 * Math.pow(2, cmachine.parallel_muti)));
            if (parallel == 0) return null;
            recipe.multiplyEUt(1 / cmachine.energy_muti);
            recipe.multiplyAllContents(parallel);
            recipe.multiplyDuration(1 / cmachine.speed_up);
            recipe.parallels *= parallel;
            return null;
        }
        return RecipeModifier.nullWrongType(CryotheumFreezer.class, machine);
    }

    @Override
    public void attachSideTabs(TabsWidget sideTabs) {
        sideTabs.setMainTab(this);

        if (this.getRecipeTypes().length > 0) {
            sideTabs.attachSubTab(new MachineModeFancyConfiguratorTest(this));
        }
        var directionalConfigurator = CombinedDirectionalFancyConfigurator.of(self(), self());
        if (directionalConfigurator != null)
            sideTabs.attachSubTab(directionalConfigurator);
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(textList.size(),
                cryotheumFreezerUi5.translate(used_energy, target));
    }
}
