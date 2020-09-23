package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.controller.MenuItem;
import com.perfectsoft.game.controller.MenuSection;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CliMenuChoiceSection<T extends GameController> implements MenuSection<T> {

    private final Consumer<T> noop;
    private final Map<String, MenuItem<T>> itemMap;
    private final List<MenuItem<T>> items;
    private String errorMessage;

    public CliMenuChoiceSection(Consumer<T> noop, List<MenuItem<T>> items) {
        this.noop = noop;
        this.items = items;
        this.itemMap = items.stream().collect(Collectors.toMap(MenuItem::getCliCommand, item -> item));
    }

    @Override
    public Consumer<T> get(String input) {
        MenuItem<T> item = itemMap.get(input);
        if (item != null) {
            return item.getCommand();
        } else {
            return noop;
        }
    }

    @Override
    public List<String> getItemsToRender() {
        return IntStream
                .range(0, items.size())
                .mapToObj(idx -> {
                    MenuItem<T> item = items.get(idx);
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
