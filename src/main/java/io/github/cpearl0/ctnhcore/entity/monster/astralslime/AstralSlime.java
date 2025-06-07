package io.github.cpearl0.ctnhcore.entity.monster.astralslime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public class AstralSlime extends Slime {

    protected AstralSlime(EntityType<? extends Slime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected boolean isDealsDamage() {
        return true;
    }

    @Override
    public void setSize(int pSize, boolean pResetHealth) {
        super.setSize(pSize, pResetHealth);
        this.getAttribute(Attributes.ARMOR).setBaseValue(pSize * 3);
    }

    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        float f = (float)this.getSize() * 0.1F;
        this.setDeltaMovement(vec3.x, (double)(this.getJumpPower() + f), vec3.z);
        this.hasImpulse = true;
        ForgeHooks.onLivingJump(this);
    }
}
