package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;

/**
 * Created by CraftedCart on 15/02/2016 (DD/MM/YYYY)<br>
 * <br>
 * It just keeps on spinning!<br>
 * This is basically a throbber
 */
public class UIIndeterminateRadialProgressBar extends UIRadialProgressBar {

    /**
     * Create a new UIIndeterminateRadialProgressBar by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIIndeterminateRadialProgressBar(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                                            AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        setProgress(0.25);
    }

    /**
     * This is called every frame
     */
    @Override
    protected void onUpdate() {
        super.onUpdate();

        rotationalOffset += GuiUtils.getDelta() * 200;

    }
    
}
