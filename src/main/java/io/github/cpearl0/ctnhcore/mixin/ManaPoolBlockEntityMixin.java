package io.github.cpearl0.ctnhcore.mixin;

import io.github.cpearl0.ctnhcore.CTNHCore;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.block.mana.ManaPoolBlock;

@Mixin(ManaPoolBlockEntity.class)
public class ManaPoolBlockEntityMixin {
    @Redirect(method = "initManaCapAndNetwork()V", at = @At(value = "FIELD", target = "Lvazkii/botania/common/block/block_entity/mana/ManaPoolBlockEntity;manaCap:I", opcode = Opcodes.PUTFIELD), remap = false)
    public void CTNH$initManaCapAndNetwork(ManaPoolBlockEntity be, int x) {
        int cap = x;
        if (((ManaPoolBlock)be.getBlockState().getBlock()).variant == ManaPoolBlock.Variant.FABULOUS)
            cap *= 10;
        try {
            var beclass = be.getClass();
            var manaCap = beclass.getDeclaredField("manaCap");
            manaCap.setAccessible(true);
            manaCap.set(be, cap);
        }
        catch (Exception ignored) {
            CTNHCore.LOGGER.debug("Mixin Exception.");
        }
    }
}
