package com.furryfaust.patterns.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.furryfaust.patterns.Core;

public class HelpScreen implements Screen {

    Core core;
    SpriteBatch batch;
    OrthographicCamera camera;
    int help1X, help1Y, help1Width, help1Height,
            help2X, help2Y, help2Width, help2Height,
            help3X, help3Y, help3Width, help3Height,
            help4X, help4Y, help4Width, help4Height,
            help5X, help5Y, help5Width, help5Height,
            continueX, continueY, continueWidth,
            continueHeight;


    public HelpScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        camera.position.set(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 0);
        double multiplier = (double) Gdx.graphics.getWidth() / 340D;
        help1Width = (int) ((double) core.assets.help1.getWidth() * multiplier);
        help1Height = (int) ((double) core.assets.help1.getHeight() * multiplier);
        help1X = Gdx.graphics.getWidth() / 2 - (help1Width / 2);
        help1Y = Gdx.graphics.getHeight() / 2 - (help1Height / 2);
        help2Width = (int) ((double) core.assets.help2.getWidth() * multiplier);
        help2Height = (int) ((double) core.assets.help2.getHeight() * multiplier);
        help2X = Gdx.graphics.getWidth() / 2 - (help2Width / 2) + Gdx.graphics.getWidth();
        help2Y = Gdx.graphics.getHeight() / 2 - (help2Height / 2);
        help3Width = (int) ((double) core.assets.help3.getWidth() * multiplier);
        help3Height = (int) ((double) core.assets.help3.getHeight() * multiplier);
        help3X = Gdx.graphics.getWidth() / 2 - (help3Width / 2) + (2 * Gdx.graphics.getWidth());
        help3Y = Gdx.graphics.getHeight() / 2 - (help3Height / 2);
        help4Width = (int) ((double) core.assets.help4.getWidth() * multiplier);
        help4Height = (int) ((double) core.assets.help4.getHeight() * multiplier);
        help4X = Gdx.graphics.getWidth() / 2 - (help4Width / 2) + (3 * Gdx.graphics.getWidth());
        help4Y = Gdx.graphics.getHeight() / 2 - (help4Height / 2);
        help5Width = (int) ((double) core.assets.help5.getWidth() * multiplier);
        help5Height = (int) ((double) core.assets.help5.getHeight() * multiplier);
        help5X = Gdx.graphics.getWidth() / 2 - (help5Width / 2) + (4 * Gdx.graphics.getWidth());
        help5Y = Gdx.graphics.getHeight() / 2 - (help5Height / 2);
        continueWidth = (int) ((67D / 262D) * (double) help5Width);
        continueHeight = (int) ((21D / 260D) * (double) help5Height);
        continueX = help5X + (int) ((93D / 260D) * (double) help5Width);
        continueY = help5Y + (int) ((21D / 260D) * (double) help5Height);
        Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(core.assets.help1, help1X, help1Y, help1Width, help1Height);
        batch.draw(core.assets.help2, help2X, help2Y, help2Width, help2Height);
        batch.draw(core.assets.help3, help3X, help3Y, help3Width, help3Height);
        batch.draw(core.assets.help4, help4X, help4Y, help4Width, help4Height);
        batch.draw(core.assets.help5, help5X, help5Y, help5Width, help5Height);
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
            Vector3 worldCoords = camera.unproject(new Vector3(x, y, 0));
            if (worldCoords.x > continueX && worldCoords.x < continueX + continueWidth
                    && worldCoords.y > continueY && worldCoords.y < continueY + continueHeight) {
                core.setScreen(core.startScreen);
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
            if (deltaX < 0) {
                if (camera.position.x < (double) Gdx.graphics.getWidth() * 4.5) {
                    camera.position.set(camera.position.sub(deltaX, 0, 0));
                    return true;
                }
            }
            if (deltaX > 0) {
                if (camera.position.x > Gdx.graphics.getWidth() / 2) {
                    camera.position.set(camera.position.sub(deltaX, 0, 0));
                    return true;
                }
            }
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
