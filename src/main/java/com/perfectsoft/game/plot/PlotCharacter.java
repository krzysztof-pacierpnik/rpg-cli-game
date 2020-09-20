package com.perfectsoft.game.plot;

import com.perfectsoft.game.physics.PhysicsCharacter;

public interface PlotCharacter {

    PhysicsCharacter getPhysicsCharacter();

    void setName(String name);

    String getName();

    void die();
}
