package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import io.github.cpearl0.ctnhcore.api.gui.CTNHGuiTextures;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.ingredient.fluid.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.item.ItemIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.FluidStack;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.enderio.base.common.init.EIOFluids;
import com.enderio.machines.common.init.MachineBlocks;
import com.mojang.authlib.GameProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;
import tech.vixhentx.mcmod.ctnhlib.utils.MachineUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlaughterHouseMachine extends RecipeElectricMultiblockMachine implements IMachineModifyDrops {

    @CN("怪物种类：%d (%s)")
    @EN("Mob Types: %d (%s)")
    public static Lang slaughterHouseInfoMobcount;

    @CN("没有可宰杀的怪物：请放入带有实体数据的电动刷怪笼")
    @EN("No mob to slaughter: insert a powered spawner holding entity data")
    public static Lang noMobToSlaughter;

    @Persisted
    public final NotifiableItemStackHandler machineStorage;
    public UUID uuid = UUID.randomUUID();
    public List<String> mobList = new ArrayList<>();
    public double damagePerSecond = 4.0;
    public static int ticksPerSecond = 20;
    public ItemStack hostWeapon = Items.DIRT.getDefaultInstance();

    private static final int LOOT_SAMPLES_PER_REFRESH = 4;
    private static final int LOOT_CACHE_TTL_TICKS = 20;
    private static final int ENTITY_CACHE_LIMIT = 64;
    private static final int LOOT_TABLE_CACHE_LIMIT = 128;

    private boolean mobListDirty = true;
    private boolean lootCacheDirty = true;
    private long lootCacheComputedAtTick = -1;
    private long lootCacheValidUntilTick = -1;
    private List<ItemStack> lootCacheStacks = List.of();
    private int lootCacheTotalExperience = 0;
    private int lootCacheDuration = 0;
    private int lootCacheRepeatTimes = 1;

    private final LinkedHashMap<String, LivingEntity> entityCache = new LinkedHashMap<>(ENTITY_CACHE_LIMIT, 0.75f,
            true) {

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, LivingEntity> eldest) {
            return size() > ENTITY_CACHE_LIMIT;
        }
    };

    private final LinkedHashMap<ResourceLocation, LootTable> lootTableCache = new LinkedHashMap<>(
            LOOT_TABLE_CACHE_LIMIT,
            0.75f, true) {

        @Override
        protected boolean removeEldestEntry(Map.Entry<ResourceLocation, LootTable> eldest) {
            return size() > LOOT_TABLE_CACHE_LIMIT;
        }
    };

    private FakePlayer fakePlayer;

    public FakePlayer getFakePlayer(ServerLevel level) {
        if (fakePlayer == null) {
            fakePlayer = new FakePlayer(level, new GameProfile(uuid, "slaughter"));
        }
        return fakePlayer;
    }

    public SlaughterHouseMachine(IMachineBlockEntity holder) {
        super(holder);
        this.machineStorage = attachTrait(createMachineStorage((byte) 1));
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        if (getLevel() instanceof ServerLevel serverLevel) {
            getFakePlayer(serverLevel);
        }
        resetWeapon();
        markMobListDirty();
        attachInputChangeSubscriptions();
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        mobList.clear();
        markMobListDirty();
        clearLootCache();
        entityCache.clear();
        lootTableCache.clear();
    }

    @Override
    public void onUnload() {
        super.onUnload();
        entityCache.clear();
        lootTableCache.clear();
        clearLootCache();
        mobList.clear();
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
    public void onDrops(List<ItemStack> drops) {
        clearInventory(machineStorage.storage);
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
        if (machineStorage.isEmpty()) {
            hostWeapon = Items.DIRT.getDefaultInstance();
        } else {
            hostWeapon = getMachineStorageItem();
        }
        if (getLevel() instanceof ServerLevel serverLevel) {
            getFakePlayer(serverLevel).setItemInHand(InteractionHand.MAIN_HAND, hostWeapon);
        }

        damagePerSecond = calculateFinalValue(1,
                hostWeapon.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream()) *
                calculateFinalValue(4,
                        hostWeapon.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED).stream());

        lootCacheDirty = true;
    }

    public static double calculateFinalValue(double baseValue, Stream<AttributeModifier> modifiers) {
        // 按操作类型分组并处理
        var modifiersByOp = modifiers.collect(
                Collectors.groupingBy(AttributeModifier::getOperation));

        // 1. 处理 ADDITION
        double addition = modifiersByOp.getOrDefault(AttributeModifier.Operation.ADDITION, List.of())
                .stream()
                .mapToDouble(AttributeModifier::getAmount)
                .sum();

        // 2. 处理 MULTIPLY_BASE（基于原始值）
        double multiplyBase = modifiersByOp.getOrDefault(AttributeModifier.Operation.MULTIPLY_BASE, List.of())
                .stream()
                .mapToDouble(AttributeModifier::getAmount)
                .sum();

        // 3. 处理 MULTIPLY_TOTAL（基于当前值）
        double multiplyTotal = modifiersByOp.getOrDefault(AttributeModifier.Operation.MULTIPLY_TOTAL, List.of())
                .stream()
                .mapToDouble(AttributeModifier::getAmount)
                .reduce(1.0, (a, b) -> a * (1 + b)); // 连乘 (1 + amount)

        // 最终计算
        return (baseValue + addition + (baseValue * multiplyBase)) * multiplyTotal;
    }

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        var failReason = super.beforeWorking(recipe);
        if (failReason != null) return failReason;
        ensureMobListUpToDate();
        return mobList.isEmpty() ? noMobToSlaughter.translate() : null;
    }

    public void resetMobList() {
        mobList.clear();
        MachineUtils.applyContents(this, contents -> {
            if (contents instanceof ItemStack item) {
                if (item.is(MachineBlocks.POWERED_SPAWNER.asItem()) && item.hasTag()) {
                    var mob = item.getTag().getCompound("BlockEntityTag").getCompound("EntityStorage")
                            .getCompound("Entity").getString("id");
                    if (!mob.isEmpty() && !mobList.contains(mob)) mobList.add(mob);
                }
            }
        }, ItemRecipeCapability.CAP, IO.IN);
    }

    private void markMobListDirty() {
        mobListDirty = true;
        lootCacheDirty = true;
    }

    private void ensureMobListUpToDate() {
        if (!mobListDirty) return;
        resetMobList();
        mobListDirty = false;
    }

    private void clearLootCache() {
        lootCacheDirty = true;
        lootCacheComputedAtTick = -1;
        lootCacheValidUntilTick = -1;
        lootCacheStacks = List.of();
        lootCacheTotalExperience = 0;
        lootCacheDuration = 0;
        lootCacheRepeatTimes = 1;
    }

    private void attachInputChangeSubscriptions() {
        if (!(getLevel() instanceof ServerLevel)) return;

        for (var part : getParts()) {
            for (var handlerList : part.getRecipeHandlers()) {
                if (handlerList.getAllHandlers().stream()
                        .noneMatch(handler -> handler.getHandlerIO().support(IO.IN)))
                    continue;
                getRecipeLogic().getTraitSubscriptions()
                        .add(handlerList.subscribe(this::markMobListDirty, ItemRecipeCapability.CAP));
            }
        }
    }

    private LivingEntity getOrCreateCachedEntity(ServerLevel level, String mobId) {
        var cached = entityCache.get(mobId);
        if (cached != null && cached.level() == level) {
            return cached;
        }

        var typeOpt = EntityType.byString(mobId);
        if (typeOpt.isEmpty()) return null;
        var created = typeOpt.get().create(level);
        if (!(created instanceof LivingEntity living)) return null;
        entityCache.put(mobId, living);
        return living;
    }

    private static double getEffectiveHealth(LivingEntity livingEntity) {
        if (livingEntity.getArmorValue() != 0) {
            var armor = livingEntity.getArmorValue();
            return livingEntity.getMaxHealth() / ((double) 20 / (armor + 20));
        }
        return livingEntity.getMaxHealth();
    }

    private record ItemKey(net.minecraft.world.item.Item item, @Nullable CompoundTag tag) {}

    private LootTable getOrCacheLootTable(MinecraftServer server, ResourceLocation tableId) {
        return lootTableCache.computeIfAbsent(tableId, id -> server.getLootData().getLootTable(id));
    }

    private void rebuildLootCache(ServerLevel level) {
        ensureMobListUpToDate();
        if (mobList.isEmpty()) {
            clearLootCache();
            lootCacheDirty = false;
            lootCacheComputedAtTick = level.getGameTime();
            lootCacheValidUntilTick = level.getGameTime() + LOOT_CACHE_TTL_TICKS;
            return;
        }

        int repeatTimes = Math.max(1, getTier() - 2);
        lootCacheRepeatTimes = repeatTimes;

        double totalTime = 0;
        int totalExperience = 0;
        Map<ItemKey, Long> lootCounts = new HashMap<>();

        var server = Objects.requireNonNull(level.getServer());
        var fakePlayer = getFakePlayer(level);
        var damageSource = new DamageSources(server.registryAccess()).mobAttack(fakePlayer);
        var origin = getPos().getCenter();
        var blockState = getBlockState();
        var blockEntity = level.getBlockEntity(getPos());

        for (int i = 0; i < LOOT_SAMPLES_PER_REFRESH; i++) {
            String mob = mobList.get(level.getRandom().nextInt(mobList.size()));

            if (mob.equals("minecraft:wither")) {
                var stack = Items.NETHER_STAR.getDefaultInstance();
                var key = new ItemKey(stack.getItem(), null);
                lootCounts.merge(key, (long) stack.getCount(), Long::sum);
                continue;
            }

            var livingEntity = getOrCreateCachedEntity(level, mob);
            if (livingEntity == null) continue;

            var enchantInfluence = EnchantmentHelper.getDamageBonus(hostWeapon, livingEntity.getMobType());
            totalTime += getEffectiveHealth(livingEntity) / ((damagePerSecond + enchantInfluence) * repeatTimes) *
                    ticksPerSecond;
            totalExperience += livingEntity.getExperienceReward() * 20;

            var mobId = ResourceLocation.tryParse(mob);
            if (mobId == null) continue;

            var lootTableId = ResourceLocation.tryBuild(mobId.getNamespace(), "entities/" + mobId.getPath());
            var lootTable = getOrCacheLootTable(server, lootTableId);
            var lootParams = new LootParams.Builder(level)
                    .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer)
                    .withParameter(LootContextParams.TOOL, hostWeapon)
                    .withParameter(LootContextParams.THIS_ENTITY, livingEntity)
                    .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                    .withParameter(LootContextParams.ORIGIN, origin)
                    .withParameter(LootContextParams.KILLER_ENTITY, fakePlayer)
                    .withParameter(LootContextParams.BLOCK_STATE, blockState)
                    .withParameter(LootContextParams.BLOCK_ENTITY, blockEntity)
                    .withParameter(LootContextParams.DIRECT_KILLER_ENTITY, fakePlayer)
                    .withParameter(LootContextParams.EXPLOSION_RADIUS, 0F)
                    .create(lootTable.getParamSet());

            for (var loot : lootTable.getRandomItems(lootParams)) {
                if (loot.isEmpty()) continue;
                var key = new ItemKey(loot.getItem(), loot.getTag());
                lootCounts.merge(key, (long) loot.getCount(), Long::sum);
            }
        }

        if (repeatTimes > 1) {
            totalExperience = Math.multiplyExact(totalExperience, repeatTimes);
            for (var entry : lootCounts.entrySet()) {
                entry.setValue(Math.multiplyExact(entry.getValue(), (long) repeatTimes));
            }
        }

        var mergedStacks = new ArrayList<ItemStack>(lootCounts.size());
        for (var entry : lootCounts.entrySet()) {
            long totalCount = entry.getValue();
            var item = entry.getKey().item;
            var tag = entry.getKey().tag;

            int count = (int) Math.min(Integer.MAX_VALUE, totalCount);
            var stack = new ItemStack(item, count);
            if (tag != null) stack.setTag(tag.copy());
            mergedStacks.add(stack);
        }

        lootCacheStacks = mergedStacks;
        lootCacheTotalExperience = totalExperience;
        lootCacheDuration = Math.max(1, (int) totalTime * repeatTimes);
        lootCacheDirty = false;
        lootCacheComputedAtTick = level.getGameTime();
        lootCacheValidUntilTick = level.getGameTime() + LOOT_CACHE_TTL_TICKS;
    }

    private void ensureLootCacheUpToDate(ServerLevel level) {
        ensureMobListUpToDate();
        long now = level.getGameTime();
        if (!lootCacheDirty && lootCacheValidUntilTick >= now) return;
        rebuildLootCache(level);
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (!(machine instanceof SlaughterHouseMachine smachine)) {
            return RecipeModifier.nullWrongType(SlaughterHouseMachine.class, machine);
        }
        Component failure = OverclockingLogic.NON_PERFECT_OVERCLOCK.getModifier(machine, group, recipe,
                smachine.getOverclockVoltage());
        if (failure != null) return failure;
        if (machine instanceof SlaughterHouseMachine) {
            if (machine.getLevel() instanceof ServerLevel level) {
                smachine.ensureLootCacheUpToDate(level);
                if (!smachine.mobList.isEmpty()) {
                    var itemList = smachine.lootCacheStacks.stream()
                            .map(stack -> ItemIngredient.of(stack.copy()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    recipe.outputs.put(ItemRecipeCapability.CAP, itemList);
                    recipe.outputs.put(FluidRecipeCapability.CAP,
                            List.of(FluidIngredient.of(new FluidStack(EIOFluids.XP_JUICE.get().getSource(),
                                    smachine.lootCacheTotalExperience))));
                    recipe.duration = smachine.lootCacheDuration;
                }
            }
        }
        return null;
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        var mobName = mobList.stream().map(mob -> EntityType.byString(mob).get().getDescription().getString()).toList();
        textList.add(textList.size(),
                slaughterHouseInfoMobcount.translate(mobList.size(), mobName));
    }
}
