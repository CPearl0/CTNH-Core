package io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;

import com.mo_guang.ctpp.api.StressRecipeCapability;
import com.mo_guang.ctpp.common.machine.multiblock.KineticWorkableMultiblockMachine;
import com.mo_guang.ctpp.data.recipe.builder.CTPPRecipeHelper;
import com.moguang.ctnhbio.api.machine.trait.NotifiableEntityContainer;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.api.CrossParallelRecipeLogic;

import java.util.List;

public class MeadowMachine extends KineticWorkableMultiblockMachine {

    public final NotifiableEntityContainer entityContainer;

    public MeadowMachine(IMachineBlockEntity holder) {
        super(holder);
        entityContainer = attachTrait(new NotifiableEntityContainer(this, getAABB(), IO.IN));
    }

    public AABB getAABB() {
        final Direction b = getFrontFacing().getOpposite();
        final Direction l = b.getCounterClockWise();
        final Direction u = Direction.UP;

        return new AABB(
                getPos().relative(b, 0).relative(l, 5).relative(u, 0),
                getPos().relative(b, 10).relative(l, -5).relative(u, 6));
    }

    @Override
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new CrossParallelRecipeLogic(this);
    }

    public static Component stressCrossParallel(@NotNull MetaMachine machine, RecipeHandlerGroup group,
                                                @NotNull GTRecipe recipe) {
        if (machine instanceof KineticWorkableMultiblockMachine kmachine &&
                kmachine.getRecipeLogic() instanceof CrossParallelRecipeLogic recipeLogic) {
            float totalStress = kmachine.getTotalInputStress();
            float usedStress = recipeLogic.mergedRecipe == null ? 0 :
                    CTPPRecipeHelper.getInputStress(recipeLogic.mergedRecipe);
            float availableStress = totalStress - usedStress;
            if (availableStress >= 0) {
                int maxParallel = (int) (availableStress / CTPPRecipeHelper.getInputStress(recipe));
                if (maxParallel > 1) {
                    int actualParallel = ParallelLogic.getParallelAmount(group, recipe, maxParallel,
                            List.of(StressRecipeCapability.CAP));
                    if (actualParallel > 1) {
                        recipe.multiplyAllContents(actualParallel);
                        recipe.parallels *= actualParallel;
                    }
                }
            }
            return null;
        }
        return RecipeModifier.nullWrongType(KineticWorkableMultiblockMachine.class, machine);
    }
}
