package io.github.cpearl0.ctnhcore.common.item;

import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import dev.shadowsoffire.apotheosis.adventure.boss.BossRegistry;
import dev.shadowsoffire.apotheosis.adventure.compat.GameStagesCompat;
import dev.shadowsoffire.placebo.reload.WeightedDynamicRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.util.function.Predicate;

public class ThrowableSummoner extends ThrowableItemProjectile {
    public Item defaultItem;
    public int tier;
    public ThrowableSummoner(Level level, LivingEntity shooter, Item item, int tier){
        super(EntityType.SNOWBALL,shooter,level);
        this.defaultItem = item;
        this.tier = tier;
    }
    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        for(int i = 0; i < 32; ++i) {
            this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), this.random.nextGaussian(), 0.0, this.random.nextGaussian());
        }
        if (!this.level().isClientSide && !this.isRemoved()) {
            Entity entity = this.getOwner();
            if (entity instanceof ServerPlayer player) {
                ApothBoss bossItem = (ApothBoss) BossRegistry.INSTANCE.getRandomItem(this.level().getRandom(), player.getLuck() * tier, new Predicate[]{WeightedDynamicRegistry.IDimensional.matches(this.level()), GameStagesCompat.IStaged.matches(player)});
                if (bossItem != null) {
                    Mob mobEntity = bossItem.createBoss((ServerLevel) this.level(), this.getOnPos(), this.level().getRandom(), player.getLuck());
                    mobEntity.setTarget(player);
                    mobEntity.setPersistenceRequired();
                    ((ServerLevel) this.level()).addFreshEntityWithPassengers(mobEntity);
                }
            }
            this.discard();
        }
    }
}
