package com.perfectsoft.game.conf;

import com.perfectsoft.game.controller.MenuSection;
import com.perfectsoft.game.controller.cli.*;
import com.perfectsoft.game.controller.cli.conf.CliMenuConf;
import com.perfectsoft.game.dao.properties.PropertiesUtils;
import com.perfectsoft.game.plot.actions.PlotActionFactory;
import com.perfectsoft.game.plot.engine.PlotEngineActionChannel;
import com.perfectsoft.game.render.MenuRenderer;
import com.perfectsoft.game.render.PlotRenderer;
import com.perfectsoft.game.render.cli.CliMenuPrinter;
import com.perfectsoft.game.render.cli.CliMenuPrinterService;
import com.perfectsoft.game.render.cli.CliPlotPrinter;
import com.perfectsoft.game.render.cli.CliRenderMenu;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.cli.CliPoint;
import com.perfectsoft.game.texture.cli.CliStageRenderer;
import com.perfectsoft.game.texture.cli.CliTextureReader;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

public final class GameConf {

    private GameConf() {}

    public static void run(final PlotConf plotConf) {

        try(OutputStream renderStream = new BufferedOutputStream(System.out, 12000)) {

            CliMainGameController controller = createGameController(plotConf, renderStream);
            controller.run();
        } catch (IOException e) {
            throw new RuntimeException("Failed run game", e);
        }
    }

    private static CliMainGameController createGameController(final PlotConf plotConf, OutputStream renderStream)
            throws IOException {

        Properties properties = PropertiesUtils.loadProperties("/app.properties");

        //create action channels
        PlotEngineActionChannel plotEngineActionChannel = new PlotEngineActionChannel();
        PlotActionFactory plotActionFactory = PlotActionFactory.getInstance();

        //create cli controllers
        CliMenuPrinterService cliMenuPrinterService = new CliMenuPrinterService(
                properties.getProperty("render.cli.menu.message-template"));
        CliMenu<CliMainGameController> cliMenu = CliMenuConf.createMainMenu();
        MenuRenderer menuRenderer = new CliMenuPrinter(cliMenuPrinterService, "MESSAGE");
        MenuSection<CliPlotController> eventMenuSection = CliMenuConf.getEventMenuSection();
        PlotRenderer plotRenderer = new CliPlotPrinter(cliMenuPrinterService, "STORY");

        Scanner scanner = new Scanner(System.in);
        CliPlotController cliPlotController = new CliPlotController(plotRenderer, eventMenuSection, scanner);
        CliMainStageController cliMainStageController = new CliMainStageController(plotEngineActionChannel, cliPlotController);
        CliMenu<CliStageController> stageMenu = CliMenuConf.createStageMenu();
        CliStageController cliStageController = new CliStageController(scanner, stageMenu);

        Texture screenTexture = CliTextureReader.readTexture("stage_screen", new CliPoint(0,0));
        CliRenderMenu cliRenderMenu = new CliRenderMenu(stageMenu, new CliPoint(1, 111));
        int animationDelay = Integer.parseInt(properties.getProperty("render.cli.animation.duration-millis"));
        CliStageRenderer cliStageRenderer = new CliStageRenderer(screenTexture, cliRenderMenu, renderStream, animationDelay);

        return new CliMainGameController(plotEngineActionChannel, cliMenu, menuRenderer, cliMainStageController,
                cliPlotController, cliMainStageController, cliStageController, cliStageRenderer,
                plotConf, plotActionFactory, scanner);
    }
}
