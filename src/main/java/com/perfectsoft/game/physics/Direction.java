package com.perfectsoft.game.physics;

public enum  Direction {

    UP(0, -1),
    DOWN(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction rotate(RotationDirection rotationDirection) {

        switch (rotationDirection) {
            case CLOCKWISE:
                switch (this) {
                    case UP:
                        return RIGHT;
                    case RIGHT:
                        return DOWN;
                    case DOWN:
                        return LEFT;
                    case LEFT:
                        return UP;
                }
            case UNCLOCKWISE:
                switch (this) {
                    case UP:
                        return LEFT;
                    case LEFT:
                        return DOWN;
                    case DOWN:
                        return RIGHT;
                    case RIGHT:
                        return UP;
                }

        }
        throw new RuntimeException(String.format("Unsupported rotation direction %s and direction %s", rotationDirection, this));
    }
}
