package com.perfectsoft.game.physics;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.render.RenderCharacter;

public interface PhysicsCharacter {

    GameController  getGameController();

    void setPlotCharacter(PlotCharacter plotCharacter);

    RenderCharacter getRenderCharacter();

    void move(Direction direction);

    void rotate(RotationDirection rotationDirection);

    void attack();

    void heal();

    void endTurn();

    boolean isDead();

    void noop();
}
