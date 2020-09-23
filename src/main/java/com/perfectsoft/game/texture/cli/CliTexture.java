package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;

import java.util.*;

public class CliTexture implements Texture {

    public static final byte TRANSPARENT_CHARACTER = ' ';

    private final UUID uuid;
    private final SortedSet<CliCharPoint> sortedPoints;
    private final CliCharPoint first;
    private final CliCharPoint last;
    private final Point size;
    private final Point middlePosition;
    private final int zIndex;
    private final boolean transparentBackground;

    public CliTexture(Collection<CliCharPoint> points) {
        this(points, 0, false);
    }

    public CliTexture(Collection<CliCharPoint> points, int zIndex, boolean transparentBackground) {

        uuid = UUID.randomUUID();
        this.transparentBackground = transparentBackground;
        this.zIndex = zIndex;
        this.sortedPoints = new TreeSet<>(points);
        first = sortedPoints.first();
        last = sortedPoints.last();
        size = new CliPoint(last.getX() + 1 - first.getX(), last.getY() + 1 - first.getY());
        this.middlePosition = new CliPoint(first.getX(), first.getY()).add(first.middleDistance(last));
    }


    @Override
    public Optional<Byte> getCharAtPoint(Point point) {
        return sortedPoints.parallelStream()
                .filter(cliCharPoint ->
                        cliCharPoint.getX() == point.getX() && cliCharPoint.getY() == point.getY()
                )
                .map(CliCharPoint::getCharacter)
                .findAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CliTexture that = (CliTexture) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public int compareTo(Texture o) {
        return uuid.compareTo(o.getUuid());
    }

    @Override
    public Iterable<Byte> iterator() {
        return new CliTextureIterator(this);
    }

    @Override
    public CliCharPoint getFirst() {
        return first;
    }

    @Override
    public CliCharPoint getLast() {
        return last;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public Point getSize() {
        return size;
    }

    @Override
    public Point getMiddlePosition() {
        return middlePosition;
    }

    @Override
    public Point getUpperLeftCornerPosition() {
        return first;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean isTransparentBackground() {
        return transparentBackground;
    }
}
