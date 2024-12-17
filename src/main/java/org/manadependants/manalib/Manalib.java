package org.manadependants.manalib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.manadependants.manalib.components.EntityComponentRegistry;
import org.manadependants.manalib.components.WorldComponentRegistry;
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
        WorldComponentRegistry.init();
        ServerPlayConnectionEvents.JOIN.register(this::onPlayerJoin);
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) ->{
            ManaChunkAmbientGen chunkAmbientGen = chunk.getComponent(WorldComponentRegistry.CHUNK_MANA_COMPONENT);
            if(chunkAmbientGen.getMaxMana() <= 0){
                WorldComponentRegistry.initializeChunkMana(world, chunk);
            }
            leyline_generator.leylineMapGeneration(world, chunk);
        });
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
