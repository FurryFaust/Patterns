package com.furryfaust.patterns;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Timer;
import com.furryfaust.patterns.multiplayer.Multiplayer;
import com.furryfaust.patterns.multiplayer.screens.LoginScreen;
import com.furryfaust.patterns.multiplayer.screens.MultiplayerScreen;
import com.furryfaust.patterns.screens.*;

public class Core extends Game {

    public Assets assets;
    public Manager manager;
    public Multiplayer multiplayer;
    public Files files;
    public Screen startScreen, playScreen, levelScreen,
            logScreen, creditScreen, helpScreen, loginScreen,
            multiplayerScreen;

    @Override
    public void create() {
        assets = new Assets();
        manager = new Manager();
        multiplayer = new Multiplayer();
        files = new Files();
        startScreen = new StartScreen(this);
        playScreen = new PlayScreen(this);
        levelScreen = new LevelScreen(this);
        logScreen = new LogScreen(this);
        creditScreen = new CreditScreen(this);
        helpScreen = new HelpScreen(this);
        loginScreen = new LoginScreen(this);
        multiplayerScreen = new MultiplayerScreen(this);
        /*if (Gdx.files.local("seenhelp").exists()) {
            setScreen(startScreen);
        } else {
            setScreen(helpScreen);
            Gdx.files.local("seenhelp").write(false);
        }*/
        multiplayer.checkConnection("Kevin_Fuck", "BloopBloop");
        setScreen(loginScreen);
        Gdx.app.log("Local Storage", Gdx.files.getLocalStoragePath());
        Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                Gdx.app.log("Connection", multiplayer.temp);
            }

        }, 1);
    }

}