package io.github.cpearl0.ctnhcore.common.machine.computation;

import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableComputationContainer;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import io.github.cpearl0.ctnhcore.api.machine.trait.SimpleComputationContainer;
import io.github.cpearl0.ctnhcore.common.machine.trait.SimpleComputationLogic;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleComputationMachine extends SimpleTieredMachine implements IOpticalComputationReceiver {

    @Getter
    private final SimpleComputationContainer computationContainer = (SimpleComputationContainer) importComputation;

    public SimpleComputationMachine(IMachineBlockEntity holder, int tier, Int2IntFunction tankScalingFunction, Object... args) {
        super(holder, tier, tankScalingFunction, args);
    }

    @Override
    public void onLoad(){
        super.onLoad();
    }

    @Override
    protected SimpleComputationContainer createImportComputationContainer(Object... args) {
        var container = new SimpleComputationContainer(this);
//        container.setCapabilityValidator(s -> s == null || s == getFrontFacing());
        return container;
    }

    @Override
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
        var recipe_logic = (SimpleComputationLogic)super.getRecipeLogic();
        recipe_logic.setComputationContainer(computationContainer);
        return recipe_logic;
    }
}
