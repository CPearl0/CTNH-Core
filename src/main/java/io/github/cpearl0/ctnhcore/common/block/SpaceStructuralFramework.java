package io.github.cpearl0.ctnhcore.common.block;

import com.gregtechceu.gtceu.api.block.AppearanceBlock;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPBData;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPSFData;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class SpaceStructuralFramework extends AppearanceBlock {
    public final IPSFData data;
    public SpaceStructuralFramework(BlockBehaviour.Properties properties,IPSFData Data) {
        super(properties);
        this.data=Data;
    }
    public IPSFData getData() {
        return this.data;
    }
    public static enum SpaceStructuralFrameworkType implements StringRepresentable, IPSFData {
        ENERGETIC_PHOTOVOLTAIC_BLOCK(1,1),
        PULSATING_PHOTOVOLTAIC_BLOCK(2,1),
        VIBRANT_PHOTOVOLTAIC_BLOCK(3,1),
        PHOTON_PRESS_COND_BLOCK(4,2);
        private final int tier;


        public int getTier() {
            return this.tier;
        }

        private  SpaceStructuralFrameworkType(int tier, int heatlevel) {
            this.tier = tier;
        }



        public @NotNull String getPhotovoltaicName() {
            return this.name().toLowerCase();
        }

        public @NotNull String getSerializedName() {
            return this.getPhotovoltaicName();
        }
    }
}


