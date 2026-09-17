/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.buildingmod.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.buildingmod.world.inventory.VocalsGeneratorGuiMenu;
import net.mcreator.buildingmod.world.inventory.SoundGeneratorGuiMenu;
import net.mcreator.buildingmod.world.inventory.ParticleGeneratorGuiMenu;
import net.mcreator.buildingmod.world.inventory.HelpPaperMenu;
import net.mcreator.buildingmod.network.MenuStateUpdateMessage;
import net.mcreator.buildingmod.DavebuildingmodMod;

import java.util.Map;

public class DavebuildingmodModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, DavebuildingmodMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<SoundGeneratorGuiMenu>> SOUND_GENERATOR_GUI = REGISTRY.register("sound_generator_gui", () -> IMenuTypeExtension.create(SoundGeneratorGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ParticleGeneratorGuiMenu>> PARTICLE_GENERATOR_GUI = REGISTRY.register("particle_generator_gui", () -> IMenuTypeExtension.create(ParticleGeneratorGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<VocalsGeneratorGuiMenu>> VOCALS_GENERATOR_GUI = REGISTRY.register("vocals_generator_gui", () -> IMenuTypeExtension.create(VocalsGeneratorGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<HelpPaperMenu>> HELP_PAPER = REGISTRY.register("help_paper", () -> IMenuTypeExtension.create(HelpPaperMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof DavebuildingmodModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				PacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}