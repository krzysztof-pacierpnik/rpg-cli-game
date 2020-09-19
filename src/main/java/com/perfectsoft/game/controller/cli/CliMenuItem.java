package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.plot.Plot;

import java.util.function.Consumer;

public interface CliMenuItem {

    String getCliCommand();

    String getOptionName();

    Consumer<CliMainGameController> getCommand();
}
