package com.perfectsoft.game.plot;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.PhysicsStage;

public interface PlotStage {

    PhysicsStage getPhysicsStage();

    GameController getCurrentController();

    void run();

    void quit();

    boolean isRunning();
}
