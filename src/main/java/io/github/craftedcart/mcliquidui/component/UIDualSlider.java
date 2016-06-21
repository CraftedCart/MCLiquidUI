package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * Created by CraftedCart on 06/02/2016 (DD/MM/YYYY)<br>
 * <br>
 * Like a regular slider, but with two handles
 */
public class UIDualSlider extends UIComponent {

    public UIComponent minHandle;
    public UIComponent maxHandle;
    public UIComponent selectedArea;
    public double minValue;
    public double maxValue;
    public double currentMinValue;
    public double currentMaxValue;
    public double decimalPlaces = 9;
    /**
     * -1: No handle selected selected
     *  0: Min (Left) handle selected
     *  1: Max (Right) handle selected
     */
    private byte selectedHandle = -1;

    /**
     * Create a new UIDualSlider by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIDualSlider(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                        AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        selectedArea = new UIComponent(this,
                "selectedArea",
                new PosXY(0, 0),
                new PosXY(0, 0),
                new AnchorPoint(0, 0),
                new AnchorPoint(1, 1));
        minHandle = new UIComponent(this,
                "minHandle",
                new PosXY(-2, -8),
                new PosXY(2, 8),
                new AnchorPoint(0, 0),
                new AnchorPoint(0, 1));
        maxHandle = new UIComponent(this,
                "maxHandle",
                new PosXY(-2, -8),
                new PosXY(2, 8),
                new AnchorPoint(1, 0),
                new AnchorPoint(1, 1));
    }

    /**
     * This is called every frame
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (Mouse.isButtonDown(0)) { //If LMB down
            double percent = (Mouse.getX() - topLeftPx.x) / width;
            double mouseValue = MathUtils.lerp(minValue, maxValue, percent);

            if (selectedHandle == 0) { //Min handle selected
                if (Mouse.getX() > maxHandle.topLeftPx.x + 2) { //Min handle went over the max handle, so select the max handle
                    setCurrentMinValue(currentMaxValue);
                    selectedHandle = 1;
                } else {
                    setCurrentMinValue(mouseValue);
                }
            } else if (selectedHandle == 1) { //Max handle selected
                if (Mouse.getX() < minHandle.topLeftPx.x + 2) { //Max handle went over the min handle, so select the min handle
                    setCurrentMaxValue(currentMinValue);
                    selectedHandle = 0;
                } else {
                    setCurrentMaxValue(mouseValue);
                }
            }

            if (Mouse.getX() >= minHandle.topLeftPx.x - 4 &&
                    Mouse.getX() <= minHandle.bottomRightPx.x + 4 &&
                    Display.getHeight() - Mouse.getY() >= minHandle.topLeftPx.y - 4 &&
                    Display.getHeight() - Mouse.getY() <= minHandle.bottomRightPx.y + 4) { //Select the min handle
                selectedHandle = 0;
            } else if (Mouse.getX() >= maxHandle.topLeftPx.x - 4 &&
                    Mouse.getX() <= maxHandle.bottomRightPx.x + 4 &&
                    Display.getHeight() - Mouse.getY() >= maxHandle.topLeftPx.y - 4 &&
                    Display.getHeight() - Mouse.getY() <= maxHandle.bottomRightPx.y + 4) { //Select the max handle
                selectedHandle = 1;
            }
        } else { //Deselect handles
            selectedHandle = -1;
        }

    }

    /**
     * This is called every frame to call the {@link UIComponent#onUpdate()} method of all children after this
     * component is done drawing.<br>
     * <br>
     * This is only called if this component is visible.
     */
    @Override
    public void updateChildren() {
        GuiUtils.setupStencilMask();
        GuiUtils.drawQuad(topLeftPx, bottomRightPx, UIColor.pureWhite());
        GuiUtils.setupStencilDraw();
        super.updateChildren();
        GuiUtils.setupStencilEnd();
    }

    /**
     * @param currentMinValue Sets the min / left slider value
     */
    public void setCurrentMinValue(double currentMinValue) {
        double rounded = Math.max(Math.round(currentMinValue * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces), minValue);

        this.currentMinValue = rounded;

        double percent = (rounded - minValue) / (maxValue - minValue);
        minHandle.topLeftAnchor = new AnchorPoint(percent, 0);
        minHandle.bottomRightAnchor = new AnchorPoint(percent, 1);
        selectedArea.topLeftAnchor = new AnchorPoint(percent, 0);
    }

    /**
     * @param currentMaxValue The max / right slider value
     */
    public void setCurrentMaxValue(double currentMaxValue) {
        double rounded = Math.min(Math.round(currentMaxValue * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces), maxValue);

        this.currentMaxValue = rounded;

        double percent = (rounded - minValue) / (maxValue - minValue);
        maxHandle.topLeftAnchor = new AnchorPoint(percent, 0);
        maxHandle.bottomRightAnchor = new AnchorPoint(percent, 1);
        selectedArea.bottomRightAnchor = new AnchorPoint(percent, 1);
    }

    /**
     * @param decimalPlaces The decimal places to use for calculating precision
     */
    public void setDecimalPlaces(double decimalPlaces) {
        this.decimalPlaces = decimalPlaces;

        setCurrentMinValue(currentMinValue);
        setCurrentMaxValue(currentMaxValue);
    }
}
