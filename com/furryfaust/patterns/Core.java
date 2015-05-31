package com.furryfaust.patterns;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.furryfaust.patterns.multiplayer.Multiplayer;
import com.furryfaust.patterns.multiplayer.screens.CreateScreen;
import com.furryfaust.patterns.multiplayer.screens.GameScreen;
import com.furryfaust.patterns.multiplayer.screens.InviteScreen;
import com.furryfaust.patterns.multiplayer.screens.LoginScreen;
import com.furryfaust.patterns.screens.*;

public class Core extends Game {

    public Assets assets;
    public Manager manager;
    public Multiplayer multiplayer;
    public Files files;
    public Screen startScreen, playScreen, levelScreen,
            logScreen, creditScreen, helpScreen, loginScreen,
            createScreen, gameScreen, inviteScreen;

    @Override
    public void create() {
        assets = new Assets();
        manager = new Manager();
        multiplayer = new Multiplayer(this);
        files = new Files();
        startScreen = new StartScreen(this);
        playScreen = new PlayScreen(this);
        levelScreen = new LevelScreen(this);
        logScreen = new LogScreen(this);
        creditScreen = new CreditScreen(this);
        helpScreen = new HelpScreen(this);
        loginScreen = new LoginScreen(this);
        createScreen = new CreateScreen(this);
        gameScreen = new GameScreen(this);
        inviteScreen = new InviteScreen(this);
        if (Gdx.files.local("seenhelp").exists()) {
            setScreen(startScreen);
        } else {
            setScreen(helpScreen);
            Gdx.files.local("seenhelp").write(false);
        }
        setScreen(startScreen);
    }

}