package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.FactoryMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Nicoll_Dyson_Beams extends WorkableElectricMultiblockMachine implements IExplosionMachine {
    public int SLOT_COUNT =4;
    public int twist_power=0;
    public int starlight_power=0;
    public int horizen_power=0;
    public double max_mana=1000000;
    public double mana=0;
    public int overload=0;
    public int overload_crash=20;
    public int broken=0;
    public int twist_seat=0;
    public int starlight_seat=0;

    public List<String> AvailableRune=List.of("twist_rune","starlight_rune","horizen_rune","quasar_rune");
    public final NotifiableItemStackHandler machineStorage;
    public Nicoll_Dyson_Beams(IMachineBlockEntity holder){
        super(holder);
        this.machineStorage = createMachineStorage((byte) 64);
    }



    public int caculate(){
        return 0;
    }
    public double consume_twist(){
        if(twist_power<=3)return 0;
        else{
            return Math.max((twist_power-3)/3,1)*0.01+(Math.max(starlight_power-twist_power,0)*0.01)+(Math.max((100-mana/100000)*0.0005,0));
        }
    }
    public  double consume_starlight()
    {
        if(starlight_power<=3)return 0;
        else{
            return Math.max((starlight_power-3)/3,1)*0.01+(Math.max(twist_power-starlight_power,0)*0.01)+(mana/100000*0.005);
        }
    }
    public boolean check_overload()
    {
        var tier=getTier();
        if((twist_power/3)+((mana/100000)*(Math.max(twist_power/3,1)))>=starlight_power*2+5+tier*1)
        {
            return true;
        }
        return false;
    }
    public void consumeItem(int i) { machineStorage.extractItem(i, 1,false); }
    public void rune_consume()
    {
        var random=Math.random();
        for(int i = 0; i < 4; i++)
        {
            if(machineStorage.getStackInSlot(i).getItem().equals(CTNHItems.TWIST_RUNE.get()))
            {
                if(random<=consume_twist())consumeItem(i);
            }
            else if(machineStorage.getStackInSlot(i).getItem().equals(CTNHItems.STARLIGHT_RUNE.get()))
            {
                if(random<=consume_starlight())consumeItem(i);
            }
            else if(machineStorage.getStackInSlot(i).getItem().equals(CTNHItems.QUASAR_RUNE.get()))
            {
                overload=overload_crash;
            }
            else if(machineStorage.getStackInSlot(i).getItem().equals(CTNHItems.HORIZEN_RUNE.get()))
            {
                if(random<=0.025*(horizen_power))consumeItem(i);
            }
        }
    }
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            var tier = getTier();
            if(check_overload())
            {
            overload+=1;
            if (overload>=overload_crash/2)
            {
                broken=1;
            }
            else {
                if(broken<=overload_crash/2) {
                    overload -= 1;
                    overload = Math.max(overload, 0);
                }
            }

            }
            if(overload>=overload_crash)
            {
                doExplosion(3f);
            }

            if(MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid(100000),this))
            {
                if(mana+100000<max_mana)
                    mana+=100000*(1+0.005*horizen_power);

            }
        }
        return super.onWorking();
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {

        if(broken==2) doExplosion(3f);
        if(mana>=recipe.data.getInt("required_mana"))
        {
            max_mana=max_mana*(1+0.05*horizen_power);
            rune_consume();
            mana-=recipe.data.getInt("mana");
            return super.beforeWorking(recipe);
        }
        return false;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {

        if (!(machine instanceof Nicoll_Dyson_Beams xmachine)) return ModifierFunction.NULL;
        var tier=xmachine.getTier();
        return  ModifierFunction.builder()
                .durationMultiplier(1-Math.min(0.00125* xmachine.twist_power,0.5+(tier-6)*0.025)-(tier-6)*0.025)
                .eutMultiplier(1-0.01* xmachine.starlight_power)
                .build();
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
    @Override
    public void addDisplayText(List<Component> textList) {
        if(overload>0&&overload<overload_crash/2)
        {
            textList.add(Component.translatable("ctnh.beams_overload"));
            textList.add(Component.translatable("ctnh.beams_overload_1", FormattingUtil.formatNumbers(overload), overload_crash));
        }
        if(overload_crash/2<=overload) {
            textList.add(Component.translatable("ctnh.beams_overload_2"));
            textList.add(Component.translatable("ctnh.beams_overload_1", FormattingUtil.formatNumbers(overload), overload_crash));
        }
        if(broken>0)textList.add(Component.translatable("ctnh.beams_crash"));
        textList.add(Component.translatable("ctnh.beams_max_mana",String.format("%.2f",max_mana)));
        textList.add(Component.translatable("ctnh.beams_mana",String.format("%.2f",mana)));
        textList.add(Component.translatable("ctnh.twist_consumption",String.format("%.2f",consume_twist())));
        textList.add(Component.translatable("ctnh.beams_stable",String.format("%.2f",-((twist_power/3)+((mana/100000)*(Math.max(twist_power/3,1))))+starlight_power*2+10+tier*2)));
        textList.add(Component.translatable("ctnh.starlight_consumption",String.format("%.2f",consume_starlight())));
        textList.add(Component.translatable("ctnh.beams_time",String.format("%.2f",1-Math.min(0.00125* twist_power,0.5+(tier-6)*0.025)-(tier-6)*0.025)));
        textList.add(Component.translatable("ctnh.beams_eut_consumption",String.format("%.2f",1-0.01* starlight_power)));
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

    public void updateMachineCount(List<ItemStack> itemlist) {
        twist_power=0;
        starlight_power=0;
        horizen_power=0;
        for (ItemStack itemStack : itemlist) {
            switch (itemStack.getItem().toString()) {
                case "twist_rune"-> twist_power=twist_power+itemStack.getCount();
                case "starlight_rune"-> starlight_power=starlight_power+itemStack.getCount();
                case "horizen_rune"-> horizen_power=horizen_power+itemStack.getCount();
            }
            }

    }

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
