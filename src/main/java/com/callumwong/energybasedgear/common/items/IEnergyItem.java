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

package com.callumwong.energybasedgear.common.items;

import com.callumwong.energybasedgear.common.capabilties.EnergyCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public interface IEnergyItem extends IForgeItem {
    @Override
    default boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    default double getDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null).map(energyStorage -> 1 - (double) energyStorage.getEnergyStored() / (double) energyStorage.getMaxEnergyStored()).orElse(0.0);
    }

    @Override
    default int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRgb(1.0F / 3.0F, 1.0F, 1.0F);
    }

    @Override
    default boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    default int getDamage(ItemStack stack) {
        return 0;
    }

    @Nullable
    @Override
    default ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new EnergyCapabilityProvider(stack, getCapacity(), getTransferRate());
    }

    int getCapacity();
    int getTransferRate();

    @Override
    default int getItemStackLimit(ItemStack stack) {
//        return stack.getCapability(CapabilityEnergy.ENERGY).map(iEnergyStorage -> iEnergyStorage.getEnergyStored() != 0 ? 1 : getStackSize()).orElse(getStackSize());
        return 1;
    }
}
