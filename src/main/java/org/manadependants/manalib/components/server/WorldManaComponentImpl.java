package org.manadependants.manalib.components.server;

import net.minecraft.nbt.NbtCompound;
import org.manadependants.manalib.components.server.interfaces.ManaDimensionalDensityComponent;

import java.util.HashMap;
import java.util.Map;

public class WorldManaComponentImpl implements ManaDimensionalDensityComponent {
    private final Map<String, Float> dimensionDensityMap = new HashMap<>();
    private float currentDensity;

    @Override
    public float getManaDensity() {
        return currentDensity;
    }

    @Override
    public void setManaDensity(float density) {
        this.currentDensity = density;
    }

    @Override
    public void modifyDensityForDimension(String dimensionKey, float density) {
        dimensionDensityMap.put(dimensionKey, density);
    }

    @Override
    public float getDensityForDimension(String dimensionKey) {
        return dimensionDensityMap.getOrDefault(dimensionKey, 100f); // Default density
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        currentDensity = tag.getFloat("CurrentManaDensity");
        NbtCompound densityMapTag = tag.getCompound("DimensionDensityMap");
        for (String key : densityMapTag.getKeys()) {
            dimensionDensityMap.put(key, densityMapTag.getFloat(key));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("CurrentManaDensity", currentDensity);
        NbtCompound densityMapTag = new NbtCompound();
        for (Map.Entry<String, Float> entry : dimensionDensityMap.entrySet()) {
            densityMapTag.putFloat(entry.getKey(), entry.getValue());
        }
        tag.put("DimensionDensityMap", densityMapTag);
    }
}
