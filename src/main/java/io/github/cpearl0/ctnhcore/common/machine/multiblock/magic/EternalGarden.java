package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.hollingsworth.arsnouveau.common.entity.LightningEntity;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBurst;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.mojang.blaze3d.shaders.Effect;
import com.teamresourceful.resourcefulconfig.web.annotations.Link;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import vazkii.botania.common.handler.BotaniaSounds;

import java.util.LinkedList;
import java.util.Objects;

public class EternalGarden extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public EternalGarden(IMachineBlockEntity holder){
        super(holder);
    }

    @Persisted private LinkedList<String> foodlist;
    @Persisted private int foodlist_length=0;
    @Persisted private int nutrition_length=0;
    @Persisted private LinkedList<Double> nutritionlist=new LinkedList<>();
    @Persisted private int Temperature=0;
    @Persisted private boolean wither=false;
    @Persisted private boolean thunder=false;
    @Persisted private boolean burn=false;
    public void queuer(Double food)
    {
        if(nutrition_length==5)
        {
            nutritionlist.removeFirst();
        }
        else
            nutrition_length+=1;
        nutritionlist.add(food);
    }

    @Override
    public boolean onWorking() {
        var level = getLevel();
        var pos = getPos();
        if(getOffsetTimer()%5==0)
        {
            if(thunder) {
                if (level != null) {
                    var area = AABB.of(BoundingBox.fromCorners(pos.offset(-20, -10, -20), pos.offset(10, 10, 10)));
                    var entities = level.getEntities(null, area);
                    for (Entity i : entities) {
                        if (!(i instanceof LivingEntity)) {
                            continue;
                        }
                        var poser = i.blockPosition();
                        EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, poser, MobSpawnType.TRIGGERED);

                    }
                }
            }
        }
        if(getOffsetTimer() %10==0) {

            if (wither) {
                level = getLevel();
                pos = getPos();
                Holder<DamageType> type =
                        level
                                .registryAccess()
                                .registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(DamageTypes.WITHER);
                //forge文档真不如Fabric一根吧
                DamageSource damageSource = new DamageSource(type);
                var area = AABB.of(BoundingBox.fromCorners(pos.offset(-20, -10, -20), pos.offset(10, 10, 10)));
                if (level != null) {
                    var entities = level.getEntities(null, area);
                    for (Entity i : entities) {
                        if (!(i instanceof LivingEntity)) {
                            continue;
                        }
                        i.hurt(damageSource, 8);
                    }
                }
            }
            if(burn)
            {
                level = getLevel();
                pos = getPos();
                Holder<DamageType> type =
                        level
                                .registryAccess()
                                .registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(DamageTypes.WITHER);
                //forge文档真不如Fabric一根吧
                DamageSource damageSource = new DamageSource(type);
                var area = AABB.of(BoundingBox.fromCorners(pos.offset(-20, -10, -20), pos.offset(10, 10, 10)));
                if (level != null) {
                    var entities = level.getEntities(null, area);
                    for (Entity i : entities) {
                        if (!(i instanceof LivingEntity)) {
                            i.setSecondsOnFire(10);

                            }
     
                    }
                }
            }
        }
        return super.onWorking();
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {

        if(machine instanceof EternalGarden mmachine)
        {
            int tier=mmachine.getTier();
            int recipe_tier= RecipeHelper.getRecipeEUtTier(recipe);
            double overclock=Math.pow(1.2,tier-recipe_tier);
            if(recipe.data.getString("type").equals("water"))
            {
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.nutrition_length=0;
                mmachine.nutritionlist.clear();
                mmachine.Temperature=0;
                mmachine.burn=false;
                int maxparallel=8+Math.max((tier-3),0)*4;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier(parallel)
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*overclock))
                        .build();
            }
            if(recipe.data.getString("type").equals("eat"))
            {
                mmachine.wither=false;
                mmachine.Temperature=0;
                mmachine.thunder=false;
                int maxparallel=8;
                int bad_food=0;
                int target_nutrition=64;
                double nutrition=1;

                var food=recipe.data.getDouble("nutrition");
                if(mmachine.nutrition_length==0)
                {

                }
                else {
                    for (int i = 0; i <= 4; i++) {
                        if (mmachine.foodlist_length < i) break;

                        if (mmachine.nutritionlist.get(i)==(food)) {
                            bad_food += 1;
                        }
                        if (i < 4)
                            nutrition += mmachine.nutritionlist.get(i);

                    }
                }
                mmachine.queuer(food);
                nutrition+=food;
                double base_output=1;
                double base_duration=1;
                if(nutrition>target_nutrition) {
                    base_output = 8;
                    base_duration = 1 + 0.1 * (nutrition - target_nutrition);
                }
                else {
                    base_output=1+7*((double) nutrition /target_nutrition);
                }
                if(target_nutrition==nutrition)
                {
                    base_duration=0.5;
                }


                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier(parallel)
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*base_output*(1-0.05*Math.pow(bad_food,2)*(1+0.1*Math.max(tier-recipe_tier,0)))))
                        .durationMultiplier(base_duration)
                        .build();
            }
            if(recipe.data.getString("type").equals("fire"))
            {
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.nutrition_length=0;
                mmachine.nutritionlist.clear();
                mmachine.Temperature=0;
                mmachine.burn=false;
                int maxparallel = 8;
                int temp=recipe.data.getInt("temp");
                mmachine.Temperature+=temp;
                FluidStack pyrotheumFluid = new FluidStack(
                        Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:pyrotheum"))),
                        10
                );
                // 检查输入仓是否有足够流体
                boolean isFluidSufficient = MachineUtils.inputFluid(pyrotheumFluid, mmachine);
                if(isFluidSufficient)temp-=1000;
                double rate=-Math.pow((12500-temp),2)+156250001;
                double tare2=Math.sqrt(rate)/100;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier(parallel)
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*overclock*rate))
                        .build();
            }
            if(recipe.data.getString("type").equals("boom"))
            {
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.nutrition_length=0;
                mmachine.nutritionlist.clear();
                mmachine.Temperature=0;
                mmachine.burn=false;
                int maxparallel=64;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier(Math.sqrt(parallel))
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*overclock))
                        .build();
            }
            if(recipe.data.getString("type").equals("wither"))
            {
                mmachine.wither=true;
                mmachine.thunder=false;
                mmachine.nutrition_length=0;
                mmachine.nutritionlist.clear();
                mmachine.Temperature=0;
                mmachine.burn=false;
                int maxparallel=4;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier((parallel))
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*overclock))
                        .build();
            }
            if(recipe.data.getString("type").equals("lighting"))
            {
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.nutrition_length=0;
                mmachine.nutritionlist.clear();
                mmachine.Temperature=0;
                mmachine.burn=false;
                var level = mmachine.getLevel();
                var pos = mmachine.getPos();
                if(level.isThundering())
                {
                    if(recipe.data.getBoolean("light"))
                    {
                        EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level,pos,MobSpawnType.TRIGGERED);

                        return  ModifierFunction.builder()
                                .outputModifier(ContentModifier.multiplier(100000*overclock))
                                .build();
                    }
                    return  ModifierFunction.builder()
                            .outputModifier(ContentModifier.multiplier(300*overclock))
                            .build();
                }
                if(level.isRaining())
                {
                    return  ModifierFunction.builder()
                            .outputModifier(ContentModifier.multiplier(300*overclock))
                            .build();
                }

            }
            if(recipe.data.getString("type").equals("blame"))
            {
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.nutrition_length=0;
                mmachine.nutritionlist.clear();
                mmachine.Temperature=0;
                mmachine.burn=true;
                int maxparallel=8+Math.max((tier-3),0)*4;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier(parallel)
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*overclock))
                        .build();
            }
            if(recipe.data.getString("type").equals("fly"))
            {
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.nutrition_length=0;
                mmachine.nutritionlist.clear();
                mmachine.Temperature=0;
                mmachine.burn=false;
                var pos = mmachine.getPos();
                var level=mmachine.getLevel();
                //forge文档真不如Fabric一根吧
                int muti=0;
                var area = AABB.of(BoundingBox.fromCorners(pos.offset(-20, -10, -20), pos.offset(10, 10, 10)));
                if (level != null) {
                    var entities = level.getEntities(null, area);
                    muti=1;
                    for (Entity i : entities) {
                        if (!(i instanceof LivingEntity)) {
                            continue;
                        }
                        if(((LivingEntity) i).hasEffect(MobEffects.LEVITATION))
                        {
                            muti+=1;
                            Holder<DamageType> type =
                                    level
                                            .registryAccess()
                                            .registryOrThrow(Registries.DAMAGE_TYPE)
                                            .getHolderOrThrow(DamageTypes.MAGIC);
                            //forge文档真不如Fabric一根吧
                            DamageSource damageSource = new DamageSource(type);
                            i.hurt(damageSource,200);
                        }
                        level.playSound((Player)null, (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, BotaniaSounds.shulkMeNot, SoundSource.BLOCKS, 10.0F, 1.0F);
                    }
                }
                return  ModifierFunction.builder()
                        .eutMultiplier(muti)
                        .outputModifier(ContentModifier.multiplier(Math.pow(2,Math.min(17,muti*0.5))*overclock))
                        .build();
            }
            

        }
    return ModifierFunction.NULL;
    }
}
