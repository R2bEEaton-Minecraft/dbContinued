package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class RGBSteelPlatingBlock extends Block {
	public RGBSteelPlatingBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.AMETHYST).strength(3f, 6f).requiresCorrectToolForDrops());
	}
}