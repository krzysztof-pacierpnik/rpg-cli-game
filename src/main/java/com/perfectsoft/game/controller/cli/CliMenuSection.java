package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface CliMenuSection<T extends GameController> {

    Consumer<T> get(String input);

    List<String> getItemsToRender();

    Optional<String> getMessage();

    void setMessage(String msg);
}
