package net.mcreator.buildingmod.procedures;

import net.mcreator.buildingmod.Config;

public class SteelHelperConditionProcedure {
	public static boolean execute() {
		return Config.CONFIG.craftableSteel.get();
	}
}
