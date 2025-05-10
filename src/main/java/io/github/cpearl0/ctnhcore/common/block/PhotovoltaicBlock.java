package io.github.cpearl0.ctnhcore.common.block;

import com.gregtechceu.gtceu.api.block.AppearanceBlock;
import com.gregtechceu.gtceu.api.machine.multiblock.IBatteryData;
import lombok.Generated;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class PhotovoltaicBlock extends AppearanceBlock {
    public final IPBData data;
    public PhotovoltaicBlock(BlockBehaviour.Properties properties,IPBData ipbData) {
        super(properties);
        this.data=ipbData;
    }
    public IPBData getData() {
        return this.data;
    }
    public static enum PhotovoltaicType implements StringRepresentable, IPBData {
        ENERGETIC_PHOTOVOLTAIC_BLOCK(1,1),
        PULSATING_PHOTOVOLTAIC_BLOCK(2,1),
        VIBRANT_PHOTOVOLTAIC_BLOCK(3,1);
        private final int tier;
        private final int heatlevel;


        public int getTier() {
            return this.tier;
        }
        public int getheatlevel() {
            return this.heatlevel;
        }

        private PhotovoltaicType(int tier, int heatlevel) {
            this.tier = tier;
            this.heatlevel = heatlevel;
        }



        public @NotNull String getPhotovoltaicName() {
            return this.name().toLowerCase();
        }

        public @NotNull String getSerializedName() {
            return this.getPhotovoltaicName();
        }
    }
}


