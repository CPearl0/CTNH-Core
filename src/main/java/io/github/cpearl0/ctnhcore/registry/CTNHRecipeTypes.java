package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

public class CTNHRecipeTypes {
    public static final GTRecipeType UNDERFLOOR_HEATING_SYSTEM = GTRecipeTypes.register("underfloor_heating_system", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(0, 0, 1, 1)
            .setEUIO(IO.NONE)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static final GTRecipeType ASTRONOMICAL_OBSERVATORY = GTRecipeTypes.register("astronomical_observatory", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(1, 0, 0, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static void init() {

    }
}
