package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.render.MenuRenderer;

import java.util.Scanner;
import java.util.function.Consumer;

public class CliMainGameController implements GameController {

    private final CliMenu cliMenu;
    private final MenuRenderer menuRenderer;
    private final CliMainGameStageController stageController;

    private Plot plot;
    private boolean runGame;
    private String input;

    public CliMainGameController(CliMenu cliMenu, MenuRenderer menuRenderer, CliMainGameStageController stageController,
                                 Plot plot) {
        this.cliMenu = cliMenu;
        this.menuRenderer = menuRenderer;
        this.stageController = stageController;
        this.plot = plot;

        this.runGame = true;
    }

    @Override
    public void run() {
        do {
            menuRenderer.renderMenu(cliMenu.getCurrentSection());
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            Consumer<CliMainGameController> cmd = cliMenu.get(input);
            cmd.accept(this);
        } while (runGame);
    }

    public boolean createCharacter() {

        boolean success = false;
        try {
            plot.getPlotHero().setName(input);
            success = true;
        } catch (Exception e) {

            cliMenu.getCurrentSection().setErrorMessage(String.format("Could not create character: %s", e.getMessage()));
        }
        return success;
    }

    public void startNewGame() {

        if (plot.getPlotHero().getName() != null) {

            stageController.setCurrentPlot(plot);
            stageController.run();
        } else {

            cliMenu.getCurrentSection().setErrorMessage("Character is not created.");
        }
    }

    public boolean load() {

        boolean success = false;
        String saveName = input;

        try {
            plot = plot.load(saveName);
            stageController.setCurrentPlot(plot);
            stageController.run();
            success = true;
        } catch (Exception e) {

            cliMenu.getCurrentSection().setErrorMessage(String.format("Could not create character: %s", e.getMessage()));
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
}
