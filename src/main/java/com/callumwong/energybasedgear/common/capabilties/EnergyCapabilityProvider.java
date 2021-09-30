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

package com.callumwong.energybasedgear.common.capabilties;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<IEnergyStorage> capability;

    public EnergyCapabilityProvider(ItemStack stack, int energyCapacity, int maxTransfer) {
        this.capability = LazyOptional.of(() -> new ItemEnergyStorage(stack, energyCapacity, maxTransfer));
    }

    public EnergyCapabilityProvider(ItemStack stack, int energyCapacity) {
        this.capability = LazyOptional.of(() -> new ItemEnergyStorage(stack, energyCapacity));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction dire) {
        if (cap == CapabilityEnergy.ENERGY) {
            return capability.cast();
        }

        return LazyOptional.empty();
    }
}
