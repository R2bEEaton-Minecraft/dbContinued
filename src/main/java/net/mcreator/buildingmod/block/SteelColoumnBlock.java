package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Retained manually because the 1.21.1 generator omitted this legacy Wall block.
 */
public class SteelColoumnBlock extends WallBlock {
	public SteelColoumnBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3f, 6f).requiresCorrectToolForDrops());
	}
}
