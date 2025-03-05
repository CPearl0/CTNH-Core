package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrowItem extends ComponentItem {

    public ThrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        for(IItemComponent component: components){
            if(component instanceof IThrowableItem){
                return ((IThrowableItem) component).getUseDuration();
            }
        }
        return super.getUseDuration(itemStack);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeleft) {
        for(IItemComponent component: components){
            if(component instanceof IThrowableItem){
                ((IThrowableItem) component).releaseUsing(itemStack, level, livingEntity, timeleft);
                return;
            }
        }
    }
}
