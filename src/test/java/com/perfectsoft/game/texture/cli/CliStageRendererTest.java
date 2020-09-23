package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.conf.PlotConf;
import com.perfectsoft.game.controller.cli.CliMenu;
import com.perfectsoft.game.controller.cli.CliStageController;
import com.perfectsoft.game.controller.cli.conf.CliMenuConf;
import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.PlotStage;
import com.perfectsoft.game.render.cli.CliRenderCharacter;
import com.perfectsoft.game.render.cli.CliRenderMenu;
import com.perfectsoft.game.render.cli.CliRenderStage;
import com.perfectsoft.game.render.cli.CliStageScreen;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Supplier;

@ExtendWith(MockitoExtension.class)
public class CliStageRendererTest {

    @Mock
    private PlotStage plotStage;
    @Mock
    private PhysicsStage physicsStage;
    @Mock
    private PlotCharacter hero;
    @Mock
    private PhysicsCharacter heroPhysics;

    @Test
    public void readWarriorStandTexTemp() {

        Texture menuTexture = CliTextureReader.readTexture("test_menu", new CliPoint(1, 16));
        Supplier<Texture> menuSupplier = () -> menuTexture;

        TextureTemplate textureTemp = CliTextureReader.readTextureTemplate("warrior_stand");
        Texture warriorTexture = textureTemp.rotateMoveTo(Direction.RIGHT, new CliPoint(13,13), 1);

        Texture screenTexture = CliTextureReader.readTexture("test_screen", new CliPoint(0, 0));
        TextureContainer screenContainer = new TextureContainer(screenTexture, List.of(menuTexture, warriorTexture));
        CliStageRenderer cliStageRenderer = new CliStageRenderer(screenContainer, menuSupplier, 1000);
        System.out.println("Texture:");
        cliStageRenderer.printStageScreen(screenContainer);
    }

    @Test
    public void renderScreen() {

        CliMenu<CliStageController> stageMenu = CliMenuConf.createStageMenu();

        Texture screenTexture = CliTextureReader.readTexture("stage_screen", new CliPoint(0,0));
        CliRenderMenu cliRenderMenu = new CliRenderMenu(stageMenu, new CliPoint(1, 111));
        CliStageRenderer cliStageRenderer = new CliStageRenderer(screenTexture, cliRenderMenu, 1000);

        TextureTemplate warriorStand = CliTextureReader.readTextureTemplate("warrior_stand");
        TextureTemplate warriorWalk = CliTextureReader.readTextureTemplate("warrior_walk");
        TextureTemplate warriorAttack = CliTextureReader.readTextureTemplate("warrior_attack");
        TextureTemplate warriorTakeHit = CliTextureReader.readTextureTemplate("warrior_take_hit");
        CliRenderCharacter heroRender = new CliRenderCharacter(cliStageRenderer, warriorStand, warriorWalk,
                warriorAttack, warriorTakeHit);

        TextureTemplate monsterFrogStand = CliTextureReader.readTextureTemplate("monster_frog_stand");
        TextureTemplate monsterFrogWalk = CliTextureReader.readTextureTemplate("monster_frog_walk");
        TextureTemplate monsterFrogAttack = CliTextureReader.readTextureTemplate("monster_frog_attack");
        TextureTemplate monsterFrogTakeHit = CliTextureReader.readTextureTemplate("monster_frog_take_hit");
        CliRenderCharacter monsterFrogRender = new CliRenderCharacter(cliStageRenderer, monsterFrogStand, monsterFrogWalk,
                monsterFrogAttack, monsterFrogTakeHit);

        TextureTemplate exploreStationTexture = CliTextureReader.readTextureTemplate("explore_station");
        CliRenderStage exploreStationRenderStage = new CliRenderStage(cliStageRenderer, 9, exploreStationTexture,
                new CliPoint(1, 1), new CliPoint(111, 101), List.of(heroRender, monsterFrogRender));

        Mockito.when(plotStage.getPlotHero()).thenReturn(hero);
        Mockito.when(hero.getPhysicsCharacter()).thenReturn(heroPhysics);
        Mockito.when(heroPhysics.getRenderCharacter()).thenReturn(heroRender);
        exploreStationRenderStage.setPlotStage(plotStage);
        exploreStationRenderStage.setPhysicsStage(physicsStage);

        System.out.println("Texture:");
        heroRender.init(Direction.UP, new Position(6, 16));
        monsterFrogRender.init(Direction.DOWN, new Position(8, 2));
        exploreStationRenderStage.initStage();
    }
}
