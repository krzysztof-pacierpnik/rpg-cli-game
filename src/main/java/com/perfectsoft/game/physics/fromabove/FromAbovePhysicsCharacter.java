package com.perfectsoft.game.physics.fromabove;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.PhysicsCharacter;

public class FromAbovePhysicsCharacter implements PhysicsCharacter {

    private GameController gameController;

    private int speed;

    @Override
    public GameController getGameController() {
        return gameController;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
