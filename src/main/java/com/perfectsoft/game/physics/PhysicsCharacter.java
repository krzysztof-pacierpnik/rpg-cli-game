package com.perfectsoft.game.physics;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.PlotCharacter;

public interface PhysicsCharacter {

    GameController  getGameController();

    void setPlotCharacter(PlotCharacter plotCharacter);

    boolean isDead();
}
