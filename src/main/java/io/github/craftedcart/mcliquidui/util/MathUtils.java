package io.github.craftedcart.mcliquidui.util;

import java.util.Random;

/**
 * Created by CraftedCart on 18/11/2015 (DD/MM/YYYY)
 */
public class MathUtils {

    /**
     * Generates a random integer between the min and max values
     *
     * @param min The smallest possible number
     * @param max The largest possible number
     * @return A random number between the min and max parameters
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random double between the min and max values
     *
     * @param min The smallest possible number
     * @param max The largest possible number
     * @return A random number between the min and max parameters
     */
    public static double randDouble(double min, double max) {
        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }

    /**
     * Linearly interpolates between two floats
     *
     * @param a The starting float
     * @param b The ending float
     * @param f The percentage between the two floats
     * @return The float which if f% between a and b
     */
    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    /**
     * Linearly interpolates between two doubles
     *
     * @param a The starting double
     * @param b The ending dluble
     * @param f The percentage between the two double
     * @return The double which if f% between a and b
     */
    public static double lerp(double a, double b, double f) {
        return a + f * (b - a);
    }

    /**
     * Linearly interpolates between two {@link UIColor}s
     *
     * @param a The starting {@link UIColor}
     * @param b The ending {@link UIColor}
     * @param f The percentage between the two {@link UIColor}
     * @return The {@link UIColor} which if f% between a and b
     */
    public static UIColor lerpUIColor(UIColor a, UIColor b, float f) {
        return new UIColor(a.r * 255 + f * (b.r * 255 - a.r * 255), a.g * 255 + f * (b.g * 255 - a.g * 255), a.b * 255 + f * (b.b * 255 - a.b * 255), a.a * 255 + f * (b.a * 255 - a.a * 255));
    }

}
