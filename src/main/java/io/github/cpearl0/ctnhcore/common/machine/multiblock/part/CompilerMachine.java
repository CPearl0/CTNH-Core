package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDistinctPart;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.jei.IngredientIO;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CompilerMachine extends TieredIOPartMachine implements IDistinctPart, IMachineModifyDrops {
    @Getter
    @Persisted
    private final NotifiableItemStackHandler inventory;
    @Persisted
    public int ids=-1;
    public CompilerMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier, IO.IN);
        inventory = new NotifiableItemStackHandler(this, 1, IO.IN);
    }
    public void set_id(int id)
    {
        ids=id;
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
        var label=(new LabelWidget(-32, 30, Component.translatable("ctnh.compiler.noid")));
        if(ids!=-1) {
            label = (new LabelWidget(-32, 30, Component.translatable("ctnh.compiler.id", String.format("%d", ids))));
        }
        else
        {
            label = (new LabelWidget(-32, 30, Component.translatable("ctnh.compiler.noid")));
        }
        int index = 0;
        container.addWidget(
                new SlotWidget(getInventory().storage, index++, 4, 4, true, io.support(IO.IN))
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setIngredientIO(IngredientIO.INPUT));

        container.setBackground(GuiTextures.BACKGROUND_INVERSE);
        group.addWidget(container);
        group.addWidget(label);

        return group;
    }
}
