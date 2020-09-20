package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.PlotStage;

public class CliMainStageController implements GameController {

    private final PlotActionChannel plotActionChannel;
    private final GameController plotController;

    private Plot plot;

    public CliMainStageController(PlotActionChannel plotActionChannel, GameController plotController) {
        this.plotActionChannel = plotActionChannel;
        this.plotController = plotController;
    }

    @Override
    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    @Override
    public void run() {

        PlotStage currentStage = plot.getCurrentStage();
        currentStage.run();
        plotController.run();

        while (currentStage.isRunning()) {

            GameController currentController = currentStage.getCurrentController();
            currentController.run();
            plotActionChannel.executeActions();
            plotController.run();
        }
    }

    @Override
    public void setInput(String input) {}
}
