package io.github.cpearl0.ctnhcore.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkSerializer.class)
public abstract class ChunkSerializerMixin {
    @Shadow
    public static Codec<PalettedContainer<BlockState>> BLOCK_STATE_CODEC;

    @Unique
    private static <T> DataResult<Pair<BlockState, T>> CTNHCore$decode(final DynamicOps<T> ops, final T input) {
        if (ops == NbtOps.INSTANCE) {
            var tag = (CompoundTag) input;
            var name = ResourceLocation.tryParse(tag.getString("Name"));
            if (name != null && !ForgeRegistries.BLOCKS.containsKey(name)) {
                var new_name = name;
                if (name.getNamespace().equals("kubejs") || name.getNamespace().equals("gtceu"))
                    new_name = new ResourceLocation("ctnhcore", name.getPath());
                tag.putString("Name", new_name.toString());
            }
        }
        return BlockState.CODEC.decode(ops, input);
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void CTNHCore$myCodec(CallbackInfo ci) {
        Encoder<BlockState> encoder = BlockState.CODEC::encode;
        Decoder<BlockState> decoder = ChunkSerializerMixin::CTNHCore$decode;
        var blockStateCodec = Codec.of(encoder, decoder);
        ChunkSerializer.BLOCK_STATE_CODEC = PalettedContainer.codecRW(Block.BLOCK_STATE_REGISTRY, blockStateCodec, PalettedContainer.Strategy.SECTION_STATES, Blocks.AIR.defaultBlockState());
    }
}
