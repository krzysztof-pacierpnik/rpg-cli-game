package com.perfectsoft.game.controller;

import com.perfectsoft.game.plot.Plot;

public interface GameController {

    void setPlot(Plot plot);

    void setInput(String input);

    void run();
}
