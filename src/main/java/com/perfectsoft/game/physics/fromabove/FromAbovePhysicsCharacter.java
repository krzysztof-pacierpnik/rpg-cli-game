package com.perfectsoft.game.physics.fromabove;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.physics.RotationDirection;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.actions.PlotActionFactory;

public class FromAbovePhysicsCharacter implements PhysicsCharacter {

    private final GameController gameController;
    private final PlotActionChannel plotActionChannel;
    private final PlotActionFactory plotActionFactory;

    private FromAbovePhysicsStage physicsStage;
    private PlotCharacter plotCharacter;

    private int attack;
    private int defence;
    private int experience;
    private int speed;
    private int actionPoints;
    private int hitPoints;

    private int maxHitPoints;
    private int moveCost = 1;
    private int rotationCost = 1;
    private int attackCost = 2;
    private int healCost = 5;
    private int healHitPoints = 10;

    private Direction direction;
    private Position position;
    private Position newPosition;

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


    @Override
    public void move(Direction direction) {

        if (actionPoints >= moveCost) {
            newPosition = position.move(direction);
            if (!physicsStage.isOutOfBound(this)) {
                if (physicsStage.detectCollision(this).isPresent()) {
                    // TODO  schedule this changes and change states for animation
                    position = newPosition;
                    actionPoints -= moveCost;
                }
            }
        }

    }

    @Override
    public void rotate(RotationDirection rotationDirection) {

        if (actionPoints >= rotationCost) {
            // TODO  schedule this changes and change states for animation
            actionPoints -= rotationCost;
            direction = direction.rotate(rotationDirection);
        }
    }

    @Override
    public void attack() {
        // TODO
    }

    @Override
    public void heal() {

        if (actionPoints >= healCost) {
            actionPoints -= healCost;
            hitPoints += healHitPoints;
            if (hitPoints > maxHitPoints) {
                hitPoints = maxHitPoints;
            }
        }
    }

    @Override
    public void endTurn() {
        physicsStage.movingCharacterTurnEnd();
    }

    public void initForStage() {

        this.hitPoints = maxHitPoints;
    }

    private boolean takeHit(FromAbovePhysicsCharacter attacker) {
        // TODO calculate damage and return true if dead
        return false;
    }

    private void killed(FromAbovePhysicsCharacter victim) {

        plotActionChannel.publish(
                plotActionFactory.characterKilledCharacterAction(getPlotCharacter(), victim.getPlotCharacter()));
    }

    public PlotCharacter getPlotCharacter() {
        return plotCharacter;
    }

    public void setPhysicsStage(FromAbovePhysicsStage physicsStage) {
        this.physicsStage = physicsStage;
    }

    @Override
    public void setPlotCharacter(PlotCharacter plotCharacter) {
        this.plotCharacter = plotCharacter;
    }

    public Direction getDirection() {
        return direction;
    }

    public Position getPosition() {
        return position;
    }

    public Position getNewPosition() {
        return newPosition;
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

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
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
        return maxHitPoints <= 0;
    }
}
