package com.perfectsoft.game.render;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.Position;

public interface Texture {

    void render(final Texture mapTexture, final Position objectPosition, final Direction objectDirection);
}
