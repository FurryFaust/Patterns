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

    public GameScreen(Core core) {
        this.core = core;

    }

    @Override
    public void show() {
        core.multiplayer.requestGames(core.multiplayer.usernameStore, core.multiplayer.passwordStore);
        Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                String result = core.multiplayer.temp;
                String[] ids = result.split("-");
                int min = (index - 1) * 5;
                int max = index * 5;
                String idQuery = "";
                for (; min != max + 1; min++) {
                    if (ids.length - 1 >= min) {
                        idQuery += ids[min] + (min != max ? "," : "");
                    }
                }

                if (idQuery.charAt(idQuery.length() - 1) == ',') {
                    idQuery = idQuery.substring(0, idQuery.length() - 1);
                }

                core.multiplayer.infoGames(core.multiplayer.usernameStore, core.multiplayer.passwordStore,
                        idQuery);

                Timer.schedule(new Timer.Task() {

                    @Override
                    public void run() {
                        String result = core.multiplayer.temp;
                        String[] data = result.split("<br>");
                        for (int i = 0; i != data.length; i++) {
                            gameData.add(new Match(data[i]));
                        }
                    }
                }, 1);
            }
        }, 1);
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
        public String id;
        public String expiry;

        public Match(String matchData) {
            data = new HashMap<String, String>();
            String[] separation = matchData.split("\\|");
            id = separation[0];
            expiry = separation[2];
            String unprocessed = separation[1];
            String[] players = unprocessed.split(" ");
            for (int i = 0; i != players.length; i++) {
                String player = players[i];
                String[] playerData = player.split("-");
                if (player.contains("{start}") || player.contains("{end}") || player.contains("{moves}")) {
                    data.put(playerData[0], "INCOMPLETE");
                } else if (!player.contains("{start}") && !player.contains("{end}") && !player.contains("{moves}")) {
                    int start = Integer.valueOf(playerData[1]);
                    int end = Integer.valueOf(playerData[2]);
                    int moves = Integer.valueOf(playerData[3]);

                    data.put(playerData[0], (end - start) + "|" + moves);
                }
            }
        }
    }
    
}
