package com.callumwong.energybasedgear;

import com.callumwong.energybasedgear.common.config.ClientConfig;
import com.callumwong.energybasedgear.common.config.CommonConfig;
import com.callumwong.energybasedgear.core.util.EnergyBasedGearItemGroup;
import com.callumwong.energybasedgear.core.init.ItemInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "energybasedgear";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final EnergyBasedGearItemGroup ITEM_GROUP = new EnergyBasedGearItemGroup();

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);

        ItemInit.ITEMS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "EnergyBasedGear-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "EnergyBasedGear-client.toml");
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
