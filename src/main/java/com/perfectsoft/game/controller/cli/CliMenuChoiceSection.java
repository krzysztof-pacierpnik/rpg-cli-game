package com.perfectsoft.game.controller.cli;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CliMenuChoiceSection implements CliMenuSection {

    private final Map<String, CliMenuItem> itemMap;
    private final List<CliMenuItem> items;
    private String errorMessage;

    public CliMenuChoiceSection(List<CliMenuItem> items) {
        this.items = items;
        this.itemMap = items.stream().collect(Collectors.toMap(CliMenuItem::getCliCommand, item -> item));
    }

    @Override
    public Consumer<CliMainGameController> get(String input) {
        CliMenuItem item = itemMap.get(input);
        if (item != null) {
            return item.getCommand();
        } else {
            return (ctrl) -> {};
        }
    }

    @Override
    public List<String> getItemsToRender() {
        return IntStream
                .range(0, items.size())
                .mapToObj(idx -> {
                    CliMenuItem item = items.get(idx);
                    return String.format("%d. %s (%s)", idx, item.getOptionName(), item.getCliCommand());
                })
                .collect(Collectors.toList());
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
