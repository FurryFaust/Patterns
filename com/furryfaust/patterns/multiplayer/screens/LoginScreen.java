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
import com.furryfaust.patterns.screens.LevelScreen;

public class LoginScreen implements Screen {

    Core core;
    SpriteBatch batch;
    BitmapFont font;
    String tempUserStore, tempPassStore, errorMessage;
    int focus;
    int buttonWidth, buttonHeight, buttonX,
            buttonY, usernameInputX, usernameInputY, usernameInputWidth,
            usernameInputHeight, passwordInputX, passwordInputY,
            passwordInputWidth, passwordInputHeight, loginX, loginY,
            loginWidth, loginHeight, usernameX, usernameY, passwordX,
            passwordY, errorX, errorY, multiplayerWidth, multiplayerHeight,
            multiplayerX, multiplayerY;
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
        tempUserStore = tempPassStore = errorMessage = "";
        focus = 2;
        buttonWidth = (int) ((double) core.assets.button.getWidth() * multiplier * 1.5D);
        buttonHeight = (int) ((double) core.assets.button.getHeight() * multiplier * 1.5D);
        buttonX = (int) ((double) buttonWidth / 4D);
        buttonY = Gdx.graphics.getHeight() - (int) ((double) buttonWidth * 1.25D);
        usernameInputWidth = (int) ((double) core.assets.textInput.getWidth() * multiplier * 5D);
        usernameInputHeight = (int) ((double) core.assets.textInput.getHeight() * multiplier * 3.5D);
        usernameInputX = Gdx.graphics.getWidth() / 2 - usernameInputWidth / 2;
        usernameInputY = Gdx.graphics.getHeight() / 2 + usernameInputHeight;
        usernameX = usernameInputX + (int) ((double) usernameInputX * (8D / 50D));
        usernameY = usernameInputY + (int) ((double) usernameInputY * (1D / 18D));
        passwordInputWidth = usernameInputWidth;
        passwordInputHeight = usernameInputHeight;
        passwordInputX = Gdx.graphics.getWidth() / 2 - passwordInputWidth / 2;
        passwordInputY = Gdx.graphics.getHeight() / 2 - (int) (.25D * (double) passwordInputHeight);
        passwordX = passwordInputX + (int) ((double) passwordInputX * (8D / 50D));
        passwordY = passwordInputY + (int) ((double) passwordInputY * (1.25D / 18D));
        loginWidth = (int) ((double) core.assets.loginButton.getWidth() * multiplier * 4D);
        loginHeight = (int) ((double) core.assets.loginButton.getHeight() * multiplier * 4D);
        loginX = Gdx.graphics.getWidth() / 2 - loginWidth / 2;
        loginY = passwordInputY - (int) (1.25D * (double) loginHeight);
        errorX = Gdx.graphics.getWidth() / 2 - (int) (font.getBounds(errorMessage).width / 2F);
        errorY = usernameInputY + (int) (font.getBounds(errorMessage).height * 5F);
        multiplayerWidth = (int) ((double) core.assets.multiplayer.getWidth() * multiplier * 4D);
        multiplayerHeight = (int) ((double) core.assets.multiplayer.getHeight() * multiplier * 4D);
        multiplayerX = Gdx.graphics.getWidth() / 2 - multiplayerWidth / 2;
        multiplayerY = Gdx.graphics.getHeight() / 2 + (int) (2.5D * (double) multiplayerHeight);
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
        batch.draw(core.assets.button, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(core.assets.textInput, usernameInputX, usernameInputY, usernameInputWidth, usernameInputHeight);
        batch.draw(core.assets.textInput, passwordInputX, passwordInputY, passwordInputWidth, passwordInputHeight);
        batch.draw(core.assets.loginButton, loginX, loginY, loginWidth, loginHeight);
        batch.draw(core.assets.multiplayer, multiplayerX, multiplayerY, multiplayerWidth, multiplayerHeight);

        font.draw(batch, tempUserStore, usernameX, usernameY);
        font.draw(batch, tempPassStore, passwordX, passwordY);
        font.draw(batch, errorMessage, errorX, errorY);
        batch.end();

    }

    public boolean handleTouch() {
        if (Gdx.input.isTouched()) {
            Vector3 touched = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (touched.x > usernameInputX && touched.x < usernameInputX + usernameInputWidth
                    && touched.y > usernameInputY && touched.y < usernameInputY + usernameInputHeight) {
                errorMessage = "";
                focus = 0;
                Gdx.input.setOnscreenKeyboardVisible(false);
                Gdx.input.setOnscreenKeyboardVisible(true);
                return true;
            }
            if (touched.x > passwordInputX && touched.x < passwordInputX + passwordInputWidth
                    && touched.y > passwordInputY && touched.y < passwordInputY + passwordInputHeight) {
                errorMessage = "";
                focus = 1;
                Gdx.input.setOnscreenKeyboardVisible(false);
                Gdx.input.setOnscreenKeyboardVisible(true);
                return true;
            }
            if (touched.x > buttonX && touched.x < buttonX + buttonWidth && touched.y > buttonY
                    && touched.y < buttonY + buttonHeight) {
                core.setScreen(core.levelScreen);
            }
            if (touched.x > loginX && touched.x < loginX + loginWidth && touched.y > loginY
                    && touched.y < loginY + loginHeight) {
                errorMessage = "";
                focus = 2;
                Gdx.input.setOnscreenKeyboardVisible(false);
                core.multiplayer.checkConnection(tempUserStore, tempPassStore);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (core.multiplayer.temp.startsWith("true")) {
                            core.multiplayer.usernameStore = tempUserStore;
                            core.multiplayer.passwordStore = tempPassStore;
                            ((LevelScreen) core.levelScreen).loggedIn = true;
                            core.setScreen(core.levelScreen);
                        } else {
                            errorMessage = core.multiplayer.temp;
                            errorX = Gdx.graphics.getWidth() / 2 - (int) (font.getBounds(errorMessage).width / 2F);
                            errorY = usernameInputY + (int) (font.getBounds(errorMessage).height * 5F);
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
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            if (focus == 0) {
                if (((character >= 'a' && character <= 'z') || (character >= '0' && character <= '9'))
                        && tempUserStore.length() < 16) {
                    tempUserStore += character;
                    return true;
                }
                if (character == '\b' && tempUserStore.length() > 0) {
                    tempUserStore = tempUserStore.substring(0, tempUserStore.length() - 1);
                    return true;
                }
            }
            if (focus == 1) {
                if (((character >= 'a' && character <= 'z') || (character >= '0' && character <= '9'))
                        && tempPassStore.length() < 16) {
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
