package io.github.cpearl0.ctnhcore.registry;

import com.google.common.collect.Lists;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.ModelDefinition;
import lombok.Getter;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.List;
import java.util.function.Supplier;
//REGISTRATE之力!!!REGISTRATE之力!!!REGISTRATE之力!!!REGISTRATE之力!!!REGISTRATE之力!!!REGISTRATE之力!!!REGISTRATE之力!!!
public final class CTNHRegistrate extends GTRegistrate {

    CTNHRegistrate() {
        super(CTNHCore.MODID);
    }
    public static CTNHRegistrate create() {
        return new CTNHRegistrate();
    }

    @Deprecated
    @Getter
    private final List<ModelDefinition> models= Lists.newArrayList();
    @Deprecated
    public ModelDefinition registerModel(String name, Supplier<LayerDefinition> createBodyLayer) {
        ModelDefinition model = new ModelDefinition(name, createBodyLayer);
        models.add(model);
        return model;
    }

}
