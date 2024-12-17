package org.manadependants.manalib.logic.ambient;


import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.chunk.Chunk;
import org.manadependants.manalib.components.WorldComponentRegistry;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;

import java.util.Random;


public class leyline_generator {
    private static final int LEYLINE_CENTER_COUNT = 20; // Number of leyline center points
    private static final int VORONOI_SCALE = 32;

    public static void leylineMapGeneration(long seed, Chunk chunk){
        ChunkPos chunkPos = chunk.getPos();
        ManaChunkAmbientGen chunkAmbientGen = chunk.getComponent(WorldComponentRegistry.CHUNK_MANA_COMPONENT);
        float densityMod = chunkAmbientGen.getChunkDensity();
        double leylineValue = getLeylineValue(seed, chunkPos, densityMod);
        if (leylineValue > 0.7) {
            chunkAmbientGen.setLeylineChunk(true);
        } else {
            chunkAmbientGen.setLeylineChunk(false);
        }
    }

    public static double getLeylineValue(long seed, ChunkPos chunkPos, float densityModifier) {
        Random random = new Random(seed);
        // Generate pseudo-random leyline center points
        int[] leylineCentersX = new int[LEYLINE_CENTER_COUNT];
        int[] leylineCentersZ = new int[LEYLINE_CENTER_COUNT];

        for (int i = 0; i < LEYLINE_CENTER_COUNT; i++) {
            leylineCentersX[i] = random.nextInt(10000) - 5000; // Random positions in a range
            leylineCentersZ[i] = random.nextInt(10000) - 5000;
        }
        // Calculate minimum distance to any leyline center (Voronoi-like effect)
        double minDistance = Double.MAX_VALUE;
        int chunkX = chunkPos.x * VORONOI_SCALE;
        int chunkZ = chunkPos.z * VORONOI_SCALE;

        for (int i = 0; i < LEYLINE_CENTER_COUNT; i++) {
            double distance = Math.sqrt(
                    Math.pow(chunkX - leylineCentersX[i], 2) + Math.pow(chunkZ - leylineCentersZ[i], 2)
            );
            minDistance = Math.min(minDistance, distance);
        }
        return (1.0 / (1.0 + minDistance)) * densityModifier;
    }
}
