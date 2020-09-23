package com.perfectsoft.game.controller.cli.conf;

import com.perfectsoft.game.controller.MenuSection;
import com.perfectsoft.game.controller.cli.*;

import java.util.List;
import java.util.Map;

public final class CliMenuConf {

    public static final String CREATE_CHARACTER = "CREATE_CHARACTER";
    public static final String LOAD_GAME = "LOAD_GAME";
    public static final String MAIN = "MAIN";

    private CliMenuConf() {}

    public static CliMenu<CliMainGameController> createMainMenu() {

        MenuSection<CliMainGameController> mainSection = new CliMenuChoiceSection<>((ctrl) -> {}, List.of(
                new CliMenuActionItem<>("c", "Create character",
                        ctrl -> ctrl.goToMenuSection(CREATE_CHARACTER)),
                new CliMenuActionItem<>("n", "New game",
                        CliMainGameController::startNewGame),
                new CliMenuActionItem<>("l", "Load game",
                        ctrl -> ctrl.goToMenuSection(LOAD_GAME)),
                new CliMenuActionItem<>("q", "Quit game",
                        CliMainGameController::quit)
        ));
        MenuSection<CliMainGameController> createCharacterSection = new MenuInputSection<>("Input character name:",
                ctr -> {if (ctr.createCharacter()) ctr.goToMenuSection(MAIN);});
        MenuSection<CliMainGameController> loadSection = new MenuInputSection<>("Input save name:",
                ctr -> {if (ctr.load()) ctr.goToMenuSection(MAIN);});

        return new CliMenu<>(mainSection, Map.of(MAIN, mainSection, CREATE_CHARACTER, createCharacterSection,
                LOAD_GAME, loadSection));
    }
    public static CliMenu<CliStageController> createStageMenu() {

        MenuSection<CliStageController> stageSection = new CliMenuChoiceSection<>(CliStageController::noop, List.of(
                new CliMenuActionItem<>("8", "Up", CliStageController::up),
                new CliMenuActionItem<>("5", "Down", CliStageController::down),
                new CliMenuActionItem<>("6", "Right", CliStageController::right),
                new CliMenuActionItem<>("4", "Left", CliStageController::left),
                new CliMenuActionItem<>("9", "Rotate clockwise", CliStageController::rotateClockwise),
                new CliMenuActionItem<>("7", "Rotate unclockwise", CliStageController::rotateUnclockwise),
                new CliMenuActionItem<>("a", "Attack", CliStageController::attack),
                new CliMenuActionItem<>("s", "Heal", CliStageController::heal),
                new CliMenuActionItem<>("w", "End turn", CliStageController::endTurn),
                new CliMenuActionItem<>("q", "Quit", CliStageController::quit)
        ));

        return new CliMenu<>(stageSection, Map.of("STAGE", stageSection));
    }

    public static MenuSection<CliPlotController> getEventMenuSection() {

        return new CliMenuChoiceSection<>((ctrl) -> {}, List.of(
                new CliMenuActionItem<>("q", "OK", CliPlotController::quit)
        ));
    }
}
