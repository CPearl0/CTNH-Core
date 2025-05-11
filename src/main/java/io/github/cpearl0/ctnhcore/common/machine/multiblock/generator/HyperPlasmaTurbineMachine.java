package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.api.machine.computation.MultiblockComputationMachine;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.MultiblockComputationLogic;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HyperPlasmaTurbineMachine extends MultiblockComputationMachine implements IExplosionMachine {
    public static final long BASE_EU_OUTPUT = GTValues.V[GTValues.ZPM]*288;/*有算力时的基础功率*/
    public static final long DEFAULT_EU_OUTPUT = GTValues.V[GTValues.ZPM];/*没有算力时的默认功率*/
    public static final int CWUtStair = 64;
    private int lastCWUt;
    public HyperPlasmaTurbineMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }
    @Override
    public long getOverclockVoltage() {
        /*算力不足时，功率为zpm1a，算力足够时以基础功率zpm288a为底，每多提供64算力就翻倍输出功率*/
        var cwut = getMaxCWUt();
        if(cwut<CWUtStair)
        {
            return DEFAULT_EU_OUTPUT;
        }
        return (BASE_EU_OUTPUT << (int)(cwut / CWUtStair -1));
    }
    /*产能效率*/
    static public double getEfficiency() {
        return 4.0;
    }

    private int requestCWUt(int cwut,boolean simulate)
    {
        return ( lastCWUt = getComputationProvider().requestCWUt(cwut, simulate));
    }
    private int getMaxCWUt() {
        var provider = getComputationProvider();
        if(provider==null)return 0;
        return provider.getMaxCWUt();
    }
    //////////////////////////////////////
    // ****** Recipe Logic *******//
    //////////////////////////////////////

    @Override
    public RecipeLogic createRecipeLogic(Object... args) {
        return new HyperPlasmaTurbineRecipeLogic(this);
    }
    @Override
    public MultiblockComputationLogic getRecipeLogic() {
        return (HyperPlasmaTurbineRecipeLogic) recipeLogic;
    }
    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof HyperPlasmaTurbineMachine hptm)) {
            return RecipeModifier.nullWrongType(HyperPlasmaTurbineMachine.class, machine);
        }


        long EUt = RecipeHelper.getOutputEUt(recipe);
        long turbineMaxVoltage = hptm.getOverclockVoltage();

        if (EUt <= 0 || turbineMaxVoltage <= EUt) return ModifierFunction.NULL;

        // get the amount of parallel required to match the desired output voltage
        double euMultiplier = getEfficiency();
        int maxParallel = (int) ( turbineMaxVoltage / EUt );
        int actualParallel = ParallelLogic.getParallelAmountFast(hptm, recipe, maxParallel);

        return ModifierFunction.builder()
                .inputModifier(ContentModifier.multiplier(actualParallel))
                .outputModifier(ContentModifier.multiplier(actualParallel))
                .eutMultiplier(actualParallel)
                .parallels(actualParallel)
                .durationMultiplier(euMultiplier)
                .build();
    }



    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }



    @Override
    public boolean canVoidRecipeOutputs(RecipeCapability<?> capability) {
        return capability != EURecipeCapability.CAP;
    }

    //////////////////////////////////////
    // ******* GUI ********//
    //////////////////////////////////////

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            int maxCUWt = getMaxCWUt();
            textList.add(Component.translatable("gtceu.multiblock.computation.max",
                    FormattingUtil.formatNumbers(maxCUWt)));
            textList.add(Component.translatable("gtceu.multiblock.computation.usage",
                    FormattingUtil.formatNumbers(lastCWUt)));
            textList.add(Component.translatable("gtceu.multiblock.turbine.efficiency",
                    getEfficiency()*100));

            long maxProduction = getOverclockVoltage();
            long currentProduction = isActive() && recipeLogic.getLastRecipe() != null ?
                    RecipeHelper.getOutputEUt(recipeLogic.getLastRecipe()) : 0;

            if (isActive()) {
                textList.add(3, Component.translatable("gtceu.multiblock.turbine.energy_per_tick",
                        FormattingUtil.formatNumbers(currentProduction),
                        FormattingUtil.formatNumbers(maxProduction)));
            }

        }
    }



    private static class HyperPlasmaTurbineRecipeLogic  extends MultiblockComputationLogic
    {

        public HyperPlasmaTurbineRecipeLogic(MultiblockComputationMachine machine) {
            super(machine);
        }
        public static int fastLog2(long n){
            return ((Long.SIZE-1) - Long.numberOfLeadingZeros((long)n));
        }
        public static int fastLogBased2(long n,long m){
            return fastLog2(n/m);
        }
        @Override
        public int getCWUtToRequest(GTRecipe recipe) {
            var EUt = RecipeHelper.getOutputEUt(recipe);
            if(EUt<BASE_EU_OUTPUT)return 0;
            return CWUtStair * fastLogBased2(EUt,BASE_EU_OUTPUT);
        }
        @Override
        public boolean checkCWUt(GTRecipe recipe, boolean simulate) {
            var b = super.checkCWUt(recipe, simulate);
            if(!simulate&&!b){
                doExplosion();
            }
            return b;
        }

        private void doExplosion() {
            if (machine instanceof HyperPlasmaTurbineMachine hptm) {
                this.setStatus(Status.IDLE);/*不加会NPE*/
                hptm.doExplosion(20f);
            }
        }
    }
}
