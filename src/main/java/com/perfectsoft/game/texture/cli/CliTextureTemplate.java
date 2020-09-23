package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.RotationDirection;
import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class CliTextureTemplate implements TextureTemplate {

    private final SortedSet<CliCharPoint> sortedPoints;
    private final CliCharPoint last;
    private final Point size;

    public CliTextureTemplate(Collection<CliCharPoint> points) {

        SortedSet<CliCharPoint> tmpSortedPoints = new TreeSet<>(points);
        CliCharPoint tmpFirst = tmpSortedPoints.first();
        CliCharPoint tmpLast = tmpSortedPoints.last();

        size = new CliPoint(tmpLast.getX() + 1 - tmpFirst.getX(), tmpLast.getY() + 1 - tmpFirst.getY());
        CliPoint dist = tmpFirst.middleDistance(tmpLast);

        this.sortedPoints = points.stream()
                .map(point -> point.subtract(dist))
                .collect(Collectors.toCollection(TreeSet::new));
        last = tmpSortedPoints.last();
    }

    @Override
    public Texture rotateMoveTo(Direction direction, Point middlePosition, int zIndex) {

        RotationDirection rotationDirection = Direction.UP.rotationDirection(direction);
        Collection<CliCharPoint> rotatedPoints;
        if (RotationDirection.NOOP == rotationDirection) {
            rotatedPoints = sortedPoints;
        } else {
            rotatedPoints = sortedPoints.stream()
                    .map(point -> point.rotate(rotationDirection))
                    .collect(Collectors.toList());
        }

        List<CliCharPoint> movedPoints = rotatedPoints.stream()
                .map(point -> point.add(middlePosition))
                .collect(Collectors.toList());

        return new CliTexture(movedPoints, zIndex, true);
    }

    @Override
    public Texture moveToBound(Point middlePosition, Point first, Point last, int zIndex) {

        List<CliCharPoint> movedInboundPoints = sortedPoints.stream()
                .map(point -> point.add(middlePosition))
                .filter(point -> point.getX() >= first.getX() && point.getX() < last.getX()
                        && point.getY() >= first.getX() && point.getY() < last.getY())
                .collect(Collectors.toList());

        return new CliTexture(movedInboundPoints, zIndex, false);
    }

    @Override
    public Point getSize() {
        return size;
    }

    public CliCharPoint getLast() {
        return last;
    }
}
