package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IThrowableItem extends IItemComponent {
    default int getUseDuration() {
        return 0;
    }
    default void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeleft){
    }
}
