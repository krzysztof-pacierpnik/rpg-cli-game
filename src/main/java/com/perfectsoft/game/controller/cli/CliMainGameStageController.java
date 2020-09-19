package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.plot.PlotStage;

public class CliMainGameStageController implements GameController {

    private Plot currentPlot;

    public void setCurrentPlot(Plot currentPlot) {
        this.currentPlot = currentPlot;
    }

    @Override
    public void run() {

        PlotStage currentStage = currentPlot.getCurrentStage();
        currentStage.run();
        do {
            GameController currentController = currentStage.getCurrentController();
            currentController.run();
        } while ( currentStage.isRunning());
    }

}
