package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.plot.PlotCharacter;

import java.util.Objects;

public class PlotEngineCharacter implements PlotCharacter {

    private final PhysicsCharacter physicsCharacter;
    private final String code;

    private String name;

    /**
     *
     * @param physicsCharacter physical aspect of this plot character
     * @param code Unique code used to identify character on stage. Can be used also to save.
     *             Only lowercase letters and '-' allowed.
     */
    // TODO add validation of allowed characters and uniqueness on stage.
    public PlotEngineCharacter(PhysicsCharacter physicsCharacter, String code) {
        this.physicsCharacter = physicsCharacter;
        this.physicsCharacter.setPlotCharacter(this);
        this.code = code;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public PhysicsCharacter getPhysicsCharacter() {
        return physicsCharacter;
    }

    @Override
    public void die() {
        name = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlotEngineCharacter that = (PlotEngineCharacter) o;
        return Objects.equals(physicsCharacter, that.physicsCharacter) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physicsCharacter, code, name);
    }
}
