package com.perfectsoft.game.secret.title.conf;

import com.perfectsoft.game.controller.cli.CliMainGameController;

public class SecretTitleConfRunner {

    public static void main(String[] args) {

        CliMainGameController controller = SecretTitleConf.createGameController();
        controller.run();
    }
}
