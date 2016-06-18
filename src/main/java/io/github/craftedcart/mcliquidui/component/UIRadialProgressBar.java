package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;
import org.lwjgl.opengl.GL11;

/**
 * Created by CraftedCart on 07/02/2016 (DD/MM/YYYY)
 */
public class UIRadialProgressBar extends UIComponent {

    /**
     * The progress to be displayed by the progress bar (A value between 0 - 1)
     */
    protected double progress = 0;
    /**
     * The thickness of the circle outline
     */
    protected float thickness = 4;
    /**
     * The background color of the progress bar
     */
    protected UIColor radialBackgroundColor = UIColor.matGrey900();
    /**
     * The color of the bar in the progress bar
     */
    protected UIColor radialForegroundColor = UIColor.matBlue();
    /**
     * This is in degrees
     */
    protected double rotationalOffset = 0;

    /**
     * Create a new UIRadialProgressBar by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UIRadialProgressBar(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                               AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        setPanelBackgroundColor(UIColor.transparent());
    }

    /**
     * This is called every frame
     */
    @Override
    protected void onUpdate() {
        super.onUpdate();

        if (visible) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(thickness);

            //<editor-fold desc="Draw foreground circle">
            GL11.glColor4d(radialForegroundColor.r, radialForegroundColor.g, radialForegroundColor.b, radialForegroundColor.a);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            {
                for (double i = 0; i < 361 * progress; i += 0.2) {
                    GL11.glVertex2d(
                            ((topLeftPx.x + bottomRightPx.x) / 2) + ((Math.min(bottomRightPx.x - topLeftPx.x, bottomRightPx.y - topLeftPx.y) - thickness) / 2) * Math.cos(Math.toRadians(i - 90 + rotationalOffset)),
                            ((topLeftPx.y + bottomRightPx.y) / 2) + ((Math.min(bottomRightPx.x - topLeftPx.x, bottomRightPx.y - topLeftPx.y) - thickness) / 2) * Math.sin(Math.toRadians(i - 90 + rotationalOffset))
                    );
                }
            }
            GL11.glEnd();
            //</editor-fold>

            //<editor-fold desc="Draw background circle">
            GL11.glColor4d(radialBackgroundColor.r, radialBackgroundColor.g, radialBackgroundColor.b, radialBackgroundColor.a);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            {
                for (double i = (361 * progress) - 0.2; i < 361; i += 0.2) {
                    GL11.glVertex2d(
                            ((topLeftPx.x + bottomRightPx.x) / 2) + ((Math.min(bottomRightPx.x - topLeftPx.x, bottomRightPx.y - topLeftPx.y) - thickness) / 2) * Math.cos(Math.toRadians(i - 90 + rotationalOffset)),
                            ((topLeftPx.y + bottomRightPx.y) / 2) + ((Math.min(bottomRightPx.x - topLeftPx.x, bottomRightPx.y - topLeftPx.y) - thickness) / 2) * Math.sin(Math.toRadians(i - 90 + rotationalOffset))
                    );
                }
            }
            GL11.glEnd();
            //</editor-fold>

            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }
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
    }

    /**
     * @return The progress displayed by the progress bar (A value between 0 - 1)
     */
    public double getProgress() {
        return progress;
    }

    /**
     * @param thickness The thickness of the circle outline
     */
    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    /**
     * @return The thickness of the circle outline
     */
    public float getThickness() {
        return thickness;
    }

    /**
     * @param radialBackgroundColor The background color of the progress bar
     */
    public void setRadialBackgroundColor(UIColor radialBackgroundColor) {
        this.radialBackgroundColor = radialBackgroundColor;
    }

    /**
     * @return The background color of the progress bar
     */
    public UIColor getRadialBackgroundColor() {
        return radialBackgroundColor;
    }

    /**
     * @param radialForegroundColor The color of the bar in the progress bar
     */
    public void setRadialForegroundColor(UIColor radialForegroundColor) {
        this.radialForegroundColor = radialForegroundColor;
    }

    /**
     * @return The color of the bar in the progress bar
     */
    public UIColor getRadialForegroundColor() {
        return radialForegroundColor;
    }

    /**
     * @param rotationalOffset The degrees to rotate the progress bar by
     */
    public void setRotationalOffset(double rotationalOffset) {
        this.rotationalOffset = rotationalOffset;
    }

    /**
     * @return The degrees that the progress bar has been rotated by
     */
    public double getRotationalOffset() {
        return rotationalOffset;
    }

}
