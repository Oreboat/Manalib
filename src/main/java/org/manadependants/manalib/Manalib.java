package org.manadependants.manalib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.manadependants.manalib.components.ChunkComponentRegistry;
import org.manadependants.manalib.components.EntityComponentRegistry;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;
import org.manadependants.manalib.logic.ambient.leyline_generator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Manalib implements ModInitializer {
    public static final String MODID = "manalib";
    private static Set<UUID> playedUUID = new HashSet<>();

    @Override
    public void onInitialize() {
        ChunkComponentRegistry.init();
        ServerPlayConnectionEvents.JOIN.register(this::onPlayerJoin);
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) ->{
            ManaChunkAmbientGen chunkAmbientGen = ChunkComponentRegistry.CHUNK_MANA_COMPONENT.get(chunk);
            if(chunkAmbientGen.getMaxMana() <= 0){
                ChunkComponentRegistry.initializeChunkMana(world, chunk);
            }
            /*leyline_generator.leylineMapGeneration(world, chunk);*/
        });
        ServerTickEvents.END_SERVER_TICK.register(this::playerDimensionAdaptation);
    }

    private void playerDimensionAdaptation(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            String dimensionKey = player.getWorld().getDimensionKey().toString();
            ManaComponent mana = EntityComponentRegistry.MANA_COMPONENT.get(player);
            float adaptedMana = mana.getAdaptedDensity();
            float worldDensity = ChunkComponentRegistry.getDensityForDimension(dimensionKey);
            if(worldDensity <= 10*adaptedMana){
                adaptedMana += worldDensity/1000;
                mana.setAdaptedDensity(adaptedMana);
            } else if (worldDensity > 10*adaptedMana) {
                float health = player.getHealth();
                float damage = worldDensity/(10*adaptedMana);
                player.setHealth(health - (0.2f*damage));
                adaptedMana += worldDensity/1000;
                mana.setAdaptedDensity(adaptedMana);
            }

        }
    }

    private void onPlayerJoin(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer server){
        PlayerEntity player = serverPlayNetworkHandler.player;
        UUID playerUUID = player.getUuid();
        if (!playedUUID.contains(playerUUID)) {
            EntityComponentRegistry.initializeManaStats(player);
            EntityComponentRegistry.initBloodPurity(player);
        }
        playedUUID.add(playerUUID);
    }

}
