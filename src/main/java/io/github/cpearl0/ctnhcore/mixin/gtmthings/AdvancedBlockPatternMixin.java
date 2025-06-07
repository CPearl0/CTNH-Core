package io.github.cpearl0.ctnhcore.mixin.gtmthings;

import appeng.api.config.Actionable;
import appeng.api.config.FuzzyMode;
import appeng.api.networking.IGrid;
import appeng.api.networking.security.IActionSource;
import appeng.api.networking.storage.IStorageService;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.GenericStack;
import appeng.api.storage.MEStorage;
import com.hepdd.gtmthings.api.pattern.AdvancedBlockPattern;
import com.mojang.datafixers.util.Pair;
import io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior;
import io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalItem;
import io.github.cpearl0.ctnhcore.common.item.SingleItemHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.util.tuples.Triplet;

import java.util.Arrays;
import java.util.List;

import static io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior.GRID_CONTEXT;
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
        //System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
        //String s = cir.getReturnValue().getA() + " " + cir.getReturnValue().getB();
        //player.sendSystemMessage(Component.literal(s));
        if(cir.getReturnValue().getA() != null) return;
//        boolean override = Arrays.stream(Thread.currentThread().getStackTrace())
//                .anyMatch(el -> el.getClassName().contains("ctnhcore.common.item.MEAdvancedTerminal"));
//        //MEAdvancedTerminalBehavior
        if (GRID_CONTEXT.get() != null && USE_ON_CONTEXT.get() != null) {
            IGrid grid = GRID_CONTEXT.get();

            for (ItemStack candidate : candidates){
                if (candidate.isEmpty()) continue;
                MEStorage inventory = grid.getStorageService().getInventory();
                for(var entry : inventory.getAvailableStacks().findFuzzy(AEItemKey.of(candidate), FuzzyMode.IGNORE_ALL)){
                    var key = entry.getKey();
                    if(inventory.extract(key, 1L, Actionable.SIMULATE, IActionSource.ofPlayer(player)) == 1)
                    {
                        cir.setReturnValue(new Triplet(((AEItemKey)key).toStack(), new SingleItemHandler((AEItemKey) key, grid), 0));
                    }
                }
            }
        }
    }
}



