package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.RotationDirection;
import com.perfectsoft.game.plot.Plot;

import java.util.Scanner;
import java.util.function.Consumer;

public class CliStageController implements GameController {

    private final Scanner scanner;
    private final CliMenu<CliStageController> stageMenu;

    private Plot plot;

    public CliStageController(Scanner scanner, CliMenu<CliStageController> stageMenu) {
        this.scanner = scanner;
        this.stageMenu = stageMenu;
    }

    @Override
    public void run() {
        String input = scanner.next();
        Consumer<CliStageController> cmd = stageMenu.get(input);
        cmd.accept(this);

    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    @Override
    public void setInput(String input) {}

    public void up() {
        plot.getPlotHero().getPhysicsCharacter().move(Direction.UP);
    }

    public void down() {
        plot.getPlotHero().getPhysicsCharacter().move(Direction.DOWN);
    }

    public void right() {
        plot.getPlotHero().getPhysicsCharacter().move(Direction.RIGHT);
    }

    public void left() {
        plot.getPlotHero().getPhysicsCharacter().move(Direction.LEFT);
    }

    public void rotateClockwise() {
        plot.getPlotHero().getPhysicsCharacter().rotate(RotationDirection.CLOCKWISE);
    }

    public void rotateUnclockwise() {
        plot.getPlotHero().getPhysicsCharacter().rotate(RotationDirection.UNCLOCKWISE);
    }

    public void attack() {
        plot.getPlotHero().getPhysicsCharacter().attack();
    }

    public void heal() {
        plot.getPlotHero().getPhysicsCharacter().heal();
    }

    public void endTurn() {
        plot.getPlotHero().getPhysicsCharacter().endTurn();
    }

    public void save() {
        // TODO
    }
    public void quit() {
        plot.getCurrentStage().quit();
    }
}
