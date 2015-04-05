package com.furryfaust.patterns.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.furryfaust.patterns.Core;

public class PlayScreen implements Screen {

    Core core;
    SpriteBatch batch;

    public PlayScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new GestureDetector(new SwipeListener()));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        int[][] tiles = core.manager.tiles;

        Gdx.graphics.getGL20().glClearColor(1, 1, 1, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (int j = 0; j != tiles.length; j++) {
            for (int i = 0; i != tiles.length; i++) {
                Texture tileSprite = core.assets.tiles.get(tiles[i][j]);
                if (tileSprite != null) {
                    int width = tileSprite.getHeight() * (Gdx.graphics.getWidth() / 640);
                    int height = tileSprite.getHeight() * (Gdx.graphics.getHeight() / 480);
                    int x = i * width + (Gdx.graphics.getWidth() / 2 - (2 * width));
                    int y = (tiles.length * height) - j * height;
                    batch.draw(tileSprite, x, y, width, height);
                }
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

    class SwipeListener implements GestureDetector.GestureListener {

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
            if (velocityX != 0) {
                if (velocityY != 0) {
                    if (Math.abs(velocityX) >= Math.abs(velocityY)) {
                        if (velocityX < 0) {
                            core.manager.shiftTile(3);
                        } else {
                            core.manager.shiftTile(2);
                        }
                    } else {
                        if (velocityY > 0) {
                            core.manager.shiftTile(1);
                        } else {
                            core.manager.shiftTile(0);
                        }
                    }
                } else {
                    if (velocityX < 0) {
                        core.manager.shiftTile(3);
                    } else {
                        core.manager.shiftTile(2);
                    }
                }
            } else if (velocityY != 0) {
                if (velocityY > 0) {
                    core.manager.shiftTile(1);
                } else {
                    core.manager.shiftTile(0);
                }
            }
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
