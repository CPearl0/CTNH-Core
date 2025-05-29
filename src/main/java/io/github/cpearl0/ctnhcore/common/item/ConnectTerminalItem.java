package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.SpacePhotovoltaicBaseStation;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.PhotoVoltaicDroneStation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class ConnectTerminalItem extends ComponentItem implements IInteractionItem {
    public ConnectTerminalItem(Properties properties) {
        super(properties
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            CompoundTag newTag = new CompoundTag();
            stack.setTag(newTag);
            player.displayClientMessage(Component.translatable("ctnh.terminal.success_clear"), true);

            // 也可以在这里处理右键点击方块的逻辑
            return InteractionResult.PASS;
        }
        Level level = context.getLevel();

        BlockPos blockPos = context.getClickedPos();
        IMultiController controller = getMachineController(level, blockPos);
        var machine=getMachine(level,blockPos);
        if (machine == null) return InteractionResult.PASS;
        CompoundTag nbt = stack.getOrCreateTag();
        if(machine instanceof PhotoVoltaicDroneStation)
        {
            nbt.putInt("block_x", blockPos.getX());
            nbt.putInt("block_y", blockPos.getY());
            nbt.putInt("block_z", blockPos.getZ());
            player.displayClientMessage(Component.translatable("ctnh.terminal.success_get"), true);
        }
        if(machine instanceof SpacePhotovoltaicBaseStation spb)
        {
            if(nbt.contains("block_x"))
            {

                var pos=new BlockPos(nbt.getInt("block_x"),nbt.getInt("block_y"),nbt.getInt("block_z"));
                spb.Drone_location=pos;
                player.displayClientMessage(Component.translatable("ctnh.terminal.success_write"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }
    private IMultiController getMachineController(Level level, BlockPos blockPos) {
        if (MetaMachine.getMachine(level, blockPos) instanceof IMultiController controller) {
            return controller;
        }
        return null;
    }
    public static @Nullable MetaMachine getMachine(BlockGetter level, BlockPos pos) {
        BlockEntity var3 = level.getBlockEntity(pos);
        if (var3 instanceof IMachineBlockEntity machineBlockEntity) {
            return machineBlockEntity.getMetaMachine();
        } else {
            return null;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        CompoundTag nbt = stack.getOrCreateTag();
        if(nbt.contains("block_x"))
        {
            tooltipComponents.add(Component.translatable("ctnh.terminal.tips"));
            tooltipComponents.add(Component.translatable("ctnh.terminal.location",String.format("%d",nbt.getInt("block_x")),String.format("%d",nbt.getInt("block_y")),String.format("%d",nbt.getInt("block_y"))));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced); // 调用父类方法以处理原版提示信息

    }

}
