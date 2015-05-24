package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.furryfaust.patterns.Core;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen implements Screen {

    public Core core;
    int index = 1;
    int totalGames;
    ArrayList<Match> gameData;
    SpriteBatch batch;
    int lostX, lostWidth, lostHeight,
            winX, winWidth, winHeight,
            standX, standWidth, standHeight;
    OrthographicCamera camera;


    public GameScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        refreshData();
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;
        lostWidth = (int) ((double) core.assets.matchLost.getWidth() * multiplier * 1.3D);
        lostHeight = (int) ((double) core.assets.matchLost.getHeight() * multiplier * 1.3D);
        lostX = Gdx.graphics.getWidth() / 2 - lostWidth / 2;
        winWidth = (int) ((double) core.assets.matchWin.getWidth() * multiplier * 1.3D);
        winHeight = (int) ((double) core.assets.matchWin.getHeight() * multiplier * 1.3D);
        winX = Gdx.graphics.getWidth() / 2 - winWidth / 2;
        standWidth = (int) ((double) core.assets.matchStand.getWidth() * multiplier * 1.3D);
        standHeight = (int) ((double) core.assets.matchStand.getHeight() * multiplier * 1.3D);
        standX = Gdx.graphics.getWidth() / 2 - standWidth / 2;

        camera.position.set(new Vector2(Gdx.graphics.getWidth() / 2,
                (Gdx.graphics.getHeight() / 2) + (int) (1.5D * (double) standHeight)), 0);
        Gdx.input.setInputProcessor(new GestureDetector(new InputProcessor()));
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameData.isEmpty()) {
            if (Gdx.input.justTouched()) {
                Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                for (int i = 0; i != gameData.size(); i++) {
                    if (touch.x > standX && touch.x < standX + standWidth
                            && touch.y > (Gdx.graphics.getHeight() - (i * (int) ((double) standHeight * 1.05D)))
                            && touch.y < (Gdx.graphics.getHeight() - (i * (int) ((double) standHeight * 1.05D)))
                            + standHeight) {
                        if (gameData.get(i).canStart()) {
                            final int id = Integer.valueOf(gameData.get(i).id);
                            core.multiplayer.startGame(core.multiplayer.usernameStore,
                                    core.multiplayer.passwordStore, gameData.get(i).id);

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    String board = core.multiplayer.temp;
                                    Gdx.app.log("Message", board);
                                    if (!board.startsWith("false")) {
                                        core.manager.prepare(board, id);
                                        core.setScreen(core.playScreen);
                                    }
                                }
                            }, 1F);
                        }
                    }
                }
            }
        }

        batch.begin();
        if (!gameData.isEmpty()) {
            for (int i = 0; i != gameData.size(); i++) {
                Match match = gameData.get(i);
                switch (match.getStatus()) {
                    case 1:
                    case 5:
                        int lostY = Gdx.graphics.getHeight() - (i * (int) ((double) lostHeight * 1.05D));
                        batch.draw(core.assets.matchLost, lostX, lostY, lostWidth, lostHeight);
                        break;
                    case 2:
                    case 4:
                        int winY = Gdx.graphics.getHeight() - (i * (int) ((double) winHeight * 1.05D));
                        batch.draw(core.assets.matchWin, winX, winY, winWidth, winHeight);
                        break;
                    case 3:
                    case 6:
                    case 0:
                        int standY = Gdx.graphics.getHeight() - (i * (int) ((double) standHeight * 1.05D));
                        batch.draw(core.assets.matchStand, standX, standY, standWidth, standHeight);
                        break;
                }
            }
        }
        batch.end();

    }

    public void refreshData() {
        gameData = new ArrayList<Match>();

        core.multiplayer.requestGames(core.multiplayer.usernameStore, core.multiplayer.passwordStore);
        Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                String result = new StringBuilder(core.multiplayer.temp).reverse().toString();
                String[] ids = result.split("-");
                totalGames = ids.length;
                int min = (index - 1) * 4;
                int max = index * 4;
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
                }, 1F);
            }
        }, 1F);
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
               0 - OTHER
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
            boolean clientStart = data.get(client).equals("STARTED"), clientIncomplete = data.get(client).equals("INCOMPLETE"),
                    opponentStart = data.get(opponent).equals("STARTED"), opponentIncomplete = data.get(opponent).equals("INCOMPLETE");
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
                String[] clientData = data.get(client).split("\\|"),
                        opponentData = data.get(opponent).split("\\|");
                int clientScore = Integer.valueOf(clientData[0]) * Integer.valueOf(clientData[1]),
                        opponentScore = Integer.valueOf(opponentData[0]) * Integer.valueOf(opponentData[1]);

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

        public boolean canStart() {
            return data.get(core.multiplayer.usernameStore).equals("INCOMPLETE");
        }

        public String getOpponent() {
            return data.keySet().toArray(new String[]{})[0] == core.multiplayer.usernameStore ?
                    data.keySet().toArray(new String[]{})[1] : data.keySet().toArray(new String[]{})[0];
        }

    }
}
