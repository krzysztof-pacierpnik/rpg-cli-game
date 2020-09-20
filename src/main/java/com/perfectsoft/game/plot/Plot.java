package com.perfectsoft.game.plot;

import com.perfectsoft.game.dao.Loadable;

import java.util.Optional;

public interface Plot extends Loadable<Plot> {

    PlotStage getCurrentStage();

    PlotCharacter getPlotHero();

    Optional<PlotStage> nextStage();
}
