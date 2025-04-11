package io.github.cpearl0.ctnhcore.client.renderer;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.lowdragmc.lowdraglib.utils.TrackedDummyWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import io.github.cpearl0.ctnhcore.client.ClientUtil;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.MartialMoralityEyeMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Consumer;

public class MartialMoralityEyeRender extends WorkableCasingMachineRenderer {

    public MartialMoralityEyeRender() {
        super(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"), GTCEu.id("block/multiblock/fusion_reactor"));
    }
    Vector3f rotationAxisY = new Vector3f(0, 1, 0);//局部Y轴
    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(BlockEntity blockEntity, float gameTime, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Celestial COGWHEEL = new Celestial(AllBlocks.COGWHEEL.asStack(), 0.35, 3.87, 0, true, 0.3, 0.1, null);
        Celestial LARGE_COGWHEEL = new Celestial(AllBlocks.LARGE_COGWHEEL.asStack(), 2.4, 1.4, 2.6, false, 1.8, 0.34, List.of(COGWHEEL));
        Celestial GEARBOX = new Celestial(AllBlocks.GEARBOX.asItem().getDefaultInstance(), 3.7, 1.32, 0.8, false, 1.7, -0.23, null);
        Celestial MANA_STEEL_GEARBOX_CASING = new Celestial(CTNHBlocks.CASING_MANASTEEL_GEARBOX.asItem().getDefaultInstance(), 0.35, 2.5, 3.0, false, 0.3, 0.1, null);
        Celestial ZENITH_CASING_GEARBOX = new Celestial(CTNHBlocks.ZENITH_CASING_GEARBOX.asItem().getDefaultInstance(), 6.5, 0.8, 1.0, false, 4.0, -0.13, List.of(MANA_STEEL_GEARBOX_CASING));
        List<Celestial> planets = List.of(LARGE_COGWHEEL, GEARBOX, ZENITH_CASING_GEARBOX);
        if (blockEntity instanceof IMachineBlockEntity machineBlockEntity && machineBlockEntity.getMetaMachine() instanceof MartialMoralityEyeMachine machine && machine.isFormed() && (machine.isActive() || blockEntity.getLevel() instanceof TrackedDummyWorld)) {
            Level level = blockEntity.getLevel();
            float time = level.getGameTime();
            BlockPos blockPos = MachineUtils.getOffset(machine, 0, 0, 16);//获取方块位置
            int lightLevel = LevelRenderer.getLightColor(level, blockPos);//获取亮度
            double rotationSpeedSpace = 0.05;
            double tiltAngle = (time * rotationSpeedSpace) % 360;
            double tiltRad = tiltAngle * (Math.PI / 180);
            //全局姿态变换———//
            poseStack.pushPose();
            double x = 0.5, y = 0.5, z = 0.5;
            switch (machine.getFrontFacing()) {
                case NORTH -> z = 16.5;
                case SOUTH -> z = -15.5;
                case WEST -> x = 16.5;
                case EAST -> x = -15.5;
            }
            poseStack.translate(x, y, z);
            poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(new Vector3f(1, 0, 0), (float) (tiltAngle * 0.5)));
            poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(new Vector3f(0, 0, 1), (float) (tiltAngle * 0.5)));
            //—渲染太空背景——//
            poseStack.pushPose();
            double angleSpace = (time * rotationSpeedSpace + 90) % 360;
            poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(rotationAxisY, (float) -angleSpace));
            poseStack.scale(1F, 1F, 1F);
            ClientUtil.renderModel(poseStack, buffer, ResourceLocation.tryParse("ctnhcore:space_layer"));
            poseStack.popPose();
            //—添加太阳系姿态变换———//
            double rotationSpeedSun = 0.4;
            double angleSun = (time * rotationSpeedSun) % 360;
            poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(rotationAxisY, (float) angleSun));
            //——渲染太阳——//
            poseStack.pushPose();
            poseStack.scale(15F, 15F, 15F);
            ClientUtil.renderStatic(AllBlocks.BRASS_CASING.asStack(), ItemDisplayContext.GROUND, 15728880, OverlayTexture.NO_OVERLAY, poseStack, buffer, level, 0);
            poseStack.popPose();
            //渲染行星及其卫星
            planets.forEach((planet) -> renderCelestial(planet, null, poseStack, time, tiltRad, blockEntity, buffer));
            poseStack.popPose();
        }
    }
    @Override
    public int getViewDistance() {
        return 128;
    }
    @Override
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }
    @Override
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public void onAdditionalModel(Consumer<ResourceLocation> registry) {
        registry.accept(ResourceLocation.tryParse("ctnhcore:space_layer"));
    }

    public void renderCelestial(Celestial celestial, @Nullable BlockPos parentWorldPos, PoseStack poseStack, float time, double tiltRad, BlockEntity blockEntity, MultiBufferSource buffer) {
        double sinTilt = Math.sin(tiltRad);
        double cosTilt = Math.cos(tiltRad);
        BlockPos blockPos = blockEntity.getBlockPos();
        Level level = blockEntity.getLevel();
        poseStack.pushPose();
        //计算公转
        float angleOrbit = (float) ((time * celestial.orbitSpeed) % 360);
        Quaternionf orbitQuaternion = new Quaternionf().fromAxisAngleDeg(rotationAxisY, angleOrbit);
        poseStack.mulPose( orbitQuaternion);
        //平移到公转半径位置
        poseStack.translate(celestial.orbitRadius,celestial.height,0);//自转
        Quaternionf selfRotationQuaternion = calculateSelfRotationQuaternion(celestial, time);
        poseStack.mulPose(selfRotationQuaternion);
        //缩放
        poseStack.scale((float) celestial.scale, (float) celestial.scale, (float) celestial.scale);
        //计算世界坐标
        Vector3f orbitPosLocal = new Vector3f((float) celestial.orbitRadius, 0, 0);
        orbitPosLocal.rotate( orbitQuaternion);
        double worldX = (parentWorldPos != null ? parentWorldPos.getX() : blockPos.getX() + 0.5) + orbitPosLocal.x * 1;
        double worldY = (parentWorldPos != null ? parentWorldPos.getY() : blockPos.getX() + 0.5) + orbitPosLocal.y * cosTilt - orbitPosLocal.z * sinTilt;
        double worldZ = (parentWorldPos != null ? parentWorldPos.getZ() : blockPos.getX() + 0.5) + orbitPosLocal.y * sinTilt + orbitPosLocal.z * cosTilt;
        BlockPos lightPos = new BlockPos((int) Math.floor(worldX), (int) Math.floor(worldY), (int) Math.floor(worldZ));
        int lightLevel = LevelRenderer.getLightColor(level, lightPos);
        //渲染
        ClientUtil.renderStatic(celestial.itemStack, ItemDisplayContext.GROUND,15728880,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                level,
                0
        );
        //渲染卫星(如果有)
        if (celestial.satellites != null && !celestial.satellites.isEmpty()){
            celestial.satellites.forEach(satellite -> renderCelestial(satellite, new BlockPos((int) worldX, (int) worldY, (int) worldZ), poseStack, time, tiltRad, blockEntity, buffer)
            );
        }
        poseStack.popPose();
    }

    private Quaternionf calculateSelfRotationQuaternion(Celestial celestial, float time) {
        float angleSelf = (float) (celestial.tidallyLocked
                        ? -(time * celestial.orbitSpeed) % 360
                        : (time * celestial.selfRotationSpeed) % 360);
        return new Quaternionf().fromAxisAngleDeg(rotationAxisY,angleSelf);
    }

    public class Celestial {
       ItemStack itemStack;
       double orbitRadius;
       double orbitSpeed;
       double selfRotationSpeed;
       boolean tidallyLocked;
       double scale;
       double height;
       List<Celestial> satellites;
       public Celestial(ItemStack itemStack, double orbitRadius, double orbitSpeed, double selfRotationSpeed, boolean tidallyLocked, double scale, double height, @Nullable List<Celestial> satellites){
           this.itemStack = itemStack;
           this.orbitRadius = orbitRadius;
           this.orbitSpeed = orbitSpeed;
           this.selfRotationSpeed = selfRotationSpeed;
           this.tidallyLocked = tidallyLocked;
           this.scale = scale;
           this.height = height;
           this.satellites = satellites;
       }
    }

}
