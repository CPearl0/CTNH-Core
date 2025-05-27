package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import io.github.cpearl0.ctnhcore.api.machine.computation.MultiblockComputationMachine;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.MultiblockComputationLogic;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CompilerMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.gregtechceu.gtceu.api.GTValues.IV;

public class NeuroMatrixCompiler extends WorkableElectricMultiblockMachine implements ITieredMachine{
    public NeuroMatrixCompiler(IMachineBlockEntity holder) {
        super(holder);
    }
    @Persisted public GTRecipe lastrecipe;
    @Persisted public List<Long> Equation=new ArrayList<Long>();
    @Persisted public List<Long> RawEquation;
    @Persisted public long Noise=1;
    @Persisted public long range;
    @Persisted public CompilerMachine part1;
    @Persisted public CompilerMachine part2;
    @Persisted public CompilerMachine part3;
    @Persisted public CompilerMachine part4;
    @Persisted public CompilerMachine part5;
    @Persisted public List<Integer> states=new ArrayList<>();
    @Persisted public double Noise_Muti=5.0;
    @Persisted public int tiers=0;
    @Persisted public int turn=0;
    @Persisted public long ticks=0;
    @Override
    public boolean onWorking() {

        if(turn>4)
        {
             getRecipeLogic().setProgress(getRecipeLogic().getDuration());
        }else states.set(turn,1);
        if (getRecipeLogic().getProgress()>100&&turn<=4) {
            states.set(turn,3);
            ticks=getOffsetTimer();
            var tier = getTier();
            turn+=1;
            getRecipeLogic().setProgress(0);
        }
        return super.onWorking();
    }
    @Override
     public void afterWorking() {
        turn=0;
        super.afterWorking();
        for(int i=0;i<=4;i++)
        {
            states.set(i,0);
        }
    }
    @Override
    public void onStructureFormed() {

        var tier = getTier();
        var pos=getPos();
        var m1=getMachine(this.getLevel(), MachineUtils.getOffset(this,11 ,0, -6));
        if(m1 instanceof CompilerMachine)
        {
            part1=(CompilerMachine) m1;
            tiers=part1.getTier();
        }
        var m2=getMachine(this.getLevel(), MachineUtils.getOffset(this,-11,0,-6));
        var m3=getMachine(this.getLevel(), MachineUtils.getOffset(this,15,0,7));
        var m4=getMachine(this.getLevel(), MachineUtils.getOffset(this,-15,0,7));
        var m5=getMachine(this.getLevel(), MachineUtils.getOffset(this,0,0,22));
        if(m2 instanceof IMultiPart)
        {
            part2=(CompilerMachine) m2;
        }
        if(m3 instanceof IMultiPart)
        {
            part3=(CompilerMachine) m3;
        }
        if(m4 instanceof IMultiPart)
        {
            part4=(CompilerMachine) m4;
        }
        if(m5 instanceof IMultiPart)
        {
            part5=(CompilerMachine) m5;
        }
        if(part2.getTier()!=tier||part3.getTier()!=tier||part4.getTier()!=tier||part5.getTier()!=tier)
            onStructureInvalid();
        for(int i=0;i<=4;i++)
        {
            states.add(0);
        }
        super.onStructureFormed();

    }
    @Override
    public void onStructureInvalid()
    {
        super.onStructureInvalid();
        states.clear();
    }
    public Boolean CheckRecipeValid(GTRecipe recipe,List<Item>itemList)
    {
        //这代码写的太丑陋了，我自己都受不了了
       var p=part1.getInventory().getStackInSlot(0).getItem().asItem();
        var p1=part1.getInventory().getStackInSlot(0);
        var p2=part2.getInventory().getStackInSlot(0);
        var p3=part3.getInventory().getStackInSlot(0);
        var p4=part4.getInventory().getStackInSlot(0);
        var p5=part5.getInventory().getStackInSlot(0);
        List<ItemStack> itemcontains=new ArrayList<ItemStack>();
        itemcontains.add(p1);
        itemcontains.add(p2);
        itemcontains.add(p3);
        itemcontains.add(p4);
        itemcontains.add(p5);
        for(int i=0;i<itemList.size();i++)
        {
            if(!itemcontains.get(i).getItem().asItem().equals(itemList.get(i).asItem()))
            {
                return false;
            }
        }
        return true;
    }
    public int RecipeConsume(int turn)
    {
        //这写的就是一坨
        if(turn>5)
        {
            return 0;
        }
        var p1=part1.getInventory();
        var p2=part2.getInventory();
        var p3=part3.getInventory();
        var p4=part4.getInventory();
        var p5=part5.getInventory();
        List<NotifiableItemStackHandler> Inventorys=new ArrayList<NotifiableItemStackHandler>();
        Inventorys.add(p1);
        Inventorys.add(p2);
        Inventorys.add(p3);
        Inventorys.add(p4);
        Inventorys.add(p5);
        var inventory=Inventorys.get(turn-1);
        var num=inventory.getStackInSlot(0).getCount();
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        return num;
    }
    public List<Long> getRawEquation(int num, long Noise)
    {
        var noise=(long)(Noise*Math.random()*Noise_Muti);
        List<Long>Equation=new ArrayList<Long>();
        for(int i=1;i<num;i++)
        {
            Equation.add((long)(Math.random()*(range-Math.sqrt(range))+Math.sqrt(range)));
        }
        Equation.add(noise);
        return Equation;
    }
    public Long GetAnswer()
    {
        long answer=0;
        List<Long> Equationer=new ArrayList<Long>();
        Equationer.add(3L);
        Equationer.add(2L);
        for(int i=0;i<RawEquation.size()-1;i++)
        {
            answer=RawEquation.get(i)*Equationer.get(i);
        }
        answer+=(long)(Math.random()*(range*range)*Noise);
        return answer;
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var itemsR = recipe.getInputContents(ItemRecipeCapability.CAP).stream()
                .map(num -> (SizedIngredient) num.getContent()) // 转换为 SizedIngredient
                .map(SizedIngredient::getItems) // 获取 ItemStack 数组
                .filter(itemStacks -> itemStacks.length > 0) // 确保至少有一个 ItemStack
                .map(itemStacks -> itemStacks[0].getItem()) // 获取第一个 ItemStack 的 Item
                .collect(Collectors.toList()); // 收集到一个 List<Item>
//        CheckRecipeValid(recipe,itemsR);
        ticks=getOffsetTimer();
        return super.beforeWorking(recipe);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if(machine instanceof NeuroMatrixCompiler nmachine) {
            var itemsR = recipe.getInputContents(ItemRecipeCapability.CAP).stream()
                    .map(num -> (SizedIngredient) num.getContent()) // 转换为 SizedIngredient
                    .map(SizedIngredient::getItems) // 获取 ItemStack 数组
                    .filter(itemStacks -> itemStacks.length > 0) // 确保至少有一个 ItemStack
                    .map(itemStacks -> itemStacks[0].getItem()) // 获取第一个 ItemStack 的 Item
                    .collect(Collectors.toList()); // 收集到一个 List<Item>
            for(int i=0;i<=4;i++)
            {
                nmachine.states.set(i,4);
            }
            int parallel = ParallelLogic.getParallelAmount(machine, recipe, 8);
            var reduce = new ContentModifier(0.5 * parallel, 0);
            if (parallel == 0)
                return ModifierFunction.NULL;
            return ModifierFunction.builder()
                    .eutModifier(reduce)
                    .inputModifier(ContentModifier.multiplier(parallel))
                    .outputModifier(ContentModifier.multiplier(parallel))
                    .parallels(parallel)
                    .build();
        }
        return ModifierFunction.NULL;
    }
    @Override
    public void addDisplayText(List<Component> textList) {
        //super.addDisplayText(textList);
        MultiblockDisplayText.builder(textList, this.isFormed())
                .setWorkingStatus(this.recipeLogic.isWorkingEnabled(), this.recipeLogic.isActive())
                .addMachineModeLine(this.getRecipeType(), this.getRecipeTypes().length > 1)
                .addWorkingStatusLine()
                .addProgressLine((double)this.recipeLogic.getProgress(), (double)this.recipeLogic.getMaxProgress(), this.recipeLogic.getProgressPercent())
                .addCustom(List->{
                    for(int i=0;i<states.size();i++)
                    {
                        List.add(addProgressPartSatus(i));
                    }
                });
        this.getDefinition().getAdditionalDisplay().accept(this, textList);
    }
    public MutableComponent addProgressPartSatus(int i) {
        if(states.get(i)==0)
        {
            return (Component.translatable("ctnh.compiler.part_states", new Object[]{i+1,Component.translatable("ctnh.compiler.state.idle")}));
        }
        if(states.get(i)==1)
        {
            return (Component.translatable("ctnh.compiler.part_states", new Object[]{i+1,Component.translatable("ctnh.compiler.state.working"),(double)this.recipeLogic.getProgress(), (double)this.recipeLogic.getMaxProgress()}));
        }
        if(states.get(i)==2)
        {
            return (Component.translatable("ctnh.compiler.part_states", new Object[]{i+1,Component.translatable("ctnh.compiler.state.error")}));
        }
        if(states.get(i)==3)
        {
            return (Component.translatable("ctnh.compiler.part_states", new Object[]{i+1,Component.translatable("ctnh.compiler.state.finish")}));
        }
        if(states.get(i)==4)
        {
            return (Component.translatable("ctnh.compiler.part_states", new Object[]{i+1,Component.translatable("ctnh.compiler.state.waiting")}));
        }
        return (Component.translatable("ctnh.compiler.part_states", new Object[]{i+1,Component.translatable("ctnh.compiler.state.error")}));
    }


}
