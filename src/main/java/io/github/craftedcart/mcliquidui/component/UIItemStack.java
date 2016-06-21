package io.github.craftedcart.mcliquidui.component;

import io.github.craftedcart.mcliquidui.util.AnchorPoint;
import io.github.craftedcart.mcliquidui.util.PosXY;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureImpl;

/**
 * Created by CraftedCart on 20/06/2016 (DD/MM/YYYY)
 */
public class UIItemStack extends UIComponent {

    public ItemStack itemStack;
    public double scale = 1;

    public UIItemStack(UIComponent parentComponent, String name, PosXY topLeftPoint,
                       AnchorPoint topLeftAnchor) {
        super(parentComponent, name, topLeftPoint, topLeftPoint,
                topLeftAnchor, topLeftAnchor);
    }

    @Override
    protected void draw() {
        super.draw();

        if (itemStack != null) {
            GlStateManager.enableTexture2D();

            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();

            RenderHelper.enableGUIStandardItemLighting();
            //Binding the missing texture causes it to work? Wha? Well, I guess I won't complain...
            textureManager.bindTexture(TextureMap.LOCATION_MISSING_TEXTURE);

            GL11.glPushMatrix();
            GL11.glScaled(scale, scale, scale);

            //TODO: Item layering seems to be wrong (Eg: The bottom part of stairs is rendered on top of the top part)

            renderItem.renderItemAndEffectIntoGUI(itemStack, (int) (topLeftPx.x / scale), (int) (topLeftPx.y / scale));
            renderItem.renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, itemStack, (int) (topLeftPx.x / scale), (int) (topLeftPx.y / scale));

            GL11.glPopMatrix();

            TextureImpl.bindNone();
            GlStateManager.disableTexture2D();
        }
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

}
