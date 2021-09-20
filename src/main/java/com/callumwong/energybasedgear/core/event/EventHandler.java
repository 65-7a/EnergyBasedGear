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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent e) {
        if (e.getTarget() instanceof LivingEntity) {
            PlayerEntity attacker = e.getPlayer();

            if (attacker.level.dimension() == World.OVERWORLD && attacker.isHolding(ItemInit.LIGHTNING_AXE.get())) {
                if (attacker.getAttackStrengthScale(0) >= 1.0F) {
                    Entity victim = e.getTarget();

                    LightningBoltEntity lightningBoltEntity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, victim.level);
                    lightningBoltEntity.setPos(victim.getX(), victim.getY(), victim.getZ());
                    victim.level.addFreshEntity(lightningBoltEntity);
                    victim.setSecondsOnFire(2);

                    attacker.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, false, false));
                }
            }
        }
    }
}
