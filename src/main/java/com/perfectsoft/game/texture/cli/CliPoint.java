package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.physics.RotationDirection;
import com.perfectsoft.game.texture.Point;

import java.util.Objects;

public class CliPoint implements Point, Comparable<Point> {

    private final int x;
    private final int y;

    public CliPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public CliPoint subtract(Point sub) {
        return new CliPoint(x - sub.getX(), y - sub.getY());
    }

    public CliPoint add(Point aug) {
        return new CliPoint(x + aug.getX(), y + aug.getY());
    }

    public CliPoint rotate(RotationDirection multi) {
        if (RotationDirection.NOOP == multi) {
            return this;
        }
        if (RotationDirection.INVERSE == multi) {
            return new CliPoint(x * multi.getXMultiplier(), y * multi.getYMultiplier());
        }
        return new CliPoint(y * multi.getXMultiplier(), x * multi.getYMultiplier());
    }

    public CliPoint negate() {
        return new CliPoint(-x, -y);
    }

    public CliPoint middleDistance(Point other) {
        return new CliPoint((other.getX() - x) / 2 , (other.getY() - y) / 2 );
    }

    @Override
    public int compareTo(Point o) {
        int compY = Integer.compare(y, o.getY());
        if (compY == 0) return Integer.compare(x, o.getX());
        return compY;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CliPoint cliPoint = (CliPoint) o;
        return x == cliPoint.x &&
                y == cliPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "CliPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
