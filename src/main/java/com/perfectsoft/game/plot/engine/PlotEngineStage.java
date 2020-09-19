package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.controller.QuestController;
import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.plot.PlotStage;

import java.util.Map;
import java.util.function.Consumer;

public class PlotEngineStage implements PlotStage, Consumer<Consumer<PlotStage>> {

    private final PhysicsStage physicsStage;
    private final Map<Consumer<PlotStage>, PlotEngineQuest> quests;
    private final QuestController questController;

    private boolean running;
    private PlotEngineQuest questToShow;

    public PlotEngineStage(Map<Consumer<PlotStage>, PlotEngineQuest> quests, PhysicsStage physicsStage,
                           QuestController questController) {

        this.quests = quests;
        this.physicsStage = physicsStage;
        this.questController = questController;
    }

    @Override
    public GameController getCurrentController() {

        GameController currentController;
        if (questToShow != null) {

            questController.setPlotQuestToShow(questToShow);
            currentController = questController;
            questToShow = null;
        } else {

            PhysicsCharacter movingCharacter = physicsStage.getMovingCharacter();
            return movingCharacter.getGameController();
        }
        return currentController;
    }

    public PhysicsStage getPhysicsStage() {
        return physicsStage;
    }

    @Override
    public void run() {
        running = true;
    }

    @Override
    public void quit() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void accept(Consumer<PlotStage> plotAction) {

        plotAction.accept(this);
        questToShow = quests.get(plotAction);
        if (questToShow != null) {
            questToShow.setCompleted(true);
        }
    }
}
