package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.controller.MenuSection;
import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.render.PlotRenderer;

import java.util.Scanner;
import java.util.function.Consumer;

public class CliPlotController implements GameController {

    private final PlotRenderer plotRenderer;
    private final MenuSection<CliPlotController> exitSection;
    private final Scanner scanner;

    private Plot plot;
    private boolean running;

    public CliPlotController(PlotRenderer plotRenderer, MenuSection<CliPlotController> exitSection, Scanner scanner) {
        this.plotRenderer = plotRenderer;
        this.exitSection = exitSection;
        this.scanner = scanner;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    @Override
    public void run() {

        if (plot.getCurrentStage().hasEventToShow()) {
            running = true;
            do {
                plotRenderer.renderPlotStage(plot.getCurrentStage(), exitSection);
                String input = scanner.next();
                Consumer<CliPlotController> cmd = exitSection.get(input);
                cmd.accept(this);
            } while (running);
        }
    }

    public void quit() {
        running = false;
    }

    @Override
    public void setInput(String input) {}
}
