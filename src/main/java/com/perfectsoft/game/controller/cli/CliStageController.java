package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.actions.ActionFactory;

import java.util.Scanner;

public class CliStageController implements GameController {

    private final Scanner scanner;
    private final PlotActionChannel plotActionChannel;

    private Plot plot;

    public CliStageController(Scanner scanner, PlotActionChannel plotActionChannel) {
        this.scanner = scanner;
        this.plotActionChannel = plotActionChannel;
    }

    @Override
    public void run() {
        //TODO
        plotActionChannel.publish(ActionFactory.getInstance().heroDiedAction());
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    @Override
    public void setInput(String input) {}
}
