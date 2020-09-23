package com.perfectsoft.game.secret.title.conf;

import com.perfectsoft.game.conf.PlotConf;
import com.perfectsoft.game.controller.artificial.KillHeroController;
import com.perfectsoft.game.controller.cli.CliStageController;
import com.perfectsoft.game.dao.properties.PropertiesUtils;
import com.perfectsoft.game.physics.fromabove.FromAbovePhysicsCharacter;
import com.perfectsoft.game.physics.fromabove.FromAbovePhysicsStage;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.actions.*;
import com.perfectsoft.game.plot.engine.PlotEngine;
import com.perfectsoft.game.plot.engine.PlotEngineCharacter;
import com.perfectsoft.game.plot.engine.PlotEngineEvent;
import com.perfectsoft.game.plot.engine.PlotEngineStage;
import com.perfectsoft.game.render.cli.CliRenderCharacter;
import com.perfectsoft.game.render.cli.CliRenderStage;
import com.perfectsoft.game.texture.TextureTemplate;
import com.perfectsoft.game.texture.cli.CliPoint;
import com.perfectsoft.game.texture.cli.CliStageRenderer;
import com.perfectsoft.game.texture.cli.CliTextureReader;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public final class SecretTitleConf implements PlotConf {

    public PlotEngine createPhysicsAndPlot(PlotActionChannel plotActionChannel,
                                           PlotActionFactory plotActionFactory,
                                           CliStageController cliStageController,
                                           CliStageRenderer cliStageRenderer) throws IOException {

        //Read textures and configure renderers
        Properties properties = PropertiesUtils.loadProperties("/secret-title.properties");
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

        int fieldSize = Integer.parseInt(properties.getProperty("explore-station.cli.field-size"));
        TextureTemplate exploreStationTexture = CliTextureReader.readTextureTemplate("explore_station");
        CliRenderStage exploreStationRenderStage = new CliRenderStage(cliStageRenderer, fieldSize, exploreStationTexture,
                new CliPoint(1, 1), new CliPoint(100, 100), List.of(heroRender, monsterFrogRender));

        //configure physics with artificial controllers
        FromAbovePhysicsCharacter heroPhysics = new FromAbovePhysicsCharacter(cliStageController, plotActionChannel,
                plotActionFactory, heroRender);
        heroPhysics.setAttack(Integer.parseInt(properties.getProperty("warrior.physics.attack")));
        heroPhysics.setDefence(Integer.parseInt(properties.getProperty("warrior.physics.defence")));
        heroPhysics.setSpeed(Integer.parseInt(properties.getProperty("warrior.physics.speed")));
        heroPhysics.setMaxHitPoints(Integer.parseInt(properties.getProperty("warrior.physics.hit-points")));

        KillHeroController monsterFrogController = new KillHeroController(heroPhysics);
        FromAbovePhysicsCharacter monsterFrogPhysics = new FromAbovePhysicsCharacter(monsterFrogController,
                plotActionChannel, plotActionFactory, monsterFrogRender);
        monsterFrogPhysics.setAttack(Integer.parseInt(properties.getProperty("monster-frog.physics.attack")));
        monsterFrogPhysics.setDefence(Integer.parseInt(properties.getProperty("monster-frog.physics.defence")));
        monsterFrogPhysics.setSpeed(Integer.parseInt(properties.getProperty("monster-frog.physics.speed")));
        monsterFrogPhysics.setMaxHitPoints(Integer.parseInt(properties.getProperty("monster-frog.physics.hit-points")));
        monsterFrogController.setCharacter(monsterFrogPhysics);

        FromAbovePhysicsStage exploreStationPhysicsStage = new FromAbovePhysicsStage(exploreStationRenderStage,
                List.of(heroPhysics, monsterFrogPhysics));

        PlotEngineCharacter hero = new PlotEngineCharacter(heroPhysics, "nigth-rambler");
        PlotEngineCharacter monsterFrog = new PlotEngineCharacter(monsterFrogPhysics, "monster-frog");

        RunStageAction runStageAction = plotActionFactory.runStageAction();
        DoNothingAction doNothingAction = plotActionFactory.getDoNothingAction();
        FinishStageAction finishStageAction = plotActionFactory.finishStageAction();
        CharacterKilledCharacterAction killMonsterFrogAction =
                plotActionFactory.characterKilledCharacterAction(hero, monsterFrog);
        HeroDiedAction heroDiedAction = plotActionFactory.heroDiedAction();

        String initStory = properties.getProperty("explore-station.plot.init-story");
        PlotEngineEvent startStageEvent = new PlotEngineEvent(initStory, runStageAction, doNothingAction);
        String finalStory = properties.getProperty("explore-station.plot.final-story");
        PlotEngineEvent killMonsterFrogEvent = new PlotEngineEvent(finalStory, killMonsterFrogAction, finishStageAction);
        String failStory = properties.getProperty("explore-station.plot.fail-story");
        PlotEngineEvent heroDiedEvent = new PlotEngineEvent(failStory, heroDiedAction, finishStageAction);

        PlotEngineStage exploreStation = new PlotEngineStage(exploreStationPhysicsStage, hero,
                List.of(startStageEvent, killMonsterFrogEvent, heroDiedEvent), plotActionFactory);
        exploreStationRenderStage.setPlotStage(exploreStation);

        return new PlotEngine(hero, plotActionChannel, List.of(exploreStation));
    }
}
