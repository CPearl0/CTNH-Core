package io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class MeadowMachine extends KineticMachine {
    public int CowCount = 0;
    public int SheepCount = 0;
    public int ChickenCount = 0;
    public int PigCount = 0;
    public MeadowMachine(IMachineBlockEntity holder) {
        super(holder);
    }
    public static GTRecipe recipmeModifier(MetaMachine machine, GTRecipe recipe, OCParams params, OCResult result) {
        if (machine instanceof MeadowMachine mmachine){
            var level = mmachine.getLevel();
            var aabb = MachineUtils.getArea(mmachine,-5,0,0,5,2,10);
            mmachine.CowCount = 0;
            mmachine.SheepCount = 0;
            mmachine.ChickenCount = 0;
            mmachine.PigCount = 0;
            if (level != null) {
                var entities = level.getEntities(null,aabb);
                entities.forEach(entity -> {
                    if(entity.getType().equals(EntityType.COW)){
                        mmachine.CowCount ++;
                    }
                    else if(entity.getType().equals(EntityType.SHEEP)){
                        mmachine.SheepCount ++;
                    }
                    else if(entity.getType().equals(EntityType.CHICKEN)){
                        mmachine.ChickenCount ++;
                    }
                    else if(entity.getType().equals(EntityType.PIG)){
                        mmachine.PigCount ++;
                    }
                });
            }
            if(mmachine.CowCount == 0 && mmachine.SheepCount == 0 && mmachine.ChickenCount == 0 && mmachine.PigCount == 0){
                return recipe;
            }
            var newrecipe = GTRecipeBuilder.ofRaw()
                    .outputItems(new ItemStack(Items.LEATHER,mmachine.CowCount))
                    .outputItems(new ItemStack(Items.WHITE_WOOL,mmachine.SheepCount))
                    .outputItems(new ItemStack(Items.EGG,mmachine.ChickenCount))
                    .outputItems(new ItemStack(Items.PORKCHOP, mmachine.PigCount))
                    .outputItems(new ItemStack(CTNHItems.ANIMAL_EXCRETA,mmachine.CowCount + mmachine.SheepCount + mmachine.ChickenCount + mmachine.PigCount))
                    .outputFluids(new FluidStack(GTMaterials.Milk.getFluid(),250 * mmachine.CowCount))
                    .buildRawRecipe();
            if (newrecipe.matchRecipe(mmachine).isSuccess()) {
                return newrecipe;
            }
            return recipe;
        }
        return recipe;
    }
}
