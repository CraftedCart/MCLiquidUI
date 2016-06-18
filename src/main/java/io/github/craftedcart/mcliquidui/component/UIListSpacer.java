package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;

/**
 * Created by CraftedCart on 17/02/2016 (DD/MM/YYYY)
 */
public class UIListSpacer extends UIComponent {

    /**
     * Create a new UIListSpacer by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param height The height of the spacer
     */
    public UIListSpacer(UIComponent parentComponent, String name, double height) {
        super(parentComponent, name, new PosXY(0, 0), new PosXY(0, height),
                new AnchorPoint(0, 0), new AnchorPoint(1, 0));
    }

    /**
     * This is called every frame
     */
    @Override
    protected void onUpdate() {
        calcPos();
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
}
