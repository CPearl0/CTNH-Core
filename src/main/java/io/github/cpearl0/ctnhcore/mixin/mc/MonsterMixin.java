package io.github.cpearl0.ctnhcore.mixin.mc;

import dev.shadowsoffire.attributeslib.api.ALObjects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(Monster.class)
public abstract class MonsterMixin extends LivingEntity {
    @Unique
    private static final UUID uuid1 = UUID.fromString("87ba2a09-9cb9-4d33-8108-ede659709323");
    @Unique
    private static final UUID uuid2 = UUID.fromString("9bee1025-a569-499b-8716-d7af1cfd0c17");
    @Unique
    private static final UUID uuid3 = UUID.fromString("23c17fea-f0ef-438a-a9ee-9654b71f0703");
    @Unique
    private static final UUID uuid4 = UUID.fromString("e3fadcc3-1cc6-43b1-9ab9-9df2f59c7cd1");

    protected MonsterMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void init(EntityType type, Level level, CallbackInfo ci){
        var difficulty = level.getDifficulty().getId();
        double healthMultiplier = 1;
        switch (difficulty){
            case 0 -> healthMultiplier = 0.2;
            case 1 -> healthMultiplier = 0.75 + Math.random() * 0.1;
            case 2 -> healthMultiplier = 1.0 + Math.random() * 0.2;
            case 3 -> healthMultiplier = 1.5 + Math.random() * 0.3;
        }
        double damageMultiplier = 1;
        switch (difficulty){
            case 0 -> damageMultiplier = 0.2;
            case 1 -> damageMultiplier = 0.8 + Math.random() * 0.1;
            case 2 -> damageMultiplier = 1.0 + Math.random() * 0.2;
            case 3 -> damageMultiplier = 1.2 + Math.random() * 0.3;
        }
        int armorAddition = 0;
        switch (difficulty){
            case 0 -> armorAddition = 0;
            case 1 -> armorAddition = 0;
            case 2 -> armorAddition = 2 + (int)(Math.random() * 2.5);
            case 3 -> armorAddition = 4 + (int)(Math.random() * 3.5);
        }
        int armorPierceAddition = 0;
        switch (difficulty){
            case 0 -> armorPierceAddition = 0;
            case 1 -> armorPierceAddition = 0;
            case 2 -> armorPierceAddition = 1 + (int)(Math.random() * 2);
            case 3 -> armorPierceAddition = 2 + (int)(Math.random() * 3);
        }
        AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
        if(maxHealth != null){
            maxHealth.addPermanentModifier(new AttributeModifier(uuid1,"health multiply",healthMultiplier - 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        AttributeInstance damage = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if(damage != null){
            damage.addPermanentModifier(new AttributeModifier(uuid2, "damage multiply",damageMultiplier - 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
        if(armor != null){
            armor.addPermanentModifier(new AttributeModifier(uuid3, "armor addition",armorAddition, AttributeModifier.Operation.ADDITION));
        }
        AttributeInstance armorPierce = this.getAttribute(ALObjects.Attributes.ARMOR_PIERCE.get());
        if(armorPierce != null) {
            armorPierce.addPermanentModifier(new AttributeModifier(uuid4, "armor pierce addition", armorPierceAddition, AttributeModifier.Operation.ADDITION));
        }
        List<MobEffect> effects = List.of(MobEffects.MOVEMENT_SPEED,MobEffects.DAMAGE_RESISTANCE,MobEffects.REGENERATION,MobEffects.DAMAGE_BOOST,MobEffects.INVISIBILITY);
        if(difficulty == 3) {
            if(Math.random() <= 0.2) {
                if(this.getType().equals(EntityType.CREEPER)){
                    this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1,3));
                }
                this.addEffect(new MobEffectInstance(effects.get((int) (Math.random() * 5)), -1));
            }
        }
        this.setHealth(this.getMaxHealth());
    }
}
