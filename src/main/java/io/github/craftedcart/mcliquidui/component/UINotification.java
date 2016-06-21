package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;

/**
 * Created by CraftedCart on 14/02/2016 (DD/MM/YYYY)
 */
public class UINotification extends UIComponent {

    public UILabel uiLabel;
    public double notificationTime = 5; //5 seconds

    /**
     * Create a new UINotification by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param notificationText The text to include in the notification
     * @param backgroundColor The background color of the notification
     */
    public UINotification(UINotificationManager parentComponent, String name, String notificationText, UIColor backgroundColor) {
        super(parentComponent, name, new PosXY(-536, 24), new PosXY(-24, 56), new AnchorPoint(1, 0), new AnchorPoint(1, 0));
        setPanelBackgroundColor(backgroundColor);
        uiLabel = new UILabel(this, "notificationLabel", new PosXY(4, 4), new AnchorPoint(0, 0), GuiUtils.font);
        uiLabel.setText(notificationText);
        uiLabel.setTextColor(UIColor.matWhite());
    }

    /**
     * This is called every frame
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        notificationTime -= GuiUtils.getDelta();

        if (notificationTime <= 0) {
            ((UINotificationManager) parentComponent).destroyNotification(componentID); //Destroy this component
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
}
