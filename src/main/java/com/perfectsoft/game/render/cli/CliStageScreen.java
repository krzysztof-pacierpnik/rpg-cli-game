package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.cli.TextureContainer;

import java.util.List;
import java.util.function.Supplier;

public class CliStageScreen implements Supplier<Texture> {

    private final Texture texture;
    private final Supplier<Texture> stageTextureSupplier;
    private final Supplier<Texture> stageMenuTextureSupplier;

    public CliStageScreen(Texture texture, Supplier<Texture> stageTextureSupplier, Supplier<Texture> stageMenuTextureSupplier) {
        this.texture = texture;
        this.stageTextureSupplier = stageTextureSupplier;
        this.stageMenuTextureSupplier = stageMenuTextureSupplier;
    }

    @Override
    public Texture get() {
        return new TextureContainer(texture, List.of(stageTextureSupplier.get(), stageMenuTextureSupplier.get()));
    }
}
