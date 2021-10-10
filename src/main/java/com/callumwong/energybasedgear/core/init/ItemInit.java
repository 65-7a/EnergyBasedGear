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

package com.callumwong.energybasedgear.core.init;

import com.callumwong.energybasedgear.Main;
import com.callumwong.energybasedgear.common.items.impl.Battery;
import com.callumwong.energybasedgear.common.items.impl.LightningAxe;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<Item> LIGHTNING_AXE = ITEMS.register("lightning_axe",
            () -> new LightningAxe(new Item.Properties().tab(Main.ITEM_GROUP).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BATTERY = ITEMS.register("battery",
            () -> new Battery(new Item.Properties().tab(Main.ITEM_GROUP).rarity(Rarity.UNCOMMON)));
}
