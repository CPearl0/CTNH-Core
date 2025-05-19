package io.github.cpearl0.ctnhcore.client.renderer;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class EternalGardenRender extends WorkableCasingMachineRenderer {


    public EternalGardenRender() {
        super(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false);
    }

    @Override
    public int getViewDistance() {
        return 32;
    }

    @Override
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @ParametersAreNonnullByDefault
    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        super.render(blockEntity, partialTicks, stack, buffer, combinedLight, combinedOverlay);

        var level = blockEntity.getLevel();
        if(level==null)return;

        ItemStack spark =  new ItemStack(BotaniaItems.corporeaSpark);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        stack.pushPose();
        stack.translate(0.5, 0.5, 0.5);
        itemRenderer.renderStatic(spark, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, stack, buffer, level,1);
        stack.popPose();

    }
}
