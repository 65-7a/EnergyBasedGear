/*
 *     Copyright (C) 2021  Callum Wong
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.callumwong.energybasedgear.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> FORMAT_FE_VALUES;
    public static final ForgeConfigSpec.ConfigValue<Boolean> LIGHTNING_AXE_COOLDOWN;

    static {
        BUILDER.push("Configuration for Energy-Based Gear");

        FORMAT_FE_VALUES = BUILDER.comment("Shorten large FE values, e.g. 2000 FE = 2 kFE.").define("Format FE Values", true);
        LIGHTNING_AXE_COOLDOWN = BUILDER.comment("Whether to use the attack cooldown for the Lightning Axe.").define("Lightning Axe Cooldown", true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
