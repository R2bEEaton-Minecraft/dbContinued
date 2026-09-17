package net.mcreator.buildingmod.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class LavaBucketPassiveGeneratorBlock extends Block {
	public LavaBucketPassiveGeneratorBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).instabreak().requiresCorrectToolForDrops().noCollission().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		PassiveGeneratorParticles.spawnSurface(level, pos, random, ParticleTypes.DRIPPING_DRIPSTONE_LAVA, 1, 5D, 0.25D);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}
}
