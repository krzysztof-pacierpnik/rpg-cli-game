package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.controller.MenuSection;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MenuInputSection<T extends GameController> implements MenuSection<T> {

    private final String description;
    private final Consumer<T> controllerConsumer;
    private String errorMessage;

    public MenuInputSection(String description, Consumer<T> controllerConsumer) {
        this.description = description;
        this.controllerConsumer = controllerConsumer;
    }

    @Override
    public Consumer<T> get(String input) {
        return new InputConsumer<T>(input, controllerConsumer);
    }

    private static class InputConsumer<T extends GameController> implements Consumer<T> {

        private final String input;
        private final Consumer<T> consumer;

        public InputConsumer(String input, Consumer<T> consumer) {
            this.input = input;
            this.consumer = consumer;
        }

        @Override
        public void accept(T cliMainGameController) {
            cliMainGameController.setInput(input);
            consumer.accept(cliMainGameController);
        }
    }

    @Override
    public List<String> getItemsToRender() {
        return List.of(description);
    }

    @Override
    public Optional<String> getMessage() {
        return Optional.ofNullable(errorMessage);
    }

    @Override
    public void setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
