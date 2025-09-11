# MobExplosionGriefingGamerule

<a href="https://modrinth.com/mod/mobexplosiongriefinggamerule"><img src="https://img.shields.io/badge/dynamic/json?color=158000&label=Downloads&prefix=+%20&query=downloads&url=https://api.modrinth.com/v2/project/l9H9JPmo&logo=modrinth"></a>

## Fabric, Quilt

This Minecraft mods separates explosions and other destructive mob actions from the `mobGriefing` gamerule.

It adds a custom gamerule, `mobExplosionGriefing`, which makes all mob explosions (except Withers) not destroy blocks. Other actions, like fireballs lighting fires, will still happen.

Additionally, certain actions can also be controlled separately.
 - By turning `creeperGriefing` off, Creepers will not destroy blocks when exploding.
 - By turning `ghastGriefing` off, Ghasts will not destroy blocks when shooting Fireballs. They won't light fires either.
 - By turning `witherGriefing` off, Withers will not destroy blocks with their spawn explosion, when taking damage or when shooting Skulls. However, Wither Roses will still be placed.
 - By turning `endermanGriefing` off, Endermen will not pick up or place blocks.
 - By turning `doorBreakingGriefing` off, mobs will not be able to break doors, no matter the difficulty.
 - By turning `stepAndDestroyGoal` off, mobs will not pathfind to blocks with the objective of stepping on them and destroying them (e.g. Zombies won't attack Turtle Eggs). If they (or the player) do end up stepping on the block, it will still be destroyed.

All of these gamerules are `true` by default.

Turning `mobExplosionGriefing` off will disable all explosions of the above (except Withers'), but other actions will still be allowed. For example, if `mobExplosionGriefing` is `false`, but `ghastGriefing` is `true`, Fireballs won't destroy blocks, but they will still light fires.

Turning `mobGriefing` off will disable all destructive mob actions, just like in vanilla, and will ignore the values of all gamerules added by this mod.

### This mod only needs to be installed server-side.

Other vanilla functionality should not be affected. If you find any bugs or have any ideas on how the mod could be enhanced, please post a **GitHub Issue**.