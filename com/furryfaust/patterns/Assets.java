package com.furryfaust.patterns;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Assets {

    public ArrayList<Texture> tiles = new ArrayList<Texture>();
    public Texture board = new Texture("board.png");
    public Texture trophy = new Texture("trophy.png");
    public Texture continueButton = new Texture("continue.png");
    public Texture playButton = new Texture("play.png");
    public Texture creditsButton = new Texture("credits.png");
    public Texture logButton = new Texture("log.png");
    public Texture logo = new Texture("logo.png");
    public Texture score = new Texture("score.png");

    public Assets() {
        tiles.add(null);
        for (int i = 1; i != 36; i++) {
            tiles.add(new Texture(i + ".png"));
        }
    }

}
