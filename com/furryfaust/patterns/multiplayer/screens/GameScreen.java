package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    int totalGames;
    public ArrayList<Match> gameData;
    SpriteBatch batch;
    BitmapFont font;
    int lostX, lostWidth, lostHeight,
            winX, winWidth, winHeight,
            standX, standWidth, standHeight,
            buttonWidth, buttonHeight, buttonX,
            buttonY, clientX, clientY, opponentX,
            opponentY, prevX, prevY, prevWidth,
            prevHeight, nextX, nextY, nextWidth,
            nextHeight;
    Timer.Task idsQuery, gameQuery;

    public GameScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"));
        font.setScale(2F * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        refreshData();
        index = 1;
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
        buttonWidth = (int) ((double) core.assets.button.getWidth() * multiplier * 1.5D);
        buttonHeight = (int) ((double) core.assets.button.getHeight() * multiplier * 1.5D);
        buttonX = (int) ((double) buttonWidth / 4D);
        buttonY = Gdx.graphics.getHeight() - (int) ((double) buttonWidth * 1.25D);
        clientX = standX + (int) ((50D / 206D) * (double) standWidth);
        opponentX = standX + (int) ((157D / 206D) * (double) standWidth);
        clientY = (int) (60D / 78D * (double) standHeight);
        opponentY = clientY;
        prevWidth = (int) ((double) core.assets.prevButton.getWidth() * multiplier * 1.5D);
        prevHeight = (int) ((double) core.assets.prevButton.getHeight() * multiplier * 1.5D);
        prevX = Gdx.graphics.getWidth() - (prevWidth * 2) - (int) ((double) prevWidth * .25D);
        prevY = (int) (.25D * (double) prevHeight);
        nextWidth = (int) ((double) core.assets.nextButton.getWidth() * multiplier * 1.5D);
        nextHeight = (int) ((double) core.assets.nextButton.getHeight() * multiplier * 1.5D);
        nextX = Gdx.graphics.getWidth() - nextWidth - (int) ((double) nextWidth * .25D);
        nextY = prevY;

        Gdx.input.setInputProcessor(new GestureDetector(new InputProcessor()));
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameData.isEmpty()) {
            if (Gdx.input.justTouched()) {
                Vector2 touch = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                for (int i = 0; i != gameData.size(); i++) {
                    if (touch.x > standX && touch.x < standX + standWidth
                            && touch.y > (Gdx.graphics.getHeight() - (int) (.30D * (double) standHeight)
                            - ((i + 1) * (int) ((double) standHeight * 1.05D)))
                            && touch.y < (Gdx.graphics.getHeight() - (int) (.30D * (double) standHeight)
                            - ((i + 1) * (int) ((double) standHeight * 1.05D)))
                            + standHeight) {
                        if (gameData.get(i).canStart()) {
                            final int id = Integer.valueOf(gameData.get(i).id);
                            core.multiplayer.startGame(core.multiplayer.usernameStore,
                                    core.multiplayer.passwordStore, gameData.get(i).id);

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    String board = core.multiplayer.temp;
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
        batch.draw(core.assets.prevButton, prevX, prevY, prevWidth, prevHeight);
        batch.draw(core.assets.nextButton, nextX, nextY, nextWidth, nextHeight);
        batch.draw(core.assets.button, buttonX, buttonY, buttonWidth, buttonHeight);
        if (!gameData.isEmpty()) {
            for (int i = 0; i != gameData.size(); i++) {
                Match match = gameData.get(i);
                String client = core.multiplayer.usernameStore;
                String opponent = match.getOpponent();
                int clientOffset = (int) font.getBounds(client).width / 2;
                int opponentOffset = (int) font.getBounds(opponent).width / 2;
                int matchY = Gdx.graphics.getHeight() - (int) (.30D * (double) lostHeight)
                        - ((i + 1) * (int) ((double) lostHeight * 1.05D));
                String clientMoveDisplay;
                String clientTimeDisplay;
                if (match.data.get(client).contains("|")) {
                    clientTimeDisplay = "Time: "
                            + (Integer.valueOf(match.data.get(client).split("\\|")[0]) / 60) +
                            ":" + ((Integer.valueOf(match.data.get(client).split("\\|")[0]) % 60) < 10 ? "0" : "")
                            + Integer.valueOf(match.data.get(client).split("\\|")[0]) % 60;
                    clientMoveDisplay = "Moves: " + match.data.get(client).split("\\|")[1];
                } else {
                    clientTimeDisplay = match.canStart() ? "play" : "forfeited";
                    clientMoveDisplay = "";
                }
                int clientTimeScoreOffsetX = (int) font.getBounds(clientTimeDisplay).width / 2;
                int clientTimeScoreOffsetY = (int) ((double) font.getBounds(clientTimeDisplay).height * 1.5D);
                int clientMoveScoreOffsetX = (int) font.getBounds(clientMoveDisplay).width / 2;
                int clientMoveScoreOffsetY = (int) ((double) font.getBounds(clientMoveDisplay).height * 1.5D);

                String opponentMoveDisplay;
                String opponentTimeDisplay;
                if (match.data.get(opponent).contains("|")) {
                    opponentTimeDisplay = "Time: "
                            + (Integer.valueOf(match.data.get(opponent).split("\\|")[0]) / 60) +
                            ":" + ((Integer.valueOf(match.data.get(opponent).split("\\|")[0]) % 60) < 10 ? "0" : "")
                            + Integer.valueOf(match.data.get(opponent).split("\\|")[0]) % 60;
                    opponentMoveDisplay = "Moves: " + match.data.get(opponent).split("\\|")[1];
                } else {
                    opponentTimeDisplay = match.hasExpired() ? "forfeited" : "waiting";
                    opponentMoveDisplay = "";
                }
                int opponentTimeScoreOffsetX = (int) font.getBounds(opponentTimeDisplay).width / 2;
                int opponentTimeScoreOffsetY = (int) ((double) font.getBounds(opponentTimeDisplay).height * 1.5D);
                int opponentMoveScoreOffsetX = (int) font.getBounds(opponentMoveDisplay).width / 2;
                int opponentMoveScoreOffsetY = (int) ((double) font.getBounds(opponentMoveDisplay).height * 1.5D);
                switch (match.getStatus()) {
                    case 1:
                    case 5:
                        batch.draw(core.assets.matchLost, lostX, matchY, lostWidth, lostHeight);
                        break;
                    case 2:
                    case 4:
                        batch.draw(core.assets.matchWin, winX, matchY, winWidth, winHeight);
                        break;
                    case 0:
                    case 3:
                    case 6:
                        batch.draw(core.assets.matchStand, standX, matchY, standWidth, standHeight);
                        break;
                }
                font.draw(batch, client, clientX - clientOffset, matchY + clientY);
                font.draw(batch, opponent, opponentX - opponentOffset, matchY + opponentY);
                font.draw(batch, clientTimeDisplay, clientX - clientTimeScoreOffsetX, matchY + clientY - clientTimeScoreOffsetY);
                font.draw(batch, clientMoveDisplay, clientX - clientMoveScoreOffsetX,
                        matchY + clientY - clientTimeScoreOffsetY - clientMoveScoreOffsetY);
                font.draw(batch, opponentTimeDisplay, opponentX - opponentTimeScoreOffsetX, matchY + opponentY - opponentTimeScoreOffsetY);
                font.draw(batch, opponentMoveDisplay, opponentX - opponentMoveScoreOffsetX,
                        matchY + opponentY - opponentTimeScoreOffsetY - opponentMoveScoreOffsetY);
            }
        }
        batch.end();

    }

    public void refreshData() {
        if (gameData == null) {
            gameData = new ArrayList<Match>();
        }

        core.multiplayer.requestGames(core.multiplayer.usernameStore, core.multiplayer.passwordStore);
        idsQuery = Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                if (core.multiplayer.temp != "") {
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

                    gameQuery = Timer.schedule(new Timer.Task() {

                        @Override
                        public void run() {
                            String result = core.multiplayer.temp;
                            if (!result.equals("")) {
                                if (!result.startsWith("false")) {
                                    String[] data = result.split("<br>");
                                    gameData = new ArrayList<Match>();
                                    for (int i = 0; i != data.length; i++) {
                                        gameData.add(new Match(data[i]));
                                    }
                                }
                                this.cancel();
                            }
                        }
                    }, .5F, .5F);
                    this.cancel();
                }
            }
        }, .5F, .5F);
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
        if (idsQuery != null && idsQuery.isScheduled()) {
            idsQuery.cancel();
        }
        if (gameQuery != null && gameQuery.isScheduled()) {
            gameQuery.cancel();
        }
    }

    @Override
    public void dispose() {

    }

    class InputProcessor implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            y = Gdx.graphics.getHeight() - y;
            if (x > buttonX && x < buttonX + buttonWidth
                    && y > buttonY && y < buttonY + buttonHeight) {
                core.setScreen(core.levelScreen);
                core.manager.countTask.cancel();
                return true;
            }
            if (x > prevX && x < prevX + prevWidth && y > prevY && y < prevY + prevHeight) {
                index = index != 1 ? index - 1 : index;
                refreshData();
            }
            if (x > nextX && x < nextX + nextWidth && y > nextY && y < nextY + nextHeight) {
                index = index * 4 < totalGames ? index + 1 : index;
                refreshData();
            }
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

                    data.put(playerData[0], (end - start) + "|" + moves);
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
            return data.get(core.multiplayer.usernameStore).equals("INCOMPLETE") && !hasExpired();
        }

        public boolean hasExpired() {
            return Integer.valueOf(expiry) < System.currentTimeMillis() / 1000;
        }

        public String getOpponent() {
            String[] players = data.keySet().toArray(new String[]{});
            if (players[0].equals(core.multiplayer.usernameStore)) {
                return players[1];
            }
            if (players[1].equals(core.multiplayer.usernameStore)) {
                return players[0];
            }
            return null;
        }

    }
}
