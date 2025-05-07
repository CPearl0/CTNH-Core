package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.IObjectHolder;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.capability.recipe.CWURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.api.machine.trait.SimpleComputationContainer;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import static com.gregtechceu.gtceu.api.GTValues.HV;

public class LaserSorter extends WorkableElectricMultiblockMachine implements ITieredMachine, IOpticalComputationReceiver {
    int lastCWUt;

    public LaserSorter(IMachineBlockEntity holder) {super(holder);}
    @Getter
    private IOpticalComputationProvider computationProvider;
    @Override

    public void onStructureFormed() {
        super.onStructureFormed();
        for (IMultiPart part : getParts()) {

            part.self().holder.self()
                    .getCapability(GTCapability.CAPABILITY_COMPUTATION_PROVIDER)
                    .ifPresent(provider -> this.computationProvider = provider);
        }

        // should never happen, but would rather do this than have an obscure NPE
        if (computationProvider == null) {
            onStructureInvalid();
        }
    }
    protected boolean matchTickRecipe(GTRecipe recipe, IRecipeLogicMachine holder)
    {
        recipe.tickInputs.remove(CWURecipeCapability.CAP);
        var ret = recipe.matchTickRecipe(holder);
        return ret.isSuccess()&&checkCWUt(recipe,true);
    }
    private boolean checkCWUt(GTRecipe recipe,boolean simulate)
    {
//        assert recipe != null;
        if(computationProvider ==null)return false;
        if(recipe==null)return true;
        /*如果没有规定算力大小,就按HV=1算,电压每超过hv一级算力就翻倍*/
        int CWUtToRequest=Math.max(lastCWUt,1);/*这里的获取还没有搞定，Capabilities太阴了还没搞懂，用邪道也弄不出来，所以跑出来都是默认值*/
        int tier= GTUtil.getTierByVoltage(RecipeHelper.getInputEUt(recipe));
        if(tier>HV)
        {
            CWUtToRequest<<=(tier-HV);
        }
        int requestedCWUt= computationProvider.requestCWUt(CWUtToRequest,simulate);
        return requestedCWUt>=CWUtToRequest;
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        int cwut=recipe.data.getInt(("cwut"));
        int geter=computationProvider.requestCWUt(24,true);
        int maxer= computationProvider.getMaxCWUt();
        if(cwut<computationProvider.requestCWUt(cwut,false))
        {
            return false;
        }
        return super.beforeWorking(recipe);
    }

}
