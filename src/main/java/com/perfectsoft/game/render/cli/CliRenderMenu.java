package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenu;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.cli.CliCharPoint;
import com.perfectsoft.game.texture.cli.CliTexture;

import java.util.List;
import java.util.function.Supplier;

public class CliRenderMenu implements Supplier<Texture> {

    private final CliMenu<?> cliMenu;

    public CliRenderMenu(CliMenu<?> cliMenu) {
        this.cliMenu = cliMenu;
    }

    @Override
    public Texture get() {
        // TODO print menu to stream and build texture from stream
        return new CliTexture(List.of(new CliCharPoint(0,0, (byte)'T'), new CliCharPoint(1,0, (byte)'O'),
                new CliCharPoint(2,0, (byte)'D'), new CliCharPoint(3,0, (byte)'O')));
    }
}
