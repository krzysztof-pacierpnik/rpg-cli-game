package com.perfectsoft.game.render;

import com.perfectsoft.game.controller.MenuSection;
import com.perfectsoft.game.plot.PlotStage;

public interface PlotRenderer {

    void renderPlotStage(PlotStage plotStage, MenuSection<?> section);
}
