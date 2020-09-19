package com.perfectsoft.game.render.cli.conf;

import com.perfectsoft.game.render.MenuRenderer;
import com.perfectsoft.game.render.cli.MenuPrinter;

public final class CliRenderConf {

    private CliRenderConf() {}

    public static MenuRenderer createMenuRenderer() {
        return new MenuPrinter();
    }
}
