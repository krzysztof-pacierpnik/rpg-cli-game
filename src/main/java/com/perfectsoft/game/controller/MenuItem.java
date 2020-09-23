package com.perfectsoft.game.controller;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.Plot;

import java.util.function.Consumer;

public interface MenuItem<T extends GameController> {

    String getCliCommand();

    String getOptionName();

    Consumer<T> getCommand();
}
