package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.PlotEvent;
import com.perfectsoft.game.plot.PlotStage;
import com.perfectsoft.game.plot.actions.PlotActionFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PlotEngineStage implements PlotStage, Consumer<Consumer<PlotStage>> {

    private final PhysicsStage physicsStage;
    private final Map<Consumer<PlotStage>, PlotEngineEvent> events;
    private final PlotEngineCharacter hero;
    private final PlotActionFactory actionFactory;

    private boolean running;
    private PlotEngineEvent eventToShow;

    public PlotEngineStage(PhysicsStage physicsStage, PlotEngineCharacter hero, List<PlotEngineEvent> events,
                           PlotActionFactory actionFactory) {

        this.physicsStage = physicsStage;
        this.hero = hero;
        this.events = events.stream().collect(Collectors.toMap(PlotEngineEvent::getPlotAction, event -> event));
        this.actionFactory = actionFactory;
    }

    @Override
    public GameController getCurrentController() {

        PhysicsCharacter movingCharacter = physicsStage.getMovingCharacter();
        return movingCharacter.getGameController();
    }

    @Override
    public Optional<PlotEvent> removeEventToShow() {
        Optional<PlotEvent> res = Optional.ofNullable(eventToShow);
        eventToShow = null;
        return res;
    }

    @Override
    public boolean hasEventToShow() {
        return eventToShow != null;
    }

    public PhysicsStage getPhysicsStage() {
        return physicsStage;
    }

    @Override
    public void characterKilledCharacter(PlotCharacter killer, PlotCharacter victim) {
    }

    @Override
    public void heroDied() {
        hero.die();
        quit();
    }

    @Override
    public void accept(Consumer<PlotStage> plotAction) {

        Consumer<PlotStage> actualAction;
        if (hero.getPhysicsCharacter().isDead()) {
            actualAction = actionFactory.heroDiedAction();
        } else {
            actualAction = plotAction;
        }
        actualAction.accept(this);

        eventToShow = events.get(actualAction);
        if (eventToShow != null) {
            eventToShow.setCompleted(true);
        }
    }

    @Override
    public void run() {
        accept(PlotActionFactory.getInstance().runStageAction());
        running = true;
        physicsStage.initStage();
    }

    @Override
    public void quit() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public PlotCharacter getPlotHero() {
        return hero;
    }
}
