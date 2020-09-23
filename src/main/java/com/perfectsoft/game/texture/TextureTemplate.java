package com.perfectsoft.game.texture;

import com.perfectsoft.game.physics.Direction;

public interface TextureTemplate {

    Point getSize();

    Point getLast();

    Texture rotateMoveTo(Direction direction, Point middlePosition, int zIndex);

    Texture moveToBound(Point middlePosition, Point first, Point last, int zIndex);
}
