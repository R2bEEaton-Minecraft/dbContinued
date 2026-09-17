package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.FallingBlock;

import com.mojang.serialization.MapCodec;

public class BallastBlock extends FallingBlock {
	public static final MapCodec<BallastBlock> CODEC = simpleCodec(properties -> new BallastBlock());

	public MapCodec<BallastBlock> codec() {
		return CODEC;
	}

	public BallastBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.SAND).strength(0.6f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.SNARE));
	}
}