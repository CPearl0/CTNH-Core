package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import io.github.cpearl0.ctnhcore.client.renderer.utils.RenderUtils;
import io.github.cpearl0.ctnhcore.common.machine.trait.ScalableReservoirComputingLogic;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.List;


public class ScalableReservoirComputingMachine extends WorkableElectricMultiblockMachine implements IOpticalComputationProvider, IControllable {

    public ScalableReservoirComputingMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }
    ScalableReservoirComputingLogic recipeLogic;
    public int maxCWUt,duration,lastCWUt;
    @Getter
    AABB aabb;
    public static int RANGE = 4;



    public void updateSacrifice() {
        var level = getLevel();
        if (level == null || level.isClientSide()) return;
        var sacrifices = level.getEntitiesOfClass(LivingEntity.class, aabb);
        if(sacrifices.size() == 1){
            recipeLogic.lockedSacrifice = sacrifices.iterator().next();
            sacrificeLockState=SacrificeLockState.SACRIFICE_LOCKED;
        }else if(sacrifices.size() > 1){
            sacrificeLockState=SacrificeLockState.SACRIFICE_UNLOCKED;
        }
        else sacrificeLockState=SacrificeLockState.SACRIFICE_EMPTY;
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        aabb = null;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        var pos = new Vector3i(holder.pos().getX(), holder.pos().getY(), holder.pos().getZ());
        //往后4格，往上2格
//        pos.add(
//                new Vector3i( RenderUtils.dircetionVectors.get(getFrontFacing()) ).mul(4)
//        ).add(
//                new Vector3i( RenderUtils.dircetionVectors.get(getUpwardsFacing()) ).mul(2)
//        );
        aabb = new AABB(
                pos.x - RANGE, pos.y - RANGE, pos.z - RANGE,
                pos.x + RANGE, pos.y + RANGE, pos.z + RANGE
        );
    }

    @Override
    public int requestCWUt(int cwut, boolean simulate, @NotNull Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        if(duration > 0){
            if(!simulate) {
                duration--;
                return lastCWUt = Math.min(cwut,maxCWUt);
            };
            return Math.min(cwut,maxCWUt);
        }
        return 0;
    }

    @Override
    public int getMaxCWUt(@NotNull Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return maxCWUt;
    }

    @Override
    public boolean canBridge(@NotNull Collection<IOpticalComputationProvider> seen) {
        return false;
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    protected ScalableReservoirComputingLogic createRecipeLogic(Object... args) {
        return recipeLogic = new ScalableReservoirComputingLogic(this);
    }

    @Override
    @NotNull
    public ScalableReservoirComputingLogic getRecipeLogic() {
        return recipeLogic;
    }


    /// ///////////////////////
    //          GUI
    /// ///////////////////////
    public static enum SacrificeLockState{
        SACRIFICE_EMPTY,
        SACRIFICE_LOCKED,
        SACRIFICE_UNLOCKED
    }
    SacrificeLockState sacrificeLockState = SacrificeLockState.SACRIFICE_EMPTY;

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        if(isFormed()){
            if(!isActive()){
                textList.add(Component.translatable("ctnhcore.src."+sacrificeLockState.name().toLowerCase()));
            }
            textList.add(Component.translatable("ctnhcore.src.wetware_duration",
                    FormattingUtil.formatNumbers(duration)));
            textList.add(Component.translatable("gtceu.multiblock.computation.max",
                    FormattingUtil.formatNumbers(maxCWUt)));
            textList.add(Component.translatable("gtceu.multiblock.computation.usage",
                    FormattingUtil.formatNumbers(lastCWUt)));
        }
        super.addDisplayText(textList);
    }
}
