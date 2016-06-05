package io.github.craftedcart.mcliquidui.util;

/**
 * Created by CraftedCart on 25/01/2015 (DD/MM/YYYY)
 */
public class PosXY {

    public double x;
    public double y;

    public PosXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PosXY add(double x, double y) {
        return new PosXY(this.x + x, this.y + y);
    }

}
