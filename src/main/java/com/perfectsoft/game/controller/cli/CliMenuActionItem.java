package com.perfectsoft.game.controller.cli;

import java.util.function.Consumer;

public class CliMenuActionItem implements CliMenuItem {

    private final String cliCommand;
    private final String optionName;
    private final Consumer<CliMainGameController> command;

    public CliMenuActionItem(String cliCommand, String optionName, Consumer<CliMainGameController> command) {
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

    public Consumer<CliMainGameController> getCommand() {
        return command;
    }
}
