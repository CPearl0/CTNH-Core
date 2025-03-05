package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import dev.shadowsoffire.apotheosis.adventure.boss.BossRegistry;
import dev.shadowsoffire.apotheosis.adventure.compat.GameStagesCompat;
import dev.shadowsoffire.placebo.reload.WeightedDynamicRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class BossSummonerBehavior implements IInteractionItem, IThrowableItem {
    public int tier;
    public BossSummonerBehavior(int tier){
        this.tier = tier;
    }

    @Override
    public int getUseDuration() {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeleft) {
        if(livingEntity instanceof Player player){
            int time = this.getUseDuration() - timeleft;
            if (time < 0) {
                return;
            }
            float power = getPowerForTime(time);
            if(!itemStack.isEmpty()){
                if(!(power < 0.1)){
                    if(!level.isClientSide){
                        var throwableSummoner = new ThrowableSummoner(level,player,itemStack.getItem(),tier);
                        throwableSummoner.setItem(itemStack);
                        throwableSummoner.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
                        level.addFreshEntity(throwableSummoner);
                        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                }
            }
            player.getCooldowns().addCooldown(itemStack.getItem(),40);
            if (!player.getAbilities().instabuild) {
                if (Math.random() < 0.2) {
                    itemStack.shrink(1);
                }
            }
        }
    }
    public static float getPowerForTime(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        player.swing(usedHand,true);
        return IInteractionItem.super.use(item, level, player, usedHand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
