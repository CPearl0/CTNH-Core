package io.github.cpearl0.ctnhcore.mixin.gtmthings;

import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.hepdd.gtmthings.common.item.AdvancedTerminalBehavior;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static com.hepdd.gtmthings.common.item.AdvancedTerminalBehavior.AutoBuildSetting.HATCH_NAMES;

@Mixin(AdvancedTerminalBehavior.AutoBuildSetting.class)
public class AutoBuildSettingMixin {
    @Shadow(remap = false)
    private int noHatchMode;

    /**
     * @author 1
     * @reason 1
     */
    @Overwrite(remap = false)
    public boolean isPlaceHatch(BlockInfo[] blockInfos) {
        if (this.noHatchMode == 0) return true;
        if (blockInfos != null && blockInfos.length > 0) {
            for( var blockInfo : blockInfos)
            {
                if (blockInfo.getBlockState().getBlock() instanceof MetaMachineBlock machineBlock) {
                    var id = machineBlock.getDefinition().getName();
                    for (String hatchName : HATCH_NAMES) {
                        if (id.contains(hatchName)) return false;
                    }
                }
            }
            return true;
        }
        return true;
    }
}
