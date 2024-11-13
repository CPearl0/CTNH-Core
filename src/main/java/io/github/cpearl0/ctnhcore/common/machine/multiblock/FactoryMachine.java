package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FactoryMachine extends WorkableElectricMultiblockMachine {
    public int CENTRIFUGE_COUNT = 0;
    public int LATHE_COUNT = 0;
    public int CRUSHING_COUNT = 0;
    public int DEPLOYER_COUNT = 0;
    public int BURNER_COUNT = 0;
    public int PRESSOR_COUNT = 0;
    public int MIXER_COUNT = 0;
    @Persisted
    public final NotifiableItemStackHandler machineStorage;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FactoryMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public List<String> AvailableMachine = List.of("lathe","mechanical_press","centrifuge","crushing_wheel","deployer","blaze_burner","mechanical_mixer");
    public FactoryMachine(IMachineBlockEntity holder) {
        super(holder);
        this.machineStorage = createMachineStorage((byte) 64);
    }
    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 9, IO.NONE, IO.BOTH, slots -> new ItemStackTransfer(9) {
            @Override
            public int getSlotLimit(int slot) {
                return value;
            }

            @Override
            public void onContentsChanged() {
                var Machine = getMachineStorageItem();
                updateMachineCount(Machine);
                super.onContentsChanged();
            }
        }).setFilter(itemStack -> AvailableMachine.contains(itemStack.getItem().toString()));
    }

    @Override
    public @NotNull Widget createUIWidget() {
        var widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            var size = group.getSize();
            for(int i = 0; i < 9 ;i++){
                group.addWidget(
                        new SlotWidget(machineStorage.storage, i, size.width - 30 - 18*i, size.height - 30, true, true)
                                .setBackground(GuiTextures.SLOT));
            }
        }
        return widget;
    }

    public List<ItemStack> getMachineStorageItem() {
        var ItemList = new ArrayList<ItemStack>();
        for(int i = 0; i < 9; i++){
            ItemList.add(machineStorage.getStackInSlot(i));
        }
        return ItemList;
    }

    public void updateMachineCount(List<ItemStack> itemlist) {
        CENTRIFUGE_COUNT = 0;
        LATHE_COUNT = 0;
        CRUSHING_COUNT = 0;
        DEPLOYER_COUNT = 0;
        BURNER_COUNT = 0;
        PRESSOR_COUNT = 0;
        MIXER_COUNT = 0;
        for (ItemStack itemStack : itemlist) {
            switch (itemStack.getItem().toString()) {
                case "centrifuge" -> CENTRIFUGE_COUNT = CENTRIFUGE_COUNT + itemStack.getCount();
                case "lathe" -> LATHE_COUNT = LATHE_COUNT + itemStack.getCount();
                case "crushing_wheel" -> CRUSHING_COUNT = CRUSHING_COUNT + itemStack.getCount();
                case "deployer" -> DEPLOYER_COUNT = DEPLOYER_COUNT + itemStack.getCount();
                case "blaze_burner" -> BURNER_COUNT = BURNER_COUNT + itemStack.getCount();
                case "mechanical_press" -> PRESSOR_COUNT = PRESSOR_COUNT + itemStack.getCount();
                case "mechanical_mixer" -> MIXER_COUNT = MIXER_COUNT + itemStack.getCount();
            }
        }
    }
    @Override
    public boolean onWorking() {
        return super.onWorking();
    }

    public static GTRecipe recipeModifier(MetaMachine machine, GTRecipe recipe, OCParams params, OCResult result){
        if (machine instanceof FactoryMachine fmachine) {
            var recipeType = fmachine.getRecipeType();
//            switch (recipeType) {
//                case GTRecipeTypes.CENTRIFUGE_RECIPES -> return recipe;
//            }
        }
        return recipe;
    }
}
