package io.github.cpearl0.ctnhcore.registry.sound;

import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;

public class CTNHMusics {
    public static final Music ASTRAL_BGM = Musics.createGameMusic(CTNHSoundEvents.AMBIENT_ASTRAL.getHolder().orElseThrow());
}
