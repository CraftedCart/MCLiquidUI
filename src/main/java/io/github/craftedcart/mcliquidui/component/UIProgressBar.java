package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;

/**
 * Created by CraftedCart on 12/01/2016 (DD/MM/YYYY)
 */
public class UIProgressBar extends UIComponent {

    /**
     * The bar in the progress bar
     */
    public UIComponent uiFGBar;
    /**
     * The progress to be displayed by the progress bar (A value between 0 - 1)
     */
    protected double progress = 0;

    /**
     * Create a new UIProgressBar by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIProgressBar(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                         AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        uiFGBar = new UIComponent(this,
                "fgBar",
                new PosXY(0, 0),
                new PosXY(0, 0),
                new AnchorPoint(0, 0),
                new AnchorPoint(0, 1));
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
     * @param progress The progress to be displayed by the progress bar (A value between 0 - 1)
     */
    public void setProgress(double progress) {
        this.progress = progress;
        uiFGBar.setBottomRightAnchor(new AnchorPoint(progress, 1));
    }

    /**
     * @return The progress displayed by the progress bar (A value between 0 - 1)
     */
    public double getProgress() {
        return progress;
    }

    /**
     * @param col The color of the progress bar
     */
    public void setPanelForegroundColor(UIColor col) {
        uiFGBar.setPanelBackgroundColor(col);
    }
}
