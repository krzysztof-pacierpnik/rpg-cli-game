package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.plot.PlotStage;
import com.perfectsoft.game.render.RenderStage;
import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;
import com.perfectsoft.game.texture.cli.CliStageRenderer;
import com.perfectsoft.game.texture.cli.TextureContainer;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CliRenderStage implements RenderStage, Supplier<Texture> {

    private final CliStageRenderer cliStageRenderer;
    private final int fieldSize;
    private final TextureTemplate stageTexture;
    private final Point cameraLeftUpperCorner;
    private final Point cameraSize;
    private final Collection<CliRenderCharacter> cliRenderCharacters;

    private PlotStage plotStage;
    private PhysicsStage physicsStage;

    public CliRenderStage(CliStageRenderer cliStageRenderer, int fieldSize, TextureTemplate stageTexture,
                          Point cameraLeftUpperCorner, Point cameraSize, Collection<CliRenderCharacter> cliRenderCharacters) {

        this.cliStageRenderer = cliStageRenderer;
        this.fieldSize = fieldSize;
        this.stageTexture = stageTexture;
        this.cameraLeftUpperCorner = cameraLeftUpperCorner;
        this.cameraSize = cameraSize;
        this.cliRenderCharacters = cliRenderCharacters;
        this.cliRenderCharacters.forEach(character -> character.setCliRenderStage(this));

        int stageSizeX = stageTexture.getSize().getX();
        int stageSizeY = stageTexture.getSize().getY();
        if (stageSizeX % fieldSize != 0 || stageSizeY % fieldSize != 0) {
            throw new RuntimeException(
                    String.format("Stage texture must be multiply of field size: %d, but is x: %d y: %d", fieldSize,
                            stageSizeX, stageSizeY));
        }

    }

    @Override
    public void initStage() {

        final int sizeX = stageTexture.getSize().getX();
        final int sizeY = stageTexture.getSize().getY();
        physicsStage.setSize(new Position(sizeX / fieldSize, sizeY / fieldSize));
        cliStageRenderer.renderStage(this);
    }

    @Override
    public Texture get() {

        final Point heroMiddlePosition = plotStage.getPlotHero().getPhysicsCharacter().getRenderCharacter()
                .getTexture().getMiddlePosition();
        final Point cameraMiddlePosition = heroMiddlePosition.add(cameraLeftUpperCorner);
        Texture stageShot = stageTexture.moveToBound(cameraMiddlePosition, cameraSize);
        return new TextureContainer(stageShot, cliRenderCharacters.stream()
                .map(CliRenderCharacter::get)
                .collect(Collectors.toList()));
    }

    public void setPhysicsStage(PhysicsStage physicsStage) {
        this.physicsStage = physicsStage;
    }

    public void setPlotStage(PlotStage plotStage) {
        this.plotStage = plotStage;
    }

    public int getFieldSize() {
        return fieldSize;
    }
}
