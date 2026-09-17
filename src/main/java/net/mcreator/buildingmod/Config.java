package net.mcreator.buildingmod;

import net.neoforged.neoforge.common.ModConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

public class Config {
	public static final Config CONFIG;
	public static final ModConfigSpec CONFIG_SPEC;

	public final ModConfigSpec.ConfigValue<Boolean> craftableSteel;
	public final ModConfigSpec.ConfigValue<Boolean> particleGen;

	private Config(ModConfigSpec.Builder builder) {
		craftableSteel = builder.comment("Enables crafting steel by right-clicking an iron block with coal or charcoal")
				.define("craftable_steel", true);
		particleGen = builder.comment("Enables the particle effects produced by the Particle Generator block")
				.define("particle_gen", true);
	}

	static {
		Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);
		CONFIG = pair.getLeft();
		CONFIG_SPEC = pair.getRight();
	}
}
