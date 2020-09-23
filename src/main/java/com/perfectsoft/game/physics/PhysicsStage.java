package com.perfectsoft.game.physics;

public interface PhysicsStage {

    void initStage();

    PhysicsCharacter getMovingCharacter();

    void setSize(Position size);
}
