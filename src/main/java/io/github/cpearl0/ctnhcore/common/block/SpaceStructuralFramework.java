package io.github.cpearl0.ctnhcore.common.block;

import com.gregtechceu.gtceu.api.block.AppearanceBlock;
import io.github.cpearl0.ctnhcore.common.block.blockdata.ISSFData;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class SpaceStructuralFramework extends AppearanceBlock {
    public final ISSFData data;
    public SpaceStructuralFramework(BlockBehaviour.Properties properties, ISSFData Data) {
        super(properties);
        this.data=Data;
    }
    public ISSFData getData() {
        return this.data;
    }
    public static enum SpaceStructuralFrameworkType implements StringRepresentable, ISSFData {
        NQ_EXCITE_CARBON_CARBON_NANOFIBER_STRUCTURAL_BLOCK(1);
        private final int tier;


        public int getTier() {
            return this.tier;
        }

        private  SpaceStructuralFrameworkType(int tier) {
            this.tier = tier;
        }



        public @NotNull String getSpaceStructuralFrameworkName() {
            return this.name().toLowerCase();
        }

        public @NotNull String getSerializedName() {
            return this.getSpaceStructuralFrameworkName();
        }
    }
}


