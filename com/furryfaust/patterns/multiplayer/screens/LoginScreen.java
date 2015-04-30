package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
            loginWidth, loginHeight, usernameX, usernameY, passwordX,
            passwordY;
    OrthographicCamera camera;

    public LoginScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"));
        font.setScale(2F * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        camera.position.set(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 0);
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;
        tempUserStore = tempPassStore = "";
        focus = 2;
        usernameInputWidth = (int) ((double) core.assets.textInput.getWidth() * multiplier * 5D);
        usernameInputHeight = (int) ((double) core.assets.textInput.getHeight() * multiplier * 3.5D);
        usernameInputX = Gdx.graphics.getWidth() / 2 - usernameInputWidth / 2;
        usernameInputY = Gdx.graphics.getHeight() / 2 + (3 * usernameInputHeight);
        usernameX = usernameInputX + (int) ((double) usernameInputX * (8D / 50D));
        usernameY = usernameInputY + (int) ((double) usernameInputY * (1D / 18D));
        passwordInputWidth = usernameInputWidth;
        passwordInputHeight = usernameInputHeight;
        passwordInputX = Gdx.graphics.getWidth() / 2 - passwordInputWidth / 2;
        passwordInputY = Gdx.graphics.getHeight() / 2 + (int) (1.75D * (double) passwordInputHeight);
        passwordX = passwordInputX + (int) ((double) passwordInputX * (8D / 50D));
        passwordY = passwordInputY + (int) ((double) passwordInputY * (1D / 18D));
        loginWidth = (int) ((double) core.assets.loginButton.getWidth() * multiplier * 4D);
        loginHeight = (int) ((double) core.assets.loginButton.getHeight() * multiplier * 4D);
        loginX = Gdx.graphics.getWidth() / 2 - loginWidth / 2;
        loginY = passwordInputY - (int) (1.5D * (double) loginHeight);

        Gdx.input.setInputProcessor(new InputManager());
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleTouch();

        batch.begin();
        batch.draw(core.assets.textInput, usernameInputX, usernameInputY, usernameInputWidth, usernameInputHeight);
        batch.draw(core.assets.textInput, passwordInputX, passwordInputY, passwordInputWidth, passwordInputHeight);
        batch.draw(core.assets.loginButton, loginX, loginY, loginWidth, loginHeight);

        font.draw(batch, tempUserStore, usernameX, usernameY);
        font.draw(batch, tempPassStore, passwordX, passwordY);
        batch.end();

    }

    public boolean handleTouch() {
        if (Gdx.input.isTouched()) {
            Vector3 touched = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (touched.x > usernameInputX && touched.x < usernameInputX + usernameInputWidth
                    && touched.y > usernameInputY && touched.y < usernameInputY + usernameInputHeight) {
                focus = 0;
                Gdx.input.setOnscreenKeyboardVisible(true);
                return true;
            }
            if (touched.x > passwordInputX && touched.x < passwordInputX + passwordInputWidth
                    && touched.y > passwordInputY && touched.y < passwordInputY + passwordInputHeight) {
                focus = 1;
                Gdx.input.setOnscreenKeyboardVisible(true);
                return true;
            }
            if (touched.x > loginX && touched.x < loginX + loginWidth && touched.y > loginY
                    && touched.y < loginY + loginHeight) {
                focus = 2;
                Gdx.input.setOnscreenKeyboardVisible(false);
                core.multiplayer.checkConnection(tempUserStore, tempPassStore);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (core.multiplayer.temp.startsWith("true")) {
                            core.setScreen(core.multiplayerScreen);
                        }
                    }
                }, 1F);
                return true;
            }
            focus = 2;
            Gdx.input.setOnscreenKeyboardVisible(false);
        }
        return false;
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
            if (focus == 0) {
                if ((character >= 'a' && character <= 'z') || (character >= '0' && character <= '9')) {
                    tempUserStore += character;
                    return true;
                }
                if (character == '\b' && tempUserStore.length() > 0) {
                    tempUserStore = tempUserStore.substring(0, tempUserStore.length() - 1);
                    return true;
                }
            }
            if (focus == 1) {
                if ((character >= 'a' && character <= 'z') || (character >= '0' && character <= '9')) {
                    tempPassStore += character;
                    return true;
                }
                if (character == '\b' && tempPassStore.length() > 0) {
                    tempPassStore = tempPassStore.substring(0, tempPassStore.length() - 1);
                    return true;
                }
            }
            if (character == '\n' || character == '\r') {
                focus = 2;
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
