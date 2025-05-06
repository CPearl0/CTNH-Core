package io.github.cpearl0.ctnhcore.common.machine.trait;

import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.api.machine.trait.SimpleComputationContainer;
import io.github.cpearl0.ctnhcore.common.machine.computation.SimpleComputationMachine;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.chat.Component;

import java.util.*;

import static com.gregtechceu.gtceu.api.GTValues.HV;

public class SimpleComputationLogic extends RecipeLogic {

    @Setter
    private SimpleComputationContainer computationContainer;
    public SimpleComputationLogic(SimpleComputationMachine machine) {
        super(machine);
        computationContainer = machine.getComputationContainer();
    }
    /*不是我想搞这么神秘，而是看不懂GTM的Capability(*/
    int lastCWUt;
    /*
    @Override
    protected boolean handleRecipeIO(GTRecipe recipe, IO io) {
        return checkCWUt(recipe) && super.handleRecipeIO(recipe, io);
    }

    @Override
    protected boolean handleTickRecipeIO(GTRecipe recipe, IO io) {
        return checkCWUt(recipe) && super.handleTickRecipeIO(recipe, io);
    }

     */
    @Override
    public boolean handleFuelRecipe() {
        if (!needFuel() || fuelTime > 0) return true;
        Iterator<GTRecipe> iterator = machine.getRecipeType().searchFuelRecipe(machine);
        while (iterator != null && iterator.hasNext()) {
            GTRecipe recipe = iterator.next();
            if (recipe == null || !checkCWUt(recipe,false)) continue;
            if (recipe.checkConditions(this).isSuccess() && handleRecipeIO(recipe, IO.IN)) {
                fuelMaxTime = recipe.duration;
                fuelTime = fuelMaxTime;
            }
            if (fuelTime > 0) return true;
        }
        return false;
    }

    @Override
    public GTRecipe.ActionResult handleTickRecipe(GTRecipe recipe) {
        var ret=super.handleTickRecipe(recipe);
        if(!checkCWUt(recipe,false))
            ret=GTRecipe.ActionResult.fail(() -> Component.translatable("gtceu.recipe_logic.insufficient_cwut"));
        return ret;
    }

    @Override
    public void onRecipeFinish() {
        super.onRecipeFinish();
        lastCWUt = 0;
    }

    @Override
    /*重写了tickRecipe部分,来绕过算力检查*/
    public Iterator<GTRecipe> searchRecipe() {
        var holder = this.machine;
        var recipeType = this.machine.getRecipeType();
        if (!holder.hasProxies()) return null;
        var iterator = recipeType.getLookup().getRecipeIterator(holder, recipe ->
                !recipe.isFuel
                && recipe.matchRecipe(holder).isSuccess()
                && matchTickRecipe(recipe,holder)); /*在这动手脚*/
        boolean any = false;
        while (iterator.hasNext()) {
            GTRecipe recipe = iterator.next();
            if (recipe == null) continue;
            any = true;
            break;
        }
        if (any) {
            iterator.reset();
            return iterator;
        }
        for (GTRecipeType.ICustomRecipeLogic logic : recipeType.getCustomRecipeLogicRunners()) {
            GTRecipe recipe = logic.createCustomRecipe(holder);
            if (recipe != null) return Collections.singleton(recipe).iterator();
        }
        return Collections.emptyIterator();
    }
    protected boolean matchTickRecipe(GTRecipe recipe,IRecipeLogicMachine holder)
    {
        lastCWUt = getRecipeCWUt(recipe);
        recipe.tickInputs.remove(CWURecipeCapability.CAP);
        var ret = recipe.matchTickRecipe(holder);
        return ret.isSuccess()&&checkCWUt(recipe,true);
    }

    private boolean checkCWUt(GTRecipe recipe,boolean simulate)
    {
//        assert recipe != null;
        if(computationContainer ==null)return false;
        if(recipe==null)return true;
        /*如果没有规定算力大小,就按HV=1算,电压每超过hv一级算力就翻倍*/
        int CWUtToRequest=Math.max(lastCWUt,1);/*这里的获取还没有搞定，Capabilities太阴了还没搞懂，用邪道也弄不出来，所以跑出来都是默认值*/
        int tier= GTUtil.getTierByVoltage(RecipeHelper.getInputEUt(recipe));
        if(tier>HV)
        {
            CWUtToRequest<<=(tier-HV);
        }
        int requestedCWUt= computationContainer.requestCWUt(CWUtToRequest,simulate);
        return requestedCWUt>=CWUtToRequest;
    }

    /*获取配方的算力,理论上来说应该写helper里的*/
    protected int getRecipeCWUt(GTRecipe recipe){
        return recipe.getTickInputContents(CWURecipeCapability.CAP).stream()
                .map(Content::getContent)
                .mapToInt(CWURecipeCapability.CAP::of)
                .sum();
    }

}
