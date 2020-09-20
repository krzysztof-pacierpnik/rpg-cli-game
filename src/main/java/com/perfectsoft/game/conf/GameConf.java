package com.perfectsoft.game.conf;

import com.perfectsoft.game.controller.cli.*;
import com.perfectsoft.game.controller.cli.conf.CliMenuConf;
import com.perfectsoft.game.dao.properties.FilesUtils;
import com.perfectsoft.game.plot.engine.PlotEngineActionChannel;
import com.perfectsoft.game.render.MenuRenderer;
import com.perfectsoft.game.render.PlotRenderer;
import com.perfectsoft.game.render.cli.CliMenuPrinter;
import com.perfectsoft.game.render.cli.CliPlotPrinter;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public final class GameConf {

    private GameConf() {}

    public static void run(final PlotConf plotConf) throws IOException {

        CliMainGameController controller = createGameController(plotConf);
        controller.run();
    }

    private static CliMainGameController createGameController(final PlotConf plotConf) throws IOException {

        Properties properties = FilesUtils.loadProperties("/app.properties");

        //create action channels
        PlotEngineActionChannel plotEngineActionChannel = new PlotEngineActionChannel();

        //create cli controllers
        CliMenu<CliMainGameController> cliMenu = CliMenuConf.createMainMenu();
        MenuRenderer menuRenderer = new CliMenuPrinter("MESSAGE");
        CliMenuSection<CliPlotController> eventMenuSection = CliMenuConf.getEventMenuSection();
        PlotRenderer plotRenderer = new CliPlotPrinter("STORY");

        Scanner scanner = new Scanner(System.in);
        CliPlotController cliPlotController = new CliPlotController(plotRenderer, eventMenuSection, scanner);
        CliMainStageController cliMainStageController = new CliMainStageController(plotEngineActionChannel, cliPlotController);
        CliStageController cliStageController = new CliStageController(scanner, plotEngineActionChannel);


        return new CliMainGameController(properties, plotEngineActionChannel, cliMenu, menuRenderer, cliMainStageController,
                cliPlotController, cliMainStageController, cliStageController, plotConf, scanner);
    }
}
