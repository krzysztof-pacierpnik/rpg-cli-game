package com.perfectsoft.game.physics;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.PlotCharacter;

public interface PhysicsCharacter {

    GameController  getGameController();

    void setPlotCharacter(PlotCharacter plotCharacter);

    void move(Direction direction);

    void rotate(RotationDirection rotationDirection);

    void attack();

    void heal();

    void endTurn();

    boolean isDead();
}
