/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.buildingmod.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.buildingmod.DavebuildingmodMod;

public class DavebuildingmodModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, DavebuildingmodMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> NUCLEAR_SIREN = REGISTRY.register("nuclear_siren", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "nuclear_siren")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_LABREPAIR = REGISTRY.register("vocal_labrepair", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_labrepair")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_EXPERIMENT_START = REGISTRY.register("vocal_experiment_start",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_experiment_start")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_REACTOR_BREACH = REGISTRY.register("vocal_reactor_breach",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_reactor_breach")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_SYSTEMS_LOADING = REGISTRY.register("vocal_systems_loading",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_systems_loading")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_DECONTAMINATION = REGISTRY.register("vocal_decontamination",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_decontamination")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_DECOMPRESSION = REGISTRY.register("vocal_decompression", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_decompression")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_LOCKDOWN = REGISTRY.register("vocal_lockdown", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_lockdown")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_LIFESYSTEM_FAIL = REGISTRY.register("vocal_lifesystem_fail",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_lifesystem_fail")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_LEAVE_FACILITY = REGISTRY.register("vocal_leave_facility",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_leave_facility")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_UNAUTHORIZED_ACTIVITY = REGISTRY.register("vocal_unauthorized_activity",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_unauthorized_activity")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_EVACUATE_COMPLEX = REGISTRY.register("vocal_evacuate_complex",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_evacuate_complex")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_ATTENTION = REGISTRY.register("vocal_attention", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_attention")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_WARNING = REGISTRY.register("vocal_warning", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_warning")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_ALERT = REGISTRY.register("vocal_alert", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_alert")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_NUKE_ALERT = REGISTRY.register("vocal_nuke_alert", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_nuke_alert")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_EMERGENCY_EXIT = REGISTRY.register("vocal_emergency_exit",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_emergency_exit")));
	public static final DeferredHolder<SoundEvent, SoundEvent> VOCAL_SECURITY_BREACH = REGISTRY.register("vocal_security_breach",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("davebuildingmod", "vocal_security_breach")));
}