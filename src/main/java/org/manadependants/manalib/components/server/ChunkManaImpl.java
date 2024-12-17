package org.manadependants.manalib.components.server;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.chunk.Chunk;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;

public class ChunkManaImpl implements ManaChunkAmbientGen, AutoSyncedComponent {

    private float currentMana;
    private float maxMana;
    private float chunkDensity;
    private boolean isLeyline;
    private Chunk chunk;

    @Override
    public float getCurrentMana() {
        return currentMana;
    }

    @Override
    public void setCurrentMana(float mana) {
        this.currentMana = Math.min(mana, maxMana);
        this.chunk.setNeedsSaving(true);
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(float mana) {
        this.maxMana = mana;
        if (this.isLeyline) {
            this.maxMana = maxMana*100;
        }
        this.chunk.setNeedsSaving(true);
    }

    @Override
    public float getChunkDensity() {
        return chunkDensity;
    }

    @Override
    public void setChunkDensity(float amount) {
        this.chunkDensity = amount;
        this.chunk.setNeedsSaving(true);
    }

    @Override
    public boolean isLeylineChunk(){
        return isLeyline;
    }

    @Override
    public void setLeylineChunk(boolean leyline) {
        this.isLeyline = leyline;
        this.chunk.setNeedsSaving(true);
    }

    @Override
    public void regenManaOverTime() {
        currentMana += maxMana / 10000.0f; // 1/100 of max mana per second
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
        this.chunk.setNeedsSaving(true);
    }

    @Override
    public void consumeChunkMana(float amount) {
        currentMana -= amount;
        if (currentMana < 0) {
            currentMana = 0;
        }
        this.chunk.setNeedsSaving(true);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        currentMana = tag.getFloat("CurrentMana");
        maxMana = tag.getFloat("MaxMana");
        chunkDensity = tag.getFloat("ChunkDensity");
        isLeyline = tag.getBoolean("Leyline");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("CurrentMana", currentMana);
        tag.putFloat("MaxMana", maxMana);
        tag.putFloat("ChunkDensity", chunkDensity);
        tag.putBoolean("Leyline", isLeyline);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf) {
        buf.writeFloat(maxMana);
        buf.writeFloat(currentMana);
        buf.writeFloat(chunkDensity);
        buf.writeBoolean(isLeyline);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        maxMana = buf.readFloat();
        currentMana = buf.readFloat();
        chunkDensity = buf.readFloat();
        isLeyline = buf.readBoolean();
    }
}

