package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.enderio.base.common.init.EIOFluids;
import com.enderio.machines.common.init.MachineBlocks;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.mojang.authlib.GameProfile;
import io.github.cpearl0.ctnhcore.api.gui.CTNHGuiTextures;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SlaughterHouseMachine extends WorkableElectricMultiblockMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            SlaughterHouseMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public final NotifiableItemStackHandler machineStorage;
    public UUID uuid = UUID.randomUUID();
    public List<String> mobList = new ArrayList<>();
    public double timeCost = 20;
    public ItemStack hostWeapon = Items.DIRT.getDefaultInstance();

    private FakePlayer fakePlayer;

    public FakePlayer getFakePlayer(ServerLevel level) {
        if (fakePlayer == null) {
            fakePlayer = new FakePlayer(level, new GameProfile(uuid, "slaughter"));
        }
        return fakePlayer;
    }


    public SlaughterHouseMachine(IMachineBlockEntity holder) {
        super(holder);
        this.machineStorage = createMachineStorage((byte) 1);
    }
    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 1, IO.NONE, IO.BOTH, slots -> new CustomItemStackHandler(1) {
            @Override
            public int getSlotLimit(int slot) {
                return value;
            }
            @Override
            public void onContentsChanged(int slot) {
                resetWeapon();
                super.onContentsChanged(slot);
            }
        });
    }

    @Override
    public @NotNull Widget createUIWidget() {
        var widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            var size = group.getSize();
            group.addWidget(
                    new SlotWidget(machineStorage.storage, 0, size.width - 30, size.height - 30, true, true)
                            .setBackground(CTNHGuiTextures.SLOT_WEAPON));
        }
        return widget;
    }

    public ItemStack getMachineStorageItem() {
        return machineStorage.getStackInSlot(0);
    }

    public void resetWeapon() {
        if (machineStorage.isEmpty()){
            hostWeapon = Items.DIRT.getDefaultInstance();
        }
        else {
            hostWeapon = getMachineStorageItem();
        }
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (!super.beforeWorking(recipe))
            return false;
        resetWeapon();
        mobList.clear();
        getParts().forEach(part -> {
            part.getRecipeHandlers().forEach(trait -> {
                if (trait.getHandlerIO().equals(IO.IN) && trait.getCapability() == ItemRecipeCapability.CAP) {
                    trait.getContents().forEach(contents -> {
                        if (contents instanceof ItemStack item) {
                            if (item.is(MachineBlocks.POWERED_SPAWNER.asItem()) && item.hasTag()) {
                                var mob = item.getTag().getCompound("BlockEntityTag").getCompound("EntityStorage").getCompound("Entity").getString("id");
                                if(!mobList.contains(mob)){
                                    mobList.add(mob);
                                }
                            }
                        }
                    });
                }
            });
        });

        return !mobList.isEmpty();
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
        ServerLevel level = (ServerLevel) machine.getLevel();
        var newrecipe = GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK).applyModifier(machine,recipe);
        if(machine instanceof SlaughterHouseMachine smachine && !smachine.mobList.isEmpty()) {
            // 战利品模式
            double totalhealth = 0;
            List<Content> itemList = new ArrayList<>();
            for (int i = 0; i < (((SlaughterHouseMachine) machine).getTier() - 2) * 4; i++) {
                int index = level.getRandom().nextInt(smachine.mobList.size());
                String mob = smachine.mobList.get(index);
                var mobentity = EntityType.byString(mob).get().create(machine.getLevel());
                if (mobentity instanceof LivingEntity) {
                    if (((LivingEntity) mobentity).getArmorValue() != 0) {
                        var armor = ((LivingEntity) mobentity).getArmorValue();
                        totalhealth += ((LivingEntity) mobentity).getMaxHealth() / ((double) 20 / (armor + 20));
                    } else {
                        totalhealth += ((LivingEntity) mobentity).getMaxHealth();
                    }
                    if (mob.equals("minecraft:wither")){
                        itemList.add(new Content(SizedIngredient.create(Items.NETHER_STAR.getDefaultInstance()), 1, 1, 0, null, null));
                        continue;
                    }
                    var fakePlayer = smachine.getFakePlayer(level);
                    var loottable = Objects.requireNonNull(level.getServer()).getLootData().getLootTable(new ResourceLocation(mob.split(":")[0] + ":entities/" + mob.split(":")[1]));
                    var lootparams = new LootParams.Builder((ServerLevel) machine.getLevel())
                            .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer)
                            .withParameter(LootContextParams.TOOL,smachine.hostWeapon)
                            .withParameter(LootContextParams.THIS_ENTITY, mobentity)
                            .withParameter(LootContextParams.DAMAGE_SOURCE, new DamageSources(level.getServer().registryAccess()).mobAttack(fakePlayer))
                            .withParameter(LootContextParams.ORIGIN, machine.getPos().getCenter())
                            .create(loottable.getParamSet());
                    var loots = loottable.getRandomItems(lootparams);
                    loots.forEach(itemStack -> {
                        if (!itemStack.isEmpty()){
                            itemList.add(new Content(SizedIngredient.create(itemStack), 1, 1, 0, null, null));
                        }
                    });
                }
            }
            newrecipe.outputs.put(ItemRecipeCapability.CAP,itemList);
            newrecipe.outputs.put(FluidRecipeCapability.CAP, List.of(new Content(FluidIngredient.of(new FluidStack(EIOFluids.XP_JUICE.get().getSource(), (int) (totalhealth * 5))), 1, 1, 0, null, null)));
            newrecipe.duration = (int)(totalhealth / (20 * (smachine.getTier() - 2) * 4) * 40);
        }
        return recipe1 -> newrecipe;
    }
    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        var mobName = mobList.stream().map(mob -> EntityType.byString(mob).get().getDescription().getString()).toList();
        textList.add(textList.size(), Component.translatable("ctnh.multiblock.slaughter_house.mobcount", mobList.size(),mobName));
    }
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
