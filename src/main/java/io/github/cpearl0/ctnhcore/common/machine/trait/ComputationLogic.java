package io.github.cpearl0.ctnhcore.common.machine.trait;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.recipe.CWURecipeCapability;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.utils.GTUtil;
import lombok.Getter;
import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.api.GTValues.*;

public class ComputationLogic extends RecipeLogic {
    @Getter
    public IOpticalComputationProvider computationContainer;
    @Getter
    int lastCWUt=0;

    public ComputationLogic(IRecipeLogicMachine machine) {
        super(machine);
    }


    @Override
    public GTRecipe.ActionResult handleTickRecipe(GTRecipe recipe) {
        GTRecipe.ActionResult ret;
        if(!checkCWUt(recipe,false))
            ret=GTRecipe.ActionResult.fail(() -> Component.translatable("ctnhcore.recipe_logic.insufficient_cwut"));
        else ret=super.handleTickRecipe(recipe);
        return ret;
    }

    @Override
    public void setupRecipe(GTRecipe recipe)
    {
        if(checkCWUt(recipe,true)) super.setupRecipe(recipe);
    }

    private int requestCWUt(int cwut,boolean simulate)
    {
        return ( lastCWUt = computationContainer.requestCWUt(cwut,simulate));
    }

    public boolean checkCWUt(GTRecipe recipe,boolean simulate)
    {
//        assert recipe != null;
        if(computationContainer ==null)return false;
        if(recipe==null)return true;
        /*如果没有规定算力大小,就按HV=1算,电压每超过hv一级算力就翻倍*/
        int CWUtToRequest=getCWUtToRequest(recipe);
        if(CWUtToRequest<=0)return true;
        int requestedCWUt=requestCWUt(CWUtToRequest,simulate) ;
        return requestedCWUt>=CWUtToRequest;
    }
    /*在checkCWUt里调用，如果要修改机器需要的算力，重写这个方法就行*/
    public int getCWUtToRequest(GTRecipe recipe) {
        /*这是默认的算力计算方式，初始算力为0，HV为1,每超过HV一级就翻倍一次*/
        int ret=0;
        int tier= GTUtil.getTierByVoltage(RecipeHelper.getInputEUt(recipe));
        if(tier>=HV)
        {
            ret=1<<(tier-HV);
        }
        return ret;
    }

    /*获取配方的算力,理论上来说应该写helper里的*/
    static public int getRecipeCWUt(GTRecipe recipe){
        return recipe.getTickInputContents(CWURecipeCapability.CAP).stream()
                .map(Content::getContent)
                .mapToInt(CWURecipeCapability.CAP::of)
                .sum();
    }

}
