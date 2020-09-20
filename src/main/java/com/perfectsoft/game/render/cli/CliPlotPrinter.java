package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenuSection;
import com.perfectsoft.game.plot.PlotStage;
import com.perfectsoft.game.render.PlotRenderer;

public class CliPlotPrinter implements PlotRenderer {

    private final String messageTitle;

    public CliPlotPrinter(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    @Override
    public void renderPlotStage(PlotStage plotStage, CliMenuSection<?> cliMenuSection) {

        plotStage.removeEventToShow().ifPresent(plotEvent -> {
            cliMenuSection.setMessage(plotEvent.getStory());
            CliUtils.printMenuWithMessage(cliMenuSection, messageTitle);
        });
    }
}
