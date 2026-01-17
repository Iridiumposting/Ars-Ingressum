package com.iridiumposting.arsingressum.setup.registry;

import com.hollingsworth.arsnouveau.api.registry.RitualRegistry;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.iridiumposting.arsingressum.common.ritual.RitualPlaneShift;

import java.util.ArrayList;
import java.util.List;

public class ArsNouveauRegistry {

    public static List<AbstractRitual> RITUALS = new ArrayList<>();

    public static void init() {
        registerRituals();

    }

    private static void registerRituals() {
        register(new RitualPlaneShift());
    }

    private static void register(AbstractRitual ritual) {
        RitualRegistry.registerRitual(ritual);
        RITUALS.add(ritual);
    }
}
