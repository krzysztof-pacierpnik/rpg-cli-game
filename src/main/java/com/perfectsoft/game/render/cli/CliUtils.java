package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenuSection;

public final class CliUtils {

    public static final String MESSAGE_TEMPLATE = "+---------------------%s------------------+\n%s\n+----------------------------------------------+\n";
    public static final String RETURN_CURSOR_SEQ = "\033[H";
    public static final String CLEAR_CONSOLE_UNDER_CURSOR_SEQ = "\033[2J";
    public static final String RETURN_CLEAR_SEQ = RETURN_CURSOR_SEQ + CLEAR_CONSOLE_UNDER_CURSOR_SEQ;

    private CliUtils() {}

    public static void printMenuWithMessage(CliMenuSection<?> cliMenuSection, String messageTitle) {

        System.out.print(CliUtils.RETURN_CLEAR_SEQ);
        cliMenuSection.getMessage()
                .ifPresent(err -> System.out.printf(CliUtils.MESSAGE_TEMPLATE, messageTitle, err));
        cliMenuSection.getItemsToRender().forEach(System.out::println);
        System.out.flush();

        cliMenuSection.setMessage(null);
    }
}
