package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IDataItem extends ComponentItem implements IInteractionItem {
    public IDataItem(Properties properties) {
        super(properties
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        CompoundTag nbt = stack.getOrCreateTag();
        if(nbt.contains("formula"))
        {
            var formula=nbt.getLongArray("formula");
            tooltipComponents.add(Component.translatable("ctnh.data.tip1",String.format("%d",formula[0]),String.format("%d",formula[1]),String.format("%d",formula[2])));
        }
        if(nbt.contains("muti"))
        {
            tooltipComponents.add(Component.translatable("ctnh.data.muti",String.format("%.2f",nbt.getDouble("muti"))));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced); // 调用父类方法以处理原版提示信息

    }
}
