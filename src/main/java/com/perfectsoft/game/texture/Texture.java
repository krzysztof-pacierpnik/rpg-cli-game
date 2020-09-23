package com.perfectsoft.game.texture;

import com.perfectsoft.game.physics.RotationDirection;

import java.util.Optional;

public interface Texture {

    Point getSize();

    Point getUpperLeftCornerPosition();

    Point getMiddlePosition();

    Point getFirst();

    Point getLast();

    int getZIndex();

    Optional<Byte> getCharAtPoint(final Point point);

    Iterable<Byte> iterator();
}
