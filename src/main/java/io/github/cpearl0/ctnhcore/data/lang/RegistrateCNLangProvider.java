package io.github.cpearl0.ctnhcore.data.lang;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateProvider;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import io.github.cpearl0.ctnhcore.data.CTNHCoreDatagen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.LogicalSide;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class RegistrateCNLangProvider extends LanguageProvider implements RegistrateProvider {
    private final AbstractRegistrate<?> owner;

    public RegistrateCNLangProvider(AbstractRegistrate<?> owner, PackOutput packOutput) {
        super(packOutput, owner.getModid(), "zh_cn");
        this.owner = owner;
    }

    public LogicalSide getSide() {
        return LogicalSide.CLIENT;
    }

    public String getName() {
        return "Lang (zh_cn)";
    }

    protected void addTranslations() {
        this.owner.genData(CTNHCoreDatagen.CNLANG, this);
    }

    public static final String toEnglishName(String internalName) {
        return (String) Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }

    public <T> String getAutomaticName(NonNullSupplier<? extends T> sup, ResourceKey<Registry<T>> registry) {
        return toEnglishName(((Registry) BuiltInRegistries.REGISTRY.get(registry.location())).getKey(sup.get()).getPath());
    }

    public void addBlock(NonNullSupplier<? extends Block> block) {
        this.addBlock(block, this.getAutomaticName(block, Registries.BLOCK));
    }

    public void addBlockWithTooltip(NonNullSupplier<? extends Block> block, String tooltip) {
        this.addBlock(block);
        this.addTooltip(block, tooltip);
    }

    public void addBlockWithTooltip(NonNullSupplier<? extends Block> block, String name, String tooltip) {
        this.addBlock(block, name);
        this.addTooltip(block, tooltip);
    }

    public void addItem(NonNullSupplier<? extends Item> item) {
        this.addItem(item, this.getAutomaticName(item, Registries.ITEM));
    }

    public void addItemWithTooltip(NonNullSupplier<? extends Item> block, String name, List<@NonnullType String> tooltip) {
        this.addItem(block, name);
        this.addTooltip(block, tooltip);
    }

    public void addTooltip(NonNullSupplier<? extends ItemLike> item, String tooltip) {
        this.add(((ItemLike)item.get()).asItem().getDescriptionId() + ".desc", tooltip);
    }

    public void addTooltip(NonNullSupplier<? extends ItemLike> item, List<@NonnullType String> tooltip) {
        for(int i = 0; i < tooltip.size(); ++i) {
            this.add(((ItemLike)item.get()).asItem().getDescriptionId() + ".desc." + i, (String)tooltip.get(i));
        }

    }

    public void add(CreativeModeTab tab, String name) {
        ComponentContents contents = tab.getDisplayName().getContents();
        if (contents instanceof TranslatableContents lang) {
            this.add(lang.getKey(), name);
        } else {
            throw new IllegalArgumentException("Creative tab does not have a translatable name: " + tab.getDisplayName());
        }
    }

    public void addEntityType(NonNullSupplier<? extends EntityType<?>> entity) {
        this.addEntityType(entity, this.getAutomaticName(entity, Registries.ENTITY_TYPE));
    }

    public void add(String key, String value) {
        super.add(key, value);
    }

    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(super.run(cache));
    }
}
