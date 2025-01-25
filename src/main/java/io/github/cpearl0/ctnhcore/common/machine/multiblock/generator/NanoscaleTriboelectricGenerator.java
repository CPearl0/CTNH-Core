package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.ManaLargeTurbineMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.ZenithMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NanoscaleTriboelectricGenerator extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public final NotifiableItemStackHandler machineStorage;
    public int parallel=1024;
    public double effencicy=1.0;
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            NanoscaleTriboelectricGenerator.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public NanoscaleTriboelectricGenerator(IMachineBlockEntity holder){
        super(holder);
        this.machineStorage = createMachineStorage((byte) 64);
    }
    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 1, IO.NONE, IO.BOTH, slots -> new CustomItemStackHandler(1) {
            @Override
            public int getSlotLimit(int slot) {
                return value;
            }
        });
    }
    @Override
    public @NotNull Widget createUIWidget() {
        var widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            var size = group.getSize();
            group.addWidget(
                    new SlotWidget(machineStorage.storage, 0, size.width - 30, size.height - 30, true, true)
                            .setBackground(GuiTextures.SLOT));
        }
        return widget;
    }

    public ItemStack getMachineStorageItem() {
        return machineStorage.getStackInSlot(0);
    }
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof NanoscaleTriboelectricGenerator zmachine) {
            double effencicy=0;
            if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.Rubber).get()))
            {
                effencicy=0.8;
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.Polyethylene).get()))
            {
                effencicy=1.2;
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.SiliconeRubber).get()))
            {
                effencicy=2.0;
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.StyreneButadieneRubber).get()))
            {
                effencicy=3.2;
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.Polybenzimidazole).get()))
            {
                effencicy=5.0;
            }
            var maxParallel = ParallelLogic.getParallelAmount(machine,recipe,zmachine.parallel);
            return ModifierFunction.builder()
                    .parallels(maxParallel)
                    .inputModifier(ContentModifier.multiplier(maxParallel))
                    .outputModifier(ContentModifier.multiplier(maxParallel))
                    .durationMultiplier(Math.sqrt(maxParallel))
                    .eutMultiplier(maxParallel*(1+maxParallel*0.01)*zmachine.effencicy)
                    .build();
        }
        return ModifierFunction.IDENTITY;
    }
}
