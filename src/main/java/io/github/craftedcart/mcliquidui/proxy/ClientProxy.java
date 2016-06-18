package io.github.craftedcart.mcliquidui.proxy;

import io.github.craftedcart.mcliquidui.util.DependencyUtils;
import io.github.craftedcart.mcliquidui.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by CraftedCart on 17/11/2015 (DD/MM/YYYY)
 */
public class ClientProxy extends CommonProxy {

    private File depsPath = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), "mods/CraftedCart");

    @Override
    public void getDeps() throws IOException {
        super.getDeps();

        //Download Deps
        if (new File(depsPath, "slick-util.jar").exists()) {
            LogHelper.info("Found mods/CraftedCart/slick-util.jar - Minecraft LiquidUI won't download it");
        } else {
            LogHelper.info("Couldn't find mods/CraftedCart/slick-util.jar - Minecraft LiquidUI will now download the dependency from http://slick.ninjacave.com/slick-util.jar");
            DependencyUtils.downloadFileWithWindow("http://slick.ninjacave.com/slick-util.jar", new File(depsPath, "slick-util.jar"));
        }

        //Load Deps
        LaunchClassLoader loader = (LaunchClassLoader) DependencyUtils.class.getClassLoader();

        LogHelper.info("Loading slick-util.jar");
        loader.addURL(new File(depsPath, "slick-util.jar").toURI().toURL());

    }
}
