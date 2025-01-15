package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import sereneseasons.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;
import vazkii.botania.common.item.BotaniaItems;

public class season_reactor extends WorkableElectricMultiblockMachine {
    public ItemStack autumn_rune = new ItemStack(BotaniaItems.runeAutumn,1);
    public ItemStack summer_rune = new ItemStack(BotaniaItems.runeSummer,1);
    public ItemStack spring_rune = new ItemStack(BotaniaItems.runeSpring,1);
    public ItemStack winter_rune = new ItemStack(BotaniaItems.runeWinter,1);
    public double efficiency=1.0;
    public double getEfficiency_flower=1.0;
    public double time=1.0;
    public int season_need=-1;
    public double outputEnergy = 0;

    public season_reactor(IMachineBlockEntity holder) {
        super(holder);
    }
    public  String timer()
    {
        Level world = getLevel();
        SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(world);
        SeasonTime times = new SeasonTime(seasonData.seasonCycleTicks);
        String string = times.getSeason().toString();
        return string;

    }
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        efficiency=1;
        time=1;
        String season=timer();
        if (MachineUtils.inputItem(autumn_rune,this)){
            if(season.equals("SPRING"))
            {
                time=efficiency=0.5;
            }
            if(season.equals("AUTUMN"))
            {
                time=efficiency=2;
            }
            return super.beforeWorking(recipe);
        }
        else if (MachineUtils.inputItem(summer_rune,this)){
            if(season.equals("SUMMER"))
            {
                efficiency=2;
                time=2;
            }
            if(season.equals("WINTER")){
                time=efficiency=0.5;
            }
            return super.beforeWorking(recipe);
        }
        else if (MachineUtils.inputItem(spring_rune,this)){
            if(season.equals("SPRING")){
                time=efficiency=2;
            }
            if(season.equals("AUTUMN")){
                time=efficiency=0.5;
            }
            return super.beforeWorking(recipe);
        }
        else if (MachineUtils.inputItem(winter_rune,this)){
            if(season.equals("SUMMER")){
                time=efficiency=0.5;
            }
            if(season.equals("WINTER"))
            {
                time=efficiency=2.0;
            }
            return super.beforeWorking(recipe);
        }
        return false;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof season_reactor wmachine) {
            return ModifierFunction.builder()
                    .outputModifier(ContentModifier.multiplier(wmachine.efficiency*wmachine.getEfficiency_flower))
                    .durationModifier(ContentModifier.multiplier(wmachine.time))
                    .build();
        }
        return ModifierFunction.NULL;
    }
}