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

package com.callumwong.energybasedgear.core.util;

import com.callumwong.energybasedgear.common.config.Config;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class NumberUtils {
    private static final NavigableMap<Long, String> energySuffixes = new TreeMap<>();

    static {
        energySuffixes.put(1_000L, " kFE");
        energySuffixes.put(1_000_000L, " MFE");
        energySuffixes.put(1_000_000_000L, " GFE");
        energySuffixes.put(1_000_000_000_000L, " TFE");
        energySuffixes.put(1_000_000_000_000_000L, " PFE");
        energySuffixes.put(1_000_000_000_000_000_000L, " EFE");
    }

    public static String formatEnergy(long value) {
        if (!Config.FORMAT_FE_VALUES.get()) return value + " FE";

        // Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatEnergy(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatEnergy(-value);
        if (value < 1000) return value + " FE"; // default FE suffix

        Map.Entry<Long, String> e = energySuffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); // the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
