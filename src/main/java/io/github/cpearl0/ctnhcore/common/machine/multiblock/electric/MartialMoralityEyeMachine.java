package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHBlockEntities;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class MartialMoralityEyeMachine extends WorkableElectricMultiblockMachine {
    public MartialMoralityEyeMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public boolean onWorking() {
        var center = MachineUtils.getOffset(this,0,0,16);
        var entities = getLevel().getEntities(null,AABB.of(BoundingBox.fromCorners(center.offset(-2,-2,-2),center.offset(2,2,2))));
        entities.forEach(Entity::kill);
        return super.onWorking();
    }
}
