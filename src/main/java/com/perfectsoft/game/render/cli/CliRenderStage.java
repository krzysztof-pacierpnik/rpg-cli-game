package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.PlotStage;
import com.perfectsoft.game.render.RenderCharacter;
import com.perfectsoft.game.render.RenderStage;
import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;
import com.perfectsoft.game.texture.cli.CliPoint;
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
    private final Point cameraMiddlePosition;
    private final Point cameraBound;
    private final Collection<CliRenderCharacter> cliRenderCharacters;
    private final Point stageMiddleDistance;

    private PlotStage plotStage;
    private PhysicsStage physicsStage;

    public CliRenderStage(CliStageRenderer cliStageRenderer, int fieldSize, TextureTemplate stageTexture,
                          Point cameraLeftUpperCorner, Point cameraBound, Collection<CliRenderCharacter> cliRenderCharacters) {

        this.cliStageRenderer = cliStageRenderer;
        this.fieldSize = fieldSize;
        this.stageTexture = stageTexture;
        this.cameraLeftUpperCorner = cameraLeftUpperCorner;
        this.cameraBound = cameraBound;
        this.cameraMiddlePosition = cameraLeftUpperCorner.middleDistance(cameraBound);
        this.cliRenderCharacters = cliRenderCharacters;
        this.cliRenderCharacters.forEach(character -> character.setCliRenderStage(this));

        int stageSizeX = stageTexture.getSize().getX();
        int stageSizeY = stageTexture.getSize().getY();
        if (stageSizeX % fieldSize != 0 || stageSizeY % fieldSize != 0) {
            throw new RuntimeException(
                    String.format("Stage texture must be multiply of field size: %d, but is x: %d y: %d", fieldSize,
                            stageSizeX, stageSizeY));
        }
        stageMiddleDistance = new CliPoint(stageSizeX / 2, stageSizeY /2);
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

        final PlotCharacter plotHero = plotStage.getPlotHero();
        final PhysicsCharacter physicsCharacter = plotHero.getPhysicsCharacter();
        final RenderCharacter renderCharacter = physicsCharacter.getRenderCharacter();
        final Point heroMiddlePosition = renderCharacter.getMiddlePointOnStage();

        final Point transition = cameraLeftUpperCorner.add(stageMiddleDistance).add(cameraMiddlePosition)
                .subtract(heroMiddlePosition);
        Texture stageShot = stageTexture.moveToBound(transition, cameraLeftUpperCorner, cameraBound, 1);

        return new TextureContainer(stageShot, cliRenderCharacters.stream()
                .map(CliRenderCharacter::get)
                .collect(Collectors.toList()));
    }

    public Point alignToCamera(Point pointOnStage) {

        final PlotCharacter plotHero = plotStage.getPlotHero();
        final PhysicsCharacter physicsCharacter = plotHero.getPhysicsCharacter();
        final RenderCharacter renderCharacter = physicsCharacter.getRenderCharacter();
        final Point heroMiddlePosition = renderCharacter.getMiddlePointOnStage();

        return pointOnStage.add(cameraLeftUpperCorner).add(cameraMiddlePosition).subtract(heroMiddlePosition);
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
