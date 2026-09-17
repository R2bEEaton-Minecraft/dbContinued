package net.mcreator.buildingmod.procedures;

import net.mcreator.buildingmod.Config;

public class SteelHelperConditionNotProcedure {
	public static boolean execute() {
		return !Config.CONFIG.craftableSteel.get();
	}
}
