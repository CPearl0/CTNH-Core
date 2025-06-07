package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.IObjectHolder;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.BlockableSlotWidget;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IDataItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ObjectHolderMachine;
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.jei.IngredientIO;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.utils.Position;
import io.github.cpearl0.ctnhcore.common.item.IDroneItem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DroneHolderMachine extends MultiblockPartMachine implements IMachineLife {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(DroneHolderMachine.class,
            MultiblockPartMachine.MANAGED_FIELD_HOLDER);
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Persisted
    private final DroneHolderMachine.DroneHolderHandler heldItems;
    @Getter
    @Setter
    @Persisted
    @DescSynced
    private boolean isLocked;

    public DroneHolderMachine(IMachineBlockEntity holder) {
        super(holder);
        heldItems = new DroneHolderMachine.DroneHolderHandler(this);
    }
    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 0, 0);
        var container = new WidgetGroup(4, 4, 26, 26);
        int index = 0;
        for(int i=0;i<=4;i++)
        {
            for(int j=0;j<=2;j++)
                group.addWidget(new BlockableSlotWidget(heldItems, i*3+j, -45+18*i, -24+18*j)
                        .setIsBlocked(this::isLocked)
                        .setBackground(GuiTextures.SLOT, GuiTextures.RESEARCH_STATION_OVERLAY));
        }
        return group;
    }
    @Override
    public void onMachineRemoved() {
        clearInventory(this.heldItems.storage);
    }

    @NotNull
    public ItemStack getHeldItem(int slot) {
        ItemStack stackInSlot = heldItems.getStackInSlot(slot);
        return stackInSlot;
    }
    public void consumeItem(int slot)
    {
        var stack=heldItems.getStackInSlot(slot);
        if(!stack.isEmpty())
        {
            var c=stack.getItem().getMaxDamage(stack);

            var d=stack.isDamaged();
            stack.setDamageValue(stack.getDamageValue()+1);
            if(stack.getDamageValue()>=c)heldItems.setStackInSlot(slot,ItemStack.EMPTY);
            else heldItems.setStackInSlot(slot,stack);
        }
    }



    private class DroneHolderHandler extends NotifiableItemStackHandler {
        public DroneHolderHandler(MetaMachine metaTileEntity) {
            super(metaTileEntity, 15, IO.IN, IO.BOTH, size -> new CustomItemStackHandler(size) {

                @Override
                public int getSlotLimit(int slot) {
                    return 1;
                }
            });
        }

        // only allow a single item, no stack size
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }


        // prevent extracting the item while running
        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!isLocked()||this.getStackInSlot(slot).isEmpty()) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }


        // only allow data items in the second slot
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (stack.isEmpty()) {
                return true;
            }

            boolean isDataItem = false;

            var p=stack.getItem();
            if (true) {
                isDataItem = true;
            }
            if(stack.getItem() instanceof IDroneItem) return true;
            else return false;
        }
    }
}
