package org.manadependants.manalib.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.manadependants.manalib.Manalib;
import org.manadependants.manalib.components.server.WorldManaComponentImpl;
import org.manadependants.manalib.components.server.interfaces.ManaDimensionalDensityComponent;

public class WorldComponentRegistry implements WorldComponentInitializer {
    public static final ComponentKey<ManaDimensionalDensityComponent> MANA_DENSITY_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(
            new Identifier(Manalib.MODID, "mana_density"),
            ManaDimensionalDensityComponent.class
    );


    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry){
        registry.register(MANA_DENSITY_COMPONENT, new WorldManaComponentImpl(), );
    }

    public static void initializeVanillaDimensions(ManaDimensionalDensityComponent component) {
        component.modifyDensityForDimension("minecraft:overworld", 100.0f); // Overworld
        component.modifyDensityForDimension("minecraft:nether", 1000.0f);  // Nether
        component.modifyDensityForDimension("minecraft:the_end", 50.0f);   // End
    }

    public void updateManaDensity(ServerWorld world) {
        BlockPos center = new BlockPos(world.getSpawnPos());
        float averageTemperature = calculateAverageBiomeTemperature(world, center, 32); // 32-block radius
        calculateTemperatureBasedDensity(world, averageTemperature);// Example scaling factor
    }

    private void calculateTemperatureBasedDensity(ServerWorld world, float temperature) {
        ManaDimensionalDensityComponent component = world.getComponent(MANA_DENSITY_COMPONENT);
        float dimensionalDensity = component.getDensityForDimension(String.valueOf(world.getDimensionKey()));
        float densityValue = Math.max(1.0f, dimensionalDensity/(1.0f + temperature));
        component.setManaDensity(densityValue);
    }


    private float calculateAverageBiomeTemperature(ServerWorld world, BlockPos center, int radius) {
        int sampleCount = 0;
        float temperatureSum = 0.0f;

        for (int x = -radius; x <= radius; x += 4) { // Sample at intervals
            for (int z = -radius; z <= radius; z += 4) {
                BlockPos pos = center.add(x, 0, z);
                Biome biome = world.getBiome(pos).value();
                temperatureSum += biome.getTemperature();
                sampleCount++;
            }
        }

        return sampleCount > 0 ? temperatureSum / sampleCount : 0.5f; // Default value if no samples
    }
}
