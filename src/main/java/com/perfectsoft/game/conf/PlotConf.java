package com.perfectsoft.game.conf;

import com.perfectsoft.game.controller.cli.CliStageController;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.actions.PlotActionFactory;
import com.perfectsoft.game.plot.engine.PlotEngine;

import java.io.IOException;

public interface PlotConf {

    PlotEngine createPhysicsAndPlot(PlotActionChannel plotEngineActionChannel,
                                    PlotActionFactory plotActionFactory,
                                    CliStageController cliStageController) throws IOException;
}
