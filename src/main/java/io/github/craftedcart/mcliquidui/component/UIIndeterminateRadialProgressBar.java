package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;

/**
 * Created by CraftedCart on 15/02/2016 (DD/MM/YYYY)
 */
public class UIIndeterminateRadialProgressBar extends UIRadialProgressBar {

    public UIIndeterminateRadialProgressBar(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                                            AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        setProgress(0.25);
    }

    @Override
    protected void onUpdate() {
        super.onUpdate();

        rotationalOffset += GuiUtils.getDelta() * 200;

    }
    
}
