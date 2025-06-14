package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.machines.GTMultiMachines.FUSION_REACTOR;

public class MachineUtils {
    public static boolean inputItem(ItemStack itemStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().inputItems(itemStack).buildRawRecipe();
        if (Recipe.matchRecipe(machine).isSuccess()) {
            Recipe.handleRecipeIO(IO.IN, machine, machine.getRecipeLogic().getChanceCaches());
            return true;
        }
        return false;
    }
    public static boolean inputFluid(FluidStack fluidStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().inputFluids(fluidStack).buildRawRecipe();
        if (Recipe.matchRecipe(machine).isSuccess()) {
            Recipe.handleRecipeIO(IO.IN, machine, machine.getRecipeLogic().getChanceCaches());
            return true;
        }
        return false;
    }
    //返回值:处理剩下的
    public static int inputFluidBrute(FluidStack fluidStack, List<FluidHatchPartMachine> source){
        int ret = fluidStack.getAmount();
        for(var part : source){
            var tankStorages = part.tank.getStorages();
            for (var tankStorage : tankStorages) {
                FluidStack fs = tankStorage.getFluid();
                if (fs.getFluid() != fluidStack.getFluid()) continue;
                int toDrain = Math.min(fs.getAmount(), ret);
                tankStorage.drain(toDrain, IFluidHandler.FluidAction.EXECUTE);
                ret -= toDrain;
                if(ret == 0)break;
            }
        }
        return ret;
    }
    public static int getFluidStorageBrute(Fluid fluidType,List<FluidHatchPartMachine> source){
        int ret = 0;
        for(var part : source){
            var tankStorages = part.tank.getStorages();
            for (var tankStorage : tankStorages) {
                FluidStack fs = tankStorage.getFluid();
                if (fs.getFluid() != fluidType) continue;
                ret += fs.getAmount();
            }
        }
        return ret;
    }
    public static boolean outputFluid(FluidStack fluidStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().outputFluids(fluidStack).buildRawRecipe();
        if (Recipe.matchRecipe(machine).isSuccess()) {
            Recipe.handleRecipeIO(IO.OUT, machine, machine.getRecipeLogic().getChanceCaches());
            return true;
        }
        return false;
    }
    public static  boolean inputCWUT(int CWU,WorkableMultiblockMachine machine){
        var Recipe = GTRecipeBuilder.ofRaw().inputCWU(CWU).buildRawRecipe();
        if(Recipe.matchRecipe(machine).isSuccess())
        {
            Recipe.handleRecipeIO(IO.IN,machine,machine.getRecipeLogic().getChanceCaches());
            return true;
        }
        return false;
    }
    public static boolean canInputFluid(FluidStack fluidStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().inputFluids(fluidStack).buildRawRecipe();
        return Recipe.matchRecipe(machine).isSuccess();
    }
    public static boolean canOutputFluid(FluidStack fluidStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().outputFluids(fluidStack).buildRawRecipe();
        return Recipe.matchRecipe(machine).isSuccess();
    }
    public static boolean canInputItem(ItemStack itemStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().inputItems(itemStack).buildRawRecipe();
        return Recipe.matchRecipe(machine).isSuccess();
    }
    public static BlockPos getOffset(MetaMachine machine,int leftoff, int upoff, int backoff) {
        var pos = machine.getPos();
        var facing = machine.getFrontFacing();
        switch (facing) {
            case NORTH -> {
                return pos.offset(leftoff,upoff,backoff);
            }
            case SOUTH -> {
                return pos.offset(-leftoff,upoff,-backoff);
            }
            case WEST -> {
                return pos.offset(backoff,upoff,-leftoff);
            }
            case EAST -> {
                return pos.offset(-backoff,upoff,leftoff);
            }
        }
        return pos;
    }
    public static AABB getArea(MetaMachine machine,int left1, int up1, int back1, int left2, int up2, int back2) {
        var pos = machine.getPos();
        var facing = machine.getFrontFacing();
        switch (facing) {
            case NORTH -> {
                return AABB.of(BoundingBox.fromCorners(pos.offset(left1,up1,back1),pos.offset(left2,up2,back2)));
            }
            case SOUTH -> {
                return AABB.of(BoundingBox.fromCorners(pos.offset(-left1,up1,-back1),pos.offset(-left2,up2,-back2)));
            }
            case WEST -> {
                return AABB.of(BoundingBox.fromCorners(pos.offset(back1,up1,-left1),pos.offset(back2,up2,-left2)));
            }
            case EAST -> {
                return AABB.of(BoundingBox.fromCorners(pos.offset(-back1,up1,left1),pos.offset(-back2,up2,left2)));
            }
        }
        return AABB.of(BoundingBox.fromCorners(pos,pos));
    }

}
