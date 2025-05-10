package io.github.cpearl0.ctnhcore.api.data.material;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;

@Accessors(chain = true, fluent = true)
public class CTNHMaterialBuilder extends Material.Builder {
    public CTNHMaterialBuilder(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }
    public CTNHMaterialBuilder nuclear(boolean isFertile, boolean isFissile) {
        MaterialProperties properties = new MaterialProperties();
        try {
            Field field = Material.Builder.class.getDeclaredField("properties");
            field.setAccessible(true); // 关闭访问检查
            properties = (MaterialProperties) field.get(this); // 获取值
        } catch (Exception e) {
            e.printStackTrace();
        }
        properties.ensureSet(CTNHPropertyKeys.NUCLEAR);
        properties.getProperty(CTNHPropertyKeys.NUCLEAR).setFertile(isFertile);
        properties.getProperty(CTNHPropertyKeys.NUCLEAR).setFissile(isFissile);
        return (CTNHMaterialBuilder) this;
    }
}
