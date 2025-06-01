package io.github.cpearl0.ctnhcore.client.renderer;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.MagicCubeModel;
import io.github.cpearl0.ctnhcore.client.renderer.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;

public class EternalGardenRender extends WorkableCasingMachineRenderer {

    private static final ResourceLocation TEXTURE =
            CTNHCore.id("textures/block/magic_cube/texture.png");
    static MagicCubeModel model=new MagicCubeModel();

    int ticks = 0;

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
            ticks++;

            var recipe = machine.getRecipeLogic().getLastRecipe();

            Vector3f vOffset;
            Direction facing = machine.self().getFrontFacing();
//            Direction upward = machine.self().getUpwardsFacing();

            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));

            stack.pushPose();
            stack.translate(0.5 , 2, 0.5);

            //后退21
            stack.pushPose();
            vOffset=new Vector3f(RenderUtils.directionVectors.get(facing)).mul(19.5f);
            stack.translate(-vOffset.x, -vOffset.y, -vOffset.z);
            model.render(ticks, stack, consumer, combinedOverlay);
            stack.popPose();

            //后退25
            stack.pushPose();
            vOffset=new Vector3f(RenderUtils.directionVectors.get(facing)).mul(26.5f);
            stack.translate(-vOffset.x, -vOffset.y, -vOffset.z);
            model.render(ticks, stack, consumer, combinedOverlay);
            stack.popPose();

            //后退23，左2
            stack.pushPose();
            vOffset=new Vector3f(RenderUtils.directionVectors.get(facing)).mul(23);
            vOffset.add(new Vector3f(RenderUtils.directionVectors.get(facing.getClockWise())).mul(3.5f));
            stack.translate(-vOffset.x, -vOffset.y, -vOffset.z);
            model.render(ticks, stack, consumer, combinedOverlay);
            stack.popPose();

            //后退23，右2
            stack.pushPose();
            vOffset=new Vector3f(RenderUtils.directionVectors.get(facing)).mul(23);
            vOffset.add(new Vector3f(RenderUtils.directionVectors.get(facing.getCounterClockWise())).mul(3.5f));
            stack.translate(-vOffset.x, -vOffset.y, -vOffset.z);
            model.render(ticks, stack, consumer, combinedOverlay);
            stack.popPose();

            stack.popPose();
        }

    }
}
