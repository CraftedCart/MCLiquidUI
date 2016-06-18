package io.github.craftedcart.mcliquidui.util;

/**
 * Created by CraftedCart on 25/01/2015 (DD/MM/YYYY)
 *
 * A two point vector
 */
public class PosXY {

    public double x;
    public double y;

    /**
     * Create a two point vector
     *
     * @param x Point 1: The X
     * @param y Point 2: The Y
     */
    public PosXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add two doubles to this {@link PosXY}
     *
     * @param x The X value to add
     * @param y The Y value to add
     * @return A new {@link PosXY with the added X and Y values}
     */
    public PosXY add(double x, double y) {
        return new PosXY(this.x + x, this.y + y);
    }

    /**
     * Add a {@link PosXY} to this {@link PosXY}
     *
     * @param toAdd The {@link PosXY} to add
     * @return A new {@link PosXY with the added X and Y values from toAdd}
     */
    public PosXY add(PosXY toAdd) {
        return new PosXY(this.x + toAdd.x, this.y + toAdd.y);
    }

}
