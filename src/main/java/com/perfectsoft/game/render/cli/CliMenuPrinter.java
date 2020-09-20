package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenuSection;
import com.perfectsoft.game.render.MenuRenderer;

public class CliMenuPrinter implements MenuRenderer {

    private final String messageTitle;

    public CliMenuPrinter(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    @Override
    public void renderMenu(CliMenuSection<?> cliMenuSection) {

        CliUtils.printMenuWithMessage(cliMenuSection, messageTitle);
    }

}
