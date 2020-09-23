package com.perfectsoft.game.render;

import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.plot.PlotStage;

public interface RenderStage {

    void initStage();

    void setPlotStage(PlotStage plotStage);

    void setPhysicsStage(PhysicsStage physicsStage);
}
