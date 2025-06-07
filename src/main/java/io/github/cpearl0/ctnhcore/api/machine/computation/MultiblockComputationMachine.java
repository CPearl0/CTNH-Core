package io.github.cpearl0.ctnhcore.api.machine.computation;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IObjectHolder;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableComputationContainer;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.MultiblockComputationLogic;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.SimpleComputationLogic;
import lombok.Getter;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class MultiblockComputationMachine extends WorkableElectricMultiblockMachine implements ITieredMachine, IOpticalComputationReceiver {


    private IOpticalComputationProvider computationContainer;

    public MultiblockComputationMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }


    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        for (IMultiPart part : getParts()) {

            part.self().holder.self()
                    .getCapability(GTCapability.CAPABILITY_COMPUTATION_PROVIDER)
                    .ifPresent(provider -> this.computationContainer = provider);
        }
        if (computationContainer == null) {
            onStructureInvalid();
        }
    }
    @Override
    public void onStructureInvalid() {
        computationContainer = null;
        super.onStructureInvalid();
    }

    @Override
    public IOpticalComputationProvider getComputationProvider()
    {
        if ( computationContainer instanceof NotifiableComputationContainer notifiableContainer)
        {
            return notifiableContainer.getComputationProvider();
        }
        else if(computationContainer instanceof MachineTrait trait)
        {
            /*代码来自 NotifiableComputationContainer::getComputationProvider()*/
            for (Direction direction : GTUtil.DIRECTIONS) {
                BlockEntity blockEntity = trait.getMachine().getLevel().getBlockEntity(trait.getMachine().getPos().relative(direction));
                if (blockEntity == null) continue;
                // noinspection DataFlowIssue can be null just fine.
                IOpticalComputationProvider provider = blockEntity.getCapability(GTCapability.CAPABILITY_COMPUTATION_PROVIDER, direction.getOpposite()).orElse(null);
                // noinspection ConstantValue can be null because above.
                if (provider != null && provider != this) {
                    return provider;
                }
            }
        }
        return null;

    }

    public int getMaxCWUt() {
        var provider = getComputationProvider();
        if(provider==null)return 0;
        return provider.getMaxCWUt();
    }
    public int getLastCWUt() {
        return getRecipeLogic().getLastCWUt();
    }

    //////////////////////////////////////
    // ****** Recipe Logic *******//
    //////////////////////////////////////

    @Override
    @NotNull
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new MultiblockComputationLogic(this);
    }
    @Override
    @NotNull
    public MultiblockComputationLogic getRecipeLogic() {
        return (MultiblockComputationLogic)super.getRecipeLogic();
    }

    //////////////////////////////////////
    // ******* GUI ********//
    //////////////////////////////////////
    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        if(isFormed()) {
            int maxCUWt = getMaxCWUt(),lastCWUt = getLastCWUt();
            textList.add(Component.translatable("gtceu.multiblock.computation.max",
                    FormattingUtil.formatNumbers(maxCUWt)));
            textList.add(Component.translatable("gtceu.multiblock.computation.usage",
                    FormattingUtil.formatNumbers(lastCWUt)));
        }
        super.addDisplayText(textList);
    }
}

