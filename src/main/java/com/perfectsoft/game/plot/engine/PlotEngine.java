package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.PlotStage;

import java.util.List;
import java.util.Optional;

public class PlotEngine implements Plot {

    private final List<PlotEngineStage> stages;
    private final PlotEngineCharacter hero;
    private final PlotActionChannel actionChannel;

    public PlotEngine(PlotEngineCharacter hero, PlotActionChannel actionChannel, List<PlotEngineStage> stages) {
        this.stages = stages;
        this.hero = hero;
        this.actionChannel = actionChannel;
    }

    private int currentStageIdx = -1;

    @Override
    public PlotCharacter getPlotHero() {
        return hero;
    }

    @Override
    public PlotStage getCurrentStage() {
        return stages.get(currentStageIdx);
    }

    @Override
    public Optional<PlotStage> nextStage() {

        if (currentStageIdx != -1) {

            PlotEngineStage currStage = stages.get(currentStageIdx);
            actionChannel.unsubscribe(currStage);
        }

        if (currentStageIdx < stages.size() - 1) {

            currentStageIdx++;
            PlotEngineStage nextStage = stages.get(currentStageIdx);
            actionChannel.subscribe(nextStage);
            return Optional.of(nextStage);
        } else {

            currentStageIdx = -1;
            return Optional.empty();
        }
    }

    @Override
    public Plot load(String saveName) {
        // TODO
        return null;
    }

    @Override
    public void save(String saveName, Plot toSave) {
        //TODO
    }
}
