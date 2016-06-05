package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.PosXY;

/**
 * Created by CraftedCart on 17/02/2016 (DD/MM/YYYY)
 */
public class UIListSpacer extends UIComponent {

    public UIListSpacer(UIComponent parentComponent, String name, double height) {
        super(parentComponent, name, new PosXY(0, 0), new PosXY(0, height),
                new AnchorPoint(0, 0), new AnchorPoint(1, 0));
    }

    @Override
    protected void onUpdate() {
        calcPos();
    }
}
