package org.manadependants.manalib.components.player;

import net.minecraft.nbt.NbtCompound;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;

public class ManaComponentImpl implements ManaComponent {
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
    public void regenMana() {
        // Example regeneration logic: totalMana increases by (clarity / 10), capped at maxMana
        totalMana += manaClarity / 10;
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
}
