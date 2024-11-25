package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDistinctPart;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.jei.IngredientIO;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CircuitBusPartMachine extends TieredIOPartMachine implements IDistinctPart, IMachineModifyDrops {
    @Getter
    @Persisted
    private final NotifiableItemStackHandler inventory;

    public CircuitBusPartMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier, IO.IN);
        inventory = new NotifiableItemStackHandler(this, 1, IO.IN);
    }

    @Override
    public void onDrops(List<ItemStack> drops) {
        clearInventory(getInventory().storage);
    }

    @Override
    public boolean isDistinct() {
        return getInventory().isDistinct();
    }

    @Override
    public void setDistinct(boolean isDistinct) {
        getInventory().setDistinct(isDistinct);
    }

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 34, 34);
        var container = new WidgetGroup(4, 4, 26, 26);
        int index = 0;
        container.addWidget(
                new SlotWidget(getInventory().storage, index++, 4, 4, true, io.support(IO.IN))
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setIngredientIO(IngredientIO.INPUT));

        container.setBackground(GuiTextures.BACKGROUND_INVERSE);
        group.addWidget(container);

        return group;
    }
}
