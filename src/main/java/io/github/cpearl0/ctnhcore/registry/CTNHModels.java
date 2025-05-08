package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.block.IFilterType;
import com.gregtechceu.gtceu.common.block.CoilBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.block.PhotovoltaicBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;

public class CTNHModels {
    public static NonNullBiConsumer<DataGenContext<Block, CoilBlock>, RegistrateBlockstateProvider> createCoilModel(String name, ICoilType coilType) {
            return (ctx, prov) -> {
                ActiveBlock block = ctx.getEntry();
                ModelFile inactive = prov.models().cubeAll(name, coilType.getTexture());
                ModelFile active = prov.models().withExistingParent(name + "_active", CTNHCore.id("block/cube_2_layer/all"))
                        .texture("bot_all", coilType.getTexture())
                        .texture("top_all", coilType.getTexture().withSuffix("_bloom"));
                prov.getVariantBuilder(block)
                        .partialState().with(ActiveBlock.ACTIVE, false).modelForState().modelFile(inactive).addModel()
                        .partialState().with(ActiveBlock.ACTIVE, true).modelForState().modelFile(active).addModel();
            };
    }
    public static NonNullBiConsumer<DataGenContext<Block, RotatedPillarBlock>, RegistrateBlockstateProvider> createMapCasingModel(String name,
                                                                                                                                  String map) {
        return (ctx, prov) -> {
            prov.axisBlock(ctx.getEntry(),  CTNHCore.id("block/casings/map/%s/side".formatted(map)),CTNHCore.id("block/casings/map/%s/top".formatted(map)));
        };
    }
    public static NonNullBiConsumer<DataGenContext<Block, PhotovoltaicBlock>, RegistrateBlockstateProvider> createpvModel(String name,
                                                                                                                                       String location) {
        return (ctx, prov) -> {
            prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(name, CTNHCore.id(location)));
        };
    }
}
