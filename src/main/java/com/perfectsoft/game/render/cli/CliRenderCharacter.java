package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.render.RenderCharacter;
import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.StageRenderer;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;
import com.perfectsoft.game.texture.cli.CliPoint;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;

public class CliRenderCharacter implements RenderCharacter, Supplier<Texture> {

    private CliRenderStage cliRenderStage;
    private final StageRenderer stageRenderer;
    private final TextureTemplate standTexture;
    private final TextureTemplate moveTexture;
    private final TextureTemplate attackTexture;
    private final TextureTemplate takeHitTexture;

    private Texture currentTexture;

    public CliRenderCharacter(StageRenderer stageRenderer, TextureTemplate standTexture,
                              TextureTemplate moveTexture, TextureTemplate attackTexture, TextureTemplate takeHitTexture) {

        this.stageRenderer = stageRenderer;
        this.standTexture = standTexture;
        this.moveTexture = moveTexture;
        this.attackTexture = attackTexture;
        this.takeHitTexture = takeHitTexture;
    }

    public void setCliRenderStage(CliRenderStage cliRenderStage) {
        this.cliRenderStage = cliRenderStage;
    }

    @Override
    public void init(Direction direction, Position position) {

        Point midPoint = calculateMiddlePoint(position);
        rotateMoveCurrentTexture(standTexture, direction, midPoint);
    }

    @Override
    public ScheduledFuture<?> move(Direction direction, Position position) {

        return takeAction(moveTexture, direction, position);
    }

    @Override
    public ScheduledFuture<?> attack(Direction direction, Position position, RenderCharacter victim) {

        return takeAction(victim, attackTexture, direction, position);
    }

    @Override
    public void takeHit(Direction direction, Position position) {

        Point midPoint = calculateMiddlePoint(position);
        rotateMoveCurrentTexture(takeHitTexture, direction, midPoint);
    }

    @Override
    public void recover(Direction direction, Position position) {

        Point midPoint = calculateMiddlePoint(position);
        rotateMoveCurrentTexture(standTexture, direction, midPoint);
    }

    @Override
    public ScheduledFuture<?> rotate(Direction direction, Position position) {
        return takeAction(standTexture, direction, position);
    }

    private ScheduledFuture<?> takeAction(TextureTemplate template, Direction direction, Position position) {

        return takeAction(null, template, direction, position);
    }

    private ScheduledFuture<?> takeAction(RenderCharacter other, TextureTemplate template, Direction direction,
                                          Position position) {

        try {
            Point middlePoint = calculateMiddlePoint(position);
            rotateMoveCurrentTexture(template, direction, middlePoint);
            stageRenderer.delayStageRendering(cliRenderStage).get();
            recover(direction, position);
            if (other != null) other.recover(direction, position);
            return stageRenderer.delayStageRendering(cliRenderStage);

        } catch (InterruptedException | ExecutionException e) {

            throw new RuntimeException("Failed to wait for animation end", e);
        }
    }

    private Point calculateMiddlePoint(Position position) {
        final int fieldSize = cliRenderStage.getFieldSize();
        final int fieldMiddle = fieldSize / 2 + 1;
        return new CliPoint(position.getX() * fieldSize + fieldMiddle,
                position.getY() * fieldSize + fieldMiddle);
    }

    private void rotateMoveCurrentTexture(TextureTemplate template, Direction direction, Point middlePosition) {

        currentTexture = template.rotateMoveTo(direction, middlePosition);
    }

    @Override
    public Texture get() {
        return currentTexture;
    }

    @Override
    public Texture getTexture() {
        return currentTexture;
    }
}
