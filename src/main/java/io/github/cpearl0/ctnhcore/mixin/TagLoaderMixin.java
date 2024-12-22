package io.github.cpearl0.ctnhcore.mixin;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(value = TagLoader.class, priority = 0)
public class TagLoaderMixin {
    @Inject(method = "load", at = @At("RETURN"))
    public void CTNH$loadTags(ResourceManager manager, CallbackInfoReturnable<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> cir) {
        if (Config.disableFTBUltimineOnGTOres) {
            List<TagLoader.EntryWithSource> tags = new ArrayList<>();
            GTBlocks.MATERIAL_BLOCKS.rowMap().forEach((prefix, map) -> {
                if (TagPrefix.ORES.containsKey(prefix)) {
                    map.forEach((material, blockEntry) -> {
                        tags.add(new TagLoader.EntryWithSource(
                                TagEntry.element(blockEntry.getId()),
                                CTNHCore.CUSTOM_TAG_SOURCE
                        ));
                    });
                }
            });

            Map<ResourceLocation, List<TagLoader.EntryWithSource>> tagMap = (Map) cir.getReturnValue();
            tagMap.computeIfPresent(FTBUltimine.EXCLUDED_BLOCKS.location(), (path, oldValue) -> {
                oldValue.addAll(tags);
                return oldValue;
            });
        }
    }
}
