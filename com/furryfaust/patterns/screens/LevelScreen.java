package com.furryfaust.patterns.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.furryfaust.patterns.Core;

public class LevelScreen implements Screen {

    Core core;
    SpriteBatch batch;
    int buttonWidth, buttonHeight, buttonX,
            buttonY, easyX, easyY, easyWidth,
            easyHeight, hardX, hardY, hardWidth,
            hardHeight;

    public LevelScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;
        buttonWidth = (int) ((double) core.assets.button.getWidth() * multiplier * 1.5D);
        buttonHeight = (int) ((double) core.assets.button.getHeight() * multiplier * 1.5D);
        buttonX = (int) ((double) buttonWidth / 4D);
        buttonY = Gdx.graphics.getHeight() - (int) ((double) buttonWidth * 1.25D);
        easyWidth = (int) ((double) core.assets.easyButton.getWidth() * multiplier * 3.5D);
        easyHeight = (int) ((double) core.assets.easyButton.getHeight() * multiplier * 3.5D);
        easyX = Gdx.graphics.getWidth() / 2 - (int) (1.5D * (double) easyWidth);
        easyY = Gdx.graphics.getHeight() / 2 + (int) (1.5D * (double) easyHeight);
        hardWidth = (int) ((double) core.assets.hardButton.getWidth() * multiplier * 3.5D);
        hardHeight = (int) ((double) core.assets.hardButton.getHeight() * multiplier * 3.5D);
        hardX = Gdx.graphics.getWidth() / 2 + (int) (.55D * (double) hardWidth);
        hardY = Gdx.graphics.getHeight() / 2 + (int) (1.5D * (double) hardHeight);
        Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(core.assets.button, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(core.assets.easyButton, easyX, easyY, easyWidth, easyHeight);
        batch.draw(core.assets.hardButton, hardX, hardY, hardWidth, hardHeight);
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
