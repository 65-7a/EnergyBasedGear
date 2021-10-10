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

import com.callumwong.energybasedgear.common.items.AbstractEnergyItem;

public class Battery extends AbstractEnergyItem {
    public Battery(Properties properties) {
        super(properties);
    }

    @Override
    public int getCapacity() {
        return 100000;
    }

    @Override
    public int getTransferRate() {
        return 1000;
    }

//    @Override
//    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int slot, boolean selected) {
//        if (entity instanceof ServerPlayerEntity) {
//            NonNullList<ItemStack> items = ((ServerPlayerEntity) entity).inventory.items;
//            items.forEach(item -> {
//                if (item.getItem() instanceof Battery) return;
//                item.getCapability(CapabilityEnergy.ENERGY).ifPresent(otherEnergyStorage ->
//                        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
//                            int energyNeeded = Math.min(otherEnergyStorage.getMaxEnergyStored() - otherEnergyStorage.getEnergyStored(), getTransferRate());
//                            if (energyNeeded <= 0) return;
//
//                            if (energyStorage.getEnergyStored() > otherEnergyStorage.getEnergyStored()) {
//                                int transferAmount = energyStorage.extractEnergy(energyNeeded, true);
//                                int acceptedEnergy = otherEnergyStorage.receiveEnergy(transferAmount, false);
//                                energyStorage.extractEnergy(acceptedEnergy, false);
//                            }
//                        }));
//            });
//        }
//    }
//
//    Charges all items in inventory. Unwanted behaviour but I don't wanna rewrite it so I'm keeping it.
}
