package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.physics.RotationDirection;
import com.perfectsoft.game.texture.Point;

import java.util.Objects;

public class CliCharPoint extends CliPoint {

    private final byte character;

    public CliCharPoint(int x, int y, byte character) {
        super(x, y);
        this.character = character;
    }

    public byte getCharacter() {
        return character;
    }

    public CliCharPoint rotate(RotationDirection rotationDirection) {

        CliPoint rotated = super.rotate(rotationDirection);
        return new CliCharPoint(rotated.getX(), rotated.getY(), character);
    }

    public CliCharPoint subtract(Point sub) {

        CliPoint subtracted = super.subtract(sub);
        return new CliCharPoint(subtracted.getX(), subtracted.getY(), character);
    }

    public CliCharPoint add(Point aug) {

        CliPoint sum = super.add(aug);
        return new CliCharPoint(sum.getX(), sum.getY(), character);
    }

    public CliCharPoint copy() {

        return new CliCharPoint(getX(), getY(), character);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CliCharPoint that = (CliCharPoint) o;
        return character == that.character;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), character);
    }

    @Override
    public String toString() {
        return "CliCharPoint{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", character=" + character +
                '}';
    }
}
