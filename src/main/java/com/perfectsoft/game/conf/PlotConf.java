package com.perfectsoft.game.conf;

import com.perfectsoft.game.controller.cli.CliStageController;
import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.engine.PlotEngine;

import java.util.Properties;

public interface PlotConf {

    PlotEngine createPhysicsAndPlot(Properties properties,
                                    PlotActionChannel plotEngineActionChannel,
                                    CliStageController cliStageController);
}
