package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenu;
import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.cli.CliCharPoint;
import com.perfectsoft.game.texture.cli.CliTexture;

import java.util.List;
import java.util.function.Supplier;

public class CliRenderMenu implements Supplier<Texture> {

    private final CliMenu<?> cliMenu;
    private final Point transition;

    public CliRenderMenu(CliMenu<?> cliMenu, Point transition) {
        this.cliMenu = cliMenu;
        this.transition = transition;
    }

    @Override
    public Texture get() {
        // TODO print menu to stream and build texture from stream
        return new CliTexture(List.of(
                new CliCharPoint(0,0, (byte)'T').add(transition),
                new CliCharPoint(1,0, (byte)'O').add(transition),
                new CliCharPoint(2,0, (byte)'D').add(transition),
                new CliCharPoint(3,0, (byte)'O').add(transition))
        );
    }
}
