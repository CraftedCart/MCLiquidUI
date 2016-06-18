package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;
import org.newdawn.slick.TrueTypeFont;

/**
 * Created by CraftedCart on 09/01/2016 (DD/MM/YYYY)
 */
public class UILabel extends UIComponent {

    /**
     * The font used to render text
     */
    public TrueTypeFont font;
    /**
     * The text displayed
     */
    public String text = "";
    /**
     * The color of the text
     */
    public UIColor textColor = UIColor.matGrey900();
    /**
     * The alignment of the text
     *
     * -1: Left<br>
     *  0: Center<br>
     *  1: Right<br>
     */
    public byte horizontalAlign = -1;

    /**
     * Create a new UILabel by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param font The font to use (For example: You can use {@link GuiUtils#font} which will use Roboto, size 16)
     */
    public UILabel(UIComponent parentComponent, String name, PosXY topLeftPoint,
                   AnchorPoint topLeftAnchor, TrueTypeFont font) {
        super(parentComponent, name, topLeftPoint, topLeftPoint,
                topLeftAnchor, topLeftAnchor);
        this.font = font;
        this.setPanelBackgroundColor(UIColor.transparent());
    }

    /**
     * This is called every frame
     */
    @Override
    protected void onUpdate() {
        if (visible) {
            super.onUpdate();
            int xPoint = 0;
            if (horizontalAlign == -1) { //Left align
                xPoint = (int) topLeftPx.x;
            } else if (horizontalAlign == 0) { //Center align
                xPoint = (int) (topLeftPx.x - font.getWidth(text) / 2);
            } else if (horizontalAlign == 1) {
                xPoint = (int) (topLeftPx.x - font.getWidth(text));
            }
            GuiUtils.drawString(font, xPoint, (int) topLeftPx.y, text, textColor);
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
     * Sets the color of the text
     *
     * @param textColor The color of the text
     */
    public void setTextColor(UIColor textColor) {
        this.textColor = textColor;
    }

    /**
     * Sets the text to be rendered
     *
     * @param text The text to be displayed
     */
    public void setText(String text) {
        this.text = text;
        bottomRightPoint = topLeftPoint.add(font.getWidth(text), font.getHeight());
    }

    /**
     * @param font The font to use when rendering text
     */
    public void setFont(TrueTypeFont font) {
        this.font = font;
        bottomRightPoint = topLeftPoint.add(font.getWidth(text), font.getHeight());
    }

    /**
     * -1: Left<br>
     *  0: Center<br>
     *  1: Right<br>
     *
     * @param horizontalAlign The horizontal alignment between -1 and 1
     */
    public void setHorizontalAlign(int horizontalAlign) {
        this.horizontalAlign = (byte) horizontalAlign;
    }

}
