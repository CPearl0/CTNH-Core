package io.github.cpearl0.ctnhcore.common.item;

import com.lowdragmc.lowdraglib.client.renderer.IItemRendererProvider;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import io.github.cpearl0.ctnhcore.client.renderer.TurbineRotorRender;
import lombok.NonNull;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class TurbineRotorItem extends BlockItem implements IItemRendererProvider {

    public TurbineRotorItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public @NonNull IRenderer getRenderer(ItemStack itemStack) {
        return TurbineRotorRender.INSTANCE;
    }
}
