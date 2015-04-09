package com.furryfaust.patterns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;

public class Files {

    public Data data;

    public Files() {
        data = new Data();
        loadData();
    }

    public boolean loadData() {
        if (Gdx.files.local("patterns.dat").exists()) {
            data.history = new Json().fromJson(HashMap.class, Gdx.files.local("patterns.dat"));
            return true;
        }
        return false;
    }

    public void saveData() {
        Gdx.files.local("patterns.dat").writeString(new Json().toJson(data.history), false);
    }


    public class Data {

        public HashMap<String, String> history;

        public Data() {
            history = new HashMap<String, String>();
        }

        public void log(String date, String score) {
            history.put(date, score);
        }

    }

}
