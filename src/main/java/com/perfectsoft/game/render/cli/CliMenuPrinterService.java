package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenuSection;

public final class CliMenuPrinterService {

    public static final String RETURN_CURSOR_SEQ = "\033[H";
    public static final String CLEAR_CONSOLE_UNDER_CURSOR_SEQ = "\033[2J";
    public static final String RETURN_CLEAR_SEQ = RETURN_CURSOR_SEQ + CLEAR_CONSOLE_UNDER_CURSOR_SEQ;

    private final String messageTemplate;

    public CliMenuPrinterService(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public void printMenuWithMessage(CliMenuSection<?> cliMenuSection, String messageTitle) {

        System.out.print(CliMenuPrinterService.RETURN_CLEAR_SEQ);
        cliMenuSection.getMessage()
                .ifPresent(err -> System.out.printf(messageTemplate, messageTitle, err));
        cliMenuSection.getItemsToRender().forEach(System.out::println);
        System.out.flush();

        cliMenuSection.setMessage(null);
    }
}
