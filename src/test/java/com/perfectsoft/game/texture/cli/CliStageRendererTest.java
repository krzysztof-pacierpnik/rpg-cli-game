package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.function.Supplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CliStageRendererTest {

    private CliStageRenderer cliStageRenderer;
    private TextureContainer screenContainer;

    @BeforeAll
    public void init() {

        Texture menuTexture = CliTextureReader.readTexture("test_menu", new CliPoint(1, 16));
        Supplier<Texture> menuSupplier = () -> menuTexture;

        TextureTemplate textureTemp = CliTextureReader.readTextureTemplate("warrior_stand");
        Texture warriorTexture = textureTemp.rotateMoveTo(Direction.LEFT, new CliPoint(13,13));

        Texture screenTexture = CliTextureReader.readTexture("test_screen", new CliPoint(0, 0));
        screenContainer = new TextureContainer(screenTexture, List.of(menuTexture, warriorTexture));
        cliStageRenderer = new CliStageRenderer(screenContainer, menuSupplier, 1000);
    }

    @Test
    public void readWarriorStandTexTemp() {

        System.out.println("Texture:");
        cliStageRenderer.printStageScreen(screenContainer);
    }
}
