package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.furryfaust.patterns.Core;

public class LoginScreen implements Screen {

    Core core;
    SpriteBatch batch;
    BitmapFont font;
    String tempUserStore, tempPassStore;
    int usernameInputX, usernameInputY, usernameInputWidth,
            usernameInputHeight, passwordInputX, passwordInputY,
            passwordInputWidth, passwordInputHeight, loginX, loginY,
            loginWidth, loginHeight, usernameX, usernameY;

    public LoginScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"));
        font.setScale(2F * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
        Gdx.input.setOnscreenKeyboardVisible(true);
    }

    @Override
    public void show() {
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;
        tempUserStore = tempPassStore = "";
        usernameInputWidth = (int) ((double) core.assets.textInput.getWidth() * multiplier * 5D);
        usernameInputHeight = (int) ((double) core.assets.textInput.getHeight() * multiplier * 3.5D);
        usernameInputX = Gdx.graphics.getWidth() / 2 - usernameInputWidth / 2;
        usernameInputY = Gdx.graphics.getHeight() / 2 + usernameInputHeight;
        usernameX = usernameInputX + (int) ((double) usernameInputX * (2D / 50D));
        usernameY = usernameInputY;
        Gdx.input.setInputProcessor(new InputManager());
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(core.assets.textInput, usernameInputX, usernameInputY, usernameInputWidth, usernameInputHeight);
        batch.draw(core.assets.textInput, passwordInputX, passwordInputY, passwordInputWidth, passwordInputHeight);
        batch.draw(core.assets.loginButton, loginX, loginY, loginWidth, loginHeight);

        font.draw(batch, tempUserStore, usernameX, usernameY);
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

    class InputManager implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            Gdx.app.log("Key Code", String.valueOf(keycode));
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            if (character >= 'a' && character <= 'z') {
                tempUserStore += character;
            }
            if (character == '\b') {
                tempUserStore = tempUserStore.substring(0, tempUserStore.length() - 1);
            }
            if (character == '\n' || character == '\r') {
                Gdx.input.setOnscreenKeyboardVisible(false);
            }
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
