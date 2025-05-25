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
        if(nbt.contains("data1"))
        {
            tooltipComponents.add(Component.translatable("ctnh.terminal.tips"));
        }
        if(nbt.contains("block_x"))
        {
            tooltipComponents.add(Component.translatable("ctnh.terminal.tips"));
            tooltipComponents.add(Component.translatable("ctnh.terminal.location",String.format("%d%d%d",nbt.getInt("block_x"),nbt.getInt("block_y"),nbt.getInt("block_z"))));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced); // 调用父类方法以处理原版提示信息

    }
}
