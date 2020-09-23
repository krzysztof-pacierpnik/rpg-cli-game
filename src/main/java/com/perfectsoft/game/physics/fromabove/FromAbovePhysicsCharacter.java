package com.perfectsoft.game.physics.fromabove;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.physics.RotationDirection;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.actions.PlotActionFactory;
import com.perfectsoft.game.render.RenderCharacter;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;

public class FromAbovePhysicsCharacter implements PhysicsCharacter {

    private final GameController gameController;
    private final PlotActionChannel plotActionChannel;
    private final PlotActionFactory plotActionFactory;
    private final RenderCharacter renderCharacter;

    private ScheduledFuture<?> renderFuture;
    private FromAbovePhysicsStage physicsStage;
    private PlotCharacter plotCharacter;

    private int attack;
    private int defence;
    private int experience;
    private int speed;
    private int actionPoints;
    private int hitPoints;

    private int maxHitPoints;

    private Direction direction;
    private Position position;
    private Position newPosition;

    public FromAbovePhysicsCharacter(GameController gameController, PlotActionChannel plotActionChannel,
                                     PlotActionFactory plotActionFactory, RenderCharacter renderCharacter) {

        this.gameController = gameController;
        this.plotActionChannel = plotActionChannel;
        this.plotActionFactory = plotActionFactory;
        this.renderCharacter = renderCharacter;
    }

    @Override
    public GameController getGameController() {
        return gameController;
    }

    @Override
    public void move(final Direction direction) {

        int moveCost = 1;
        takeAction(moveCost, () -> {

            newPosition = position.move(direction);
            if (physicsStage.isInBound(newPosition)) {
                if (physicsStage.detectCollision(this).isPresent()) {
                    position = newPosition;
                    return Optional.of(renderCharacter.move(direction, position));
                }
            }
            return Optional.empty();
        });
    }

    @Override
    public void rotate(final RotationDirection rotationDirection) {

        int rotationCost = 1;
        takeAction(rotationCost, () -> {

            direction = direction.rotate(rotationDirection);
            return Optional.empty();
        });
    }

    @Override
    public void attack() {

        int attackCost = 2;
        takeAction(attackCost, () -> {

            newPosition = position.move(direction);
            if (physicsStage.isInBound(newPosition)) {
                Optional<FromAbovePhysicsCharacter> otherOpt = physicsStage.detectCollision(this);
                if (otherOpt.isPresent()) {
                    FromAbovePhysicsCharacter other = otherOpt.get();
                    int damage = getAttack() - other.getDefence();
                    if (damage > 0) {
                        experience += damage;
                        other.takeHit(this, damage);
                    }
                    return Optional.of(renderCharacter.attack(direction, position, other.renderCharacter));
                }
            }
            return Optional.empty();
        });
    }

    @Override
    public void heal() {

        int healCost = 5;
        takeAction(healCost, () -> {

            int healHitPoints = 10;
            hitPoints += healHitPoints;
            if (hitPoints > maxHitPoints) {
                hitPoints = maxHitPoints;
            }
            return Optional.empty();
        });

    }

    @Override
    public void endTurn() {
        physicsStage.movingCharacterTurnEnd();
    }

    public void initForStage() {

        this.hitPoints = maxHitPoints;
        renderCharacter.init(direction, position);
    }

    private void takeHit(FromAbovePhysicsCharacter attacker, int damage) {

        hitPoints -= damage;
        if (hitPoints < 0) {
            killedBy(attacker);
            renderCharacter.takeHit(direction, position);
        }
    }

    private void killedBy(FromAbovePhysicsCharacter attacker) {

        physicsStage.characterKilled(this);
        plotActionChannel.publish(
                plotActionFactory.characterKilledCharacterAction(attacker.getPlotCharacter(), getPlotCharacter()));
    }

    private void takeAction(int actionPointNeeded, Supplier<Optional<ScheduledFuture<?>>> action) {

        if (actionPoints >= actionPointNeeded) {
            action.get().ifPresent(renderFuture -> {

                actionPoints -= actionPointNeeded;
                this.renderFuture = renderFuture;
                waitForAnimationEnd();
            });
        }
    }

    private void waitForAnimationEnd() {

        try {
            renderFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to wait for animation end", e);
        }
        renderFuture = null;
    }

    public PlotCharacter getPlotCharacter() {
        return plotCharacter;
    }

    @Override
    public RenderCharacter getRenderCharacter() {
        return renderCharacter;
    }

    public void setPhysicsStage(FromAbovePhysicsStage physicsStage) {
        this.physicsStage = physicsStage;
    }

    @Override
    public void setPlotCharacter(PlotCharacter plotCharacter) {
        this.plotCharacter = plotCharacter;
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

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
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

    public boolean isAlive() {
        return !isDead();
    }
}
