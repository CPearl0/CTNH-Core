package io.github.cpearl0.ctnhcore.common.machine.simple;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import io.github.cpearl0.ctnhcore.common.machine.trait.SimpleComputationContainer;
import io.github.cpearl0.ctnhcore.common.machine.trait.SimpleComputationLogic;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class SimpleComputationMachine extends SimpleTieredMachine {

    private final SimpleComputationContainer computationContainer = new SimpleComputationContainer(this);

    public SimpleComputationMachine(IMachineBlockEntity holder, int tier, Int2IntFunction tankScalingFunction, Object... args) {
        super(holder, tier, tankScalingFunction, args);
    }

    @Override
    public void onLoad(){
        super.onLoad();
    }

    @Override
    @NotNull
    protected SimpleComputationContainer createImportComputationContainer(Object... args) {
        var container = new SimpleComputationContainer(this);
//        container.setCapabilityValidator(s -> s == null || s == getFrontFacing());
        return container;
    }

    public IOpticalComputationProvider getComputationProvider() {
        return computationContainer.getComputationProvider();
    }



    @Override
    public void onNeighborChanged(Block block, BlockPos fromPos, boolean isMoving) {
        super.onNeighborChanged(block, fromPos, isMoving);
        computationContainer.updateCWUtProvider();
    }

    @Override
    @NotNull
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new SimpleComputationLogic(this);
    }
    @Override
    @NotNull
    public SimpleComputationLogic getRecipeLogic() {
        return (SimpleComputationLogic)super.getRecipeLogic();
    }


}
