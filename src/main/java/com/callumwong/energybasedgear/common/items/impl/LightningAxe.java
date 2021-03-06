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

import com.callumwong.energybasedgear.Main;
import com.callumwong.energybasedgear.common.config.CommonConfig;
import com.callumwong.energybasedgear.common.items.AbstractEnergyAxeItem;
import com.callumwong.energybasedgear.core.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LightningAxe extends AbstractEnergyAxeItem {
    public static final String LIGHTNING_IMMUNITY_TAG = "LightningImmunityEndTime";
    public static final int LIGHTNING_ENERGY_USAGE = 1000;

    public LightningAxe(Properties properties) {
        super(ItemTier.DIAMOND, 5.0F, -3.0F, properties);
    }

    @Override
    public int getCapacity() {
        return 200000;
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

    @SubscribeEvent
    public static void onStruckByLightning(EntityStruckByLightningEvent e) {
        if (e.getEntity() instanceof PlayerEntity) {
            if (((PlayerEntity) e.getEntity()).isHolding(ItemInit.LIGHTNING_AXE.get())) {
                ItemStack stack = ((PlayerEntity) e.getEntity()).getMainHandItem();
                if (stack.hasTag() && stack.getTag().contains(LIGHTNING_IMMUNITY_TAG) && stack.getTag().getLong(LIGHTNING_IMMUNITY_TAG) > e.getEntity().level.getGameTime()) {
                    e.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent e) {
        if (!e.getTarget().isAlive()) return;
        if (e.getTarget() instanceof LivingEntity) {
            PlayerEntity attacker = e.getPlayer();

            if (attacker.level.dimension() == World.OVERWORLD && attacker.isHolding(ItemInit.LIGHTNING_AXE.get())) {
                attacker.getMainHandItem().getCapability(CapabilityEnergy.ENERGY, null).ifPresent(energyStorage -> {
                    if (attacker.getAttackStrengthScale(0) >= 1.0F || !CommonConfig.LIGHTNING_AXE_COOLDOWN.get()) {
                        if (energyStorage.getEnergyStored() >= LIGHTNING_ENERGY_USAGE) {
                            CompoundNBT nbt = attacker.getMainHandItem().getOrCreateTag();
                            nbt.putLong(LIGHTNING_IMMUNITY_TAG, e.getTarget().level.getGameTime() + 20L);

                            LightningBoltEntity lightningBoltEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, e.getTarget().level);
                            lightningBoltEntity.setPos(e.getTarget().getX(), e.getTarget().getY(), e.getTarget().getZ());
                            e.getTarget().level.addFreshEntity(lightningBoltEntity);
                            e.getTarget().setSecondsOnFire(2);

                            energyStorage.extractEnergy(LIGHTNING_ENERGY_USAGE, false);

                            attacker.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 120, 0, false, false));
                        }
                    }
                });
            }
        }
    }
}
