package com.perfectsoft.game.render;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;

import java.util.concurrent.ScheduledFuture;

public interface RenderCharacter {

    void init(Direction direction, Position position);

    Texture getTexture();

    ScheduledFuture<?> move(Direction direction, Position position);

    ScheduledFuture<?> rotate(Direction direction, Position position);

    ScheduledFuture<?> attack(Direction direction, Position position, RenderCharacter victim);

    void takeHit(Direction direction, Position position);

    void recover(Direction direction, Position position);

    Point getMiddlePointOnStage();
}
