package org.manadependants.manalib.components;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.manadependants.manalib.components.server.ChunkManaImpl;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;

import java.util.HashMap;
import java.util.Map;

public class ChunkComponentRegistry implements ChunkComponentInitializer {
    public static final ComponentKey<ManaChunkAmbientGen> CHUNK_MANA_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(
            new Identifier("manalib", "chunk_mana"),
            ManaChunkAmbientGen.class
    );
    private static final Map<String, Float> dimensionDensityMap = new HashMap<>();

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registery) {
        registery.register(CHUNK_MANA_COMPONENT, c -> new ChunkManaImpl());
    }

    public static void init(){
        initializeDimensions();
    }

    public static void initializeDimensions() {
        modifyDensityForDimension("minecraft:overworld", 100.0f); // Overworld
        modifyDensityForDimension("minecraft:nether", 50.0f);  // Nether
        modifyDensityForDimension("minecraft:the_end", 1000.0f);   // End
    }

    public static void initializeChunkMana(ServerWorld world, WorldChunk chunk) {
        ManaChunkAmbientGen chunkMana = ChunkComponentRegistry.CHUNK_MANA_COMPONENT.get(chunk);
        float biomeTemperature = world.getBiome(chunk.getPos().getStartPos().add(8,0,8)).value().getTemperature();
        float density = chunkDensityTempCalc(chunk, biomeTemperature);
        chunkMana.setChunkDensity(density);
        chunkMana.setMaxMana(density * 100000.0f);
        chunkMana.setCurrentMana(chunkMana.getMaxMana());
    }

    private static float chunkDensityTempCalc(WorldChunk chunk, float temperature){
        ManaChunkAmbientGen component = chunk.getComponent(CHUNK_MANA_COMPONENT);
        World world = chunk.getWorld();
        float dimensionalDensity = getDensityForDimension(String.valueOf(world.getDimensionKey()));
        return Math.max(1.0f, dimensionalDensity/(1.0f + temperature));
    }

    public static float getDensityForDimension(String dimensionKey) {
        return dimensionDensityMap.getOrDefault(dimensionKey, 100f); // Default density
    }

    public static void modifyDensityForDimension(String dimensionKey, float density) {
        dimensionDensityMap.put(dimensionKey, density);
    }
}
