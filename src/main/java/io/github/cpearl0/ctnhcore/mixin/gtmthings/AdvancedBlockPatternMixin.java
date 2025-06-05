package io.github.cpearl0.ctnhcore.mixin.gtmthings;

import appeng.api.config.Actionable;
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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

import static io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior.GRID_CONTEXT;
import static io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior.USE_ON_CONTEXT;

@Mixin(AdvancedBlockPattern.class)
public class AdvancedBlockPatternMixin {

    @Inject(
            method = "getMatchStackWithHandler",
            at = @At("TAIL"),
            cancellable = true,
            remap = false
    )
    private static void getMatchStackWithHandlerMixin(List<ItemStack> candidates, LazyOptional<IItemHandler> cap, CallbackInfoReturnable<Pair<Integer, IItemHandler>> cir) {
        //System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
        if(cir.getReturnValue()!= null) return;
        boolean override = Arrays.stream(Thread.currentThread().getStackTrace())
                .anyMatch(el -> el.getClassName().contains("ctnhcore.common.item.MEAdvancedTerminal"));
        //MEAdvancedTerminalBehavior
        if (override) {
            IGrid grid = GRID_CONTEXT.get();
            var player = USE_ON_CONTEXT.get().getPlayer();
            for (ItemStack candidate : candidates){
                if (candidate.isEmpty()) continue;
                MEStorage inventory = grid.getStorageService().getInventory();
                for(var entry : inventory.getAvailableStacks()){
                    var key = entry.getKey();
                    if(key.matches(GenericStack.fromItemStack(candidate))
                            && inventory.extract(key, 1L, Actionable.SIMULATE, IActionSource.ofPlayer(player)) == 1)
                    {

                        System.out.println(">>> Find Match");
                        cir.setReturnValue(Pair.of(0, new SingleItemHandler((AEItemKey) key, grid)));
                    }
                }
            }
                // 返回自定义结果

        }
    }
}



