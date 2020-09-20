package com.perfectsoft.game.plot;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.PhysicsStage;

import java.util.Optional;

public interface PlotStage {

    PhysicsStage getPhysicsStage();

    GameController getCurrentController();

    boolean hasEventToShow();

    Optional<PlotEvent> removeEventToShow();

    PlotCharacter getPlotHero();

    void characterKilledCharacter(PlotCharacter killer, PlotCharacter victim);

    void heroDied();

    void run();

    void quit();

    boolean isRunning();
}
