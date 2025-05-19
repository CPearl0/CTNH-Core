package io.github.cpearl0.ctnhcore.registry;

import io.github.cpearl0.ctnhcore.client.model.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

//不同于CTNHModel，这个是用来注册BlockBench的实体模型的
public class CTNHModelLayers {
    public static void init(){}
    public static ModelDefinition TURBINE_ROTOR_MODEL = REGISTRATE.registerModel("turbine_rotor", TurbineRotorModel::createBodyLayer);
    public static ModelDefinition MAGIC_CUBE_MODEL = REGISTRATE.registerModel("magic_cube", MagicCubeModel::createBodyLayer);
}
