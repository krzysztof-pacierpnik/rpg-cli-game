package com.perfectsoft.game.plot.actions;

import com.perfectsoft.game.plot.PlotStage;

import java.util.function.Consumer;

public class FinishStageAction implements Consumer<PlotStage> {

    FinishStageAction(){}

    @Override
    public void accept(PlotStage plotStage) {
        plotStage.quit();
    }
}
