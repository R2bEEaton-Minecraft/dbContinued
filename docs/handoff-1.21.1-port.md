# Handoff: Porting "Dave's Building Extended" from Forge 1.18.2 to NeoForge 1.21.1

## What this project is

This is an **MCreator** project (not a hand-authored mod) — `davebuildingmod.mcreator` plus an
`elements/` folder of 911 `*.mod.json` declarative element definitions. MCreator regenerates all
Java/resources from these definitions per "generator" (Minecraft version + modloader). You do not
hand-port MCreator mods the way you would a normal Gradle project — you retarget the generator and
let MCreator regenerate, then fix whatever breaks.

## Two workspace copies — READ THIS FIRST

There are currently **two copies** of this workspace, and they are out of sync:

1. **`F:\dbExtended\buildingmod - 1_18 port`** — the original project (git repo root is
   `F:\dbExtended`). Its `.mcreator` index and Gradle scaffold have been updated to target
   `neoforge-1.21.1` (see below), but its `src/main/java` still contains stale Forge-1.18.2
   generated code that has never been regenerated.
2. **`C:\Users\scgry\MCreatorWorkspaces\test`** (outside the git repo, in MCreator's default
   workspace folder) — a fresh workspace MCreator created natively for `neoforge-1.21.1`, into
   which the *content* of the folder above (elements, models, textures, sounds, recipes, tags,
   workspace index) was merged. **This is the workspace that is actually open in MCreator and
   actually builds.** It is not under git.

**Recommendation for next steps: decide whether to keep working in `test` and copy it back into
the git repo when stable, or migrate effort back to the git-tracked folder.** Right now all live
fixes are happening in `test`, and the git repo has fallen behind it. Don't lose the work in
`test` — copy it into `F:\dbExtended` (or init git in place there) before it's cleaned up.

## Environment

- MCreator installed at `C:\Program Files\Pylo\MCreator`, version **2026.1.14619**.
- Target generator: **`neoforge-1.21.1`** (MCreator's own Forge generators only go up to 1.20.1 —
  there is no official Forge 1.21.1 generator, hence NeoForge).
- A "Just enough recipes" plugin (id `jei_recipes`) is installed but **not currently used** —
  `mcreatorDependencies` is empty in both workspace index files. Turns out nothing in the 911
  elements actually references JEI at all (verified via `grep -il jei` across all `elements/*.mod.json`
  — zero hits). The plugin can be left installed or removed; it's inert either way.

## Root cause of build failures: silently-dropped Blockly blocks

MCreator's `neoforge-1.21.1` generator (community-maintained, essentially a fork of the 1.20.6
generator per MCreator forum reports) **has no Java templates for the file/JSON I/O block
family**: `file_exists`, `create_file`, `write_json`, `add_json_property`, `read_json`,
`get_json_logic`, `file_variable`, `get_game_directory`, `variables_set_file`.

Critically, when a **container** block (like `read_json`, which has a nested `<statement>` slot for
"code to run after reading") has no template, MCreator doesn't error — it silently drops the
**entire nested subtree**, even blocks inside it that do have valid templates (e.g.
`spawn_particle_multi`, `block_nbt_num_get` are both fully supported, confirmed present in
`generator-1.21.1.zip` under `neoforge-1.21.1/procedures/`, but they were nested inside a
`read_json` block and so vanished too). One case (`ConfigSetup`) produced literally invalid Java
(`if (!) {}`) because a `logic_negate` block's only child (`file_exists`) had no template; the rest
degraded silently to defaults (e.g. `boolean craftable_steel = false;`) with no compile error at all.

**This is the dangerous part**: most of the 15 `procedure`-type elements regenerated with zero
errors but were still silently gutted of their real logic if they touched this config system.
Always verify regenerated procedure Java actually contains real logic, not just that it compiles.

### Audit performed (should not need repeating, but if regeneration happens again, re-verify)

Of 906 mod elements, only **15 are `procedure` type**. Cross-checked each one's Blockly block-type
list (`grep -oE 'type=\\"[a-z_0-9]+\\"'` on the `.mod.json`) against its generated Java. Only 5 were
actually broken, all tied to a hand-rolled JSON config file (`dbe_v5_common.json`, two booleans:
`craftable_steel`, `particle_gen`):

- `ConfigSetup` — created the config file with defaults on first load
- `CoalToSteelProcedure` — right-click iron block w/ coal/charcoal → steel block (gated by `craftable_steel`)
- `ParticleGeneratorFunction` — spawns particles based on inventory slot 0 item + block-entity NBT (`Y-Offset`, `Size`) (gated by `particle_gen`)
- `SteelHelperCondition` / `SteelHelperConditionNot` — just returned the `craftable_steel` flag / its negation

Everything else (chair spawning/despawning, redstone light color-swaps, sound/vocals generators
keyed off inventory slot contents, offset function, etc.) was verified to have generated complete,
correct-looking Java. **No other silent-drop cases found.**

## Fix applied (chosen approach: migrate to NeoForge's native config system)

User explicitly chose to replace the ad-hoc JSON file with NeoForge's `ModConfigSpec` rather than
hardcoding values or hand-rolling file I/O in a custom-code block. Implemented in the `test`
workspace:

- **`net/mcreator/buildingmod/Config.java`** (new file, not an MCreator-managed element) — defines
  `ModConfigSpec` with `craftable_steel` and `particle_gen` (both default `true`, matching original
  defaults). Registered via `ModList.get().getModContainerById(MODID).ifPresent(container ->
  container.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC))` inside
  `DavebuildingmodMod`'s constructor **user code block** (the `// Start/End of user code block mod
  constructor` markers — these survive MCreator regeneration regardless of lock state, unlike full
  procedure files).
- Rewrote by hand (bypassing Blockly, using MCreator's own confirmed Java template patterns for
  `getBlockNBTNumber`/`itemFromBlockInventory` helpers and the exact `ParticleTypes` mapping table
  from `generator-1.21.1.zip`'s `datapack-1.21.1/mappings/particles.yaml`):
  - `ConfigSetupProcedure.java` → no-op (NeoForge handles config file lifecycle automatically now)
  - `SteelHelperConditionProcedure.java` / `SteelHelperConditionNotProcedure.java` → read `Config.CONFIG.craftableSteel.get()`
  - `CoalToSteelProcedureProcedure.java` → full reimplementation
  - `ParticleGeneratorFunctionProcedure.java` → full reimplementation (10-way item→particle mapping preserved exactly from original Blockly graph)

## ⚠️ Persistence risk — MCreator must lock the repaired generated code

## Update: 2026-09-17 — build restored in the live `test` workspace

The repairs below were reapplied directly to the active workspace at
`C:\Users\scgry\MCreatorWorkspaces\test` and verified with:

```text
gradlew.bat build --no-daemon
BUILD SUCCESSFUL
```

The output is `C:\Users\scgry\MCreatorWorkspaces\test\build\libs\modid-1.0.jar`.

Runtime smoke test was also completed with `gradlew.bat runClient --no-daemon`. The first launch
revealed that the fresh workspace still contained the stock template entrypoint
`src/main/java/cc/spea/test/TestMod.java`; NeoForge rejected it because `neoforge.mods.toml`
defines only `davebuildingmod`. The obsolete template source was removed. A second run loaded the
mod, entered a new singleplayer world, and shut down cleanly after saving all dimensions.

The runtime log has non-fatal content warnings still worth auditing later: several recipes reference
`davebuildingmod:deleted_mod_element` or an invalid `minecraft:air` ingredient, and numerous model
textures are missing. These did not prevent the client or integrated server from starting.

### Dependency and asset follow-up

The initial NeoForge workspace had no Gradle dependencies even though its recipes and custom models
reference Create. It now uses Create 6.0.10's official 1.21.1 dependency set (Create, Ponder,
Flywheel, and Registrate), declares Create as a required production dependency, and raises NeoForge
from `21.1.190` to `21.1.219` because the current Create/Ponder artifacts require that patch level.

JEI is not referenced by any mod element or generated Java source, so it remains optional rather
than a runtime requirement.

The apparent "all textures missing" issue was a path-layout mismatch from the legacy workspace:
the assets were at `textures/blocks` and `textures/items`, while 1.21.1-generated models reference
`textures/block` and `textures/item`. Both directories were migrated in the active `test` workspace.
Static checks now resolve every generated block and item texture reference. Restart the development
client after this move; resource paths are fixed only on the next resource load.

Additional generator omission found during compilation:

- The generator registered `STEEL_COLOUMN` and `STEEL_GIRDER`, but emitted neither
  `SteelColoumnBlock.java` nor `SteelGirderBlock.java`. Both missing WallBlock classes were
  restored using the NeoForge 1.21.1 `BlockBehaviour.Properties` pattern used by the generated
  `SteelWallBlock`.

The five configuration-related procedure repairs are again present:

- `ConfigSetupProcedure` is a no-op because `ModConfigSpec` manages file lifecycle.
- `SteelHelperConditionProcedure` and `SteelHelperConditionNotProcedure` read `Config.CONFIG`.
- `CoalToSteelProcedureProcedure` implements the original iron-block + coal/charcoal conversion,
  including wax-off particles and sound.
- `ParticleGeneratorFunctionProcedure` restores all ten input-item particle mappings, config
  gating, inventory capability lookup, and persistent block-data reads. Its generated block call
  was also corrected from a context-less `execute()` to `execute(world, pos)`.

**Important:** these are still direct Java changes in the active MCreator workspace. The MCreator
desktop app was not available to automation in this session, so its UI-level code locks could not
be applied. Do not regenerate these five procedures or `ParticleGeneratorBlock` until their code
is locked through MCreator; otherwise the current community generator will overwrite the repairs.

**Confirmed just now**: while MCreator had the `test` workspace open, I edited `test.mcreator` on
disk directly (setting `locked_code: true` for the 5 procedure elements above, so MCreator would
stop regenerating/overwriting their hand-written Java). **MCreator does not appear to pick up or
respect external edits to its index file while running — it silently overwrote `test.mcreator` back
to `locked_code: false` for at least `particle_generator_function` and `coal_to_steel_procedure`
(re-checked on disk, confirmed `false`), and regenerated `ParticleGeneratorFunctionProcedure.java`
back to the broken empty stub.** `DavebuildingmodMod.java`'s constructor edit *did* survive (that's
protected by the always-preserved user-code-block markers, a different mechanism), but the 5
procedure files themselves were wiped.

**Next steps for whoever picks this up:**

1. Do **not** edit `test.mcreator` (or any `*.mcreator` index file) directly on disk while MCreator
   has that workspace open — it will be silently discarded and will discard your Java changes with
   it. Either:
   - Close MCreator first, make the edits, then reopen, **or**
   - Set the lock through MCreator's own UI (right-click the element → there should be a "Lock
     code" / code-editor toggle per the MCreator wiki: https://mcreator.net/wiki/locking-code —
     "Each mod element can have its code locked, which means that MCreator won't generate the code
     of such mod element again"), then paste in the hand-written Java through MCreator's own code
     editor for that element, so both the lock flag and the code live in MCreator's in-memory model
     consistently.
2. Verify the five procedure files and `ParticleGeneratorBlock` are still present, then lock them.
   Reapply the implementations described in the update above only if regeneration has overwritten
   them.
3. `Config.java` is a **new, non-MCreator-managed file** — per MCreator 2025.2+, user-provided files
   in the mod's Java package survive regeneration automatically (no lock needed). Verify it's still
   present; if MCreator wiped it too during the same episode, recreate it (definition is above).
4. Once all 5 are correctly locked and re-verified, rebuild (`Build & run → Build` in MCreator, or
   the Gradle `build` task) and confirm no compile errors, then actually test in-game that
   right-clicking an iron block with coal/charcoal produces steel, and the particle generator block
   still produces particles matching the item in its input slot.
5. Decide on the git-repo-vs-`test`-workspace situation described at the top of this doc.

## Reference: registry/API details used during the fix (for reconstruction if needed)

- Custom block: `DavebuildingmodModBlocks.STEEL_BLOCK` (registry name `steel_block`)
- Custom items (Particle Generator inputs), all under `DavebuildingmodModItems`:
  `RED_CIRCUIT_BEE_NEST`, `RED_CIRCUIT_CAMPFIRE`, `RED_CIRCUIT_FIRE_CHARGE`, `RED_CIRCUIT_FIREWORK`,
  `RED_CIRCUIT_FLINT_AND_STEEL`, `RED_CIRCUIT_LAVA_BUCKET`, `RED_CIRCUIT_SMALL_SMOKE`,
  `RED_CIRCUIT_STEAM`, `RED_CIRCUIT_TNT`, `RED_CIRCUIT_WATER_BUCKET`
- Particle label → Java mapping (from `generator-1.21.1.zip:datapack-1.21.1/mappings/particles.yaml`):
  `DRIPPING_HONEY`, `CAMPFIRE_COSY_SMOKE`, `SMALL_FLAME`, `FLASH`, `LAVA`,
  `DRIPPING_DRIPSTONE_LAVA`, `SMOKE_NORMAL→ParticleTypes.SMOKE`, `CLOUD`,
  `EXPLOSION_NORMAL→ParticleTypes.EXPLOSION`, `WATER_SPLASH→ParticleTypes.SPLASH`,
  `WAX_OFF` (all others map 1:1 to the same-named `ParticleTypes` constant).
- Block-entity NBT read helper (MCreator's own template,
  `neoforge-1.21.1/procedures/utils/block_nbt/get_num.java.ftl`):
  ```java
  private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity != null) return blockEntity.getPersistentData().getDouble(tag);
      return -1;
  }
  ```
- Block-inventory item read helper (MCreator's own template, confirmed working via
  `SoundGeneratorRedstoneOnProcedure.java` which uses it successfully):
  ```java
  private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
      if (world instanceof ILevelExtension ext) {
          IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
          if (itemHandler != null) return itemHandler.getStackInSlot(slot);
      }
      return ItemStack.EMPTY;
  }
  ```
- NeoForge config registration pattern (from https://docs.neoforged.net/docs/1.21.1/misc/config/):
  ```java
  import net.neoforged.neoforge.common.ModConfigSpec;
  import org.apache.commons.lang3.tuple.Pair;

  public class Config {
      public static final Config CONFIG;
      public static final ModConfigSpec CONFIG_SPEC;
      public final ModConfigSpec.ConfigValue<Boolean> craftableSteel;
      public final ModConfigSpec.ConfigValue<Boolean> particleGen;

      private Config(ModConfigSpec.Builder builder) {
          craftableSteel = builder.define("craftable_steel", true);
          particleGen = builder.define("particle_gen", true);
      }
      static {
          Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);
          CONFIG = pair.getLeft();
          CONFIG_SPEC = pair.getRight();
      }
  }
  ```
  Registered in the mod constructor's user-code-block:
  ```java
  net.neoforged.fml.ModList.get().getModContainerById(MODID)
      .ifPresent(container -> container.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, Config.CONFIG_SPEC));
  ```
