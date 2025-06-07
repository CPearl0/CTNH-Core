package io.github.cpearl0.ctnhcore.registry;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.recipe.chance.boost.ChanceBoostFunction;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.List;

public class CTNHChanceLogic {
    public static final ChanceLogic BASIC = new ChanceLogic("basic") {
        @Override
        public @Unmodifiable List<@NotNull Content> roll(@NotNull @Unmodifiable List<@NotNull Content> chancedEntries,
                                                         @NotNull ChanceBoostFunction boostFunction,
                                                         int recipeTier, int chanceTier,
                                                         @Nullable Object2IntMap<?> cache, int times) {
            ImmutableList.Builder<Content> builder = ImmutableList.builder();
            for (Content entry : chancedEntries) {
                int maxChance = entry.maxChance;

                int newChance = entry.chance;
                int totalChance = times * newChance;
                int guaranteed = totalChance / maxChance;
                if (guaranteed > 0) builder.addAll(Collections.nCopies(guaranteed, entry));
                newChance = totalChance % maxChance;

                int cached = GTValues.RNG.nextInt(entry.maxChance);
                int chance = newChance + cached;
                while (chance >= maxChance) {
                    builder.add(entry);
                    chance -= maxChance;
                    newChance -= maxChance;
                }
            }
            return builder.build();
        }

        @Override
        public @NotNull Component getTranslation() {
            return Component.literal("BASIC");
        }
    };
    public static void init(){}
}
