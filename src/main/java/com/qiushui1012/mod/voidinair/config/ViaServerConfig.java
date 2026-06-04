package com.qiushui1012.mod.voidinair.config;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import dev.anvilcraft.lib.v2.config.BoundedDiscrete;
import dev.anvilcraft.lib.v2.config.Comment;
import dev.anvilcraft.lib.v2.config.Config;
import net.neoforged.fml.config.ModConfig;

@Config(name = AncVoidInAir.MOD_ID, type = ModConfig.Type.SERVER)
public class ViaServerConfig {
    @Comment("The cooldown of Void Fountain (in ticks)")
    @BoundedDiscrete(min = 4, max = Integer.MAX_VALUE)
    public int voidFountainCooldown = 600;
}
