package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by CraftedCart on 14/02/2016 (DD/MM/YYYY)
 */
public class UINotificationManager extends UIComponent {

    /**
     * IDs of child components that will be destroyed the next time {@link UINotificationManager#onUpdate()} is called
     */
    private List<Integer> componentsToDestroy = new ArrayList<Integer>();

    /**
     * Create a new UINotificationManager by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     */
    public UINotificationManager(UIComponent parentComponent, String name) {
        super(parentComponent, name, new PosXY(0, 0), new PosXY(0, 0), new AnchorPoint(1, 0), new AnchorPoint(1, 0));
    }

    public void addNotification(String notificationText, UIColor backgroundColor) {
        new UINotification(this, notificationText + "Notification", notificationText, backgroundColor);
    }

    /**
     * @param componentID Remove a notification
     */
    protected void destroyNotification(int componentID) {
        componentsToDestroy.add(componentID);
    }

    /**
     * This is called every frame
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        Iterator<Integer> iterToDestroy = componentsToDestroy.iterator();
        while (iterToDestroy.hasNext()) {
            childUiComponents.set(iterToDestroy.next(), null);
            iterToDestroy.remove();
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

    @Override
    protected void draw() {
        //No-Op
    }
}
