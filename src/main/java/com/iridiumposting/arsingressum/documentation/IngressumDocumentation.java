package com.iridiumposting.arsingressum.documentation;

import com.hollingsworth.arsnouveau.api.documentation.DocCategory;
import com.hollingsworth.arsnouveau.api.documentation.ReloadDocumentationEvent;
import com.hollingsworth.arsnouveau.api.documentation.builder.DocEntryBuilder;
import com.hollingsworth.arsnouveau.api.registry.DocumentationRegistry;
import com.iridiumposting.arsingressum.setup.registry.AddonItemRegistry;
import net.minecraft.resources.ResourceLocation;

import static com.hollingsworth.arsnouveau.setup.registry.Documentation.addBasicItem;

public class IngressumDocumentation {

    public static void init(ReloadDocumentationEvent.AddEntries ignored) {
        addBasicItem(AddonItemRegistry.VACANT_TUNING_FORK.get(), DocumentationRegistry.ITEMS);
    }
    static class IngressumDocEntryBuilder extends DocEntryBuilder {
        public IngressumDocEntryBuilder(DocCategory category, String name, ResourceLocation entryID) {
            super(category, name.contains(".") ? name : "ars_ingressum.page." + name, entryID);
        }
    }


}
