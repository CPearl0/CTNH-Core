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
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    @Persisted private  double nutrition=1;
    @Persisted private List<ItemStack> flower;
    @Persisted private List<ItemStack> rune;
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
    public static List<ItemStack> getallrunes()
    {
        List<ItemStack> ItemsRune = new ArrayList<>();
        List<ItemStack> ItemsFlower = new ArrayList<>();
        TagKey<Item> RuneTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("botania", "runes"));
        TagKey<Item> FlowerTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("botania", "generating_special_flowers"));
        for (Item item : ForgeRegistries.ITEMS) {
            ItemStack itemStack = item.getDefaultInstance();

            if (itemStack.is(RuneTag)) {
                ItemsRune.add(itemStack);
            }
            if (itemStack.is(FlowerTag)) {
                ItemsFlower.add(itemStack);
            }
        }
        return ItemsRune;
    }
    public static List<ItemStack> getallflowers()
    {
        List<ItemStack> ItemsRune = new ArrayList<>();
        List<ItemStack> ItemsFlower = new ArrayList<>();
        TagKey<Item> RuneTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("botania", "runes"));
        TagKey<Item> FlowerTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("botania", "generating_special_flowers"));
        for (Item item : ForgeRegistries.ITEMS) {
            ItemStack itemStack = item.getDefaultInstance();

            if (itemStack.is(RuneTag)) {
                ItemsRune.add(itemStack);
            }
            if (itemStack.is(FlowerTag)) {
                ItemsFlower.add(itemStack);
            }
        }
        return ItemsFlower;
    }
    public void onStructureFormed() {
        super.onStructureFormed();
        flower=getallflowers();
        rune=getallrunes();
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
            var base_overclock=1.1;
            for(int i=0;i<=mmachine.flower.size()-1;i++)
            {
                if(MachineUtils.canInputItem(mmachine.flower.get(i),mmachine))
                {
                    base_overclock+=0.0005;
                }
            }
            for(int i=0;i<=mmachine.rune.size()-1;i++)
            {
                if(MachineUtils.canInputItem(mmachine.rune.get(i),mmachine))
                {
                    base_overclock+=0.0002;
                }
            }
            if(MachineUtils.canInputItem(new ItemStack(CTNHItems.TWIST_RUNE),mmachine))
            {
                base_overclock+=0.05;
            }
            if(MachineUtils.canInputItem(new ItemStack(CTNHItems.HORIZEN_RUNE),mmachine))
            {
                base_overclock+=0.05;
            }
            if(MachineUtils.canInputItem(new ItemStack(CTNHItems.PROLIFERATION_RUNE),mmachine))
            {
                base_overclock+=0.1;
            }
            if(MachineUtils.canInputItem(new ItemStack(CTNHItems.STARLIGHT_RUNE),mmachine))
            {
                base_overclock+=0.05;
            }
            if(MachineUtils.canInputItem(new ItemStack(CTNHItems.QUASAR_RUNE),mmachine))
            {
                base_overclock+=0.5;
            }

            double overclock=Math.pow(base_overclock,tier-recipe_tier);
            if(recipe.data.getString("type").equals("water"))
            {
                //水绣球
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.burn=false;
                mmachine.Temperature=0;
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
                //彼方兰
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.burn=false;
                mmachine.Temperature=0;
                int maxparallel=8;
                int bad_food=0;
                int target_nutrition=64;


                var food=recipe.data.getDouble("nutrition")/2;
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
                            mmachine.nutrition += mmachine.nutritionlist.get(i);

                    }
                }
                mmachine.queuer(food);
                mmachine.nutrition+=food;
                double base_output=1;
                double base_duration=1;
                if(mmachine.nutrition>target_nutrition) {
                    base_output = 36;
                    base_duration = 1 + 0.1 * (mmachine.nutrition - target_nutrition);
                }
                else {
                    base_output=1+35*((double) mmachine.nutrition /target_nutrition);
                }
                if(target_nutrition==mmachine.nutrition)
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
                //烧煤草
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.burn=false;
                int maxparallel = 8;
                int temp=recipe.data.getInt("temp")/2;
                mmachine.Temperature+=temp;
                FluidStack pyrotheumFluid = new FluidStack(
                        Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:cryotheum"))),
                        100
                );
                // 检查输入仓是否有足够流体
                boolean isFluidSufficient = MachineUtils.inputFluid(pyrotheumFluid, mmachine);
                if(isFluidSufficient)mmachine.Temperature-=1000;
                double rate=-Math.pow((12500- mmachine.Temperature),2)+156250001;
                double rate2=Math.sqrt(rate)/1000;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier(parallel)
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*overclock*Math.max(rate2,0.5)))
                        .build();
            }
            if(recipe.data.getString("type").equals("boom"))
            {
                //热爆花
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.burn=false;
                mmachine.Temperature=0;
                int maxparallel=64;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .eutMultiplier(Math.sqrt(parallel))
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*overclock*overclock))
                        .build();
            }
            if(recipe.data.getString("type").equals("wither"))
            {
               // 凋零兔葵
                mmachine.wither=true;
                mmachine.thunder=false;
                mmachine.burn=false;
                mmachine.Temperature=0;
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
                //雷德花
                mmachine.wither=false;
                mmachine.thunder=true;
                mmachine.burn=false;
                mmachine.Temperature=0;
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
                //热绣球
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.burn=true;
                mmachine.Temperature=0;
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
                //勿落草
                mmachine.wither=false;
                mmachine.thunder=false;
                mmachine.burn=false;
                mmachine.Temperature=0;
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
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var tier = getTier();
        if(nutrition>1)
        {
            textList.add(textList.size(),Component.translatable("ctnh.garden.eat",String.format("%.1f",nutrition)));
        }
        if(Temperature>0)
        {
            textList.add(textList.size(),Component.translatable("ctnh.garden.fire",String.format("%d",Temperature)));
        }
    }
}
