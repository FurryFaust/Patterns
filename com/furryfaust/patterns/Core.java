package com.furryfaust.patterns;

import com.badlogic.gdx.Game;
import com.furryfaust.patterns.screens.PlayScreen;
import com.furryfaust.patterns.screens.StartScreen;
import com.furryfaust.patterns.screens.WinScreen;

public class Core extends Game {

    public Assets assets;
    public Manager manager;
    public Files files;
    public StartScreen startScreen;
    public PlayScreen playScreen;
    public WinScreen winScreen;

    @Override
    public void create() {
        assets = new Assets();
        manager = new Manager();
        files = new Files();
        startScreen = new StartScreen(this);
        manager.prepare(4, 100);
        playScreen = new PlayScreen(this);
        winScreen = new WinScreen(this);
        setScreen(playScreen);
    }

}