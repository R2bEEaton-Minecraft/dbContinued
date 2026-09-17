package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class StarsBlock extends Block {
	public StarsBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.AMETHYST_CLUSTER).strength(1.8f).lightLevel(blockstate -> 1).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).instrument(NoteBlockInstrument.BASEDRUM));
	}
}