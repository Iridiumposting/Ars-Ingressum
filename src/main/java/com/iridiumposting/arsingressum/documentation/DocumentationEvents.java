package com.iridiumposting.arsingressum.documentation;

import com.hollingsworth.arsnouveau.api.documentation.ReloadDocumentationEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class DocumentationEvents {

    @SubscribeEvent
    public static void addPages(ReloadDocumentationEvent.AddEntries event) {
        IngressumDocumentation.init(event);
    }
}
