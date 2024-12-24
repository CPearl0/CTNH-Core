package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.pattern.BlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockState;
import com.gregtechceu.gtceu.api.pattern.error.PatternError;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.gregtechceu.gtceu.api.pattern.error.SinglePredicateError;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TestingTerminalBehavior implements IInteractionItem {
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        Level level = context.getLevel();
        if (level instanceof ClientLevel) {
            return InteractionResult.PASS;  // 客户端不进行实际操作
        }

        BlockPos blockPos = context.getClickedPos();
        IMultiController controller = getMachineController(level, blockPos);
        if (controller == null) return InteractionResult.PASS;

        // 处理机器是否成型
        if (controller.isFormed()) {
            sendSuccessMessage(player);
        } else {
            handleUnformedController(player, controller);
        }
        return InteractionResult.SUCCESS;
    }

    private IMultiController getMachineController(Level level, BlockPos blockPos) {
        if (MetaMachine.getMachine(level, blockPos) instanceof IMultiController controller) {
            return controller;
        }
        return null;
    }

    private void sendSuccessMessage(Player player) {
        player.sendSystemMessage(Component.translatable("ctnh.test_terminal.success").withStyle(ChatFormatting.GREEN));
    }

    private void handleUnformedController(Player player, IMultiController controller) {
        if (!controller.self().allowFlip()) {
            MultiblockState multiblockState = controller.getMultiblockState();
            PatternError error = multiblockState.error;
            if (error != null) {
                showError(player, error, false);
            }
        } else {
            detectPatternErrors(player, controller);
        }
    }

    private void detectPatternErrors(Player player, IMultiController controller) {
        BlockPattern pattern = controller.getPattern();
        List<PatternError> errors = check(controller, pattern);
        for (int i = 0; i < errors.size(); i++) {
            showError(player, errors.get(i), (i == 1));
        }
    }

    private List<PatternError> check(IMultiController controller, BlockPattern pattern) {
        List<PatternError> errors = new ArrayList<>();
        if (controller == null) {
            errors.add(new PatternStringError("no controller found"));
            return errors;
        }

        BlockPos centerPos = controller.self().getPos();
        Direction frontFacing = controller.self().getFrontFacing();
        Direction[] facings = controller.hasFrontFacing() ? new Direction[] { frontFacing } :
                new Direction[] { Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST };
        Direction upwardsFacing = controller.self().getUpwardsFacing();
        boolean allowsFlip = controller.self().allowFlip();
        MultiblockState worldState = new MultiblockState(controller.self().getLevel(), controller.self().getPos());

        for (Direction direction : facings) {
            checkPatternAndCollectErrors(pattern, errors, worldState, centerPos, direction, upwardsFacing, false);
            if (allowsFlip) {
                // 检查翻转状态
                worldState = new MultiblockState(worldState.getWorld(), worldState.getPos());
                checkPatternAndCollectErrors(pattern, errors, worldState, centerPos, direction, upwardsFacing, true);
            }
        }

        return errors;
    }

    private void checkPatternAndCollectErrors(BlockPattern pattern, List<PatternError> errors, MultiblockState worldState,
                                              BlockPos centerPos, Direction direction, Direction upwardsFacing, boolean flip) {
        pattern.checkPatternAt(worldState, centerPos, direction, upwardsFacing, flip, false);
        if (worldState.hasError()) {
            errors.add(worldState.error);
        }
    }

    private void showError(Player player, PatternError error, boolean flip) {
        if (error instanceof PatternStringError) {
            player.sendSystemMessage(((PatternStringError) error).getErrorInfo());
            return;
        }

        List<Component> show = generateErrorMessages(error, flip);
        show.forEach(player::sendSystemMessage);
    }

    private List<Component> generateErrorMessages(PatternError error, boolean flip) {
        List<Component> messages = new ArrayList<>();
        var pos = error.getPos();

        if (error instanceof SinglePredicateError) {
            List<List<ItemStack>> candidates = error.getCandidates();
            var root = candidates.get(0).get(0).getHoverName();
            messages.add(Component.translatable("ctnh.test_terminal.lack_error", Component.translatable("ctnh.test_terminal.position", pos.getX(), pos.getY(), pos.getZ())));
            messages.add(Component.literal(" - ").append(root).append(Component.translatable("ctnh.test_terminal.error_info",error.getErrorInfo())));
        } else {
            messages.add(Component.translatable("ctnh.test_terminal.wrong_error", Component.translatable("ctnh.test_terminal.position", pos.getX(), pos.getY(), pos.getZ())));
            List<List<ItemStack>> candidates = error.getCandidates();
            for (List<ItemStack> candidate : candidates) {
                if (!candidate.isEmpty()) {
                    messages.add(Component.literal(" - ").append(candidate.get(0).getDisplayName()));
                }
            }
        }
        return messages;
    }

}
