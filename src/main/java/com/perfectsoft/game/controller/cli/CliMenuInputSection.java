package com.perfectsoft.game.controller.cli;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CliMenuInputSection implements CliMenuSection {

    private final String description;
    private final Consumer<CliMainGameController> controllerConsumer;
    private String errorMessage;

    public CliMenuInputSection(String description, Consumer<CliMainGameController> controllerConsumer) {
        this.description = description;
        this.controllerConsumer = controllerConsumer;
    }

    @Override
    public Consumer<CliMainGameController> get(String input) {
        return new InputConsumer(input, controllerConsumer);
    }

    private static class InputConsumer implements Consumer<CliMainGameController> {

        private final String input;
        private final Consumer<CliMainGameController> consumer;

        public InputConsumer(String input, Consumer<CliMainGameController> consumer) {
            this.input = input;
            this.consumer = consumer;
        }

        @Override
        public void accept(CliMainGameController cliMainGameController) {
            cliMainGameController.setInput(input);
            consumer.accept(cliMainGameController);
        }
    }

    @Override
    public List<String> getItemsToRender() {
        return List.of(description);
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
