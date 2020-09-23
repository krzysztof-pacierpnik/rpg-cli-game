package com.perfectsoft.game.texture;

public interface Point {

    int getX();

    int getY();

    Point subtract(Point point);

    Point add(Point point);
}
