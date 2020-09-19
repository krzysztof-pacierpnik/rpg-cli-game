package com.perfectsoft.game.secret.title.conf;

import com.perfectsoft.game.controller.cli.CliMainGameController;
import com.perfectsoft.game.controller.cli.CliMenu;
import com.perfectsoft.game.controller.cli.CliMainGameStageController;
import com.perfectsoft.game.controller.cli.conf.CliMenuConf;
import com.perfectsoft.game.render.MenuRenderer;
import com.perfectsoft.game.render.cli.conf.CliRenderConf;
import com.perfectsoft.game.secret.title.plot.SecretPlot;

public final class SecretTitleConf {

    private static final String SECRET_TITLE = "secret_title";

    private SecretTitleConf() {}

    public static CliMainGameController createGameController() {

        CliMenu cliMenu = CliMenuConf.createMenu();
        MenuRenderer menuRenderer = CliRenderConf.createMenuRenderer();
        CliMainGameStageController cliMainGameStageController = new CliMainGameStageController();
        //TODO configure plot
        SecretPlot secretPlot = new SecretPlot();

        return new CliMainGameController(cliMenu, menuRenderer, cliMainGameStageController, secretPlot);
    }
}
