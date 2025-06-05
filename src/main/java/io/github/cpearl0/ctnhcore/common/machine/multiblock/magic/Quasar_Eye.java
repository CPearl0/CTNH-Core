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
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.nbt.CompoundTag;
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
 public static final String RUNE_ENERGY = "rune_energy";
    public static final String ET = "energy_tier";
    public static final String ACTIVE = "active";
    public long power=0;
 private static int[] based_output={0,67108864,134217728,268435456};
    public Quasar_Eye(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        rune_energy=0;
        energy_tier=0;
        active=0;
    }
    public double energy_caculate(double rune,int energy_tier){
        if(rune<=50)
        {
            return 0.5;
        }
        return Math.min((Math.log(((rune)/50)))+1,1+energy_tier*1);
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if(!MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid(recipe.data.getInt("consumption")),this))
        {
            if(recipe.data.getInt("active")>active) return false;
        }

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
            rune_energy+=512;
        }
        if(active<recipe.data.getInt("active"))
        {active=recipe.data.getInt("active");
            }
        energy_tier=recipe.data.getInt("tier");
        return super.beforeWorking(recipe);

    }
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 100 == 0) {
            if(rune_energy>0)rune_energy-=Math.max((rune_energy/50)*Math.log((rune_energy/50)+1),0);
        }
        return super.onWorking();
    }
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(textList.size(),Component.translatable("ctnh.mana_model",String.format("%d",energy_tier)));
        textList.add(textList.size(), Component.translatable("ctnh.rune_energy",String.format("%.2f",rune_energy)));
        textList.add(textList.size(), Component.translatable("ctnh.mana_production",String.format("%.2f",energy_caculate(rune_energy,energy_tier))));
        textList.add(textList.size(), Component.translatable("ctnh.rune_consumption",String.format("%.2f",(rune_energy/50)*Math.log(rune_energy/50+1))));
        textList.add(textList.size(), Component.translatable("ctnh.quasar_parallel",String.format("%.2f",energy_caculate(rune_energy,energy_tier)*5)));
        textList.add(textList.size(), Component.translatable("ctnh.consumption_parallel",String.format("%.2f",(1-0.05*Math.max((rune_energy-50)/50,10)))));
        textList.add(textList.size(),Component.translatable("ctnh.quasar.tip.3",(double)power/100000000+"E EU"));
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if(machine instanceof Quasar_Eye qmachine){
            if(recipe.recipeType.equals(CTNHRecipeTypes.QUASAR_CREATE))
            {

                var true_eut=Math.min(qmachine.power/200,Long.MAX_VALUE);
                if(true_eut<10000000)return ModifierFunction.NULL;
                var outputmuti=true_eut/50000000L;
                qmachine.power=0;
                return ModifierFunction.builder()
                        .eutMultiplier(true_eut)
                        .outputModifier(ContentModifier.multiplier(outputmuti))
                        .build();
            }
            var EUt = RecipeHelper.getOutputEUt(recipe);
            var tier=recipe.data.getInt("tier");
            var power=(long)(qmachine.energy_caculate(qmachine.rune_energy,tier)*RecipeHelper.getOutputEUt(recipe)*(qmachine.energy_caculate(qmachine.rune_energy,tier)*5)*recipe.duration*0.2*(qmachine.rune_energy/25));
            qmachine.power+=power/200;
            return ModifierFunction.builder()
                    .eutMultiplier(qmachine.energy_caculate(qmachine.rune_energy,tier))
                    .durationMultiplier(qmachine.energy_caculate(qmachine.rune_energy,tier)*5)
                    .inputModifier(ContentModifier.multiplier(qmachine.energy_caculate(qmachine.rune_energy,tier)*5*(1-0.05*Math.max((qmachine.rune_energy-50)/50,10))))
                    .outputModifier(ContentModifier.multiplier(qmachine.energy_caculate(qmachine.rune_energy,tier)*5*(1-0.05*Math.max((qmachine.rune_energy-50)/50,10))))
                    .build();
        }
        return ModifierFunction.NULL;
    }
    @Override
    public void saveCustomPersistedData(@NotNull CompoundTag tag, boolean forDrop) {
        super.saveCustomPersistedData(tag, forDrop);
        if (!forDrop) {
            tag.putInt(ACTIVE, active);
            tag.putInt(ET,energy_tier);
            tag.putDouble(RUNE_ENERGY,rune_energy);
        }
    }

    @Override
    public void loadCustomPersistedData(@NotNull CompoundTag tag) {
        super.loadCustomPersistedData(tag);
        active = tag.contains(ACTIVE) ? tag.getInt(ACTIVE) : 0;
        energy_tier=tag.contains(ET)? tag.getInt(ET):0;
        rune_energy=tag.contains(RUNE_ENERGY)?tag.getDouble(RUNE_ENERGY):0;
    }
    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }
}