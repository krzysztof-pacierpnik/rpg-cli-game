package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.plot.Plot;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface CliMenuSection {

    Consumer<CliMainGameController> get(String input);

    List<String> getItemsToRender();

    Optional<String> getErrorMessage();

    void setErrorMessage(String msg);
}
