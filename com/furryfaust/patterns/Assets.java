package com.furryfaust.patterns;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Assets {

    public ArrayList<Texture> tiles = new ArrayList<Texture>();
    public Texture board = new Texture("board.png");

    public Assets() {
        tiles.add(null);
        for (int i = 1; i != 36; i++) {
            tiles.add(new Texture(i + ".png"));
        }
    }

}
