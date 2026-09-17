package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SlabBlock;

public class PurpleSteelSlabBlock extends SlabBlock {
	public PurpleSteelSlabBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3f, 6f).requiresCorrectToolForDrops());
	}
}