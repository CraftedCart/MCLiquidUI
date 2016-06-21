package io.github.craftedcart.mcliquidui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by CraftedCart on 29/12/2015 (DD/MM/YYYY)
 */
public class GuiUtils {

    public static TrueTypeFont font;
    public static TrueTypeFont debugFont;
    private static long lastFrame = 0;
    private static long delta = 0;
    public static boolean debugEnabled = false;
    public static int debugSidebarWidth = 256;
    public static Sequencer sequencer;
    private static int mouseDWheel = 0;

    public static void init() throws FontFormatException, IOException {
        //Get the Roboto font InputStream
        InputStream inputStream	= Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("mcliquidui:Roboto-Regular.ttf")).getInputStream();

        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream); //Regular Font
        awtFont = awtFont.deriveFont(16f); //Set font size
        font = new TrueTypeFont(awtFont, true);
        inputStream.close();

        //Get the Roboto font InputStream again
        inputStream	= Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("mcliquidui:Roboto-Regular.ttf")).getInputStream();
        Font awtFontDebug = Font.createFont(Font.TRUETYPE_FONT, inputStream); //Debug Font
        awtFontDebug = awtFontDebug.deriveFont(12f); //Set font size
        debugFont = new TrueTypeFont(awtFontDebug, true);
        inputStream.close();

        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The delta time in seconds (Time since the last frame)
     */
    public static double getDelta() {
        return delta / 1000d;
    }

    /**
     * Calculate the new delta time at the time of calling compared to the old one
     * You shouldn't need to call this! A {@link UIDisplay} will do it for you!
     *
     * @param isFirstFrame Is this the first frame of the {@link UIDisplay} being drawn?
     */
    public static void calcDelta(boolean isFirstFrame) {
        long time = Sys.getTime();
        if (!isFirstFrame) {
            delta = (time - lastFrame);
        } else {
            delta = 0;
        }
        lastFrame = time;
    }

    /**
     * Calculate the new mouse scroll distance at the time of calling compared to the old one
     * You shouldn't need to call this! A {@link UIDisplay} will do it for you!
     */
    public static void calcMouseDWheel() {
        mouseDWheel = Mouse.getDWheel();
    }

    /**
     * @return The scroll distance since the last frame
     */
    public static int getMouseDWheel() {
        return mouseDWheel;
    }

    /**
     * Draws a quadrilateral given four points and a color
     *
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     * @param p4 Point 4
     * @param col The color
     */
    public static void drawQuad(PosXY p1, PosXY p2, PosXY p3, PosXY p4, UIColor col) {
        GL11.glColor4d(col.r, col.g, col.b, col.a);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
            GL11.glVertex2d(p3.x, p3.y);
            GL11.glVertex2d(p4.x, p4.y);
        }
        GL11.glEnd();
    }

    /**
     * Draws a quadrilateral given four points<br>
     * This will use the currently bound color in OpenGL
     *
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     * @param p4 Point 4
     */
    public static void drawQuad(PosXY p1, PosXY p2, PosXY p3, PosXY p4) {
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
            GL11.glVertex2d(p3.x, p3.y);
            GL11.glVertex2d(p4.x, p4.y);
        }
        GL11.glEnd();
    }

    /**
     * Draws a quadrilateral given four points which fades between two colors
     *
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     * @param p4 Point 4
     * @param colFrom The color used on p1 and p2
     * @param colTo The color used on p3 and p4
     */
    public static void drawQuadGradient(PosXY p1, PosXY p2, PosXY p3, PosXY p4, UIColor colFrom, UIColor colTo) {
        GL11.glColor4d(colFrom.r, colFrom.g, colFrom.b, colFrom.a);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
            GL11.glColor4d(colTo.r, colTo.g, colTo.b, colTo.a);
            GL11.glVertex2d(p3.x, p3.y);
            GL11.glVertex2d(p4.x, p4.y);
        }
        GL11.glEnd();
    }

    /**
     * Draws a quadrilateral given two points and a color<br>
     * This will use the currently bound color in OpenGL
     *
     * @param p1 Point 1: The top left point
     * @param p2 Point 2: The bottom right point
     * @param col The color
     */
    public static void drawQuad(PosXY p1, PosXY p2, UIColor col) {
        drawQuad(
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                new PosXY(p2.x, p1.y),
                col
        );
    }

    /**
     * Draws a quadrilateral given two points
     *
     * @param p1 Point 1: The top left point
     * @param p2 Point 2: The bottom right point
     */
    public static void drawQuad(PosXY p1, PosXY p2) {
        drawQuad(
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                new PosXY(p2.x, p1.y)
        );
    }

    /**
     * Draws a quadrilateral given two points and a Slick texture<br>
     * This will bind white as the OpenGL color
     *
     * @param p1 Point 1: The top left point
     * @param p2 Point 2: The bottom right point
     * @param texture The texture to draw over the quad
     */
    public static void drawTexturedQuad(PosXY p1, PosXY p2, Texture texture) {
        Color.white.bind();
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glTexCoord2d(0, texture.getHeight());
            GL11.glVertex2d(p1.x, p2.y);
            GL11.glTexCoord2d(texture.getWidth(), texture.getHeight());
            GL11.glVertex2d(p2.x, p2.y);
            GL11.glTexCoord2d(texture.getWidth(), 0);
            GL11.glVertex2d(p2.x, p1.y);
        }
        GL11.glEnd();
    }

    /**
     * Draws a quadrilateral given two points which fades horizontally between two colors
     *
     * @param p1 Point 1: The top left point
     * @param p2 Point 2: The bottom right point
     * @param colFrom The color used on the left
     * @param colTo The color used on the right
     */
    public static void drawQuadGradientHorizontal(PosXY p1, PosXY p2, UIColor colFrom, UIColor colTo) {
        drawQuadGradient(
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                new PosXY(p2.x, p1.y),
                colFrom, colTo
        );
    }

    /**
     * Draws a quadrilateral given two points which fades vertically between two colors
     *
     * @param p1 Point 1: The top left point
     * @param p2 Point 2: The bottom right point
     * @param colFrom The color used on the top
     * @param colTo The color used on the bottom
     */
    public static void drawQuadGradientVertical(PosXY p1, PosXY p2, UIColor colFrom, UIColor colTo) {
        drawQuadGradient(
                new PosXY(p2.x, p1.y),
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                colFrom, colTo
        );
    }

    /**
     * Sets the projection to an orthographic one<br>
     * Optionally clears the color and depth buffers<br>
     * Enables blending<br>
     * Disables Texture2D<br>
     * Sets BlendFuncSeparate<br>
     * Sets the OpenGL bound color to pure white<br>
     * Unbinds any bound Texture2Ds
     *
     * @param clearBuffer Should the color and depth buffers be cleared?
     */
    public static void setup(boolean clearBuffer) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 10000, -10000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        if (clearBuffer) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        }

        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1, 1, 1, 1);
        TextureImpl.bindNone();
    }

    /**
     * Draws some text given a font, a position, a string and a color
     *
     * @param font The font
     * @param x The X position (relative to the top left corner of the screen) to draw text at
     * @param y The Y position (relative to the top left corner of the screen) to draw text at
     * @param string The string of text to draw
     * @param col The color of the text
     */
    public static void drawString(org.newdawn.slick.Font font, int x, int y, String string, UIColor col) {
        GlStateManager.enableTexture2D();
        font.drawString(x, y, string, new Color((float) col.r, (float) col.g, (float) col.b, (float) col.a));
        GlStateManager.disableTexture2D();
    }

    /**
     * Draws a line given two points and a color using GL_LINES
     *
     * @param p1 Point 1
     * @param p2 Point 2
     * @param col The color of the line
     */
    public static void drawLine(PosXY p1, PosXY p2, UIColor col) {
        GL11.glColor4d(col.r, col.g, col.b, col.a);
        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
        }
        GL11.glEnd();
    }

    /**
     * @param drawStencilShape Executed to draw the shape / mask of the stencil
     * @param drawScene Executed to draw the scene that will be obscured by the stencil
     */
    public static void drawWithStencil(UIAction drawStencilShape, UIAction drawScene) {
        setupStencilMask();
        drawStencilShape.execute();
        setupStencilDraw();
        drawScene.execute();
        setupStencilEnd();
    }

    /**
     * Call before drawing the shape / mask of the stencil
     */
    public static void setupStencilMask() {
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glColorMask(false, false, false, false);
        GL11.glDepthMask(false);
        GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);  // draw 1s on test fail (always)

        // draw stencil pattern
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);  // needs mask=0xFF
    }

    /**
     * Call before drawing a scene using a stencil
     */
    public static void setupStencilDraw() {
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilMask(0x00);
        // draw where stencil's value is 0
        GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFF);
        /* (nothing to draw) */
        // draw only where stencil's value is 1
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
    }

    /**
     * Disables GL_STENCIL_TEST
     */
    public static void setupStencilEnd() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

}
