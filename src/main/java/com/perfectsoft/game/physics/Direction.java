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

    public RotationDirection rotationDirection(Direction to) {

        switch (this) {
            case UP:
                switch (to) {
                    case RIGHT:
                        return RotationDirection.CLOCKWISE;
                    case LEFT:
                        return RotationDirection.UNCLOCKWISE;
                    case UP:
                        return RotationDirection.NOOP;
                    case DOWN:
                        return RotationDirection.INVERSE;
                }
            case DOWN:
                switch (to) {
                    case LEFT:
                        return RotationDirection.CLOCKWISE;
                    case RIGHT:
                        return RotationDirection.UNCLOCKWISE;
                    case DOWN:
                        return RotationDirection.NOOP;
                    case UP:
                        return RotationDirection.INVERSE;
                }
            case RIGHT:
                switch (to) {
                    case DOWN:
                        return RotationDirection.CLOCKWISE;
                    case UP:
                        return RotationDirection.UNCLOCKWISE;
                    case RIGHT:
                        return RotationDirection.NOOP;
                    case LEFT:
                        return RotationDirection.INVERSE;
                }
            case LEFT:
                switch (to) {
                    case UP:
                        return RotationDirection.CLOCKWISE;
                    case DOWN:
                        return RotationDirection.UNCLOCKWISE;
                    case LEFT:
                        return RotationDirection.NOOP;
                    case RIGHT:
                        return RotationDirection.INVERSE;
                }
        }
        throw new RuntimeException(String.format("Unsupported direction combination: %s, %s", this, to));
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
