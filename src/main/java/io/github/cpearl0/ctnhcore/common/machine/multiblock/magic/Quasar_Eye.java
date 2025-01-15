package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Quasar_Eye extends WorkableElectricMultiblockMachine implements ITieredMachine {
private double rune_energy=0;
 private int energy_tier=0;
 private  int active=0;
 private static int[] based_output={0,67108864,134217728,268435456};
    public Quasar_Eye(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        rune_energy=0;
        energy_tier=0;
        active=0;

        super.onStructureFormed();
    }
    public double energy_caculate(double rune){
        if(rune<=50)
        {
            return 0.5;
        }
        return Math.min((Math.log(((rune)/50)))+1,1+energy_tier*1);
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {


        if (recipe.data.getInt(("consumption")) == 1000000) {
            if (MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid(1000000), this)||active>=1) {
                if (MachineUtils.inputItem(CTNHItems.TWIST_RUNE.asStack(1),this )){
                    rune_energy+=32;
                }
                if (MachineUtils.inputItem(CTNHItems.HORIZEN_RUNE.asStack(1),this )){
                    rune_energy+=32;
                }
                if (MachineUtils.inputItem(CTNHItems.STARLIGHT_RUNE.asStack(1),this )){
                    rune_energy+=32;
                }
                if (MachineUtils.inputItem(CTNHItems.PROLIFERATION_RUNE.asStack(1),this )){
                    rune_energy+=16;
                }
                if (MachineUtils.inputItem(CTNHItems.QUASAR_RUNE.asStack(1),this )){
                    rune_energy+=128;
                }
                if(active<1)active=1;
                energy_tier=1;
                return super.beforeWorking(recipe);
            }
        }
        if(recipe.data.getInt("consumption")==100000){
            if (MachineUtils.inputFluid(CTNHMaterials.Zenith_essence.getFluid(100000), this)||active>=2) {
                if (MachineUtils.inputItem(CTNHItems.TWIST_RUNE.asStack(1),this )){
                    rune_energy+=32;
                }
                if (MachineUtils.inputItem(CTNHItems.HORIZEN_RUNE.asStack(1),this )){
                    rune_energy+=32;
                }
                if (MachineUtils.inputItem(CTNHItems.STARLIGHT_RUNE.asStack(1),this )){
                    rune_energy+=32;
                }
                if (MachineUtils.inputItem(CTNHItems.PROLIFERATION_RUNE.asStack(1),this )){
                    rune_energy+=16;
                }
                if (MachineUtils.inputItem(CTNHItems.QUASAR_RUNE.asStack(1),this )){
                    rune_energy+=128;
                }
                if(active<2)active=2;
                energy_tier=2;
                return super.beforeWorking(recipe);
            }
        }
        return false;
    }
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 100 == 0) {
            var tier = getTier();
            if(rune_energy>0)rune_energy-=Math.max((rune_energy/50)*Math.log((rune_energy/50)+1)*energy_tier,0);
        }
        return super.onWorking();
    }
    public void addDisplayText(List<Component> textList) {
        var tier = getTier();
        super.addDisplayText(textList);
        textList.add(textList.size(),Component.translatable("ctnh.mana_model",String.format("%d",energy_tier)));
        textList.add(textList.size(), Component.translatable("ctnh.rune_energy",String.format("%.2f",rune_energy)));
        textList.add(textList.size(), Component.translatable("ctnh.mana_production",String.format("%.2f",energy_caculate(rune_energy))));
        textList.add(textList.size(), Component.translatable("ctnh.rune_consumption",String.format("%.2f",(rune_energy/50)*Math.log(rune_energy/50+1)*energy_tier)));
        textList.add(textList.size(), Component.translatable("ctnh.quasar_parallel",String.format("%.2f",energy_caculate(rune_energy)*5)));
        textList.add(textList.size(), Component.translatable("ctnh.consumption_parallel",String.format("%.2f",1-0.05*Math.max((rune_energy-50)/50,0.75))));
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if(machine instanceof Quasar_Eye qmachine){
            var EUt = RecipeHelper.getOutputEUt(recipe);
            return ModifierFunction.builder()
                    .eutMultiplier(qmachine.energy_caculate(qmachine.rune_energy))
                    .durationMultiplier(qmachine.energy_caculate(qmachine.rune_energy)*5)
                    .inputModifier(ContentModifier.multiplier(qmachine.energy_caculate(qmachine.rune_energy)*5*(1-0.05*Math.max((qmachine.rune_energy-50)/50,0))))
                    .build();
        }
        return ModifierFunction.NULL;
    }

}