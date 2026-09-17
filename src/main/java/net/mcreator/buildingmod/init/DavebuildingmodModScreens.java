/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.buildingmod.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.buildingmod.client.gui.VocalsGeneratorGuiScreen;
import net.mcreator.buildingmod.client.gui.SoundGeneratorGuiScreen;
import net.mcreator.buildingmod.client.gui.ParticleGeneratorGuiScreen;
import net.mcreator.buildingmod.client.gui.HelpPaperScreen;

@EventBusSubscriber(Dist.CLIENT)
public class DavebuildingmodModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(DavebuildingmodModMenus.SOUND_GENERATOR_GUI.get(), SoundGeneratorGuiScreen::new);
		event.register(DavebuildingmodModMenus.PARTICLE_GENERATOR_GUI.get(), ParticleGeneratorGuiScreen::new);
		event.register(DavebuildingmodModMenus.VOCALS_GENERATOR_GUI.get(), VocalsGeneratorGuiScreen::new);
		event.register(DavebuildingmodModMenus.HELP_PAPER.get(), HelpPaperScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}