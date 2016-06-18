package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;
import org.lwjgl.input.Mouse;

/**
 * Created by CraftedCart on 16/02/2016 (DD/MM/YYYY)
 */
public class UIButton extends UIComponent {

    public UIButton(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                    AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
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

    @Override
    protected void checkMouseStateFromChildren() {
        boolean checkMouseOverChildComponent = false;

        for (UIComponent component : childUiComponents) { //Loop through every component
            if (component.mouseOver || component.mouseOverChildComponent) {
                mouseOverChildComponent = true;
                checkMouseOverChildComponent = true;
                break;
            }
        }

        if (!checkMouseOverChildComponent) {
            mouseOverChildComponent = false;
        }

        mouseOver = true;

        if (Mouse.isButtonDown(0)) { //IF LMB is down
            if (!lmbDown) {
                lmbDown = true;
                onClick(); //Mouse was clicked on this component
            }
        } else {
            lmbDown = false;
        }
    }

}
