package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.GuiUtils;
import io.github.craftedcart.mcliquidui.util.PosXY;
import io.github.craftedcart.mcliquidui.util.UIColor;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by CraftedCart on 13/01/2016 (DD/MM/YYYY)
 */
public class UILineGraph extends UIComponent {

    protected List<Double> graphPoints = new ArrayList<Double>();
    protected UIColor frameColor = UIColor.matGrey300();
    protected double graphWidth;
    private double leftBarWidth = 200;
    private double bottomBarHeight = 24;
    protected UILabel topLabel;
    protected UILabel bottomLabel;
    protected UILabel leftLabel;
    protected UILabel rightLabel;
    protected String yAxisLabelPrefix = "";
    protected String yAxisLabelSuffix = "";
    protected String xAxisLabelPrefix = "";
    protected String xAxisLabelSuffix = "";
    protected String leftLabelOverride;
    protected String rightLabelOverride;
    protected double xAxisMinValue = 0;
    protected double xAxisMaxValue = 0;
    protected int yAxisLabelDecimalPlaces = 2;
    protected int xAxisLabelDecimalPlaces = 2;

    /**
     * Create a new UILineGraph by calling this<br>
     * The component will automatically register itself with the parentComponent provided
     *
     * @param parentComponent The {@link UIComponent} which the component will get registered to
     * @param name The name of the component
     * @param topLeftPoint The top left point of the component
     * @param bottomRightPoint The bottom right point of the component
     * @param topLeftAnchor The top left anchor point of the component
     * @param bottomRightAnchor The bottom right anchor point of the component
     */
    public UILineGraph(UIComponent parentComponent, String name, PosXY topLeftPoint, PosXY bottomRightPoint,
                       AnchorPoint topLeftAnchor, AnchorPoint bottomRightAnchor) {
        super(parentComponent, name, topLeftPoint, bottomRightPoint,
                topLeftAnchor, bottomRightAnchor);
        setPanelBackgroundColor(UIColor.transparent());

        topLabel = new UILabel(this,
                "topLabel",
                new PosXY(0, 0),
                new AnchorPoint(0, 0),
                GuiUtils.font);
        bottomLabel = new UILabel(this,
                "bottomLabel",
                new PosXY(0, -16 - bottomBarHeight),
                new AnchorPoint(0, 1),
                GuiUtils.font);
        leftLabel = new UILabel(this,
                "leftLabel",
                new PosXY(leftBarWidth, -16),
                new AnchorPoint(0, 1),
                GuiUtils.font);
        rightLabel = new UILabel(this,
                "rightLabel",
                new PosXY(width, -16),
                new AnchorPoint(1, 1),
                GuiUtils.font);
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
        super.draw();

        graphWidth = width - leftBarWidth;

        double highestValue;
        double lowestValue;

        if (graphPoints.size() > 0) {
            highestValue = Collections.max(graphPoints);
            lowestValue = Collections.min(graphPoints);
        } else {
            highestValue = 0;
            lowestValue = 0;
        }

        //<editor-fold desc="Draw graph frame">
        GuiUtils.drawLine(topLeftPx.add(leftBarWidth, 0),
                new PosXY(topLeftPx.x + leftBarWidth, bottomRightPx.y - bottomBarHeight),
                frameColor);
        GuiUtils.drawLine(new PosXY(topLeftPx.x + leftBarWidth, bottomRightPx.y - bottomBarHeight),
                bottomRightPx.add(0, -bottomBarHeight),
                frameColor);
        //</editor-fold>

        //<editor-fold desc="Draw Graph">
        GL11.glColor4d(UIColor.matGrey900().r, UIColor.matGrey900().g, UIColor.matGrey900().b, 1);
        GL11.glLineWidth(2f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            for (int i = 0; i < graphPoints.size(); i++) {
                double percentageHeightOnGraph;

                if (highestValue - lowestValue == 0) {
                    percentageHeightOnGraph = 1;
                } else {
                    percentageHeightOnGraph = (graphPoints.get(i) - lowestValue) / (highestValue - lowestValue);
                }

                GL11.glVertex2d(graphWidth / graphPoints.size() * i + topLeftPx.x + leftBarWidth, topLeftPx.y + (height - bottomBarHeight) * (1 - percentageHeightOnGraph));
            }
        }
        GL11.glEnd();
        //</editor-fold>

        topLabel.setText(String.format("%s %." + String.valueOf(yAxisLabelDecimalPlaces) + "f %s", yAxisLabelPrefix, highestValue, yAxisLabelSuffix));
        bottomLabel.setText(String.format("%s %." + String.valueOf(yAxisLabelDecimalPlaces) + "f %s", yAxisLabelPrefix, lowestValue, yAxisLabelSuffix));

        if (leftLabelOverride == null) {
            leftLabel.setText(String.format("%s %." + String.valueOf(xAxisLabelDecimalPlaces) + "f %s", xAxisLabelPrefix, xAxisMinValue, xAxisLabelSuffix));
        } else {
            leftLabel.setText(leftLabelOverride);
        }

        if (rightLabelOverride == null) {
            rightLabel.setText(String.format("%s %." + String.valueOf(xAxisLabelDecimalPlaces) + "f %s", xAxisLabelPrefix, xAxisMaxValue, xAxisLabelSuffix));
            rightLabel.setTopLeftPoint(new PosXY(
                    -rightLabel.font.getWidth(String.format("%s %." + String.valueOf(xAxisLabelDecimalPlaces) + "f %s", xAxisLabelPrefix, xAxisMaxValue, xAxisLabelSuffix)),
                    -16));
        } else {
            rightLabel.setText(rightLabelOverride);
            rightLabel.setTopLeftPoint(new PosXY(
                    -rightLabel.font.getWidth(rightLabelOverride),
                    -16));
        }

    }

    /**
     * @param leftBarWidth The width to use for labeling the Y axis
     */
    public void setLeftBarWidth(double leftBarWidth) {
        this.leftBarWidth = leftBarWidth;
    }

    /**
     * @return The width used for labeling the Y axis
     */
    public double getLeftBarWidth() {
        return leftBarWidth;
    }

    /**
     * @param bottomBarHeight The height to use for labeling the X axis
     */
    public void setBottomBarHeight(double bottomBarHeight) {
        this.bottomBarHeight = bottomBarHeight;
        bottomLabel.setTopLeftPoint(new PosXY(0, -bottomLabel.font.getHeight() - bottomBarHeight));
    }

    /**
     * @return The height used for labeling the X axis
     */
    public double getBottomBarHeight() {
        return bottomBarHeight;
    }

    /**
     * @param graphPoints A list of doubles to plot on the graph
     */
    public void setGraphPoints(List<Double> graphPoints) {
        this.graphPoints = graphPoints;
    }

    /**
     * @param yAxisLabelPrefix The prefix to use when labeling the Y axis
     */
    public void setYAxisLabelPrefix(String yAxisLabelPrefix) {
        this.yAxisLabelPrefix = yAxisLabelPrefix;
    }

    /**
     * @param yAxisLabelSuffix The suffix to use when labeling the Y axis
     */
    public void setYAxisLabelSuffix(String yAxisLabelSuffix) {
        this.yAxisLabelSuffix = yAxisLabelSuffix;
    }

    /**
     * @param yAxisLabelDecimalPlaces The amount of decimal places to round off values when labeling the Y axis
     */
    public void setYAxisLabelDecimalPlaces(int yAxisLabelDecimalPlaces) {
        this.yAxisLabelDecimalPlaces = yAxisLabelDecimalPlaces;
    }

    /**
     * @param xAxisLabelPrefix The prefix to use when labeling the X axis
     */
    public void setXAxisLabelPrefix(String xAxisLabelPrefix) {
        this.xAxisLabelPrefix = xAxisLabelPrefix;
    }

    /**
     * @param xAxisLabelSuffix The suffix to use when labeling the X axis
     */
    public void setXAxisLabelSuffix(String xAxisLabelSuffix) {
        this.xAxisLabelSuffix = xAxisLabelSuffix;
    }

    /**
     * @param xAxisLabelDecimalPlaces The amount of decimal places to round off values when labeling the X axis
     */
    public void setXAxisLabelDecimalPlaces(int xAxisLabelDecimalPlaces) {
        this.xAxisLabelDecimalPlaces = xAxisLabelDecimalPlaces;
    }

    /**
     * @param leftLabelOverride A string to override automatic labeling of the left label on the X axis
     */
    public void setLeftLabelOverride(String leftLabelOverride) {
        this.leftLabelOverride = leftLabelOverride;
    }

    /**
     * @param rightLabelOverride A string to override automatic labeling of the right label on the X axis
     */
    public void setRightLabelOverride(String rightLabelOverride) {
        this.rightLabelOverride = rightLabelOverride;
    }

    /**
     * @param xAxisMinValue The minimum value on the X axis to use for automatic labeling (on the left)
     */
    public void setXAxisMinValue(double xAxisMinValue) {
        this.xAxisMinValue = xAxisMinValue;
    }

    /**
     * @param xAxisMaxValue The maximum value on the X axis to use for automatic labeling (on the right)
     */
    public void setXAxisMaxValue(double xAxisMaxValue) {
        this.xAxisMaxValue = xAxisMaxValue;
    }
}
