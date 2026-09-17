package net.mcreator.buildingmod.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.buildingmod.Config;
import net.mcreator.buildingmod.init.DavebuildingmodModBlocks;

import javax.annotation.Nullable;

@EventBusSubscriber
public class CoalToSteelProcedureProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != InteractionHand.MAIN_HAND)
			return;
		execute(event);
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		if (!(event instanceof PlayerInteractEvent.RightClickBlock rightClick) || !Config.CONFIG.craftableSteel.get())
			return;
		Level level = rightClick.getLevel();
		if (level.isClientSide() || !level.getBlockState(rightClick.getPos()).is(Blocks.IRON_BLOCK))
			return;
		var heldItem = rightClick.getItemStack();
		if (!heldItem.is(Items.COAL) && !heldItem.is(Items.CHARCOAL))
			return;
		heldItem.shrink(1);
		level.setBlock(rightClick.getPos(), DavebuildingmodModBlocks.STEEL_BLOCK.get().defaultBlockState(), 3);
		level.playSound(null, rightClick.getPos(), SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1, 1);
		if (level instanceof net.minecraft.server.level.ServerLevel serverLevel)
			serverLevel.sendParticles(ParticleTypes.WAX_OFF, rightClick.getPos().getX(), rightClick.getPos().getY(), rightClick.getPos().getZ(), 5, 1, 1, 1, 1);
	}
}
