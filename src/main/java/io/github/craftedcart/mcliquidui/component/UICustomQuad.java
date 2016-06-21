package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;

/**
 * Created by CraftedCart on 13/01/2016 (DD/MM/YYYY)
 *
 * UICustomQuad<br>
 * <br>
 * A quadrilateral which can be given four points<br>
 * Unlike other components, a UICustomQuad it not restricted to being a rectangle<br>
 * <br>
 * Please note that mouse detection does not work correctly with a UICustomQuad<br>
 * It only detects for the mouse cursor to be in a rectangular box between the topLeftPx and the bottomRightPx
 */
public class UICustomQuad extends UIComponent {

    protected PosXY topRightPoint;
    protected PosXY bottomLeftPoint;
    protected PosXY topRightPx;
    protected PosXY bottomLeftPx;

    /**
     * Create a new UICustomQuad by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param topRightPoint The top right point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param bottomLeftPoint The bottom left point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UICustomQuad(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY topRightPoint, PosXY bottomRightPoint, PosXY bottomLeftPoint,
                        AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        this.topRightPoint = topRightPoint;
        this.bottomLeftPoint = bottomLeftPoint;
    }

    /**
     * This is called every frame
     */
    @Override
    public void onUpdate() {
        if (visible) {
            topRightPx = new PosXY(
                    parentComponent.width * bottomRightAnchor.xPercent + topRightPoint.x + parentComponent.topLeftPx.x + parentComponent.pointOffset.x,
                    parentComponent.height * topLeftAnchor.yPercent + topRightPoint.y + parentComponent.topLeftPx.y + parentComponent.pointOffset.y
            );

            bottomLeftPx = new PosXY(
                    parentComponent.width * topLeftAnchor.xPercent + bottomLeftPoint.x + parentComponent.topLeftPx.x + parentComponent.pointOffset.x,
                    parentComponent.height * bottomRightAnchor.yPercent + bottomLeftPoint.y + parentComponent.topLeftPx.y + parentComponent.pointOffset.y
            );
        }
        super.onUpdate();
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

    @Override
    protected void draw() {
        if (lmbDown) {
            GuiUtils.drawQuad(topLeftPx, bottomLeftPx, bottomRightPx, topRightPx, panelHitBackgroundColor);
        } else if (mouseOver || mouseOverChildComponent) {
            GuiUtils.drawQuad(topLeftPx, bottomLeftPx, bottomRightPx, topRightPx, panelActiveBackgroundColor);
        } else {
            GuiUtils.drawQuad(topLeftPx, bottomLeftPx, bottomRightPx, topRightPx, panelDefaultBackgroundColor);
        }
    }

}
