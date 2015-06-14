package com.furryfaust.apps.patterns.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.furryfaust.apps.patterns.Core;

public class StartScreen implements Screen {

    Core core;
    SpriteBatch batch;
    int playX, playY, playWidth, playHeight,
            creditsX, creditsY, creditsWidth, creditsHeight,
            logX, logY, logWidth, logHeight, logoX, logoY,
            logoWidth, logoHeight, helpX, helpY, helpWidth,
            helpHeight;

    public StartScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        double multiplier = (double) Gdx.graphics.getWidth() / 340D;
        playWidth = (int) ((double) core.assets.playButton.getWidth() * multiplier * 5D);
        playHeight = (int) ((double) core.assets.playButton.getHeight() * multiplier * 5D);
        playX = Gdx.graphics.getWidth() / 2 - (playWidth / 2);
        playY = Gdx.graphics.getHeight() / 2 + playHeight;
        logWidth = (int) ((double) core.assets.logButton.getWidth() * multiplier * 5D);
        logHeight = (int) ((double) core.assets.logButton.getHeight() * multiplier * 5D);
        logX = Gdx.graphics.getWidth() / 2 - (logWidth / 2);
        logY = playY - playHeight - (int) ((double) logHeight * .4D);
        helpWidth = (int) ((double) core.assets.helpButton.getWidth() * multiplier * 5D);
        helpHeight = (int) ((double) core.assets.helpButton.getHeight() * multiplier * 5D);
        helpX = Gdx.graphics.getWidth() / 2 - (helpWidth / 2);
        helpY = logY - logHeight - (int) ((double) logHeight * .4D);
        creditsWidth = (int) ((double) core.assets.creditsButton.getWidth() * multiplier * 5D);
        creditsHeight = (int) ((double) core.assets.creditsButton.getHeight() * multiplier * 5D);
        creditsX = Gdx.graphics.getWidth() / 2 - (creditsWidth / 2);
        creditsY = helpY - helpHeight - (int) ((double) logHeight * .4D);
        logoWidth = (int) ((double) core.assets.logo.getWidth() * multiplier * 6D);
        logoHeight = (int) ((double) core.assets.logo.getHeight() * multiplier * 6D);
        logoX = Gdx.graphics.getWidth() / 2 - (logoWidth / 2);
        logoY = playY + playHeight + (int) ((double) logHeight * .4D);
        Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(core.assets.playButton, playX, playY, playWidth, playHeight);
        batch.draw(core.assets.creditsButton, creditsX, creditsY, creditsWidth, creditsHeight);
        batch.draw(core.assets.logButton, logX, logY, logWidth, logHeight);
        batch.draw(core.assets.logo, logoX, logoY, logoWidth, logoHeight);
        batch.draw(core.assets.helpButton, helpX, helpY, helpWidth, helpHeight);
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
    }

    @Override
    public void dispose() {

    }

    class InputHandler implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            y = Gdx.graphics.getHeight() - y;
            if (x > playX && x < playX + playWidth && y > playY && y < playY + playHeight) {
                core.setScreen(core.levelScreen);
                return true;
            }
            if (x > logX && x < logX + logWidth && y > logY && y < logY + logHeight) {
                core.setScreen(core.logScreen);
                return true;
            }
            if (x > helpX && x < helpX + helpWidth && y > helpY && y < helpY + helpHeight) {
                core.setScreen(core.helpScreen);
                return true;
            }
            if (x > creditsX && x < creditsX + creditsWidth && y > creditsY && y < creditsY + creditsHeight) {
                core.setScreen(core.creditScreen);
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
