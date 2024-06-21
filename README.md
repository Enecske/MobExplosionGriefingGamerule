# MobExplosionGriefingGamerule

## Fabric

This Minecraft mods separates explosions other destructive mob actions from the `mobGriefing` gamerule.

It adds a custom gamerule, `mobExplosionGriefing`, which makes all mob explosions (except Withers) not destroy blocks. Other actions, like fireballs lighting fires, will still happen.

Additionally, certain actions can also be controlled separately.
 - By turning `creeperGriefing` off, Creepers will not destroy blocks when exploding.
 - By turning `ghastGriefing` off, Ghasts will not destroy blocks when shooting Fireballs. They won't light fires either.
 - By turning `witherGriefing` off, Withers will not destroy blocks with their spawn explosion, when taking damage or when shooting Skulls. However, Wither Roses will still be placed.
 - By turning `endermanGriefing` off, Endermen will not pick up or place blocks.

All of these gamerules are `true` by default.

Turning off `mobExplosionGriefing` will disable all explosions of the above (except Withers'), but other actions will still be allowed. For example, if `mobExplosionGriefing` is `false`, but `ghastGriefing` is `true`, Fireballs won't destroy blocks, but they will still light fires.

### This mod only needs to be installed server-side.

Other vanilla functionality should not be affected. If you find any bugs, please report them as a **GitHub Issue**.