package org.manadependants.manalib.components;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import org.manadependants.manalib.components.server.ChunkManaImpl;

public class ChunkComponentRegistry implements ChunkComponentInitializer {


    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registery) {
        registery.register(WorldComponentRegistry.CHUNK_MANA_COMPONENT, c -> new ChunkManaImpl());
    }
}
