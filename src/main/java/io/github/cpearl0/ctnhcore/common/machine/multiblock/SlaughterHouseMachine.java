package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.enderio.machines.common.init.MachineBlocks;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SlaughterHouseMachine extends WorkableElectricMultiblockMachine {
    public UUID uuid = UUID.randomUUID();
    public List<String> mobList = new ArrayList<>();
    public double timeCost = 20;

    public SlaughterHouseMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public boolean onWorking() {
        ServerLevel level = (ServerLevel) getLevel();
        if (level != null && !mobList.isEmpty() && getRecipeLogic().getProgress() == 10) {
            // 战利品模式
            double totalhealth = 0;
            for (int oc = 0; oc <= getTier() * 4; oc++) {
                int index = level.getRandom().nextInt(mobList.size());
                String mob = mobList.get(index);
                var mobentity = EntityType.byString(mob).get().create(getLevel());
                if (mobentity instanceof LivingEntity) {
                    if (((LivingEntity) mobentity).getArmorValue() != 0) {
                        var armor = ((LivingEntity) mobentity).getArmorValue();
                        totalhealth += ((LivingEntity) mobentity).getMaxHealth() / ((double) 20 / (armor + 20));
                    } else {
                        totalhealth += ((LivingEntity) mobentity).getMaxHealth();
                    }
                    var fakePlayer = new FakePlayer(level, new GameProfile(uuid, "slaughter"));
                    var loottable = Objects.requireNonNull(level.getServer()).getLootData().getLootTable(new ResourceLocation(mob.split(":")[0] + ":entities/" + mob.split(":")[1]));
                    var lootparams = new LootParams.Builder((ServerLevel) getLevel())
                            .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer)
                            .withParameter(LootContextParams.THIS_ENTITY, mobentity)
                            .withParameter(LootContextParams.DAMAGE_SOURCE, new DamageSources(level .getServer().registryAccess()).mobAttack(fakePlayer))
                            .withParameter(LootContextParams.ORIGIN, getPos().getCenter())
                            .create(loottable.getParamSet());
                    var loots = loottable.getRandomItems(lootparams);
                    loots.forEach(itemStack -> {
                        var tmp = GTRecipeBuilder.ofRaw().outputItems(itemStack).buildRawRecipe();
                        if (tmp.matchRecipe(this).isSuccess()) {
                            tmp.handleRecipeIO(IO.OUT, this, recipeLogic.getChanceCaches());
                        }
                    });
                }
            }
            timeCost = totalhealth / (20 * getTier() * 4);
        }
        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (!super.beforeWorking(recipe))
            return false;

        mobList.clear();
        getParts().forEach(part -> part.getRecipeHandlers().forEach(trait -> {
            if (trait.getHandlerIO().equals(IO.IN)) {
                trait.getContents().forEach(contents -> {
                    if (contents instanceof ItemStack item) {
                        if (item.is(MachineBlocks.POWERED_SPAWNER.asItem()) && item.hasTag()) {
                            mobList.add(item.getTag().getCompound("BlockEntityTag").getCompound("EntityStorage").getCompound("Entity").getString("id"));
                        }
                    }
                });
            }
        }));

        return !mobList.isEmpty();
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(textList.size(), Component.translatable("ctnh.multiblock.slaughter_house.mobcount", mobList.size()));
    }
}
