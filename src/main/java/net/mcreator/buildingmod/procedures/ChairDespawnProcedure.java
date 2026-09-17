package net.mcreator.buildingmod.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.buildingmod.entity.ChairEntity;

import java.util.Comparator;

public class ChairDespawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((findEntityInWorldRange(world, ChairEntity.class, (x + 0.5), (y + 0.5), (z + 0.5), 1)) instanceof ChairEntity == true) {
			if (!(findEntityInWorldRange(world, ChairEntity.class, (x + 0.5), (y + 0.5), (z + 0.5), 1)).level().isClientSide())
				(findEntityInWorldRange(world, ChairEntity.class, (x + 0.5), (y + 0.5), (z + 0.5), 1)).discard();
		}
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return (Entity) world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).findFirst().orElse(null);
	}
}