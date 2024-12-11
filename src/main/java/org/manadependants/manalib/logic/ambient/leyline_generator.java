package org.manadependants.manalib.logic.ambient;

public class leyline_generator {

    public static float[] peakTemperatures = {.7f, .95f};
    public static int cellSize = 32;

    static float getClosestTemperature(float temperature) {
        float closest = 2.0f;
        for (float peakTemperature : peakTemperatures) {
            if (temperature > peakTemperatures[2]) {
                float temp = temperature - peakTemperature;
                if (temp < closest) {
                    closest = peakTemperature;
                }

            } else {
                float temp = peakTemperature - temperature;
                if (temp < closest) {
                    closest = peakTemperature;
                }
            }
        }
        return closest;
    }

    public static float getDifference(float temperature, float closest){
        if(closest > temperature){
            return closest - temperature;
        }
        return temperature - closest;
    }

}
