package io.github.cpearl0.ctnhcore.mixin.gtmthings;

import appeng.api.config.Actionable;
import appeng.api.config.FuzzyMode;
import appeng.api.networking.IGrid;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.items.tools.powered.WirelessTerminalItem;
import com.hepdd.gtmthings.api.pattern.AdvancedBlockPattern;
import io.github.cpearl0.ctnhcore.common.item.SingleItemHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.util.tuples.Triplet;

import java.util.List;

import static io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior.ACCESS_POINT_CONTEXT;
import static io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior.USE_ON_CONTEXT;

@Mixin(AdvancedBlockPattern.class)
public class AdvancedBlockPatternMixin {

    @Inject(
            method = "foundItem",
            at = @At("TAIL"),
            cancellable = true,
            remap = false
    )
    private void foundItemMixin(Player player, List<ItemStack> candidates, CallbackInfoReturnable<Triplet<ItemStack, IItemHandler, Integer>> cir) {

        if(cir.getReturnValue().getA() != null) return;

        if (ACCESS_POINT_CONTEXT.get() != null && USE_ON_CONTEXT.get() != null) {
            //player.sendSystemMessage(Component.literal("search in ae"));
            IGrid grid = ACCESS_POINT_CONTEXT.get().getGrid();

            for (ItemStack candidate : candidates){
                if (candidate.isEmpty()) continue;
                MEStorage inventory = grid.getStorageService().getInventory();
                for(var entry : inventory.getAvailableStacks().findFuzzy(AEItemKey.of(candidate), FuzzyMode.IGNORE_ALL)){
                    var key = entry.getKey();
                    if(inventory.extract(key, 1L, Actionable.SIMULATE, IActionSource.ofMachine(ACCESS_POINT_CONTEXT.get())) == 1)
                    {
                        cir.setReturnValue(new Triplet(((AEItemKey)key).toStack(), new SingleItemHandler((AEItemKey) key, grid), 0));
                    }

                }
            }
        }
    }

    @ModifyVariable(
            method = "getMatchStackWithHandler",
            at = @At("STORE"),
            ordinal = 0,
            remap = false
    )
    private static IGrid modifyIGrid(IGrid original) {
        if (ACCESS_POINT_CONTEXT.get() != null) {
            return null;
        } else {
            return original;
        }
    }
}



