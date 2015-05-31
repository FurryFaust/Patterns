package com.furryfaust.patterns.multiplayer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.furryfaust.patterns.Core;

public class InviteScreen implements Screen {

    Core core;
    SpriteBatch batch;
    String tempInviteStore;
    BitmapFont font;
    int difficulty, focus;
    int buttonWidth, buttonHeight, buttonX,
            buttonY, easyX, easyY, easyWidth, easyHeight,
            hardX, hardY, hardWidth, hardHeight, selectWidth,
            selectHeight, inviteInputWidth, inviteInputHeight,
            inviteInputX, inviteInputY, invitedX, invitedY;

    public InviteScreen(Core core) {
        this.core = core;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"));
        font.setScale(2F * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        tempInviteStore = "";
        difficulty = 0;
        focus = 1;
        double multiplier = (double) Gdx.graphics.getWidth() / 330D;
        buttonWidth = (int) ((double) core.assets.button.getWidth() * multiplier * 1.5D);
        buttonHeight = (int) ((double) core.assets.button.getHeight() * multiplier * 1.5D);
        buttonX = (int) ((double) buttonWidth / 4D);
        buttonY = Gdx.graphics.getHeight() - (int) ((double) buttonWidth * 1.25D);
        easyWidth = (int) ((double) core.assets.easyButton.getWidth() * multiplier);
        easyHeight = (int) ((double) core.assets.easyButton.getHeight() * multiplier);
        easyWidth = (int) ((double) core.assets.easyButton.getWidth() * multiplier * 2.5D);
        easyHeight = (int) ((double) core.assets.easyButton.getHeight() * multiplier * 2.5D);
        easyX = Gdx.graphics.getWidth() / 2 - (easyWidth * 2);
        easyY = Gdx.graphics.getHeight() / 2 - (easyHeight / 2);
        hardWidth = (int) ((double) core.assets.hardButton.getWidth() * multiplier * 2.5D);
        hardHeight = (int) ((double) core.assets.hardButton.getHeight() * multiplier * 2.5D);
        hardX = Gdx.graphics.getWidth() / 2 + (int) ((double) hardWidth * 1D);
        hardY = Gdx.graphics.getHeight() / 2 - (hardHeight / 2);
        selectWidth = (int) ((double) core.assets.selectdif.getWidth() * multiplier * 2.5D);
        selectHeight = (int) ((double) core.assets.selectdif.getHeight() * multiplier * 2.5D);
        inviteInputWidth = (int) ((double) core.assets.textInput.getWidth() * multiplier * 5D);
        inviteInputHeight = (int) ((double) core.assets.textInput.getHeight() * multiplier * 3.5D);
        inviteInputX = Gdx.graphics.getWidth() / 2 - inviteInputWidth / 2;
        inviteInputY = Gdx.graphics.getHeight() / 2 + (2 * inviteInputHeight);
        invitedX = inviteInputX + (int) ((double) inviteInputWidth * (1D / 50D));
        invitedY = inviteInputY + (int) ((double) inviteInputHeight * (9D / 18D));
        Gdx.input.setInputProcessor(new InputManager());
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(237 / 255F, 237 / 255F, 213 / 255F, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleTouch();

        batch.begin();

        int x = (difficulty == 0 ? easyX : hardX) - (int) ((double) (difficulty == 0 ? easyWidth : hardWidth) * 2D / 17D);
        int y = (difficulty == 0 ? easyY : hardY) + (difficulty == 0 ? easyHeight : hardHeight) - selectHeight
                + (int) ((double) (difficulty == 0 ? easyHeight : hardHeight) * 1.7D / 17D);
        batch.draw(core.assets.selectdif, x, y, selectWidth, selectHeight);
        batch.draw(core.assets.easyButton, easyX, easyY, easyWidth, easyHeight);
        batch.draw(core.assets.hardButton, hardX, hardY, hardWidth, hardHeight);
        batch.draw(core.assets.button, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(core.assets.textInput, inviteInputX, inviteInputY, inviteInputWidth, inviteInputHeight);

        font.draw(batch, tempInviteStore, invitedX, invitedY);
        batch.end();
    }

    public void handleTouch() {
        int x = Gdx.input.getX();
        int y = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (x > buttonX && x < buttonX + buttonWidth && y > buttonY && y < buttonY + buttonHeight) {
            core.setScreen(core.levelScreen);
        }

        if (x > easyX && x < easyX + easyWidth && y > easyY && y < easyY + easyHeight) {
            difficulty = 0;
        }

        if (x > hardX && x < hardX + hardWidth && y > hardY && y < hardY + hardHeight) {
            difficulty = 1;
        }
        if (x > inviteInputX && x < inviteInputX + inviteInputWidth && y > inviteInputY && y < inviteInputY + inviteInputHeight) {
            focus = 0;
            Gdx.input.setOnscreenKeyboardVisible(true);
            return;
        }
        focus = 1;
        Gdx.input.setOnscreenKeyboardVisible(false);
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
                        && tempInviteStore.length() < 16) {
                    tempInviteStore += character;
                    return true;
                }
                if (character == '\b' && tempInviteStore.length() > 0) {
                    tempInviteStore = tempInviteStore.substring(0, tempInviteStore.length() - 1);
                    return true;
                }
            }
            if (character == '\n' || character == '\r') {
                focus = 1;
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
