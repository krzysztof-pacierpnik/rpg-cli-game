package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.plot.Plot;
import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.PlotStage;

import java.util.List;

public class PlotEngine implements Plot {

    private List<PlotStage> stages;
    private PlotEngineCharacter hero;

    @Override
    public PlotStage getInitialStage() {
        return stages.get(0);
    }

    @Override
    public PlotCharacter getPlotHero() {
        return hero;
    }

    @Override
    public PlotStage getCurrentStage() {
        return null;
    }

    public List<PlotStage> getStages() {
        return stages;
    }

    public void setStages(List<PlotStage> stages) {
        this.stages = stages;
    }

    public PlotEngineCharacter getHero() {
        return hero;
    }

    public void setHero(PlotEngineCharacter hero) {
        this.hero = hero;
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
