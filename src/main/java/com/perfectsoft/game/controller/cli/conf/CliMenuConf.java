package com.perfectsoft.game.controller.cli.conf;

import com.perfectsoft.game.controller.cli.*;

import java.util.List;
import java.util.Map;

public final class CliMenuConf {

    public static final String CREATE_CHARACTER = "CREATE_CHARACTER";
    public static final String LOAD_GAME = "LOAD_GAME";
    public static final String MAIN = "MAIN";

    private CliMenuConf() {}

    public static CliMenu<CliMainGameController> createMainMenu() {

        CliMenuSection<CliMainGameController> mainSection = new CliMenuChoiceSection<>(List.of(
                new CliMenuActionItem<>("c", "Create character",
                        ctrl -> ctrl.goToMenuSection(CREATE_CHARACTER)),
                new CliMenuActionItem<>("n", "New game",
                        CliMainGameController::startNewGame),
                new CliMenuActionItem<>("l", "Load game",
                        ctrl -> ctrl.goToMenuSection(LOAD_GAME)),
                new CliMenuActionItem<>("q", "Quit game",
                        CliMainGameController::quit)
        ));
        CliMenuSection<CliMainGameController> createCharacterSection = new CliMenuInputSection<>("Input character name:",
                ctr -> {if (ctr.createCharacter()) ctr.goToMenuSection(MAIN);});
        CliMenuSection<CliMainGameController> loadSection = new CliMenuInputSection<>("Input save name:",
                ctr -> {if (ctr.load()) ctr.goToMenuSection(MAIN);});

        return new CliMenu<>(mainSection, Map.of(MAIN, mainSection, CREATE_CHARACTER, createCharacterSection,
                LOAD_GAME, loadSection));
    }

    public static CliMenuSection<CliPlotController> getEventMenuSection() {

        return new CliMenuChoiceSection<>(List.of(
                new CliMenuActionItem<>("q", "OK", CliPlotController::quit)
        ));
    }
}
