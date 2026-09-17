package net.mcreator.buildingmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

/** Shared legacy particle effect for the invisible passive-generator blocks. */
final class PassiveGeneratorParticles {
	private PassiveGeneratorParticles() {
	}

	static void spawnVolume(Level level, BlockPos pos, RandomSource random, SimpleParticleType particle, int count, double speed) {
		for (int index = 0; index < count; index++) {
			double x = pos.getX() + random.nextFloat();
			double y = pos.getY() + random.nextFloat();
			double z = pos.getZ() + random.nextFloat();
			double dx = (random.nextFloat() - 0.5D) * speed;
			double dy = (random.nextFloat() - 0.5D) * speed;
			double dz = (random.nextFloat() - 0.5D) * speed;
			level.addParticle(particle, x, y, z, dx, dy, dz);
		}
	}

	static void spawnSurface(Level level, BlockPos pos, RandomSource random, SimpleParticleType particle, int count, double horizontalSpread,
			double verticalSpread) {
		for (int index = 0; index < count; index++) {
			double x = pos.getX() + 0.5D + (random.nextFloat() - 0.5D) * horizontalSpread;
			double y = pos.getY() + 1.2D + (random.nextFloat() - 0.5D) * verticalSpread;
			double z = pos.getZ() + 0.5D + (random.nextFloat() - 0.5D) * horizontalSpread;
			level.addParticle(particle, x, y, z, 0, 0, 0);
		}
	}
}
