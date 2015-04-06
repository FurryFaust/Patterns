package com.furryfaust.patterns;

import com.badlogic.gdx.Game;
import com.furryfaust.patterns.screens.PlayScreen;
import com.furryfaust.patterns.screens.StartScreen;

public class Core extends Game {

    public Assets assets;
    public GameManager manager;
    StartScreen startScreen;
    PlayScreen playScreen;

    @Override
    public void create() {
        assets = new Assets();
        manager = new GameManager();
        manager.prepare(4, 100);
        startScreen = new StartScreen(this);
        playScreen = new PlayScreen(this);
        setScreen(playScreen);
    }

}