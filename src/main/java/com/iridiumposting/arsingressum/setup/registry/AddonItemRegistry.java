package com.iridiumposting.arsingressum.setup.registry;

import com.iridiumposting.arsingressum.common.item.*;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.iridiumposting.arsingressum.ArsIngressum.MODID;

public class AddonItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> VACANT_TUNING_FORK = ITEMS.register("vacant_tuning_fork",
            () -> new TuningFork(new Item.Properties()
                    .stacksTo(16)
            ));
    public static final DeferredItem<Item> ATTUNED_TUNING_FORK = ITEMS.register("attuned_tuning_fork",
            () -> new TuningFork(new Item.Properties()
                    .stacksTo(1)
                    .durability(8)
            ));
}
