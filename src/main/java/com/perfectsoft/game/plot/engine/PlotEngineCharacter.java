package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.plot.PlotCharacter;

public class PlotEngineCharacter implements PlotCharacter {

    private final PhysicsCharacter physicsCharacter;

    public PlotEngineCharacter(PhysicsCharacter physicsCharacter) {
        this.physicsCharacter = physicsCharacter;
    }

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PhysicsCharacter getPhysicsCharacter() {
        return physicsCharacter;
    }
}
