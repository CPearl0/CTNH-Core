package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class IDroneItem extends ComponentItem {
    @Getter
    public int tier;
    @Getter
    public int eut;
    @Getter
    public int durability;
    public IDroneItem(Properties properties, int tier, int eut, int durability, Supplier<Item> finishedItem) {
        super(properties
                .stacksTo(1)
                .durability(durability)
                .rarity(Rarity.EPIC)
        );
        this.tier=tier;
        this.eut=eut;
        this.durability=durability;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable("ctnh.drone_tier",String.format("%d",tier)));
        tooltipComponents.add(Component.translatable("ctnh.drone_eut",String.format("%d",eut)));
        tooltipComponents.add(Component.translatable("ctnh.drone_durability",String.format("%d",durability)));
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced); // 调用父类方法以处理原版提示信息

    }



}

