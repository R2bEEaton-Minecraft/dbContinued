package net.mcreator.buildingmod.procedures;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.items.IItemHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.mcreator.buildingmod.Config;
import net.mcreator.buildingmod.init.DavebuildingmodModItems;

public class ParticleGeneratorFunctionProcedure {
	public static void execute(LevelAccessor world, BlockPos pos) {
		if (!Config.CONFIG.particleGen.get() || !(world instanceof ServerLevel serverLevel))
			return;

		double sizeTag = getBlockNBTNumber(world, pos, "Size");
		double spread = sizeTag / 10;
		int count = (int) Math.pow(sizeTag / 4, 3);
		double y = pos.getY() + 0.5 + getBlockNBTNumber(world, pos, "Y-Offset");
		ItemStack input = itemFromBlockInventory(world, pos, 0);
		ParticleOptions particle;
		if (input.is(DavebuildingmodModItems.RED_CIRCUIT_BEE_NEST.get())) particle = ParticleTypes.DRIPPING_HONEY;
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_CAMPFIRE.get())) particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_FIRE_CHARGE.get())) particle = ParticleTypes.SMALL_FLAME;
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_FIREWORK.get())) { particle = ParticleTypes.FLASH; count /= 10; }
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_FLINT_AND_STEEL.get())) particle = ParticleTypes.LAVA;
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_LAVA_BUCKET.get())) particle = ParticleTypes.DRIPPING_DRIPSTONE_LAVA;
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_SMALL_SMOKE.get())) particle = ParticleTypes.SMOKE;
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_STEAM.get())) particle = ParticleTypes.CLOUD;
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_TNT.get())) { particle = ParticleTypes.EXPLOSION; count /= 10; }
		else if (input.is(DavebuildingmodModItems.RED_CIRCUIT_WATER_BUCKET.get())) particle = ParticleTypes.SPLASH;
		else return;
		serverLevel.sendParticles(particle, pos.getX() + 0.5, y, pos.getZ() + 0.5, count, spread, spread, spread, 0.1);
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null ? blockEntity.getPersistentData().getDouble(tag) : -1;
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
			if (itemHandler != null) return itemHandler.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
	}
}
