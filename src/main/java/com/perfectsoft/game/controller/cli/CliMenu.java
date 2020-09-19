package com.perfectsoft.game.controller.cli;

import com.perfectsoft.game.plot.Plot;

import java.util.Map;
import java.util.function.Consumer;

public class CliMenu {

    private final Map<String, CliMenuSection> sectionMap;
    private CliMenuSection currentSection;

    public CliMenu(CliMenuSection currentSection, Map<String, CliMenuSection> sectionMap) {
        this.sectionMap = sectionMap;
        this.currentSection = currentSection;
    }

    Consumer<CliMainGameController> get(String input) {
        return currentSection.get(input);
    }

    public CliMenuSection getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(String name) {
        CliMenuSection section = sectionMap.get(name);
        if (section != null) {
            currentSection = section;
        }
    }
}
