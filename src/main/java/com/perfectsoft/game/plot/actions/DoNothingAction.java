package com.perfectsoft.game.plot.actions;

import com.perfectsoft.game.plot.PlotStage;

import java.util.function.Consumer;

public class DoNothingAction implements Consumer<PlotStage> {

    DoNothingAction(){}

    @Override
    public void accept(PlotStage plotStage) {}
}
