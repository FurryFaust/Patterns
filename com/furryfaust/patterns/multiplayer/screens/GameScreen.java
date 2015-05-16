package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Timer;
import com.furryfaust.patterns.Core;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen implements Screen {

    public Core core;
    int index = 1;
    ArrayList<Match> gameData;

    public GameScreen(Core cor) {
        this.core = cor;
        core.multiplayer.requestGames(core.multiplayer.usernameStore, core.multiplayer.passwordStore);
        Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                String result = core.multiplayer.temp;
                String[] ids = result.split("-");
                int min = (index - 1) * 5;
                int max = index * 5;
                String idQuery = "";
                for (; min != max + 1; min ++) {
                  idQuery += ids[min] + (min != max ? "," : "");
                }

                core.multiplayer.infoGames(core.multiplayer.usernameStore, core.multiplayer.passwordStore,
                        idQuery);

                Timer.schedule(new Timer.Task() {

                    @Override
                    public void run() {
                        String result = core.multiplayer.temp;
                    }
                }, 1);
            }
        }, 1);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public class Match {

        public HashMap<String, String> data;
        public String expiry;

        public Match(String matchData) {
            data = new HashMap<String, String>();
            String[] expirySplit = matchData.split("\\|");
            expiry = expirySplit[1];
            String[] playerSplit = expirySplit[0].split(" ");
            String playerA = playerSplit[0].substring(playerSplit[0].indexOf("-") - 1);
            String playerB = playerSplit[1].substring(playerSplit[1].indexOf("-") - 1);
            if (!playerSplit[0].contains("{start}") && !playerSplit[0].contains("{end}") && !playerSplit[0].contains("")) {
                String[] params = playerSplit[0].split("-");
            }

        }

    }
}
