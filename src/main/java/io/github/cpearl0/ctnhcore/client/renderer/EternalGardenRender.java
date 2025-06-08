package io.github.cpearl0.ctnhcore.client.renderer;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.MagicCubeModel;
import io.github.cpearl0.ctnhcore.client.renderer.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.sql.Array;

public class EternalGardenRender extends WorkableCasingMachineRenderer {

    private static final ResourceLocation TEXTURE =
            CTNHCore.id("textures/block/magic_cube/texture.png");
    static MagicCubeModel model=new MagicCubeModel();


    public EternalGardenRender() {
        super(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false);
    }

    @Override
    public boolean shouldRender(BlockEntity blockEntity, Vec3 cameraPos) {
        return true;
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
            float time = RenderUtils.getTime();


            Direction facing = machine.self().getFrontFacing();
            Vector3f[] vOffsets= {
                    new Vector3f(RenderUtils.directionVectors.get(facing)).mul(19.5f),//后退21
                    new Vector3f(RenderUtils.directionVectors.get(facing)).mul(26.5f),//后退25
                    new Vector3f(RenderUtils.directionVectors.get(facing)).mul(23)//后退23，左2
                            .add(new Vector3f(RenderUtils.directionVectors.get(facing.getClockWise())).mul(3.5f)),
                    new Vector3f(RenderUtils.directionVectors.get(facing)).mul(23)//后退23，右2
                            .add(new Vector3f(RenderUtils.directionVectors.get(facing.getCounterClockWise())).mul(3.5f))
            };
//            Direction upward = machine.self().getUpwardsFacing();

            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));

            stack.pushPose();
            stack.translate(0.5 , 2, 0.5);

            for(var vOffset : vOffsets){
                stack.pushPose();
                stack.translate(-vOffset.x, -vOffset.y, -vOffset.z);
                model.render(time, stack, consumer, combinedOverlay);
                stack.popPose();
            }

            if(machine.isActive()) {
                var recipe = machine.getRecipeLogic().getLastRecipe();
                if(recipe!= null)
                {
                    var items = RecipeHelper.getInputItems(recipe);
                    if(items!= null && !items.isEmpty())
                    {
                        var itemToRender = items.get(0);

                        var itemRenderer = Minecraft.getInstance().getItemRenderer();
                        for (var vOffset : vOffsets) {
                            stack.pushPose();
                            stack.translate(-vOffset.x, -vOffset.y, -vOffset.z);
                            stack.mulPose(Axis.YP.rotationDegrees(time));
                            itemRenderer.renderStatic(itemToRender, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, combinedOverlay, stack, buffer, null, 0);
                            stack.popPose();
                        }
                    }
                }
            }

            stack.popPose();
        }

    }
}
