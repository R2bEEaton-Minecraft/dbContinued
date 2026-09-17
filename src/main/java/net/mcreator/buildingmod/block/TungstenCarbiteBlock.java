package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class TungstenCarbiteBlock extends Block {
	public TungstenCarbiteBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5f, 8f).requiresCorrectToolForDrops());
	}
}