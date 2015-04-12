package com.furryfaust.patterns.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.furryfaust.patterns.Core;

public class LogScreen implements Screen {

    Core core;
    SpriteBatch batch;
    int easyX, easyY, easyWidth, easyHeight,
            hardX, hardY, hardWidth, hardHeight,
            buttonWidth, buttonHeight, buttonX,
            buttonY, dateX, timeX, moveX, easyStartY,
            hardDistance, hardStartY, easyDistance;
    BitmapFont font;

    public LogScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    @Override
    public void show() {
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;
        buttonWidth = (int) ((double) core.assets.button.getWidth() * multiplier * 1.5D);
        buttonHeight = (int) ((double) core.assets.button.getHeight() * multiplier * 1.5D);
        buttonX = (int) ((double) buttonWidth / 4D);
        buttonY = Gdx.graphics.getHeight() - (int) ((double) buttonWidth * 1.25D);
        easyWidth = (int) ((double) core.assets.easyHistory.getWidth() * multiplier * 1.3D);
        easyHeight = (int) ((double) core.assets.easyHistory.getHeight() * multiplier * 1.3D);
        easyX = Gdx.graphics.getWidth() / 2 - (easyWidth / 2);
        easyY = Gdx.graphics.getHeight() / 2 - (int) (12D * multiplier);
        hardWidth = (int) ((double) core.assets.hardHistory.getWidth() * multiplier * 1.3D);
        hardHeight = (int) ((double) core.assets.hardHistory.getHeight() * multiplier * 1.3D);
        hardX = Gdx.graphics.getWidth() / 2 - (hardWidth / 2);
        hardY = Gdx.graphics.getHeight() / 2 - hardHeight - (int) (3D * multiplier) - (int) (12D * multiplier);
        dateX = easyX + (int) ((8D / 206D) * (double) easyWidth);
        timeX = easyX + (int) ((120D / 206D) * (double) easyWidth);
        moveX = easyX + (int) ((169D / 206D) * (double) easyWidth);
        easyStartY = easyY + (int) ((124D / 156D) * (double) easyHeight);
        hardStartY = hardY + (int) ((124D / 156D) * (double) hardHeight);
        easyDistance = (int) ((10D / 156D) * (double) easyHeight);
        hardDistance = (int) ((10D / 156D) * (double) hardHeight);
        font.setScale(1.3F * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(core.assets.button, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(core.assets.easyHistory, easyX, easyY, easyWidth, easyHeight);
        batch.draw(core.assets.hardHistory, hardX, hardY, hardWidth, hardHeight);
        for (int i = 1; i != 12; i++) {
            if (core.files.data.easyHistory.size() - i > -1) {
                String easy = core.files.data.easyHistory.get(core.files.data.easyHistory.size() - i);
                String[] split = easy.split("\\|");
                String date = split[0];
                String time = split[1];
                String moves = split[2];
                font.draw(batch, date, dateX, easyStartY - (i * easyDistance));
                font.draw(batch, time, timeX, easyStartY - (i * easyDistance));
                font.draw(batch, moves, moveX, easyStartY - (i * easyDistance));
            }
            if (core.files.data.hardHistory.size() - i > -1) {
                String hard = core.files.data.hardHistory.get(core.files.data.hardHistory.size() - i);
                String[] split = hard.split("\\|");
                String date = split[0];
                String time = split[1];
                String moves = split[2];
                font.draw(batch, date, dateX, hardStartY - (i * hardDistance));
                font.draw(batch, time, timeX, hardStartY - (i * hardDistance));
                font.draw(batch, moves, moveX, hardStartY - (i * hardDistance));
            }
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
