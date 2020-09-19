package com.perfectsoft.game.plot;

import com.perfectsoft.game.dao.Loadable;

public interface Plot extends Loadable<Plot> {

    PlotStage getInitialStage();

    PlotStage getCurrentStage();

    PlotCharacter getPlotHero();
}
