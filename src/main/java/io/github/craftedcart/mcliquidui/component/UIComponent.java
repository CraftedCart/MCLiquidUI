package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by CraftedCart on 09/01/2016 (DD/MM/YYYY)
 */
public class UIComponent {

    /**
     * A list of all child components
     */
    public List<UIComponent> childUiComponents = new ArrayList<UIComponent>();
    /**
     * The index of the selected component in the {@link UIComponent#childUiComponents} list
     */
    public Integer selectedComponent = null;
    /**
     * The ID of the last component registered as a child component to this
     */
    public int lastComponentID = 0;
    /**
     * This is only really used in the hierarchy inspector
     */
    public String name;

    /**
     * True if the mouse is over this component, false otherwise
     */
    public boolean mouseOver = false;
    /**
     * True if the mouse is over a child component, false otherwise
     */
    public boolean mouseOverChildComponent = false;
    /**
     * True if this component is selected, false otherwise
     */
    public boolean selected = false;
    /**
     * True if this component is visible, false otherwise
     */
    public boolean visible = true;

    /**
     * True if the left mouse button is down while the mouse if over this component, false otherwise
     */
    public boolean lmbDown = true;

    /**
     * The offset in pixels that the top left point is relative to the {@link UIComponent#topLeftAnchor}
     */
    public PosXY topLeftPoint;
    /**
     * The offset in pixels that the bottom right point is relative to the {@link UIComponent#bottomRightAnchor}
     */
    public PosXY bottomRightPoint;
    /**
     * The offset in pixels that this component is located at
     */
    public PosXY pointOffset = new PosXY(0, 0);
    /**
     * The width in pixels of this component
     */
    public double width;
    /**
     * The height in pixels of this component
     */
    public double height;
    /**
     * The position in pixels that the top left point of this component is relative to the top left of the screen
     */
    public PosXY topLeftPx = new PosXY(0, 0);
    /**
     * The position in pixels that the bottom right point of this component is relative to the top left of the screen
     */
    public PosXY bottomRightPx = new PosXY(0, 0);

    /**
     * The percentage along the X and Y axis of the screen that the {@link UIComponent#topLeftPoint} is located relative to
     */
    public AnchorPoint topLeftAnchor;
    /**
     * The percentage along the X and Y axis of the screen that the {@link UIComponent#bottomRightPoint} is located relative to
     */
    public AnchorPoint bottomRightAnchor;

    /**
     * The component that owns this component
     */
    public UIComponent parentComponent;
    /**
     * The ID of this component in the parent component
     */
    public int componentID;

    /**
     * An action that is executed when this component is clicked on
     */
    public UIAction onClickAction;
    /**
     * An action that is executed after drawing this component is done
     */
    public UIAction onUpdateAction;

    /**
     * Default idle background color
     */
    public UIColor panelDefaultBackgroundColor = UIColor.matGrey900();
    /**
     * Selected / mouse over background color
     */
    public UIColor panelActiveBackgroundColor = UIColor.matGrey900();
    /**
     * Clicked on background color
     */
    public UIColor panelHitBackgroundColor = UIColor.matGrey900();

    public Texture image;
    /**
     * Will convert to a Slick Texture and store it in the image variable if set
     */
    public BufferedImage bufferedImage;

    /**
     * Should textures be unloaded when overwritten (using setBufferedImage)?
     */
    public boolean unloadTextureWhenDone = false;



    /**
     * Create a new UIComponent by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIComponent(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                       AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {

        this.parentComponent = parentComponent;
        this.name = name;
        this.topLeftPoint = topLeftPoint;
        this.bottomRightPoint = bottomRightPoint;
        this.topLeftAnchor = topLeftAnchor;
        this.bottomRightAnchor = bottomRightAnchor;
        if (parentComponent != null) {
            this.componentID = this.parentComponent.lastComponentID;
            this.parentComponent.lastComponentID++;
            this.parentComponent.registerComponent(componentID, this);
        }

    }

    public UIComponent() {}

    /**
     * This is called every frame
     */
    public void onUpdate() {

        final int h = Display.getHeight(); //Get the height of the display

        final int mx = Mouse.getX(); //mx: Short for Mouse X
        final int my = h - Mouse.getY(); //my: Short for Mouse Y

        calcPos(); //Calculate pixel points on the screen as well as the width and height of this component

        if (visible) {

            //<editor-fold desc="Check mouse state">
            if (mx >= topLeftPx.x && mx < bottomRightPx.x && my >= topLeftPx.y && my < bottomRightPx.y) { //If the mouse is over this component
                checkMouseStateFromChildren();
            } else {
                mouseOver = false;
                mouseOverChildComponent = false;
                lmbDown = false;
                if (Mouse.isButtonDown(0)) { //If LMB down
                    if (parentComponent.selectedComponent != null && parentComponent.selectedComponent == componentID) {
                        parentComponent.setSelectedComponent(null); //Deselect this component
                    }
                }
            }
            //</editor-fold>

            draw();

            if (onUpdateAction != null) {
                onUpdateAction.execute();
            }

            updateChildren();

        } else {
            mouseOver = false;
            lmbDown = false;
            selected = false;
        }

    }

    /**
     * This is called every frame to call the {@link UIComponent#onUpdate()} method of all children after this
     * component is done drawing.<br>
     * <br>
     * This is only called if this component is visible.
     */
    public void updateChildren() {
        for (int i = 0; i < childUiComponents.size(); i++) { //Loop through every component
            UIComponent component = childUiComponents.get(i);
            if (component != null) {
                component.onUpdate(); //Update all registered components
            }
        }
    }

    /**
     * Checks to see if the mouse is over, and whether or not LMB is down, or over a child component
     */
    public void checkMouseStateFromChildren() {
        boolean checkMouseOverChildComponent = false;

        for (int i = 0, childUiComponentsSize = childUiComponents.size(); i < childUiComponentsSize; i++) {
            UIComponent component = childUiComponents.get(i); //Loop through every component
            if (component != null) {
                if (component.mouseOver || component.mouseOverChildComponent) {
                    mouseOverChildComponent = true;
                    checkMouseOverChildComponent = true;
                    break;
                }
            }
        }

        if (!checkMouseOverChildComponent) {
            mouseOverChildComponent = false;
        }

        if (!mouseOverChildComponent) {
            mouseOver = true;

            if (Mouse.isButtonDown(0)) { //IF LMB is down
                if (!lmbDown) {
                    lmbDown = true;
                    onClick(); //Mouse was clicked on this component
                }
            } else {
                lmbDown = false;
            }
        } else {
            mouseOver = false;
            lmbDown = false;
        }
    }

    /**
     * This is called when the {@link UIComponent} is clicked on
     */
    protected void onClick() {
        parentComponent.setSelectedComponent(componentID);

        if (onClickAction != null) {
            onClickAction.execute();
        }
    }

    /**
     * This is called every frame as long as the component is visible<br>
     * You may override and put custom drawing code in here
     */
    protected void draw() {
        if (lmbDown) {
            GuiUtils.drawQuad(topLeftPx, bottomRightPx, panelHitBackgroundColor);
        } else if (mouseOver || mouseOverChildComponent) {
            GuiUtils.drawQuad(topLeftPx, bottomRightPx, panelActiveBackgroundColor);
        } else {
            GuiUtils.drawQuad(topLeftPx, bottomRightPx, panelDefaultBackgroundColor);
        }

        if (bufferedImage != null) {
            try {
                if (image != null && unloadTextureWhenDone) {
                    image.release();
                }
                image = BufferedImageUtil.getTexture(null, bufferedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bufferedImage = null;
        }

        if (image != null) {
            GuiUtils.drawTexturedQuad(topLeftPx, bottomRightPx, image);
        }
    }

    /**
     * Set a method to be called whenever the component is clicked on
     *
     * @param clickAction the interface containing an execute() method to be called
     */
    public void setOnClickAction(UIAction clickAction) {
        this.onClickAction = clickAction;
    }

    /**
     * Set a method to be called every frame as long as the component is visible
     *
     * @param onUpdateAction the interface containing an execute() method to be called
     */
    public void setOnUpdateAction(UIAction onUpdateAction) {
        this.onUpdateAction = onUpdateAction;
    }

    /**
     * Set the default, active and hit background color of the UIComponent
     *
     * @param color The color which the background should be
     */
    public void setPanelBackgroundColor(UIColor color) {
        this.panelDefaultBackgroundColor = color;
        this.panelActiveBackgroundColor = color;
        this.panelHitBackgroundColor = color;
    }

    /**
     * Set the default background color (When it isn't selected) of the UIComponent
     *
     * @param color The color which the background should be
     */
    public void setPanelDefaultBackgroundColor(UIColor color) {
        this.panelDefaultBackgroundColor = color;
    }

    /**
     * Set the active background color (When it is selected / the mouse is over it) of the UIComponent
     *
     * @param color The color which the background should be
     */
    public void setPanelActiveBackgroundColor(UIColor color) {
        this.panelActiveBackgroundColor = color;
    }

    /**
     * Set the hit background color (When it is clicked on) of the UIComponent
     *
     * @param color The color which the background should be
     */
    public void setPanelHitBackgroundColor(UIColor color) {
        this.panelHitBackgroundColor = color;
    }

    /**
     * Set the visibility and interactivity of this component
     *
     * @param visibility Whether this component should be visible and interactable
     */
    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }

    /**
     * The method sets the active selected component
     *
     * @param componentID The ID of the component being selected
     */
    public void setSelectedComponent(Integer componentID) {
        if (selectedComponent != null) {
            childUiComponents.get(selectedComponent).selected = false;
        }
        selectedComponent = componentID;
        if (componentID != null) {
            childUiComponents.get(componentID).selected = true;
        }
    }

    /**
     * You shouldn't need to call this!<br>
     * When a new {@link UIComponent} is created, it will register itself
     *
     * @param componentID The ID of the component being registered
     * @param component The {@link UIComponent} being registered
     */
    protected void registerComponent(int componentID, UIComponent component) {
        childUiComponents.add(componentID, component);
    }

    /**
     * Calculates the pixel position from anchor points and regular points, as well as determine the width and height
     * of this component.
     */
    public void calcPos() {
        topLeftPx = new PosXY(
                parentComponent.width * topLeftAnchor.xPercent + topLeftPoint.x + parentComponent.topLeftPx.x + parentComponent.pointOffset.x,
                parentComponent.height * topLeftAnchor.yPercent + topLeftPoint.y + parentComponent.topLeftPx.y + parentComponent.pointOffset.y
        );

        bottomRightPx = new PosXY(
                parentComponent.width * bottomRightAnchor.xPercent + bottomRightPoint.x + parentComponent.topLeftPx.x + parentComponent.pointOffset.x,
                parentComponent.height * bottomRightAnchor.yPercent + bottomRightPoint.y + parentComponent.topLeftPx.y + parentComponent.pointOffset.y
        );

        width = bottomRightPx.x - topLeftPx.x;
        height = bottomRightPx.y - topLeftPx.y;
    }

    /**
     * @return True if the mouse of over this component, false otherwise
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * @return True if the mouse if over a child component, false otherwise
     */
    public boolean isMouseOverChildComponent() {
        return mouseOverChildComponent;
    }

    /**
     * Sets the top left anchor point.<br>
     * Anchor points are defined as a value from 0 to 1.<br>
     * X: 0 = Left of the screen | 1 = Right of the screen<br>
     * Y: 0 = Top of the screen | 1 = Bottom of the screen<br>
     *
     * @param topLeftAnchor The anchor point to set
     *
     */
    public void setTopLeftAnchor(AnchorPoint topLeftAnchor) {
        this.topLeftAnchor = topLeftAnchor;
    }

    /**
     * Sets the top left point.<br>
     * Points are defined as pixels relative to the corresponding anchor point.<br>
     * For example, an anchor point of (0.5, 0.5) with a point of (256, 256) will
     * result in the position of this to in the middle of the screen, across and down by 256 pixels.
     *
     * @param topLeftPoint The point to set
     *
     */
    public void setTopLeftPoint(PosXY topLeftPoint) {
        this.topLeftPoint = topLeftPoint;
    }

    /**
     * Sets the bottom right anchor point.<br>
     * Anchor points are defined as a value from 0 to 1.<br>
     * X: 0 = Left of the screen | 1 = Right of the screen<br>
     * Y: 0 = Top of the screen | 1 = Bottom of the screen<br>
     *
     * @param bottomRightAnchor The anchor point to set
     *
     */
    public void setBottomRightAnchor(AnchorPoint bottomRightAnchor) {
        this.bottomRightAnchor = bottomRightAnchor;
    }

    /**
     * Sets the bottom right point.<br>
     * Points are defined as pixels relative to the corresponding anchor point.<br>
     * For example, an anchor point of (0.5, 0.5) with a point of (256, 256) will
     * result in the position of this to in the middle of the screen, across and down by 256 pixels.
     *
     * @param bottomRightPoint The point to set
     *
     */
    public void setBottomRightPoint(PosXY bottomRightPoint) {
        this.bottomRightPoint = bottomRightPoint;
    }

    /**
     * Sets the background image for this component
     *
     * @param image The Slick texture to use as the image
     */
    public void setImage(Texture image) {
        this.image = image;
    }

    /**
     * Sets the background image for this component
     *
     * @param bufferedImage The buffered image to use as the image
     */
    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    /**
     * @param pointOffset Offset this component's position by pointOffset pixels
     */
    public void setPointOffset(PosXY pointOffset) {
        this.pointOffset = pointOffset;
    }

    /**
     * Should textures be unloaded when overwritten (using setBufferedImage)?
     */
    public void setUnloadTextureWhenDone(boolean unloadTextureWhenDone) {
        this.unloadTextureWhenDone = unloadTextureWhenDone;
    }

    /**
     * Find a child UI component by its name
     *
     * @param name The name of the child UI component to find
     * @return The UIComponent found, or null if it doesn't exist
     */
    public UIComponent getChildUiComponentByName(String name) {
        for (UIComponent component : childUiComponents) {
            if (Objects.equals(component.name, name)) {
                return component;
            }
        }

        return null;
    }
}
