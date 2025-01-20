package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.FactoryMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Nicoll_Dyson_Beams extends WorkableElectricMultiblockMachine {
    public int SLOT_COUNT =5;
    public int twist_power=0;
    public int starlight_power=0;
    public int horizen_power=0;
    public int max_mana=1000000;
    public int mana=0;
    public List<String> AvailableRune=List.of("twist_rune","starlight_rune","horizen_rune","quasar_rune");
    public final NotifiableItemStackHandler machineStorage;
    public Nicoll_Dyson_Beams(IMachineBlockEntity holder){
        super(holder);
        this.machineStorage = createMachineStorage((byte) 64);
    }



    public int caculate(){
        return 0;
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 60 == 0) {
            var tier = getTier();
            if(MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid(100000),this))
            {
                if(mana+100000<max_mana)
                    mana+=100000;
                else mana=max_mana;
            }
        }
        return super.onWorking();
    }
    public static ModifierFunction recipeModifier(MetaMachine xmachine, @NotNull GTRecipe recipe) {
        if (!(xmachine instanceof ManaLargeTurbineMachine turbineMachine)) return ModifierFunction.NULL;
return  ModifierFunction.NULL;
    }
    public void onDrops(List<ItemStack> drops) {
        clearInventory(machineStorage.storage);
    }

    @Override
    public @NotNull Widget createUIWidget() {
        var widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            var size = group.getSize();
            for(int i = 0; i < SLOT_COUNT ;i++){
                group.addWidget(
                        new SlotWidget(machineStorage.storage, i, size.width - 30 - 18*i, size.height - 30, true, true)
                                .setBackground(GuiTextures.SLOT));
            }
        }
        return widget;
    }

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FactoryMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    @Override
    public boolean keepSubscribing() {
        return true;
    }


    public List<ItemStack> getMachineStorageItem() {
        var ItemList = new ArrayList<ItemStack>();
        for(int i = 0; i < 4; i++){
            ItemList.add(machineStorage.getStackInSlot(i));
        }
        return ItemList;
    }

    public void updateMachineCount(List<ItemStack> itemlist) {}

    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 5, IO.NONE, IO.BOTH, slots -> new CustomItemStackHandler(SLOT_COUNT) {
            @Override
            public int getSlotLimit(int slot) {
                return value;
            }

            @Override
            public void onContentsChanged(int slot) {
                var Machine = getMachineStorageItem();
                updateMachineCount(Machine);
                super.onContentsChanged(slot);
            }
        }).setFilter(itemStack -> AvailableRune.contains(itemStack.getItem().toString()));
    }
}
