package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.plot.Plot;

import java.util.function.Consumer;

public interface CliMenuItem<T extends GameController> {

    String getCliCommand();

    String getOptionName();

    Consumer<T> getCommand();
}
