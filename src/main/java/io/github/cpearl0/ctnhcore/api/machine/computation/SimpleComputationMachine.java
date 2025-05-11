package io.github.cpearl0.ctnhcore.api.machine.computation;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableComputationContainer;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.SimpleComputationContainer;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.SimpleComputationLogic;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class SimpleComputationMachine extends SimpleTieredMachine /*implements IOpticalComputationReceiver*/ {

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

//    @Override
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
