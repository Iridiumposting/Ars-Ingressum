package com.iridiumposting.arsingressum.setup.registry;

import com.hollingsworth.arsnouveau.api.registry.RitualRegistry;
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.iridiumposting.arsingressum.common.glyphs.*;
import com.iridiumposting.arsingressum.common.ritual.RitualPlaneShift;

import java.util.ArrayList;
import java.util.List;

public class ArsNouveauRegistry {

    public static List<AbstractRitual> RITUALS = new ArrayList<>();
    public static List<AbstractSpellPart> registeredSpells = new ArrayList<>(); //this will come handy for datagen

    public static void init() {
        registerRituals();
        registerGlyphs();
    }

    public static void registerGlyphs(){
        register(GlyphAlacrity.INSTANCE);
        register(GlyphStalwart.INSTANCE);
        register(GlyphEmpower.INSTANCE);
        register(GlyphBless.INSTANCE);
        register(GlyphInvigorate.INSTANCE);
        register(GlyphFalseLife.INSTANCE);
    }

    private static void registerRituals() {
        register(new RitualPlaneShift());
    }

    private static void register(AbstractRitual ritual) {
        RitualRegistry.registerRitual(ritual);
        RITUALS.add(ritual);
    }
    public static void register(AbstractSpellPart spellPart){
        GlyphRegistry.registerSpell(spellPart);
        registeredSpells.add(spellPart);
    }
}
