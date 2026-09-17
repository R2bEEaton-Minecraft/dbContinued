package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class CrossBlockBlock extends Block {
	public CrossBlockBlock() {
		super(BlockBehaviour.Properties.of().strength(1.8f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM));
	}
}