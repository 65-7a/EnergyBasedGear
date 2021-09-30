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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.callumwong.energybasedgear.core.util.NumberUtils.formatEnergy;

public class LightningAxe extends AxeItem {
    public static final String LIGHTNING_IMMUNITY_TAG = "LightningImmunityEndTime";

    public LightningAxe(Properties properties) {
        super(ItemTier.DIAMOND, 5.0F, -3.0F, properties);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null).map(energyStorage -> 1 - (double) energyStorage.getEnergyStored() / (double) energyStorage.getMaxEnergyStored()).orElse(0.0);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRgb(1.0F / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> text, @Nonnull ITooltipFlag tooltipFlag) {
        super.appendHoverText(stack, world, text, tooltipFlag);
        stack.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(energyStorage ->
                text.add(new TranslationTextComponent("tooltip.energybasedgear.energy_stored", formatEnergy(energyStorage.getEnergyStored()), formatEnergy(energyStorage.getMaxEnergyStored()))
                        .setStyle(Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.GRAY)))));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new EnergyCapabilityProvider(stack, 100000, 1000);
    }
}
