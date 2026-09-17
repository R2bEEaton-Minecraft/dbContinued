package net.mcreator.buildingmod.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.buildingmod.init.DavebuildingmodModEntities;
import net.mcreator.buildingmod.DavebuildingmodMod;

public class ChairSpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		DavebuildingmodMod.queueServerWork(2, () -> {
			if ((getBlockDirection(world, BlockPos.containing(x, y, z))) == Direction.NORTH) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = DavebuildingmodModEntities.CHAIR.get().spawn(_level, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(180);
						entityToSpawn.setYBodyRot(180);
						entityToSpawn.setYHeadRot(180);
						entityToSpawn.setDeltaMovement(0, 0, 0);
					}
				}
			} else if ((getBlockDirection(world, BlockPos.containing(x, y, z))) == Direction.SOUTH) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = DavebuildingmodModEntities.CHAIR.get().spawn(_level, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setDeltaMovement(0, 0, 0);
					}
				}
			} else if ((getBlockDirection(world, BlockPos.containing(x, y, z))) == Direction.WEST) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = DavebuildingmodModEntities.CHAIR.get().spawn(_level, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(90);
						entityToSpawn.setYBodyRot(90);
						entityToSpawn.setYHeadRot(90);
						entityToSpawn.setDeltaMovement(0, 0, 0);
					}
				}
			} else if ((getBlockDirection(world, BlockPos.containing(x, y, z))) == Direction.EAST) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = DavebuildingmodModEntities.CHAIR.get().spawn(_level, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(270);
						entityToSpawn.setYBodyRot(270);
						entityToSpawn.setYHeadRot(270);
						entityToSpawn.setDeltaMovement(0, 0, 0);
					}
				}
			}
		});
	}

	private static Direction getBlockDirection(LevelAccessor world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		Property<?> property = blockState.getBlock().getStateDefinition().getProperty("facing");
		if (property != null && blockState.getValue(property) instanceof Direction direction)
			return direction;
		else if (blockState.hasProperty(BlockStateProperties.AXIS))
			return Direction.fromAxisAndDirection(blockState.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
		else if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
			return Direction.fromAxisAndDirection(blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}
}