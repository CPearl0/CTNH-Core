package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SlaughterHouse extends WorkableElectricMultiblockMachine {
    public UUID uuid = UUID.randomUUID();
    public List<String> moblist = new ArrayList<>();
    public double timecost = 20;
    public SlaughterHouse(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public boolean onWorking() {
        if (getRecipeLogic().getProgress() == 10) {
//            String[] key = getHolder().self().getPersistentData().getAllKeys().toArray(new String[0]);
//            String[] moblist = getHolder().self().getPersistentData().get(key[0]);
            // moblist.forEach(mob =>{
            //     console.info(mob)
            // })
//            Map<Direction, BlockPos> blockpos = new HashMap<>() {{
//                put(Direction.WEST, getPos().offset(2, 1, 0));
//                put(Direction.EAST, getPos().offset(-2, 1, 0));
//                put(Direction.NORTH, getPos().offset(0, 1, 2));
//                put(Direction.SOUTH, getPos().offset(0, 1, -2));
//            }};
//            var itempos = getPos();
//            blockpos.forEach(pos -> {
//            if (facing.equals(pos[0])) {
//                itempos = pos[1]
//                return
//            }
//                })
            //战利品模式
            var lootparams = new LootParams.Builder((ServerLevel) getLevel()).withParameter(LootContextParams.LAST_DAMAGE_PLAYER, new FakePlayer((ServerLevel) getLevel(), new GameProfile(uuid, "slaughter"))).create(new LootContextParamSet.Builder().build());
            double totalhealth = 0;
            for (int oc = 0; oc <= getTier() * 4; oc++) {
                double index = 0;
                index = Math.random() * moblist.size();
                String mob = moblist.get((int) Math.floor(index));
                var mobentity = EntityType.byString(mob).get().create(getLevel());
                if (mobentity instanceof LivingEntity) {
                    if (((LivingEntity) mobentity).getArmorValue() != 0) {
                        var armor = ((LivingEntity) mobentity).getArmorValue();
                        totalhealth += ((LivingEntity) mobentity).getMaxHealth() / (20 / (armor + 20));
                    }
                    else {
                        totalhealth += ((LivingEntity) mobentity).getMaxHealth();
                    }
                }
                var loottable = Objects.requireNonNull(getLevel().getServer())
                        .getLootData()
                        .getLootTable(new ResourceLocation(mob.split(":")[0] + ":entities/" + mob.split(":")[1]))
                        .getRandomItems(lootparams);
                loottable.forEach(itemStack -> {
                        var tmp = GTRecipeBuilder.ofRaw().outputItems(itemStack).buildRawRecipe();
                if (tmp.matchRecipe(this).isSuccess()) {
                    tmp.handleRecipeIO(IO.OUT, this, recipeLogic.getChanceCaches());
                }
                    });
            }
            timecost = totalhealth / (20 * getTier() * 4);
        }
        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
            AtomicBoolean find = new AtomicBoolean(false);
            getParts().forEach(part -> part.getRecipeHandlers().forEach(trait -> {
                if (trait.getHandlerIO().equals(IO.IN)) {
                    trait.getContents().forEach(contents -> {
                        if (contents instanceof ItemStack item) {
                            if (item.getItem().equals("enderio:powered_spawner")) {
                                System.out.println(item.getTag().getCompound("BlockEntityTag"));
                                moblist.add(item.getTag().getCompound("BlockEntityTag").getCompound("EntityStorage").getCompound("Entity").getString("id"));
                                find.set(true);
                            }
                        }
                    });
                }
            }));
            if (!find.get()) {
                getRecipeLogic().interruptRecipe();
                return false;
            }
            return super.beforeWorking(recipe);
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(textList.size(),Component.translatable("ctnhcore.slaughter_house.moblist",moblist.size()));
    }
}
