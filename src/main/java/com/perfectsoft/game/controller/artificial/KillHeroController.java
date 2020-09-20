package com.perfectsoft.game.controller.artificial;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.plot.Plot;

public class KillHeroController implements GameController {

    private final PhysicsCharacter hero;

    private PhysicsCharacter character;

    public KillHeroController(PhysicsCharacter hero) {
        this.hero = hero;
    }

    public void setCharacter(PhysicsCharacter character) {
        this.character = character;
    }

    @Override
    public void run() {

    }

    @Override
    public void setPlot(Plot plot) {}

    @Override
    public void setInput(String input) {}
}
