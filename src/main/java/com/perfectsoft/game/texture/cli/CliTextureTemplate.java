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
        CliPoint tmpMiddlePosition = new CliPoint(tmpLast.getX() / 2 + tmpFirst.getX(), tmpLast.getY() / 2 + tmpFirst.getY());

        this.sortedPoints = points.stream()
                .map(point -> point.subtract(tmpMiddlePosition))
                .collect(Collectors.toCollection(TreeSet::new));
        last = tmpSortedPoints.last();
    }

    @Override
    public Texture rotateMoveTo(Direction direction, Point middlePosition) {

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

        return new CliTexture(movedPoints);
    }

    @Override
    public Texture moveToBound(Point middlePosition, Point bound) {

        List<CliCharPoint> movedInboundPoints = sortedPoints.stream()
                .map(point -> point.add(middlePosition))
                .filter(point -> point.getX() < bound.getX() && point.getY() < bound.getY())
                .collect(Collectors.toList());

        return new CliTexture(movedInboundPoints);
    }

    @Override
    public Point getSize() {
        return size;
    }

    public CliCharPoint getLast() {
        return last;
    }
}
