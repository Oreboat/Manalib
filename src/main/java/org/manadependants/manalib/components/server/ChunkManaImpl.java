package org.manadependants.manalib.components.server;

import net.minecraft.nbt.NbtCompound;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;

public class ChunkManaImpl implements ManaChunkAmbientGen {

    private float currentMana;
    private float maxMana;

    @Override
    public float getCurrentMana() {
        return currentMana;
    }

    @Override
    public void setCurrentMana(float mana) {
        this.currentMana = Math.min(mana, maxMana);
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(float mana) {
        this.maxMana = mana;
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    @Override
    public void regenManaOverTime() {
        currentMana += maxMana / 10000.0f; // 1/100 of max mana per second
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    @Override
    public void consumeChunkMana(float amount) {
        currentMana -= amount;
        if (currentMana < 0) {
            currentMana = 0;
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        currentMana = tag.getFloat("CurrentMana");
        maxMana = tag.getFloat("MaxMana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("CurrentMana", currentMana);
        tag.putFloat("MaxMana", maxMana);
    }
}

