package io.github.cpearl0.ctnhcore.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import static wayoftime.bloodmagic.util.handler.event.ClientHandler.minecraft;

public class ClientUtil {
    public static void renderStatic(ItemStack itemStack, ItemDisplayContext itemDisplayContext, int combinedLight, int combinedOverlay, PoseStack poseStack, MultiBufferSource bufferSource, @Nullable Level pLevel, int pSeed) {
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, itemDisplayContext, combinedLight, combinedOverlay, poseStack, bufferSource, pLevel, pSeed);
    }
    public static void renderModel(PoseStack poseStack, MultiBufferSource buffer, ResourceLocation resourceLocation) {
        BakedModel model = getModel(resourceLocation);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.solid()), null, model,1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
    }
    public static BakedModel getModel(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().getModelManager().getModel(resourceLocation);
    }
}
