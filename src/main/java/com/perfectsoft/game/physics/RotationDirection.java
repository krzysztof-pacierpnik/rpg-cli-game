package com.perfectsoft.game.physics;

public enum RotationDirection {

    CLOCKWISE(-1, 1),
    UNCLOCKWISE(1, -1),
    INVERSE(-1, -1),
    NOOP(1, 1);

    private final int xMultiplier;
    private final int yMultiplier;

    RotationDirection(int xMultiplier, int yMultiplier) {
        this.xMultiplier = xMultiplier;
        this.yMultiplier = yMultiplier;
    }

    public int getXMultiplier() {
        return xMultiplier;
    }

    public int getYMultiplier() {
        return yMultiplier;
    }
}
