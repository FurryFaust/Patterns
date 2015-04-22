package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.furryfaust.patterns.Core;

public class LoginScreen implements Screen {

    Core core;
    SpriteBatch batch;
    BitmapFont font;
    String tempUserStore, tempPassStore;
    int focus;
    int usernameInputX, usernameInputY, usernameInputWidth,
            usernameInputHeight, passwordInputX, passwordInputY,
            passwordInputWidth, passwordInputHeight, loginX, loginY,
            loginWidth, loginHeight;

    public LoginScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"));
        font.setScale(2F * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;

        Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
        tempUserStore = "username";
        tempPassStore = "password";
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int i = 29; i != 55; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                if (focus == 0) {
                    tempUserStore += Input.Keys.toString(i);
                }
                if (focus == 1) {
                    tempPassStore += Input.Keys.toString(i);
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            if (focus == 0) {
                tempUserStore = tempUserStore.substring(0, tempUserStore.length() - 1);
            }
            if (focus == 1) {
                tempPassStore = tempPassStore.substring(0, tempPassStore.length() - 1);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (focus == 0 && tempUserStore.length() != 0) {
                focus++;
            }
            if (focus == 1 && tempPassStore.length() != 0) {
                focus++;
                Gdx.input.setOnscreenKeyboardVisible(false);
            }
        }

        batch.begin();
        batch.draw(core.assets.textInput, usernameInputX, usernameInputY, usernameInputWidth, usernameInputHeight);
        batch.draw(core.assets.textInput, passwordInputX, passwordInputY, passwordInputWidth, passwordInputHeight);
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
            if (x > usernameInputX && x < usernameInputX + usernameInputWidth && y > usernameInputY
                    && y < usernameInputY + usernameInputHeight) {
                focus = 0;
                tempUserStore = tempUserStore == "username" ? "" : tempUserStore;
                Gdx.input.setOnscreenKeyboardVisible(true);
                return true;
            }
            if (x > passwordInputX && x < passwordInputX + passwordInputWidth && y > passwordInputY
                    && y < passwordInputY + passwordInputHeight) {
                focus = 1;
                tempPassStore = tempPassStore == "password" ? "" : tempPassStore;
                Gdx.input.setOnscreenKeyboardVisible(true);
                return true;
            }
            if (x > loginX && x < loginX + loginWidth && y > loginY && y < loginY + loginHeight) {
                core.multiplayer.checkConnection(tempUserStore, tempPassStore);
                Timer.schedule(new Timer.Task() {

                    @Override
                    public void run() {
                        if (core.multiplayer.temp) {
                            //Switch Screen
                        }
                    }

                }, 1);
            }
            focus = 2;
            tempUserStore = tempUserStore == "" ? "username" : tempUserStore;
            tempPassStore = tempPassStore == "" ? "password" : tempPassStore;
            Gdx.input.setOnscreenKeyboardVisible(false);
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
