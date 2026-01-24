package com.iridiumposting.arsingressum.common.item;

import com.hollingsworth.arsnouveau.common.block.tile.PlanariumTile;
import com.iridiumposting.arsingressum.common.item.data.TuningForkData;
import com.iridiumposting.arsingressum.setup.registry.AddonItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class TuningFork extends Item {
    public TuningFork(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn (UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity clickedBlock = level.getBlockEntity(context.getClickedPos());

        if(!level.isClientSide() && clickedBlock instanceof PlanariumTile) {
            ResourceKey<Level> dimKey = ((PlanariumTile) clickedBlock).key;

            if(dimKey != null) {
                Player player = context.getPlayer();
                ItemStack heldItem = context.getItemInHand();

                ItemStack attunedFork = new ItemStack(AddonItemRegistry.ATTUNED_TUNING_FORK.get());

                attunedFork.set(TuningForkData.DIMENSION, dimKey);

                if(player != null){
                    heldItem.shrink(1);
                    if(heldItem.isEmpty()) player.setItemInHand(context.getHand(), attunedFork);
                    else if (!player.addItem(attunedFork)) player.drop(attunedFork,false);
                }
                level.playSound(null, context.getClickedPos(), SoundEvents.BEACON_ACTIVATE, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }

            context.getPlayer().sendSystemMessage(Component.translatable("ars_ingressum.tuning_fork.planarium_dim_missing"));
            level.playSound(null, context.getClickedPos(), SoundEvents.BEACON_DEACTIVATE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
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
