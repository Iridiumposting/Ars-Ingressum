package com.iridiumposting.arsingressum.common.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PositionUtil {

    public static BlockPos getSafePosition(Level level, BlockPos pos) {
        int maxRadius = 12;

        if(isHeightValid(level, pos) && isNotLethal(level, pos) && canStandInPlace(level, pos)) return pos;

        for(int radius = 1; radius <= maxRadius; radius++) {
            for(int dy = -radius; dy <= radius; dy++) {
                for(int dx = -radius; dx <= radius; dx++) {
                    for(int dz = -radius; dz <= radius; dz++){
                        if(Math.abs(dx) != radius && Math.abs(dy) != radius && Math.abs(dz) != radius) continue;

                        BlockPos testPos = pos.offset(dx, dy, dz);

                        if(isHeightValid(level, testPos) && isSafeLocation(level, testPos)) return testPos;
                    }
                }
            }
        }

        return null;
    }

    private static boolean isSafeLocation(Level level, BlockPos pos) {
        if(!canStandInPlace(level, pos)) return false;
        return isNotLethal(level, pos.above()) && isNotLethal(level, pos) && isNotLethal(level, pos.below());
    }

    private static boolean canStandInPlace(Level level, BlockPos pos){
        if(!level.isEmptyBlock(pos) || !level.isEmptyBlock(pos.above())) return false;

        BlockPos floorPos = pos.below();
        BlockState floor = level.getBlockState(floorPos);

        return (!floor.isAir() && !floor.is(Blocks.POWDER_SNOW));
    }

    private static boolean isNotLethal(Level level, BlockPos pos) {
        BlockState block = level.getBlockState(pos);

        if(block.isAir() && pos.getY() < level.getMinBuildHeight()) return false;
        return !(block.getFluidState().is(FluidTags.LAVA) || block.is(Blocks.FIRE) || block.is(Blocks.SOUL_FIRE));
    }

    private static boolean isHeightValid(Level level, BlockPos pos) {
        int maxHeight = level.getMaxBuildHeight();
        int minHeight = level.getMinBuildHeight();

        return (pos.getY() >= minHeight && pos.getY() < maxHeight-1);
    }

}
