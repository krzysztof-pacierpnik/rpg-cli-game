package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;
import com.perfectsoft.game.controller.MenuSection;

import java.util.Map;
import java.util.function.Consumer;

public class CliMenu<T extends GameController> {

    private final Map<String, MenuSection<T>> sectionMap;
    private MenuSection<T> currentSection;

    public CliMenu(MenuSection<T> currentSection, Map<String, MenuSection<T>> sectionMap) {
        this.sectionMap = sectionMap;
        this.currentSection = currentSection;
    }

    Consumer<T> get(String input) {
        return currentSection.get(input);
    }

    public MenuSection<T> getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(String name) {
        MenuSection<T> section = sectionMap.get(name);
        if (section != null) {
            currentSection = section;
        }
    }
}
