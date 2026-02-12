package com.iridiumposting.arsingressum.setup.registry;

import com.iridiumposting.arsingressum.common.item.data.TuningForkData;
import net.neoforged.bus.api.IEventBus;

public class AddonSetup {

    public static void registers(IEventBus modEventBus) {
        AddonItemRegistry.ITEMS.register(modEventBus);
        AddonRecipeRegistry.RECIPE_TYPES.register(modEventBus);
        AddonRecipeRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        TuningForkData.register(modEventBus);
        AddonCreativeTabRegistry.register(modEventBus);
        AddonEffectRegistry.register(modEventBus);
    }

}
