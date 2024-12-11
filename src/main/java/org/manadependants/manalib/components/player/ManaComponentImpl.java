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
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("MaxMana", maxMana);
        tag.putFloat("TotalMana", totalMana);
        tag.putFloat("ManaAdaptability", manaAdaptability);
        tag.putFloat("ManaStrength", manaStrength);
        tag.putInt("ManaClarity", manaClarity);
    }

    public void writeSyncPacket(PacketByteBuf buf) {
        buf.writeFloat(maxMana);
        buf.writeFloat(totalMana);
        buf.writeFloat(manaAdaptability);
        buf.writeFloat(manaStrength);
        buf.writeInt(manaClarity);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        maxMana = buf.readFloat();
        totalMana = buf.readFloat();
        manaAdaptability = buf.readFloat();
        manaStrength = buf.readFloat();
        manaClarity = buf.readInt();
    }
}
