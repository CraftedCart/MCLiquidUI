package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;

/**
 * Created by CraftedCart on 10/01/2016 (DD/MM/YYYY)
 */
public class UIGradientPanel extends UIComponent {

    protected byte gradientDirection = 0; //0: Vertical gradient | 1: Horizontal Gradient
    protected UIColor colorFrom = UIColor.matGrey900();
    protected UIColor colorTo = UIColor.matGrey900();

    /**
     * Create a new UIGradientPanel by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIGradientPanel(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                           AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        this.setPanelBackgroundColor(UIColor.transparent());
    }

    /**
     * This is called every frame
     */
    @Override
    protected void onUpdate() {
        super.onUpdate();

        if (visible) {
            if (gradientDirection == 0) {
                GuiUtils.drawQuadGradientVertical(topLeftPx, bottomRightPx, colorFrom, colorTo);
            } else {
                GuiUtils.drawQuadGradientHorizontal(topLeftPx, bottomRightPx, colorFrom, colorTo);
            }
        }

    }

    /**
     * This is called every frame to call the {@link UIComponent#onUpdate()} method of all children after this
     * component is done drawing.<br>
     * <br>
     * This is only called if this component is visible.
     */
    @Override
    protected void updateChildren() {
        GuiUtils.setupStencilMask();
        GuiUtils.drawQuad(topLeftPx, bottomRightPx, UIColor.pureWhite());
        GuiUtils.setupStencilDraw();
        super.updateChildren();
        GuiUtils.setupStencilEnd();
    }

    /**
     * Sets a vertical gradient
     *
     * @param colorFrom The top color
     * @param colorTo The bottom color
     */
    public void setVerticalGradient(UIColor colorFrom, UIColor colorTo) {
        gradientDirection = 0;
        this.colorFrom = colorFrom;
        this.colorTo = colorTo;
    }

    /**
     * Sets a horizontal gradient
     *
     * @param colorFrom The left color
     * @param colorTo The right color
     */
    public void setHorizontalGradient(UIColor colorFrom, UIColor colorTo) {
        gradientDirection = 1;
        this.colorFrom = colorFrom;
        this.colorTo = colorTo;
    }

}
