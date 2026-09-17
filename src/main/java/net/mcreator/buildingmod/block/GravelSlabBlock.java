package net.mcreator.buildingmod.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SlabBlock;

public class GravelSlabBlock extends SlabBlock {
	public GravelSlabBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.SAND).strength(0.6f).instrument(NoteBlockInstrument.SNARE));
	}
}