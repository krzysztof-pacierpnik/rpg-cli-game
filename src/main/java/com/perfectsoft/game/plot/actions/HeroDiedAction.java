package com.perfectsoft.game.plot.actions;

import com.perfectsoft.game.plot.PlotStage;

import java.util.function.Consumer;

public class HeroDiedAction implements Consumer<PlotStage> {

    HeroDiedAction(){}

    @Override
    public void accept(PlotStage plotStage) {
        plotStage.heroDied();
    }
}
