package com.perfectsoft.game.secret.title.conf;

import com.perfectsoft.game.conf.GameConf;

public class SecretTitleConfRunner {

    public static void main(String[] args) {

        try {
            GameConf.run(new SecretTitleConf());
        } catch (Exception e) {

            System.err.println("Exception in game:");
            e.printStackTrace();
        }
    }
}
