package io.github.cpearl0.ctnhcore.client.renderer;

import com.enderio.machines.common.init.MachineBlockEntities;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.lowdragmc.lowdraglib.utils.TrackedDummyWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.cpearl0.ctnhcore.client.model.MagicCubeModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.ParametersAreNonnullByDefault;

public class EternalGardenRender extends WorkableCasingMachineRenderer {

    static MagicCubeModel model;

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
        if (blockEntity instanceof IMachineBlockEntity machineBlockEntity
                && machineBlockEntity.getMetaMachine() instanceof WorkableElectricMultiblockMachine machine
                && machine.isFormed()) {
            var level = blockEntity.getLevel();
            if (level == null) return;


            stack.pushPose();
            stack.translate(0.5, 2.5, 0.5);
            stack.popPose();
        }

    }
}
