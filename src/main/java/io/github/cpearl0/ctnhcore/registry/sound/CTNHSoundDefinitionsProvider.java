package io.github.cpearl0.ctnhcore.registry.sound;

import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import static io.github.cpearl0.ctnhcore.registry.sound.CTNHSoundEvents.AMBIENT_ASTRAL;

public class CTNHSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public CTNHSoundDefinitionsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    @Override
    public void registerSounds() {
        this.add(AMBIENT_ASTRAL.get(), definition()
                .subtitle("subtitle.ctnhcore.bgm.plague_wasteland")
                .with(sound(CTNHCore.id("astral_infection"))
                        .weight(3)
                        .stream(),
                        sound(CTNHCore.id("astral_infection_underground"))
                                .weight(3)
                                .stream(),
                        sound(CTNHCore.id("astrum_deus"))
                                .weight(1)
                                .stream(),
                        sound(CTNHCore.id("astrum_aureus"))
                                .weight(1)
                                .stream()));
    }
}
