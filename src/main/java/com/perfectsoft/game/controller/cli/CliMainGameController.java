package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.conf.PlotConf;
import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.actions.PlotActionFactory;
import com.perfectsoft.game.render.MenuRenderer;

import java.util.Scanner;
import java.util.function.Consumer;

public class CliMainGameController implements GameController {

    private final PlotActionChannel plotActionChannel;
    private final CliMenu<CliMainGameController> cliMenu;
    private final MenuRenderer menuRenderer;
    private final CliMainStageController stageController;
    private final CliPlotController cliPlotController;
    private final CliMainStageController cliMainStageController;
    private final CliStageController cliStageController;
    private final PlotConf plotConf;
    private final PlotActionFactory plotActionFactory;
    private final Scanner scanner;

    private Plot plot;
    private boolean runGame;
    private String input;

    public CliMainGameController(PlotActionChannel plotActionChannel,
                                 CliMenu<CliMainGameController> cliMenu, MenuRenderer menuRenderer,
                                 CliMainStageController stageController, CliPlotController cliPlotController,
                                 CliMainStageController cliMainStageController, CliStageController cliStageController,
                                 PlotConf plotConf, PlotActionFactory plotActionFactory, Scanner scanner) {

        this.plotActionChannel = plotActionChannel;
        this.cliMenu = cliMenu;
        this.menuRenderer = menuRenderer;
        this.stageController = stageController;
        this.cliPlotController = cliPlotController;
        this.cliMainStageController = cliMainStageController;
        this.cliStageController = cliStageController;
        this.plotConf = plotConf;
        this.plotActionFactory = plotActionFactory;
        this.scanner = scanner;

        this.runGame = true;
    }

    @Override
    public void run() {
        do {
            menuRenderer.renderMenu(cliMenu.getCurrentSection());
            String input = scanner.next();
            Consumer<CliMainGameController> cmd = cliMenu.get(input);
            cmd.accept(this);
        } while (runGame);
    }

    public boolean createCharacter() {

        boolean success = false;
        try {
            //create plot
            plot = plotConf.createPhysicsAndPlot(plotActionChannel, plotActionFactory, cliStageController);
            //set plot on controllers and
            cliStageController.setPlot(plot);
            cliPlotController.setPlot(plot);
            cliMainStageController.setPlot(plot);
            stageController.setPlot(plot);
            //set hero name
            plot.getPlotHero().setName(input);
            success = true;
        } catch (Exception e) {

            cliMenu.getCurrentSection().setMessage(String.format("Could not create character: %s", e.getMessage()));
        }
        return success;
    }

    public void startNewGame() {

        if (plot.getPlotHero().getName() != null) {

            plot.nextStage();
            stageController.run();
        } else {

            cliMenu.getCurrentSection().setMessage("Character is not created.");
        }
    }

    public boolean load() {

        boolean success = false;
        String saveName = input;

        try {
            plot = plot.load(saveName);
            stageController.setPlot(plot);
            stageController.run();
            success = true;
        } catch (Exception e) {

            cliMenu.getCurrentSection().setMessage(String.format("Could not create character: %s", e.getMessage()));
        }
        return success;
    }

    public void goToMenuSection(String sectionName) {
        cliMenu.setCurrentSection(sectionName);
    }

    public void quit() {
        runGame = false;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public void setPlot(Plot plot) {
        this.plot = plot;
    }
}
