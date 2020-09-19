package com.perfectsoft.game.render.cli;

import com.perfectsoft.game.controller.cli.CliMenuSection;
import com.perfectsoft.game.render.MenuRenderer;

public class MenuPrinter implements MenuRenderer {

    public static final String MESSAGE_TEMPLATE = "+---------------------MESSAGE------------------+\n%s\n+----------------------------------------------+\n";
    public static final String RETURN_CURSOR_SEQ = "\033[H";
    public static final String CLEAR_CONSOLE_UNDER_CURSOR_SEQ = "\033[2J";
    public static final String RETURN_CLEAR_SEQ = RETURN_CURSOR_SEQ + CLEAR_CONSOLE_UNDER_CURSOR_SEQ;

    @Override
    public void renderMenu(CliMenuSection cliMenuSection) {

        System.out.print(RETURN_CLEAR_SEQ);
        cliMenuSection
                .getErrorMessage()
                .ifPresent(err -> System.out.printf(MESSAGE_TEMPLATE, err));
        cliMenuSection.getItemsToRender().forEach(System.out::println);
        System.out.flush();

        cliMenuSection.setErrorMessage(null);
    }
}
