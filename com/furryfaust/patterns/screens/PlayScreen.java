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
    int tileWidth, tileHeight, boardWidth, boardHeight,
            boardX, boardY, trophyWidth, trophyHeight,
            trophyX, trophyY, continueWidth, continueHeight,
            continueX, continueY;

    public PlayScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new GestureDetector(new SwipeListener()));
    }

    @Override
    public void show() {
        double multiplier = (double) Gdx.graphics.getWidth() / 340D;
        tileWidth = (int) ((double) core.assets.tiles.get(1).getWidth() * multiplier);
        tileHeight = (int) ((double) core.assets.tiles.get(1).getHeight() * multiplier);
        boardWidth = (int) ((double) core.assets.board.getWidth() * multiplier);
        boardHeight = (int) ((double) core.assets.board.getWidth() * multiplier);
        boardX = Gdx.graphics.getWidth() / 2 - (2 * tileWidth) - (boardWidth / 2 - (2 * tileWidth));
        boardY = Gdx.graphics.getHeight() / 2 - (2 * tileHeight) - (boardHeight / 2 - (2 * tileHeight));
        trophyWidth = (int) ((double) core.assets.trophy.getWidth() * multiplier * 5D);
        trophyHeight = (int) ((double) core.assets.trophy.getHeight() * multiplier * 5D);
        trophyY = Gdx.graphics.getHeight() / 2 - (trophyHeight / 2);
        trophyX = Gdx.graphics.getWidth() / 2 - (trophyWidth / 2);
        continueWidth = (int) ((double) core.assets.continueButton.getWidth() * multiplier * 2D);
        continueHeight = (int) ((double) core.assets.continueButton.getHeight() * multiplier * 2D);
        continueX = Gdx.graphics.getWidth() / 2 - (continueWidth / 2);
        continueY = trophyY - continueHeight;
    }

    @Override
    public void render(float delta) {
        int[][] tiles = core.manager.tiles;

        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(core.assets.board, boardX, boardY, boardWidth, boardHeight);
        for (int j = 0; j != tiles.length; j++) {
            for (int i = 0; i != tiles.length; i++) {
                Texture tileSprite = core.assets.tiles.get(tiles[i][j]);
                if (tileSprite != null) {
                    int x = i * tileWidth + (Gdx.graphics.getWidth() / 2 - (2 * tileWidth));
                    int y = ((tiles.length - 1) * tileHeight) - j * tileHeight + ((Gdx.graphics.getHeight() / 2)
                            - (2 * tileHeight));
                    if (core.manager.shiftTask != null && core.manager.shiftTask.isScheduled()) {
                        if (core.manager.shiftTask.number == tiles[i][j]) {
                            switch (core.manager.shiftTask.direction) {
                                case 0:
                                    y += core.manager.shiftTask.getOffset();
                                    break;
                                case 1:
                                    y -= core.manager.shiftTask.getOffset();
                                    break;
                                case 2:
                                    x += core.manager.shiftTask.getOffset();
                                    break;
                                case 3:
                                    x -= core.manager.shiftTask.getOffset();
                                    break;
                            }
                        }
                    }
                    batch.draw(tileSprite, x, y, tileWidth, tileHeight);
                }
            }
        }
        if (!core.manager.checkBoard()) {
            batch.draw(core.assets.trophy, trophyX, trophyY, trophyWidth, trophyHeight);
            batch.draw(core.assets.continueButton, continueX, continueY, continueWidth, continueHeight);
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
            int speed = 8;
            if (velocityX != 0) {
                if (velocityY != 0) {
                    if (Math.abs(velocityX) >= Math.abs(velocityY)) {
                        if (velocityX < 0) {
                            core.manager.prepareShift(3, tileWidth, (float) tileWidth / speed);
                        } else {
                            core.manager.prepareShift(2, tileWidth, (float) tileWidth / speed);
                        }
                    } else {
                        if (velocityY > 0) {
                            core.manager.prepareShift(1, tileHeight, (float) tileHeight / speed);
                        } else {
                            core.manager.prepareShift(0, tileHeight, (float) tileHeight / speed);
                        }
                    }
                } else {
                    if (velocityX < 0) {
                        core.manager.prepareShift(3, tileWidth, (float) tileWidth / speed);
                    } else {
                        core.manager.prepareShift(2, tileWidth, (float) tileWidth / speed);
                    }
                }
            } else if (velocityY != 0) {
                if (velocityY > 0) {
                    core.manager.prepareShift(1, tileWidth, (float) tileWidth / speed);
                } else {
                    core.manager.prepareShift(0, tileWidth, (float) tileWidth / speed);
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
