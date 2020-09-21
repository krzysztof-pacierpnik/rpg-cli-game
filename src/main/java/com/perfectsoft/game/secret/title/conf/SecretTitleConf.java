package com.perfectsoft.game.secret.title.conf;

import com.perfectsoft.game.conf.PlotConf;
import com.perfectsoft.game.controller.artificial.KillHeroController;
import com.perfectsoft.game.controller.cli.CliStageController;
import com.perfectsoft.game.dao.properties.FilesUtils;
import com.perfectsoft.game.physics.fromabove.FromAbovePhysicsCharacter;
import com.perfectsoft.game.physics.fromabove.FromAbovePhysicsStage;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.actions.*;
import com.perfectsoft.game.plot.engine.*;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public final class SecretTitleConf implements PlotConf {

    public PlotEngine createPhysicsAndPlot(PlotActionChannel plotActionChannel,
                                            PlotActionFactory plotActionFactory,
                                            CliStageController cliStageController) throws IOException {

        Properties properties = FilesUtils.loadProperties("/secret-title.properties");
        //configure physics with artificial controllers
        // TODO load textures and set locations
        FromAbovePhysicsCharacter heroPhysics = new FromAbovePhysicsCharacter(cliStageController, plotActionChannel,
                plotActionFactory);
        heroPhysics.setAttack(Integer.parseInt(properties.getProperty("warrior.physics.attack")));
        heroPhysics.setDefence(Integer.parseInt(properties.getProperty("warrior.physics.defence")));
        heroPhysics.setSpeed(Integer.parseInt(properties.getProperty("warrior.physics.speed")));
        heroPhysics.setMaxHitPoints(Integer.parseInt(properties.getProperty("warrior.physics.hit-points")));
        heroPhysics.setExperience(Integer.parseInt(properties.getProperty("warrior.physics.experience")));

        KillHeroController monsterFrogController = new KillHeroController(heroPhysics);
        FromAbovePhysicsCharacter monsterFrogPhysics = new FromAbovePhysicsCharacter(monsterFrogController,
                plotActionChannel, plotActionFactory);
        monsterFrogPhysics.setAttack(Integer.parseInt(properties.getProperty("monster-frog.physics.attack")));
        monsterFrogPhysics.setDefence(Integer.parseInt(properties.getProperty("monster-frog.physics.defence")));
        monsterFrogPhysics.setSpeed(Integer.parseInt(properties.getProperty("monster-frog.physics.speed")));
        monsterFrogPhysics.setMaxHitPoints(Integer.parseInt(properties.getProperty("monster-frog.physics.hit-points")));
        monsterFrogPhysics.setExperience(Integer.parseInt(properties.getProperty("monster-frog.physics.experience")));
        monsterFrogController.setCharacter(monsterFrogPhysics);

        FromAbovePhysicsStage secretTitlePhysicsStage = new FromAbovePhysicsStage(List.of(heroPhysics, monsterFrogPhysics));

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

        PlotEngineStage exploreStation = new PlotEngineStage(secretTitlePhysicsStage, hero,
                List.of(startStageEvent, killMonsterFrogEvent, heroDiedEvent), plotActionFactory);

        return new PlotEngine(hero, plotActionChannel, List.of(exploreStation));
    }
}
