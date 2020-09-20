package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.controller.GameController;

import java.util.Map;
import java.util.function.Consumer;

public class CliMenu<T extends GameController> {

    private final Map<String, CliMenuSection<T>> sectionMap;
    private CliMenuSection<T> currentSection;

    public CliMenu(CliMenuSection<T> currentSection, Map<String, CliMenuSection<T>> sectionMap) {
        this.sectionMap = sectionMap;
        this.currentSection = currentSection;
    }

    Consumer<T> get(String input) {
        return currentSection.get(input);
    }

    public CliMenuSection<T> getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(String name) {
        CliMenuSection<T> section = sectionMap.get(name);
        if (section != null) {
            currentSection = section;
        }
    }
}
