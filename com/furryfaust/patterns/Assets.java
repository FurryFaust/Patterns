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
    public Texture helpButton = new Texture("help.png");
    public Texture logo = new Texture("isolatedlogo.png");
    public Texture score = new Texture("score.png");
    public Texture button = new Texture("button.png");
    public Texture easyButton = new Texture("easy.png");
    public Texture hardButton = new Texture("hard.png");
    public Texture easyHistory = new Texture("easyhistory.png");
    public Texture hardHistory = new Texture("hardhistory.png");
    public Texture help1 = new Texture("helpwin.png");
    public Texture help2 = new Texture("help1.png");
    public Texture help3 = new Texture("help2.png");
    public Texture help4 = new Texture("help3.png");
    public Texture help5 = new Texture("help4.png");
    public Texture actualCredits = new Texture("actualcredits.png");

    public Assets() {
        tiles.add(null);
        for (int i = 1; i != 36; i++) {
            tiles.add(new Texture(i + ".png"));
        }
    }

}
