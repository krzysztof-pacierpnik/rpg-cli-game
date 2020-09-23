package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.MenuSection;
import com.perfectsoft.game.render.MenuRenderer;

public class CliMenuPrinter implements MenuRenderer {

    private final CliMenuPrinterService cliMenuPrinterService;
    private final String messageTitle;

    public CliMenuPrinter(CliMenuPrinterService cliMenuPrinterService, String messageTitle) {
        this.cliMenuPrinterService = cliMenuPrinterService;
        this.messageTitle = messageTitle;
    }

    @Override
    public void renderMenu(MenuSection<?> menuSection) {

        cliMenuPrinterService.printMenuWithMessage(menuSection, messageTitle);
    }

}
