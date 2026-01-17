package com.iridiumposting.arsingressum.common.item;

import com.iridiumposting.arsingressum.common.item.data.TuningForkData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class TuningFork extends Item {
    public TuningFork(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(TuningForkData.DIMENSION) != null) {
            ResourceKey<Level> tuningForkAttunement = stack.get(TuningForkData.DIMENSION);
            String dimensionName = tuningForkAttunement.location().toLanguageKey();
            tooltipComponents.add(Component.translatable("dimension." + dimensionName));
        } else tooltipComponents.add(Component.translatable("tooltip.ars_ingressum.tuning_fork.unattuned"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
