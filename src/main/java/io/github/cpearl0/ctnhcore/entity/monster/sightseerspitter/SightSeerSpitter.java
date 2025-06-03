package io.github.cpearl0.ctnhcore.entity.monster.sightseerspitter;

import io.github.cpearl0.ctnhcore.utils.MathUtils;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static io.github.cpearl0.ctnhcore.utils.MathUtils.angleBetween;

public class SightSeerSpitter extends Mob implements Enemy {
    protected SightSeerSpitter(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    static class SightSeerSpitterSurroundTargetGoal extends Goal {
        protected final SightSeerSpitter mob;
        protected final RandomSource random;
        protected int locateCount = 0;  // 用来确定目标Y坐标
        protected int ticksLeft = 40;  // 每40刻无条件换目标
        public Vec3 targetPos;
        public double maxSpeed = 0.4;  // 给游荡眼球怪用的，以后估计还有别的字段

        public SightSeerSpitterSurroundTargetGoal(SightSeerSpitter mob){
            this.mob = mob;
            random = mob.getRandom();
        }

        @Override
        public boolean canUse(){
            return mob.getTarget() != null && mob.getTarget().isAlive();
        }

        @Override
        public void start(){
            LivingEntity target = mob.getTarget();
            if(target == null) return;
            locateCount++;
            ticksLeft=40;
            mob.setDeltaMovement(mob.getDeltaMovement().with(Direction.Axis.Y, 0));
            Vec3 targetDir = mob.position().with(Direction.Axis.Y,target.position().y).vectorTo(target.position());  // 先确定水平方向
            float[] offsetAngle = MathUtils.dirToRot(targetDir);
            double offsetY = getOffsetY();
            if(random.nextInt(3) == 0){
                offsetAngle[0] += random.nextBoolean() ? 20 : -20;
            }
            Vec3 direction = Vec3.directionFromRotation(offsetAngle[1], offsetAngle[0]);
            targetPos = direction.normalize().scale(4).with(Direction.Axis.Y, offsetY).add(target.position());  // 然后设置垂直坐标
        }

        /** 按余弦函数确定Y坐标偏移 */
        public float getOffsetY(){
            float period = 6.1f;
            float radians = Mth.TWO_PI * (locateCount % period) / period;
            return 2.57f * Mth.cos(radians) + 1;
        }

        @Override
        public boolean canContinueToUse(){
            Vec3 pos = mob.position();
            return canUse()
                    && Math.abs(pos.x - targetPos.x) > 0.1
                    && Math.abs(pos.y - targetPos.y) > 0.1
                    && Math.abs(pos.z - targetPos.z) > 0.1
                    && mob.position().distanceToSqr(targetPos)>0.09 // 0.3 * 0.3
                    && mob.getTarget().position().distanceToSqr(targetPos) < 100  // 10 * 10;
                    && ticksLeft > 0;
        }

        @Override
        public void tick(){
            Vec3 motion = mob.getDeltaMovement();
            Vec3 targetDir = mob.position().vectorTo(targetPos).normalize().multiply(0.08,0.03,0.08); // Y轴老是冲过头，给少一点
            Vec3 targetMotion = motion.add(targetDir);
            if(angleBetween(targetDir, motion) > 15 || targetMotion.length() < maxSpeed){
                mob.setDeltaMovement(targetMotion);
            }
            List<Player> entities = mob.level().getEntities(EntityType.PLAYER, mob.getBoundingBox().expandTowards(targetMotion).inflate(0.15), e -> !e.isSpectator());
            for(Player player : entities){
                mob.doHurtTarget(player);
            }

            ticksLeft--;
        }
    }
    static class SightSeerSpitterWanderGoal extends SightSeerSpitterSurroundTargetGoal {
        private double anchorY=Double.NaN;

        public SightSeerSpitterWanderGoal(SightSeerSpitter mob){
            super(mob);
            maxSpeed=0.2;
        }

        @Override
        public boolean canUse(){
            return mob.getTarget() == null && mob.level().isNight();
        }

        @Override
        public boolean canContinueToUse(){
            return canUse() && ticksLeft > 0 &&
                    mob.position().distanceToSqr(targetPos) > 2.25; // 1.5*1.5
        }

        @Override
        public void start(){
            locateCount++;
            mob.setDeltaMovement(mob.getDeltaMovement().with(Direction.Axis.Y, 0));
            if(Double.isNaN(anchorY)){
                anchorY = mob.position().y;
            }
            double x = random.nextDouble() * 10 - 5;
            double y = getOffsetY() + 5;
            double z = random.nextDouble() * 10 - 5;
            targetPos = new Vec3(x, 0, z).normalize().scale(15).add(mob.position()).with(Direction.Axis.Y, y + anchorY);
            ticksLeft = 30;
        }
    }
}
