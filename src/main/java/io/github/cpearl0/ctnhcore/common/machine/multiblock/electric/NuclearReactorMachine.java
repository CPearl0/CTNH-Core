package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class NuclearReactorMachine extends WorkableElectricMultiblockMachine {
    public float heat;
    public int coolant_amount = 0;
    public int consume_amount = 0;
    public Material current_coolant;
    public NuclearReactorMachine(IMachineBlockEntity holder) {
        super(holder);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if(machine instanceof NuclearReactorMachine nmachine) {
            nmachine.heat = recipe.data.getFloat("heat");
            return ModifierFunction.IDENTITY;
        }
        return ModifierFunction.NULL;
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            for (var part : getParts()) {
                for (var trait : part.getRecipeHandlers()) {
                    if (trait.getHandlerIO() == IO.IN) {
                        for (var contents : trait.getContents()) {
                            if (contents instanceof FluidStack fluidStack) {
                                for (var coolant : Coolant) {
                                    if (fluidStack.getFluid().equals(coolant.inputMaterial.getFluid())) {
                                        if (MachineUtils.canInputFluid(coolant.inputMaterial.getFluid((int) (heat * coolant.consume_count)), this)
                                                && MachineUtils.canOutputFluid(coolant.outputMaterial.getFluid((int) (heat * coolant.consume_count)), this)) {
                                            current_coolant = coolant.inputMaterial;
                                            coolant_amount = fluidStack.getAmount();
                                            consume_amount = (int) (heat * coolant.consume_count);
                                            MachineUtils.inputFluid(coolant.inputMaterial.getFluid(consume_amount), this);
                                            MachineUtils.outputFluid(coolant.outputMaterial.getFluid(consume_amount), this);
                                            recipeLogic.setProgress(Math.min(getProgress() + 20, getMaxProgress()));
                                            return super.onWorking();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onWorking();
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            String coolantName = "None";
            if(current_coolant != null) {
                coolantName = current_coolant.getFluid().getFluidType().getDescriptionId();
            }
            textList.add(textList.size(), Component.translatable("multiblock.ctnh.nuclear_reactor.coolant",coolantName));
            textList.add(textList.size(), Component.translatable("multiblock.ctnh.nuclear_reactor.coolant_amount", coolant_amount));
            textList.add(textList.size(), Component.translatable("multiblock.ctnh.nuclear_reactor.consume_amount", consume_amount));
        }
    }
    public static class coolantMaterial {
        Material inputMaterial;
        Material outputMaterial;
        float consume_count;
        public coolantMaterial(Material inputMaterial, Material outputMaterial, float consume_count) {
            this.inputMaterial = inputMaterial;
            this.outputMaterial = outputMaterial;
            this.consume_count = consume_count;
        }
    }
    public static List<coolantMaterial> Coolant = List.of(
            new coolantMaterial(GTMaterials.Steam, CTNHMaterials.HotSteam, 100),
            new coolantMaterial(GTMaterials.Deuterium, CTNHMaterials.HotDeuterium, (float) 100 / 3),
            new coolantMaterial(GTMaterials.Sodium, CTNHMaterials.HotSodium, 18.75F),
            new coolantMaterial(GTMaterials.SodiumPotassium, CTNHMaterials.HotSodiumPotassium, (float) 50 / 3)
    );
}
