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

package com.callumwong.energybasedgear.core.event;

import com.callumwong.energybasedgear.Main;
import com.callumwong.energybasedgear.core.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.callumwong.energybasedgear.common.items.LightningAxe.LIGHTNING_IMMUNITY_TAG;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onStruckByLightning(EntityStruckByLightningEvent e) {
        if (e.getEntity() instanceof PlayerEntity) {
            if (((PlayerEntity) e.getEntity()).isHolding(ItemInit.LIGHTNING_AXE.get())) {
                ItemStack stack = ((PlayerEntity) e.getEntity()).getMainHandItem();
                if (stack.hasTag() && stack.getTag().contains(LIGHTNING_IMMUNITY_TAG) && stack.getTag().getBoolean(LIGHTNING_IMMUNITY_TAG)) {
                    e.setCanceled(true);
                    stack.getTag().putBoolean(LIGHTNING_IMMUNITY_TAG, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent e) {
        if (e.getTarget() instanceof LivingEntity) {
            PlayerEntity attacker = e.getPlayer();

            if (attacker.level.dimension() == World.OVERWORLD && attacker.isHolding(ItemInit.LIGHTNING_AXE.get())) {
                attacker.getMainHandItem().getCapability(CapabilityEnergy.ENERGY, null).ifPresent(energyStorage -> {
                    if (energyStorage.getEnergyStored() >= 500 && attacker.getAttackStrengthScale(0) >= 1.0F) {
                        CompoundNBT nbt = attacker.getMainHandItem().getTag();

                        if (nbt == null) {
                            nbt = new CompoundNBT();
                            attacker.getMainHandItem().setTag(nbt);
                        }

                        nbt.putBoolean(LIGHTNING_IMMUNITY_TAG, true); // I want to put some sort of timer here to eventually set this to false.

                        LightningBoltEntity lightningBoltEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, e.getTarget().level);
                        lightningBoltEntity.setPos(e.getTarget().getX(), e.getTarget().getY(), e.getTarget().getZ());
                        e.getTarget().level.addFreshEntity(lightningBoltEntity);
                        e.getTarget().setSecondsOnFire(2);

                        energyStorage.extractEnergy(500, false);

                        attacker.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 120, 0, false, false));
                    }
                });
            }
        }
    }
}
