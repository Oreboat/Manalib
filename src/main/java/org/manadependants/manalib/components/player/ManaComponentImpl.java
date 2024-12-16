package org.manadependants.manalib.components.player;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;

public class ManaComponentImpl implements ManaComponent, AutoSyncedComponent {
    private float maxMana;
    private float totalMana;
    private float manaAdaptability;
    private float manaStrength;
    private int manaClarity;
    private float adaptedDensity;

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(float value) {
        this.maxMana = value;
    }

    @Override
    public float getTotalMana() {
        return totalMana;
    }

    @Override
    public void setTotalMana(float value) {
        this.totalMana = value;
    }

    @Override
    public void consumeMana(float amount){
        this.totalMana -= amount;
        if (totalMana < 0) {
            totalMana = 0;
        }
    }

    @Override
    public float getAdaptedDensity() {
        return adaptedDensity;
    }

    @Override
    public void setAdaptedDensity(float amount) {
        this.adaptedDensity = amount;
    }

    @Override
    public void addAdaptiveDensity(float density, float chunkDensity, float manaAdaptability) {
        this.adaptedDensity += Math.round(((density * manaAdaptability)/chunkDensity)/10) * 10;
    }

    @Override
    public float getManaAdaptability() {
        return manaAdaptability;
    }

    @Override
    public void setManaAdaptability(float value) {
        this.manaAdaptability = value;
    }

    @Override
    public float getManaStrength() {
        return manaStrength;
    }

    @Override
    public void setManaStrength(float value) {
        this.manaStrength = value;
    }

    @Override
    public int getManaClarity() {
        return manaClarity;
    }

    @Override
    public void setManaClarity(int value) {
        this.manaClarity = value;
    }


    @Override
    public void regenMana(float dimensionalManaDensity) {
        if (dimensionalManaDensity <= 0) {
            return; // Avoid division by zero or negative density
        }
        // Example regeneration logic: totalMana increases by (clarity / 10), capped at maxMana
        totalMana += maxMana / ((manaClarity/100)*dimensionalManaDensity);
        if (totalMana > maxMana) {
            totalMana = maxMana;
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        maxMana = tag.getInt("MaxMana");
        totalMana = tag.getInt("TotalMana");
        manaAdaptability = tag.getFloat("ManaAdaptability");
        manaStrength = tag.getFloat("ManaStrength");
        manaClarity = tag.getInt("ManaClarity");
        adaptedDensity = tag.getFloat("AdaptedMana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("MaxMana", maxMana);
        tag.putFloat("TotalMana", totalMana);
        tag.putFloat("ManaAdaptability", manaAdaptability);
        tag.putFloat("ManaStrength", manaStrength);
        tag.putInt("ManaClarity", manaClarity);
        tag.putFloat("AdaptedMana", adaptedDensity);
    }

    public void writeSyncPacket(PacketByteBuf buf) {
        buf.writeFloat(maxMana);
        buf.writeFloat(totalMana);
        buf.writeFloat(manaAdaptability);
        buf.writeFloat(manaStrength);
        buf.writeInt(manaClarity);
        buf.writeFloat(adaptedDensity);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        maxMana = buf.readFloat();
        totalMana = buf.readFloat();
        manaAdaptability = buf.readFloat();
        manaStrength = buf.readFloat();
        manaClarity = buf.readInt();
        adaptedDensity = buf.readFloat();
    }
}
