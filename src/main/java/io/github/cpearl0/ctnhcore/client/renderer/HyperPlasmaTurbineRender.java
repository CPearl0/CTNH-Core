package io.github.cpearl0.ctnhcore.client.renderer;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.client.renderer.block.FluidBlockRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.gregtechceu.gtceu.client.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.cpearl0.ctnhcore.client.renderer.utils.RenderUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.HyperPlasmaTurbineMachine;
import io.github.cpearl0.ctnhcore.registry.machines.multiblock.HyperPlasmaTurbineRegister;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.RenderTypeHelper;
import org.jetbrains.annotations.*;
import org.joml.Matrix4f;

import javax.annotation.*;
import java.util.List;

public class HyperPlasmaTurbineRender extends WorkableCasingMachineRenderer {

    private final FluidBlockRenderer fluidBlockRenderer;
    private Fluid cachedFluid;
    private ResourceLocation cachedRecipe;

    public HyperPlasmaTurbineRender(ResourceLocation baseCasing, ResourceLocation workableModel) {
        super(baseCasing, workableModel);
        fluidBlockRenderer = FluidBlockRenderer.Builder.create()
//                .setFaceOffset(-0.125f)
                .setForcedLight(LightTexture.FULL_BRIGHT)
                .getRenderer();
    }

    @Override
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        super.render(blockEntity, partialTicks, stack, buffer, combinedLight, combinedOverlay);
//        if(blockEntity instanceof MetaMachineBlockEntity mm){
//            if(mm.metaMachine instanceof HyperPlasmaTurbineMachine hptm && hptm.recipeLogic.isWorking()){
//                var lastRecipe = hptm.recipeLogic.getLastRecipe();
//                if (lastRecipe == null) {
//                    cachedRecipe = null;
//                    cachedFluid = null;
//                } else if (hptm.getOffsetTimer() % 20 == 0 || lastRecipe.id != cachedRecipe) {
//                    cachedRecipe = lastRecipe.id;
//                    if (hptm.isActive()) {
//                        cachedFluid = RenderUtil.getRecipeFluidToRender(lastRecipe);
//                    } else {
//                        cachedFluid = null;
//                    }
//                }
//
//                if (cachedFluid == null) {
//                    return;
//                }
//
//                BlockPos center = hptm.getPos();
//
//                var fluidRenderType = ItemBlockRenderTypes.getRenderLayer(cachedFluid.defaultFluidState());
//                var consumer = buffer.getBuffer(RenderTypeHelper.getEntityRenderType(fluidRenderType, false));
//                fluidBlockRenderer.drawPlane(Direction.WEST, HyperPlasmaTurbineRegister.blockOffsetWEST0,stack.last().pose(),consumer,cachedFluid,RenderUtil.FluidTextureType.FLOWING,combinedOverlay,center);
//                fluidBlockRenderer.drawPlane(Direction.EAST, HyperPlasmaTurbineRegister.blockOffsetEAST0,stack.last().pose(),consumer,cachedFluid,RenderUtil.FluidTextureType.FLOWING,combinedOverlay,center);
//
//            }
//        }
    }
}
