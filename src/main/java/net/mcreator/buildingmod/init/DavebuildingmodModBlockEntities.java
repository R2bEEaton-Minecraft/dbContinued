/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.buildingmod.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.buildingmod.block.entity.VocalsGeneratorBlockEntity;
import net.mcreator.buildingmod.block.entity.SoundGeneratorBlockEntity;
import net.mcreator.buildingmod.block.entity.ParticleGeneratorBlockEntity;
import net.mcreator.buildingmod.DavebuildingmodMod;

@EventBusSubscriber
public class DavebuildingmodModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DavebuildingmodMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoundGeneratorBlockEntity>> SOUND_GENERATOR = register("sound_generator", DavebuildingmodModBlocks.SOUND_GENERATOR, SoundGeneratorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ParticleGeneratorBlockEntity>> PARTICLE_GENERATOR = register("particle_generator", DavebuildingmodModBlocks.PARTICLE_GENERATOR, ParticleGeneratorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VocalsGeneratorBlockEntity>> VOCALS_GENERATOR = register("vocals_generator", DavebuildingmodModBlocks.VOCALS_GENERATOR, VocalsGeneratorBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SOUND_GENERATOR.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PARTICLE_GENERATOR.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, VOCALS_GENERATOR.get(), SidedInvWrapper::new);
	}
}