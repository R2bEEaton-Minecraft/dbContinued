package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class MagentaSteelPlatingBlock extends Block {
	public MagentaSteelPlatingBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3f, 6f).requiresCorrectToolForDrops());
	}
}