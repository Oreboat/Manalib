package org.manadependants.manalib.logic.maths;

import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class GaussianCalc {
    private static final double ALPHA = 50.0;
    private static final double BETA = 5.0;

    private static final Random random = new Random();


    public static double calculateSigma(double mana, double kills){
        return ALPHA/((mana/500)+kills+BETA);
    }

    public static double generateRandomGaussian(double mean, double sigma) {
        // Box-Muller Transform
        double u1 = random.nextDouble(); // Random number [0, 1)
        double u2 = random.nextDouble(); // Random number [0, 1)

        // Generate a standard normal random variable
        double z = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);

        // Scale and shift to match desired mean and standard deviation
        return mean + sigma * z;
    }

}
