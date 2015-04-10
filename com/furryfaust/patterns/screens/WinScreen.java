package com.furryfaust.patterns.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.furryfaust.patterns.Core;

import java.util.Date;

public class WinScreen implements Screen {

    Core core;
    BitmapFont font;
    BitmapFontCache cache;
    SpriteBatch batch;

    public WinScreen(Core core) {
        this.core = core;
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.setScale(2);
        cache = new BitmapFontCache(font);
        cache.addText("Congratulations!", Gdx.graphics.getWidth() / 2 - font.getBounds("Congratulations!").width / 2,
                Gdx.graphics.getHeight());
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
        core.files.data.log(new Date(TimeUtils.millis()).toString(), core.manager.movesPerformed + "|"
                + core.manager.timePlayed);
        core.files.saveData();
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        cache.draw(batch);
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
