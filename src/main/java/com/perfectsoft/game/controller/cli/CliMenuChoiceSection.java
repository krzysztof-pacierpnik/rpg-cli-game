package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CliMenuChoiceSection<T extends GameController> implements CliMenuSection<T> {

    private final Map<String, CliMenuItem<T>> itemMap;
    private final List<CliMenuItem<T>> items;
    private String errorMessage;

    public CliMenuChoiceSection(List<CliMenuItem<T>> items) {
        this.items = items;
        this.itemMap = items.stream().collect(Collectors.toMap(CliMenuItem::getCliCommand, item -> item));
    }

    @Override
    public Consumer<T> get(String input) {
        CliMenuItem<T> item = itemMap.get(input);
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
                    CliMenuItem<T> item = items.get(idx);
                    return String.format("%d. %s (%s)", idx, item.getOptionName(), item.getCliCommand());
                })
                .collect(Collectors.toList());
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
