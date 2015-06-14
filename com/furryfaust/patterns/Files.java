package com.furryfaust.patterns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class Files {

    Json json;
    public Data data;

    public Files() {
        json = new Json();
        data = new Data();
        if (!loadEasyHistory()) {
            data.easyHistory = new ArrayList<String>();
        }
        if (!loadHardHistory()) {
            data.hardHistory = new ArrayList<String>();
        }
    }

    public boolean loadEasyHistory() {
        if (Gdx.files.local("easy.dat").exists()) {
            data.easyHistory = json.fromJson(ArrayList.class, Gdx.files.local("easy.dat"));
            return true;
        }
        return false;
    }

    public boolean loadHardHistory() {
        if (Gdx.files.local("hard.dat").exists()) {
            data.hardHistory = json.fromJson(ArrayList.class, Gdx.files.local("hard.dat"));
            return true;
        }
        return false;
    }

    public void saveEasyHistory() {
        Gdx.files.local("easy.dat").writeString(json.toJson(data.easyHistory).toString(), false);
    }

    public void saveHardHistory() {
        Gdx.files.local("hard.dat").writeString(json.toJson(data.hardHistory).toString(), false);
    }


    public class Data {

        public ArrayList<String> easyHistory;
        public ArrayList<String> hardHistory;

        public void logData(int difficulty, String string) {
            switch (difficulty) {
                case 50:
                    easyHistory.add(string);
                    saveEasyHistory();
                    break;
                case 10000:
                    hardHistory.add(string);
                    saveHardHistory();
                    break;
            }
        }
    }

}
