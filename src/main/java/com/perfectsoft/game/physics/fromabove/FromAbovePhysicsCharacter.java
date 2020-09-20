package com.perfectsoft.game.physics.fromabove;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.actions.PlotActionFactory;

public class FromAbovePhysicsCharacter implements PhysicsCharacter {

    private final GameController gameController;
    private final PlotActionChannel plotActionChannel;
    private final PlotActionFactory plotActionFactory;

    private PlotCharacter plotCharacter;

    private int attack;
    private int defence;
    private int hitPoints;
    private int experience;
    private int speed;
    private int actionPoints;

    public FromAbovePhysicsCharacter(GameController gameController, PlotActionChannel plotActionChannel,
                                     PlotActionFactory plotActionFactory) {
        this.gameController = gameController;
        this.plotActionChannel = plotActionChannel;
        this.plotActionFactory = plotActionFactory;
    }

    @Override
    public GameController getGameController() {
        return gameController;
    }

    private void killed(FromAbovePhysicsCharacter victim) {
        plotActionChannel.publish(
                PlotActionFactory.getInstance().characterKilledCharacterAction(getPlotCharacter(), victim.getPlotCharacter()));
    }

    public PlotCharacter getPlotCharacter() {
        return plotCharacter;
    }

    @Override
    public void setPlotCharacter(PlotCharacter plotCharacter) {
        this.plotCharacter = plotCharacter;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void startTurn() {
        actionPoints = speed;
    }

    @Override
    public boolean isDead() {
        return hitPoints <= 0;
    }
}
