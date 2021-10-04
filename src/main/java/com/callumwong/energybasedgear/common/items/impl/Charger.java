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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;

public class Charger extends AbstractEnergyItem {
    public Charger(Properties properties) {
        super(properties);
    }

    @Override
    public int getCapacity() {
        return 1000000;
    }

    @Override
    public int getTransferRate() {
        return 10000;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int slot, boolean selected) {
        if (entity instanceof ServerPlayerEntity) {
            NonNullList<ItemStack> items = ((ServerPlayerEntity) entity).inventory.items;
            items.forEach(item -> {
                if (item.getItem() instanceof Charger) return;
                item.getCapability(CapabilityEnergy.ENERGY).ifPresent(otherEnergyStorage ->
                        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
                            int energyNeeded = Math.min(otherEnergyStorage.getMaxEnergyStored() - otherEnergyStorage.getEnergyStored(), getTransferRate());
                            if (energyNeeded <= 0) return;

                            if (energyStorage.getEnergyStored() > otherEnergyStorage.getEnergyStored()) {
                                int transferAmount = energyStorage.extractEnergy(energyNeeded, true);
                                int acceptedEnergy = otherEnergyStorage.receiveEnergy(transferAmount, false);
                                energyStorage.extractEnergy(acceptedEnergy, false);
                            }
                        }));
            });
        }
    }
}
