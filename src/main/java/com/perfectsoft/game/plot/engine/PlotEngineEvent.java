package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.plot.PlotEvent;
import com.perfectsoft.game.plot.PlotStage;

import java.util.function.Consumer;

public class PlotEngineEvent implements PlotEvent {

    private final String story;
    private final Consumer<PlotStage> plotAction;
    private final Consumer<PlotStage> plotReaction;
    private boolean completed;

    public PlotEngineEvent(String story, Consumer<PlotStage> plotAction, Consumer<PlotStage> plotReaction) {
        this.story = story;
        this.plotAction = plotAction;
        this.plotReaction = plotReaction;
    }

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
