package com.iridiumposting.arsingressum.setup.registry;

import com.iridiumposting.arsingressum.ArsIngressum;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AddonCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArsIngressum.MODID);

    public static final Supplier<CreativeModeTab> ARS_INGRESSUM_TAB = CREATIVE_MODE_TAB.register("ars_ingressum_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AddonItemRegistry.VACANT_TUNING_FORK.get()))
                    .title(Component.translatable("creativetab.ars_ingressum.ars_ingressum"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(AddonItemRegistry.VACANT_TUNING_FORK);
                        output.accept(AddonItemRegistry.ATTUNED_TUNING_FORK);
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
