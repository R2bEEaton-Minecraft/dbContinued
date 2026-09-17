package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class CyanScreenBlock extends Block {
	public CyanScreenBlock() {
		super(BlockBehaviour.Properties.of().strength(1.8f).lightLevel(blockstate -> 1).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).instrument(NoteBlockInstrument.BASEDRUM));
	}
}