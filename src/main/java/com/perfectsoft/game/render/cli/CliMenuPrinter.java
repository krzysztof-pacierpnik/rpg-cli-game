package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenuSection;
import com.perfectsoft.game.render.MenuRenderer;

public class CliMenuPrinter implements MenuRenderer {

    private final CliMenuPrinterService cliMenuPrinterService;
    private final String messageTitle;

    public CliMenuPrinter(CliMenuPrinterService cliMenuPrinterService, String messageTitle) {
        this.cliMenuPrinterService = cliMenuPrinterService;
        this.messageTitle = messageTitle;
    }

    @Override
    public void renderMenu(CliMenuSection<?> cliMenuSection) {

        cliMenuPrinterService.printMenuWithMessage(cliMenuSection, messageTitle);
    }

}
