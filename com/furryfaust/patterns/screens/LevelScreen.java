package com.furryfaust.patterns.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.furryfaust.patterns.Core;
import com.furryfaust.patterns.multiplayer.screens.GameScreen;

import java.util.ArrayList;

public class LevelScreen implements Screen {

    Core core;
    SpriteBatch batch;
    BitmapFont font;
    int buttonWidth, buttonHeight, buttonX,
            buttonY, randomX, randomY, randomWidth,
            randomHeight, easyX, easyY, easyWidth, easyHeight,
            hardX, hardY, hardWidth, hardHeight, multiplayerWidth,
            multiplayerHeight, multiplayerX, multiplayerY, loginX,
            loginY, loginWidth, loginHeight, createX, createY,
            createWidth, createHeight, welcomeX, welcomeY, signoutX,
            signoutY, signoutWidth, signoutHeight, gamesX, gamesY,
            gamesWidth, gamesHeight, inviteX, inviteY, inviteWidth,
            inviteHeight;
    public boolean loggedIn;
    boolean check;
    Timer.Task connection;

    public LevelScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"));
        font.setScale(1.9F * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        check = false;
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;
        buttonWidth = (int) ((double) core.assets.button.getWidth() * multiplier * 1.5D);
        buttonHeight = (int) ((double) core.assets.button.getHeight() * multiplier * 1.5D);
        buttonX = (int) ((double) buttonWidth / 4D);
        buttonY = Gdx.graphics.getHeight() - (int) ((double) buttonWidth * 1.25D);
        randomWidth = (int) ((double) core.assets.random.getWidth() * multiplier * 3.5D);
        randomHeight = (int) ((double) core.assets.random.getHeight() * multiplier * 3.5D);
        randomX = Gdx.graphics.getWidth() / 2 - randomWidth / 2;
        randomY = Gdx.graphics.getHeight() / 2 + (int) ((double) randomHeight * 6D);
        easyWidth = (int) ((double) core.assets.easyButton.getWidth() * multiplier * 2.5D);
        easyHeight = (int) ((double) core.assets.easyButton.getHeight() * multiplier * 2.5D);
        easyX = Gdx.graphics.getWidth() / 2 - (easyWidth * 2);
        easyY = (randomY - randomHeight) - (int) ((double) easyHeight * .65D);
        hardWidth = (int) ((double) core.assets.hardButton.getWidth() * multiplier * 2.5D);
        hardHeight = (int) ((double) core.assets.hardButton.getHeight() * multiplier * 2.5D);
        hardX = Gdx.graphics.getWidth() / 2 + (int) ((double) hardWidth * 1D);
        hardY = (randomY - randomHeight) - (int) ((double) hardHeight * .65D);
        multiplayerWidth = (int) ((double) core.assets.multiplayer2.getWidth() * multiplier * 3.5D);
        multiplayerHeight = (int) ((double) core.assets.multiplayer2.getHeight() * multiplier * 3.5D);
        multiplayerX = Gdx.graphics.getWidth() / 2 - multiplayerWidth / 2;
        multiplayerY = Gdx.graphics.getHeight() / 2 - multiplayerHeight;
        loginWidth = (int) ((double) core.assets.login2Button.getWidth() * multiplier * 2.5D);
        loginHeight = (int) ((double) core.assets.login2Button.getHeight() * multiplier * 2.5D);
        loginX = Gdx.graphics.getWidth() / 2 - (loginWidth * 2);
        loginY = (multiplayerY - multiplayerHeight) - (int) ((double) loginHeight * .65D);
        welcomeY = multiplayerY - (int) ((double) multiplayerHeight * 1D / 9D);
        createWidth = (int) ((double) core.assets.createButton.getWidth() * multiplier * 2.5D);
        createHeight = (int) ((double) core.assets.createButton.getHeight() * multiplier * 2.5D);
        createX = Gdx.graphics.getWidth() / 2 + createWidth - (int) ((6D / 21D) * (double) createWidth);
        createY = loginY;
        gamesWidth = (int) ((double) core.assets.gamesButton.getWidth() * multiplier * 2.5D);
        gamesHeight = (int) ((double) core.assets.gamesButton.getHeight() * multiplier * 2.5D);
        gamesX = Gdx.graphics.getWidth() / 2 - (gamesWidth * 2);
        gamesY = (multiplayerY - multiplayerHeight) - (int) ((double) gamesHeight * .9D);
        inviteWidth = (int) ((double) core.assets.inviteButton.getWidth() * multiplier * 2.5D);
        inviteHeight = (int) ((double) core.assets.inviteButton.getHeight() * multiplier * 2.5D);
        inviteX = Gdx.graphics.getWidth() / 2 - (int) ((double) inviteWidth * .75D);
        inviteY = (multiplayerY - multiplayerHeight) - (int) ((double) inviteHeight * .9D);
        signoutWidth = (int) ((double) core.assets.signoutButton.getWidth() * multiplier * 2.5D);
        signoutHeight = (int) ((double) core.assets.signoutButton.getHeight() * multiplier * 2.5D);
        signoutX = Gdx.graphics.getWidth() / 2 + (int) ((double) signoutWidth * .6D);
        signoutY = (multiplayerY - multiplayerHeight) - (int) ((double) signoutHeight * .9D);
        Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
        core.multiplayer.checkConnection(core.multiplayer.usernameStore, core.multiplayer.passwordStore);

        if (!loggedIn && core.multiplayer.usernameStore != "" && core.multiplayer.passwordStore != "" && check == false) {
            check = true;
            connection = Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (core.multiplayer.temp != "") {
                        loggedIn = core.multiplayer.temp.startsWith("true");
                        if (loggedIn) {
                            welcomeX = Gdx.graphics.getWidth() / 2 -
                                    (int) font.getBounds("Welcome back," + core.multiplayer.usernameStore).width / 2;
                        }
                        check = false;
                        this.cancel();
                    }
                }
            }, 1F, 1F);
        }

        if (loggedIn) {
            welcomeX = Gdx.graphics.getWidth() / 2 -
                    (int) font.getBounds("Welcome back," + core.multiplayer.usernameStore).width / 2;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(core.assets.button, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(core.assets.easyButton, easyX, easyY, easyWidth, easyHeight);
        batch.draw(core.assets.random, randomX, randomY, randomWidth, randomHeight);
        //batch.draw(core.assets.campaign, campaignX, campaignY, campaignWidth, campaignHeight);
        batch.draw(core.assets.hardButton, hardX, hardY, hardWidth, hardHeight);
        batch.draw(core.assets.multiplayer2, multiplayerX, multiplayerY, multiplayerWidth, multiplayerHeight);
        if (!loggedIn && !check) {
            batch.draw(core.assets.login2Button, loginX, loginY, loginWidth, loginHeight);
            batch.draw(core.assets.createButton, createX, createY, createWidth, createHeight);
        }
        if (loggedIn && !check) {
            font.draw(batch, "Welcome back, " + core.multiplayer.usernameStore, welcomeX, welcomeY);
            batch.draw(core.assets.inviteButton, inviteX, inviteY, inviteWidth, inviteHeight);
            batch.draw(core.assets.signoutButton, signoutX, signoutY, signoutWidth, signoutHeight);
            batch.draw(core.assets.gamesButton, gamesX, gamesY, gamesWidth, gamesHeight);
        }

        batch.end();
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
        if (connection != null && connection.isScheduled()) {
            connection.cancel();
        }
    }

    @Override
    public void dispose() {
    }

    class InputHandler implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            y = Gdx.graphics.getHeight() - y;
            if (x > buttonX && x < buttonX + buttonWidth && y > buttonY && y < buttonY + buttonHeight) {
                core.setScreen(core.startScreen);
                return true;
            }
            if (x > easyX && x < easyX + easyWidth && y > easyY && y < easyY + easyHeight) {
                core.manager.prepare(4, 50);
                core.setScreen(core.playScreen);
            }
            if (x > hardX && x < hardX + hardWidth && y > hardY && y < hardY + hardHeight) {
                core.manager.prepare(4, 10000);
                core.setScreen(core.playScreen);
            }
            if (!loggedIn && !check) {
                if (x > loginX && x < loginX + loginWidth && y > loginY && y < loginY + loginHeight) {
                    core.setScreen(core.loginScreen);
                }
                if (x > createX && x < createX + createWidth && y > createY && y < createY + createHeight) {
                    core.setScreen(core.createScreen);
                }
            }
            if (loggedIn && !check) {
                if (x > gamesX && x < gamesX + gamesWidth && y > gamesY && y < gamesY + gamesHeight) {
                    core.setScreen(core.gameScreen);
                }
                if (x > signoutX && x < signoutX + signoutWidth && y > signoutY && y < signoutY + signoutHeight) {
                    loggedIn = false;
                    core.multiplayer.usernameStore = "";
                    core.multiplayer.passwordStore = "";
                    ((GameScreen) core.gameScreen).gameData = new ArrayList<GameScreen.Match>();
                }
                if (x > inviteX && x < inviteX + inviteWidth && y > inviteY && y < inviteY + inviteHeight) {
                    core.setScreen(core.inviteScreen);
                }
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
}
