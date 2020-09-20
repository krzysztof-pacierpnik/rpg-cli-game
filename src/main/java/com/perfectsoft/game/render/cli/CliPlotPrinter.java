package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenuSection;
import com.perfectsoft.game.plot.PlotStage;
import com.perfectsoft.game.render.PlotRenderer;

public class CliPlotPrinter implements PlotRenderer {

    private final CliMenuPrinterService cliMenuPrinterService;
    private final String messageTitle;

    public CliPlotPrinter(CliMenuPrinterService cliMenuPrinterService, String messageTitle) {
        this.cliMenuPrinterService = cliMenuPrinterService;
        this.messageTitle = messageTitle;
    }

    @Override
    public void renderPlotStage(PlotStage plotStage, CliMenuSection<?> cliMenuSection) {

        plotStage.removeEventToShow().ifPresent(plotEvent -> {
            cliMenuSection.setMessage(plotEvent.getStory());
            cliMenuPrinterService.printMenuWithMessage(cliMenuSection, messageTitle);
        });
    }
}
