package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;

/**
 * Created by CraftedCart on 16/02/2016 (DD/MM/YYYY)<br>
 * <br>
 * Like a checkbox, its value can either be activated (true) or deactivated (false)
 */
public class UIToggleBox extends UIButton {

    public boolean value = false;
    public UIColor falseColor = UIColor.matRed();
    public UIColor trueColor = UIColor.matGreen();

    /**
     * Create a new UIToggleBox by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIToggleBox(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                       AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
    }

    /**
     * @param falseColor The color to use when the {@link UIToggleBox} is deactivated / false
     */
    public void setFalseColor(UIColor falseColor) {
        this.falseColor = falseColor;
    }

    /**
     * @param trueColor The color to use when the {@link UIToggleBox} is activated / true
     */
    public void setTrueColor(UIColor trueColor) {
        this.trueColor = trueColor;
    }

    /**
     * @param value True or false - Sets the value of this {@link UIToggleBox}
     */
    public void setValue(boolean value) {
        this.value = value;

        if (value) {
            setPanelDefaultBackgroundColor(trueColor);
            setPanelActiveBackgroundColor(new UIColor(trueColor.r * 255, trueColor.g * 255, trueColor.b * 255, trueColor.a * 0.75));
            setPanelHitBackgroundColor(new UIColor(trueColor.r * 255, trueColor.g * 255, trueColor.b * 255, trueColor.a * 0.5));
        } else {
            setPanelDefaultBackgroundColor(falseColor);
            setPanelActiveBackgroundColor(new UIColor(falseColor.r * 255, falseColor.g * 255, falseColor.b * 255, falseColor.a * 0.75));
            setPanelHitBackgroundColor(new UIColor(falseColor.r * 255, falseColor.g * 255, falseColor.b * 255, falseColor.a * 0.5));
        }
    }

    @Override
    protected void onClick() {
        value = !value;

        if (value) {
            setPanelDefaultBackgroundColor(trueColor);
            setPanelActiveBackgroundColor(new UIColor(trueColor.r * 255, trueColor.g * 255, trueColor.b * 255, trueColor.a * 0.75));
            setPanelHitBackgroundColor(new UIColor(trueColor.r * 255, trueColor.g * 255, trueColor.b * 255, trueColor.a * 0.5));
        } else {
            setPanelDefaultBackgroundColor(falseColor);
            setPanelActiveBackgroundColor(new UIColor(falseColor.r * 255, falseColor.g * 255, falseColor.b * 255, falseColor.a * 0.75));
            setPanelHitBackgroundColor(new UIColor(falseColor.r * 255, falseColor.g * 255, falseColor.b * 255, falseColor.a * 0.5));
        }

        super.onClick();
    }
}
