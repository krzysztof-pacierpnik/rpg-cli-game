package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.MenuSection;

public final class CliMenuPrinterService {

    public static final String RETURN_CURSOR_SEQ = "\033[H";
    public static final String CLEAR_CONSOLE_UNDER_CURSOR_SEQ = "\033[2J";
    public static final String RETURN_CLEAR_SEQ = RETURN_CURSOR_SEQ + CLEAR_CONSOLE_UNDER_CURSOR_SEQ;

    private final String messageTemplate;

    public CliMenuPrinterService(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public void printMenuWithMessage(MenuSection<?> menuSection, String messageTitle) {

        System.out.print(CliMenuPrinterService.RETURN_CLEAR_SEQ);
        menuSection.getMessage()
                .ifPresent(err -> System.out.printf(messageTemplate, messageTitle, err));
        menuSection.getItemsToRender().forEach(System.out::println);
        System.out.flush();

        menuSection.setMessage(null);
    }
}
