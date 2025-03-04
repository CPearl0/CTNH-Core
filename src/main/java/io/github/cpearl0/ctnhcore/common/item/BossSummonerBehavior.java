package io.github.cpearl0.ctnhcore.common.item;

import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import dev.shadowsoffire.apotheosis.adventure.boss.BossRegistry;
import dev.shadowsoffire.apotheosis.adventure.compat.GameStagesCompat;
import dev.shadowsoffire.placebo.reload.WeightedDynamicRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class BossSummonerBehavior implements IInteractionItem {
    public int tier;
    public BossSummonerBehavior(int tier){
        this.tier = tier;
    }
    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context) {
        Player player = context.getPlayer();
        if(player == null){
            return InteractionResult.PASS;
        }
        Level level = context.getLevel();
        var pos = context.getClickedPos();
        if(level != null & !level.isClientSide) {
            ApothBoss bossItem = (ApothBoss) BossRegistry.INSTANCE.getRandomItem(level.getRandom(), player.getLuck() * tier, new Predicate[]{WeightedDynamicRegistry.IDimensional.matches(level), GameStagesCompat.IStaged.matches(player)});
            if (bossItem != null) {
                Mob entity = bossItem.createBoss((ServerLevel) level, pos.offset(0,1,0), level.getRandom(), player.getLuck());
                entity.setTarget(player);
                entity.setPersistenceRequired();
                ((ServerLevel) level).addFreshEntityWithPassengers(entity);
            }
        }
        return IInteractionItem.super.onItemUseFirst(itemStack, context);
    }

//    @Override
//    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
//        if(livingEntity instanceof Player player){
//            var pos = livingEntity.getOnPos();
//            if(level != null & !level.isClientSide) {
//                ApothBoss bossItem = (ApothBoss) BossRegistry.INSTANCE.getRandomItem(level.getRandom(), player.getLuck() * tier, new Predicate[]{WeightedDynamicRegistry.IDimensional.matches(level), GameStagesCompat.IStaged.matches(player)});
//                if (bossItem != null) {
//                    Mob entity = bossItem.createBoss((ServerLevel) level, pos, level.getRandom(), player.getLuck());
//                    entity.setTarget(player);
//                    entity.setPersistenceRequired();
//                    ((ServerLevel) level).addFreshEntityWithPassengers(entity);
//                }
//            }
//        }
//        return IInteractionItem.super.finishUsingItem(stack, level, livingEntity);
//    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
