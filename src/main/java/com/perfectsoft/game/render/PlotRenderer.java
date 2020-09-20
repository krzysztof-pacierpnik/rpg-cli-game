package com.perfectsoft.game.render;

import com.perfectsoft.game.controller.cli.CliMenuSection;
import com.perfectsoft.game.plot.PlotStage;

public interface PlotRenderer {

    void renderPlotStage(PlotStage plotStage, CliMenuSection<?> section);
}
