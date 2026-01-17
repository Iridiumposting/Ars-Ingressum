package com.iridiumposting.arsingressum.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.iridiumposting.arsingressum.ArsIngressum;
import com.iridiumposting.arsingressum.common.item.TuningFork;
import com.iridiumposting.arsingressum.common.item.data.TuningForkData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RitualPlaneShift extends AbstractRitual {
    @Override
    protected void tick() {
        Level world = getWorld();
        BlockPos brazierPos = getPos();
        MinecraftServer server = getWorld().getServer();

        if (!world.isClientSide && world.getGameTime() % 20 == 0) {
            incrementProgress();
            if (getProgress() >= 3) {
                List<Entity> entities = getWorld().getEntitiesOfClass(Entity.class, new AABB(getPos()).inflate(5));

                ItemStack tuningFork = getConsumedItems().getFirst();
                Level targetDimension = server.getLevel(tuningFork.get(TuningForkData.DIMENSION));

                if(targetDimension != null && targetDimension != world) {
                    for(Entity a : entities) PortalTile.teleportEntityTo(a, targetDimension, targetDimension.getSharedSpawnPos(), a.getRotationVector());
                    targetDimension.playSound(null, targetDimension.getSharedSpawnPos(), SoundEvents.BEACON_POWER_SELECT, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    returnFork(targetDimension, targetDimension.getSharedSpawnPos(), tuningFork);
                }
                else {
                    world.playSound(null, brazierPos, SoundEvents.BEACON_DEACTIVATE, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    returnFork(world, brazierPos, tuningFork);
                }
                setFinished();
            }
        }
    }

    private void returnFork(Level level, BlockPos pos, ItemStack stack) {
        ItemEntity fork = new ItemEntity(level, pos.getX(), pos.getY()+1.0, pos.getZ(), stack);
        level.addFreshEntity(fork);
    }

    @Override
    public String getLangName() {
        return "Dimensional Travel";
    }

    @Override
    public String getLangDescription(){
        return "Teleport to a given dimension. Before starting the ritual, you must augment it with a Tuning Fork attuned to the target dimension.";
    }


    @Override
    public boolean canConsumeItem(ItemStack stack) {
        return stack.getItem() instanceof TuningFork && getConsumedItems().isEmpty();
    }

    @Override
    public boolean canStart(@Nullable Player player) {
        return !getConsumedItems().isEmpty();
    }

    public static ResourceLocation RESOURCE_LOCATION = ArsIngressum.prefix("ritual_plane_shift");
    @Override
    public ResourceLocation getRegistryName(){
        return RESOURCE_LOCATION;
    }


}

