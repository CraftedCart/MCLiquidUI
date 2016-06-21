package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CraftedCart on 11/02/2016 (DD/MM/YYYY)
 */
public class UIListBox extends UIComponent {

    public Map<String, UIComponent> componentMap = new HashMap<String, UIComponent>();
    public List<String> componentNumberIDs = new ArrayList<String>();

    private double targetScrollY = 0;

    /**
     * Create a new UIListBox by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIListBox(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                     AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
    }

    /**
     * This is called every frame
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        double scrollToAdd = 0;
        if (isMouseOver() || isMouseOverChildComponent()) {
            scrollToAdd = GuiUtils.getMouseDWheel();
        }

        targetScrollY = Math.min(Math.max(targetScrollY + scrollToAdd, height - getTotalHeightOfComponents()), 0);

        pointOffset.y = MathUtils.lerp(pointOffset.y, targetScrollY, Math.min(GuiUtils.getDelta() * 20, 1));

    }

    /**
     * Adds an item to this UIListBox
     *
     * @param ID The ID of the component to add
     * @param component The component to add
     */
    public void addItem(String ID, UIComponent component) {

        double totalHeight = getTotalHeightOfComponents();

        component.topLeftPoint = component.topLeftPoint.add(0, totalHeight);
        component.bottomRightPoint = component.bottomRightPoint.add(0, totalHeight);

        component.parentComponent = this;
        component.componentID = lastComponentID;
        registerComponent(lastComponentID, component);
        lastComponentID++;
        component.calcPos();
        componentMap.put(ID, component);
        componentNumberIDs.add(ID);

    }

    /**
     * Remove items from this UIListBox
     *
     * @param IDs The string IDs of the items to remove
     */
    public void removeItems(String... IDs) {
        for (String ID : IDs) {
            childUiComponents.set(componentMap.get(ID).componentID, null); //Destroy the component
            componentMap.remove(ID);
            componentNumberIDs.remove(ID);
        }
        reorganiseItems();
    }

    /**
     * Recalculates the positions of all components within the {@link UIListBox}
     */
    public void reorganiseItems() {
        double totalHeight = 0;
        for (int i = 0; i < componentMap.size(); i++) {
            if (componentMap.get(componentNumberIDs.get(i)).visible) {
                PosXY currentTopLeft = componentMap.get(componentNumberIDs.get(i)).topLeftPoint;
                PosXY currentBottomRight = componentMap.get(componentNumberIDs.get(i)).bottomRightPoint;

                componentMap.get(componentNumberIDs.get(i)).bottomRightPoint = new PosXY(currentBottomRight.x, currentBottomRight.y - currentTopLeft.y + totalHeight);
                componentMap.get(componentNumberIDs.get(i)).topLeftPoint = new PosXY(currentTopLeft.x, totalHeight);

                totalHeight = componentMap.get(componentNumberIDs.get(i)).bottomRightPoint.y;
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
    public void updateChildren() {
        GuiUtils.setupStencilMask();
        GuiUtils.drawQuad(topLeftPx, bottomRightPx, UIColor.pureWhite());
        GuiUtils.setupStencilDraw();

        for (int i = 0; i < childUiComponents.size(); i++) { //Loop through every component
            UIComponent component = childUiComponents.get(i);
            if (component != null && component.visible) {
                component.calcPos();

                if (component.topLeftPx.y <= bottomRightPx.y &&
                        component.bottomRightPx.y >= topLeftPx.y) {
                    component.onUpdate();
                }
            }
        }

        GuiUtils.setupStencilEnd();
    }

    /**
     * @param targetScrollY Sets the Y position to scroll to
     */
    public void setTargetScrollY(double targetScrollY) {
        this.targetScrollY = targetScrollY;
    }

    /**
     * @return The Y position to scroll to
     */
    public double getTargetScrollY() {
        return targetScrollY;
    }

    /**
     * @return The height of all components within the {@link UIListBox}
     */
    public double getTotalHeightOfComponents() {
        double totalHeight = 0;
        for (UIComponent uiComponent : childUiComponents) {
            if (uiComponent != null && uiComponent.visible) {
                totalHeight += uiComponent.height;
            }
        }
        return totalHeight;
    }

}
