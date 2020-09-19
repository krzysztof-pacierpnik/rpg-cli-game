package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.plot.PlotStage;

import java.util.function.Consumer;

public class PlotEngineQuest implements PlotQuest {

    private String story;
    private Consumer<PlotStage> plotAction;
    private Consumer<PlotStage> plotReaction;
    private boolean completed;

    @Override
    public String getStory() {
        return story;
    }

    public Consumer<PlotStage> getPlotAction() {
        return plotAction;
    }

    public Consumer<PlotStage> getPlotReaction() {
        return plotReaction;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
