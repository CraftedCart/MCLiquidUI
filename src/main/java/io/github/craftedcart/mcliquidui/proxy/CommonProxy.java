package io.github.craftedcart.mcliquidui.proxy;

import java.io.IOException;

/**
 * Created by CraftedCart on 17/11/2015 (DD/MM/YYYY)
 */
abstract class CommonProxy implements IProxy {

    @Override
    public void getDeps() throws IOException {
        //No-Op
    }

}
