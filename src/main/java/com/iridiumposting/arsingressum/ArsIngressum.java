package com.iridiumposting.arsingressum;

import com.hollingsworth.arsnouveau.api.registry.ImbuementRecipeRegistry;
import com.iridiumposting.arsingressum.setup.registry.AddonRecipeRegistry;
import com.iridiumposting.arsingressum.setup.registry.AddonSetup;
import com.iridiumposting.arsingressum.setup.registry.ArsNouveauRegistry;
import com.hollingsworth.arsnouveau.api.registry.GenericRecipeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArsIngressum.MODID)
public class ArsIngressum {
    public static final String MODID = "ars_ingressum";

    public static final Logger LOGGER = LogManager.getLogger();

    public ArsIngressum(IEventBus modEventBus) {
        
        AddonSetup.registers(modEventBus);
        ArsNouveauRegistry.init();
        
        modEventBus.addListener(this::common);
        modEventBus.addListener(this::client);
        
        NeoForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void common(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ImbuementRecipeRegistry.INSTANCE.addRecipeType(AddonRecipeRegistry.ATTUNE_FORK_TYPE);
        });

        NeoForge.EVENT_BUS.addListener((ServerStartedEvent e) -> {
            GenericRecipeRegistry.reloadAll(e.getServer().getRecipeManager());
        });
    }

    private void client(final FMLClientSetupEvent event) {
        /* NOTHING TO SEE YET. */
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

}
