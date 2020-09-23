package com.perfectsoft.game.texture;

import com.perfectsoft.game.texture.cli.CliPoint;

public interface Point {

    int getX();

    int getY();

    Point subtract(Point point);

    Point add(Point point);

    CliPoint middleDistance(Point other);
}
