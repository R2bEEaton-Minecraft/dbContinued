package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.SoundType;

public class RGBSteelWallBlock extends WallBlock {
	public RGBSteelWallBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.AMETHYST).strength(3f, 6f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false).forceSolidOn());
	}
}