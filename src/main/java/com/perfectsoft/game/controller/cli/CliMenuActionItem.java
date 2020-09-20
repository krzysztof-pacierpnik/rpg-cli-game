package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;

import java.util.function.Consumer;

public class CliMenuActionItem<T extends GameController> implements CliMenuItem<T> {

    private final String cliCommand;
    private final String optionName;
    private final Consumer<T> command;

    public CliMenuActionItem(String cliCommand, String optionName, Consumer<T> command) {
        this.cliCommand = cliCommand;
        this.optionName = optionName;
        this.command = command;
    }

    public String getCliCommand() {
        return cliCommand;
    }

    public String getOptionName() {
        return optionName;
    }

    public Consumer<T> getCommand() {
        return command;
    }
}
