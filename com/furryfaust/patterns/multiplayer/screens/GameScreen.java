package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.furryfaust.patterns.Core;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen implements Screen {

    public Core core;
    int index = 1;
    ArrayList<Match> gameData;
    SpriteBatch batch;


    public GameScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        refreshData();

        Gdx.input.setInputProcessor(new GestureDetector(new InputProcessor()));
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameData.isEmpty()) {
            for (Match match : gameData) {

            }
        }

    }

    public void refreshData() {
        gameData = new ArrayList<Match>();

        core.multiplayer.requestGames(core.multiplayer.usernameStore, core.multiplayer.passwordStore);
        Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                String result = core.multiplayer.temp;
                String[] ids = result.split("-");
                int min = (index - 1) * 5;
                int max = index * 5;
                String idQuery = "";
                for (; min != max; min++) {
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
                        if (!result.startsWith("false")) {
                            String[] data = result.split("<br>");
                            for (int i = 0; i != data.length; i++) {
                                gameData.add(new Match(data[i]));
                            }
                        }
                    }
                }, 1);
            }
        }, 1);
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

    class InputProcessor implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
            return false;
        }

        @Override
        public boolean longPress(float x, float y) {
            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            return false;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance) {
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            return false;
        }
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
                if (player.contains("{start}") && player.contains("{end}") && player.contains("{moves}")) {
                    data.put(playerData[0], "INCOMPLETE");
                } else if (!player.contains("{start}") && !player.contains("{end}") && !player.contains("{moves}")) {
                    int start = Integer.valueOf(playerData[1]);
                    int end = Integer.valueOf(playerData[2]);
                    int moves = Integer.valueOf(playerData[3]);

                    data.put(playerData[0], ((end - start) / 1000) + "|" + moves);
                } else if (!player.contains("{start}") && player.contains("{end}") && player.contains("{moves}")) {
                    data.put(playerData[0], "STARTED");
                }
            }
        }

        /*
               ** STATUS KEY **
               0 - FAILED
               1 - LOST DUE TO FORFEIT
               2 - WIN DUE TO FORFEIT
               3 - TIE DUE TO FORFEIT
               4 - WIN
               5 - LOST
               6 - TIE
         */

        public int getStatus() {
            String client = core.multiplayer.usernameStore;
            String opponent = getOpponent();
            boolean clientStart = data.get(client).equals("STARTED");
            boolean clientIncomplete = data.get(client).equals("INCOMPLETE");
            boolean opponentStart = data.get(opponent).equals("STARTED");
            boolean opponentIncomplete = data.get(opponent).equals("INCOMPLETE");
            if (System.currentTimeMillis() / 1000 > Integer.valueOf(expiry)) {
                if ((clientStart || clientIncomplete) && (!opponentStart && !opponentIncomplete)) {
                    return 1;
                }
                if ((!clientStart && !clientIncomplete) && (opponentStart || opponentIncomplete)) {
                    return 2;
                }
                if ((clientStart || clientIncomplete) && (opponentStart || opponentIncomplete)) {
                    return 3;
                }
            }
            if (!clientStart && !clientIncomplete && !opponentStart && !opponentIncomplete) {
                String[] clientData = data.get(client).split("\\|");
                String[] opponentData = data.get(opponent).split("\\|");
                int clientScore = Integer.valueOf(clientData[0]) * Integer.valueOf(clientData[1]);
                int opponentScore = Integer.valueOf(opponentData[0]) * Integer.valueOf(opponentData[1]);

                if (clientScore < opponentScore) {
                    return 4;
                }

                if (clientScore > opponentScore) {
                    return 5;
                }

                if (clientScore == opponentScore) {
                    return 6;
                }
            }
            return 0;
        }

        public String getOpponent() {
            return data.keySet().toArray(new String[]{})[0] == core.multiplayer.usernameStore ? data.keySet().toArray(new String[]{})[1] : data.keySet().toArray(new String[]{})[0];
        }

    }
}
