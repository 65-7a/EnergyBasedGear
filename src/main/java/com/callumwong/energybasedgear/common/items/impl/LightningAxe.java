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

package com.callumwong.energybasedgear.common.items.impl;

import com.callumwong.energybasedgear.common.items.AbstractEnergyAxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;

import javax.annotation.Nonnull;

public class LightningAxe extends AbstractEnergyAxeItem {
    public static final String LIGHTNING_IMMUNITY_TAG = "LightningImmunityEndTime";

    public LightningAxe(Properties properties) {
        super(ItemTier.DIAMOND, 5.0F, -3.0F, properties);
    }

    @Override
    public int getCapacity() {
        return 100000;
    }

    @Override
    public int getTransferRate() {
        return 1000;
    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) {
        // To deal with spazzy enchantment looks, we get rid of the glint completely
        return false;
    }
}
