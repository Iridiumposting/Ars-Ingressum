package com.iridiumposting.arsingressum.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.hollingsworth.arsnouveau.common.world.dimension.PlanariumChunkGenerator;
import com.iridiumposting.arsingressum.ArsIngressum;
import com.iridiumposting.arsingressum.common.item.TuningFork;
import com.iridiumposting.arsingressum.common.item.data.TuningForkData;
import com.iridiumposting.arsingressum.setup.registry.AddonItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RitualPlaneShift extends AbstractRitual {
    @Override
    protected void tick() {
        Level world = getWorld();
        BlockPos pos = getPos();
        MinecraftServer server = getWorld().getServer();

        if (!world.isClientSide && world.getGameTime() % 20 == 0) {
            incrementProgress();
            if(getProgress() >= 3){
                List<Entity> entities = getWorld().getEntitiesOfClass(Entity.class, new AABB(getPos()).inflate(5));
                ItemStack tuningFork = getConsumedItems().getFirst();

                Level targetDim = server.getLevel(tuningFork.get(TuningForkData.DIMENSION));

                if(targetDim == null || targetDim == world) {
                    world.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    returnFork(world, pos, tuningFork);
                    setFinished();
                    return;
                }

                BlockPos targetPos = dimAppropriatePos(world, targetDim, pos);

                if(targetPos != null){

                    for (Entity a : entities){
                        if(canBeTeleported(a)) PortalTile.teleportEntityTo(a, targetDim, targetPos, a.getRotationVector());
                    }

                    targetDim.playSound(null, targetPos, SoundEvents.BEACON_POWER_SELECT, SoundSource.NEUTRAL, 1.0f, 1.0f);

                    tuningFork.setDamageValue(tuningFork.getDamageValue()+1);

                    ItemStack returnedFork = tuningFork.getDamageValue() == tuningFork.getMaxDamage()
                            ? new ItemStack(AddonItemRegistry.VACANT_TUNING_FORK.get())
                            : tuningFork;
                    returnFork(targetDim, targetPos, returnedFork);
                }
                else {

                    world.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    returnFork(world, pos, tuningFork);
                }
                setFinished();
            }
        }
    }

    private static BlockPos dimAppropriatePos(Level world, Level targetDim, BlockPos originPos) {
        if(isJarDim(world)) return PositionUtil.getSafePosition(targetDim, targetDim.getSharedSpawnPos());
        if(isJarDim(targetDim)) return PositionUtil.getSafePosition(targetDim, new BlockPos(7,2,7));

        int yOffset = isSuperflat(world) ? 100 : (isSuperflat(targetDim) ? -100 : 0);
        return PositionUtil.getSafePosition(targetDim, originPos.offset(0, yOffset, 0));
    }

    public static boolean isJarDim(Level level){
        ServerChunkCache chunkSource = (ServerChunkCache) level.getChunkSource();
        ChunkGenerator generator = chunkSource.getGenerator();

        return generator instanceof PlanariumChunkGenerator;
    }

    public static boolean isSuperflat(Level level){
        ServerChunkCache chunkSource = (ServerChunkCache) level.getChunkSource();
        ChunkGenerator generator = chunkSource.getGenerator();

        return generator instanceof FlatLevelSource;
    }

    private boolean canBeTeleported(Entity entity){
        return !entity.isMultipartEntity();
    }

    private void returnFork(Level level, BlockPos pos, ItemStack stack) {
        ItemEntity fork = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
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

