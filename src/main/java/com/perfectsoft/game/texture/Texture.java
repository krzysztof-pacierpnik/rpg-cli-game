package com.perfectsoft.game.texture;

import java.util.Optional;
import java.util.UUID;

public interface Texture extends Comparable<Texture> {

    UUID getUuid();

    Point getSize();

    Point getUpperLeftCornerPosition();

    Point getMiddlePosition();

    Point getFirst();

    Point getLast();

    int getZIndex();

    boolean isTransparentBackground();

    Optional<Byte> getCharAtPoint(final Point point);

    Iterable<Byte> iterator();
}
