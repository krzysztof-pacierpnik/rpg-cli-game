package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.render.Camera;

public class AsciiCliCamera implements Camera {

    byte[][] stage = new byte[100][100];
    byte[][] buff = new byte[100][100];

    @Override
    public void render(PhysicsStage physicsStage) {
    }
}
