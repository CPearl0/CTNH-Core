package io.github.cpearl0.ctnhcore.registry.sound;

import com.gregtechceu.gtceu.api.sound.SoundEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHSoundEntries {
    public static void init() {
    }
    public static final SoundEntry AMBIENT_ASTRAL = REGISTRATE.sound(CTNHCore.id("ambient.astral")).build();

}
